(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [buddy.sign.jws :as jws]
            [ring.util.response :as resp]
            [qlma.db.users :as user]
            [qlma.db.messages :as messages]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clj-time.core :as time]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [qlma.settings :as settings]
            [ring.middleware.cors :as cors]))

(def secret (:secret-key (settings/get-settings)))

(defn login [request]
  (let [username (get-in request [:body :username])
        password (get-in request [:body :password])
        valid? (user/valid-user? {:username username, :password password})]
    (if valid?
      (let [user-data (user/get-my-user-data {:password password
                                              :username username})
            session-data (merge {:username (keyword username)
                                 :exp (time/plus (time/now) (time/seconds 3600))}
                                user-data)
            token (jws/sign session-data secret {:alg :hs512})]
        {:status 200
         :body {:token token}})
      {:status 400
       :body {:message "Permission denied"}})))

(defn authorized-page [request message]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (resp/response message)))

(defroutes app-routes
  (GET "/" [] (resp/content-type (resp/resource-response "index.html" {:root "public"}) "text/html"))
  (POST "/login" [] login)
  (context "/messages" []
    (GET "/" request
      (let [my-id (-> request :identity :id)]
        (authorized-page request {:messages (messages/get-messages-to-user my-id)})))
    (POST "/" request
      (let [my-id (-> request :identity :id)
            to (get-in request [:body :to])
            message (get-in request [:body :message])]
        (authorized-page request {:messages (messages/send-message my-id to message)})))
    (context "/:id" [id]
      (GET "/" request
        (let [my-id (-> request :identity :id)]
          (authorized-page request {:message (messages/get-message (read-string id) my-id)})))))
  (context "/profile" []
    (GET "/" request
        (let [info (-> request :identity)]
          (authorized-page request {:message info}))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def auth-backend (jws-backend {:secret secret :options {:alg :hs512}}))

(def app
  (->
      app-routes
      (wrap-defaults api-defaults)
      (wrap-authorization auth-backend)
      (wrap-authentication auth-backend)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))
