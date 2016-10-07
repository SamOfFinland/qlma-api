(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [ring.util.response :as resp]
            [clj-time.core :as time]
            [buddy.sign.jws :as jws]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth.accessrules :as acl]
            [qlma.db.users :as user]
            [qlma.db.messages :as messages]
            [qlma.settings :as settings]
            [ring.middleware.cors :as cors]))

(def secret (:secret-key (settings/get-settings)))

(def auth-backend (jws-backend {:secret secret
                                :options {:alg :hs512}}))

(def req-headers
  { "Access-Control-Allow-Origin" "*"
   "Access-Control-Allow-Headers" "Content-Type"
   "Access-Control-Allow-Methods" "GET,POST,OPTIONS"
   "Content-Type" "application/json; charset=utf-8" })

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
         :headers req-headers
         :body {:token token}})
      {:status 400
       :headers req-headers
       :body {:message "Permission denied"}})))

(defn any-user
  [req]
  (acl/success))

(defn logged-user
  [req]
  (if (authenticated? req)
    true
    (acl/error {:message "Only authenticated users allowed"})))

(defn on-error
  [request value]
  {:status 403
   :headers req-headers
   :body value})

(def rules [{:pattern #"^/api/(?!login$).*"
             :handler logged-user}
            {:uri "/api/login"
             :handler any-user}])



(defroutes app-routes
  (GET "/" [] (resp/content-type (resp/resource-response "index.html" {:root "public"}) "text/html"))

  (context "/api" []
    (POST "/login" [] login)
    (context "/messages" []
      (GET "/" request
        (let [my-id (-> request :identity :id)]
          (resp/response {:messages (messages/get-messages-to-user my-id)})))
      (POST "/" request
        (let [my-id (-> request :identity :id)
              to (get-in request [:body :to])
              message (get-in request [:body :message])
              subject (get-in request [:body :subject])
              parent_id (get-in request [:body :parent_id])]
          (resp/response {:messages (messages/send-message my-id to message subject parent_id)})))
      (context "/sent" []
        (GET "/" request
          (let [my-id (-> request :identity :id)]
            (resp/response {:messages (messages/get-messages-from-user my-id)})
          )
        )
      )
      (context "/:id" [id]
        (GET "/" request
          (let [my-id (-> request :identity :id)]
            (resp/response {:message (messages/get-message (read-string id) my-id)})))
        (GET "/replies" request
          (let [my-id (-> request :identity :id)]
            (resp/response {:message (messages/get-replies (read-string id) my-id)})))))

    (context "/profile" []
      (GET "/" request
        (let [info (-> request :identity)]
          (resp/response {:message info}))))
    (ANY "*" [] "Not found"))
  (route/resources "/")
  (route/not-found "Page not found"))


(def app
  (->
   app-routes
   (acl/wrap-access-rules {:rules rules
                           :on-error on-error})
   (wrap-authentication auth-backend)
   (wrap-defaults api-defaults)
   (middleware/wrap-json-body {:keywords? true})
   (middleware/wrap-json-response)))
