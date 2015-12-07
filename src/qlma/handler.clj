(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [buddy.sign.jws :as jws]
            [ring.util.response :refer [response]]
            [qlma.db.users :as user]
            [qlma.db.messages :as messages]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clj-time.core :as time]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [qlma.settings :as settings]))

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
    {:status 200
     :body message}))


(defroutes app-routes
  (GET "/" [] (response "Qlma Api"))
  (POST "/login" [] login)
  (context "/messages" []
    (GET "/" request (authorized-page request {:messages (messages/get-messages-to-user (-> request :identity :id))})))
  (route/not-found "Not Found"))

(def auth-backend (jws-backend {:secret secret :options {:alg :hs512}}))

(def app
  (-> (handler/site app-routes)
      (wrap-authorization auth-backend)
      (wrap-authentication auth-backend)
      (middleware/wrap-json-response)
      (middleware/wrap-json-body {:keywords? true})))
