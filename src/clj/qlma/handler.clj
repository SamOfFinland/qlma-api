(ns qlma.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [schema.core :as s]
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

(defn login [user]
  (let [username (:username user)
        password (:password user)
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
   :headers  {}
   :body value})

(def rules [{:pattern #"^/api/(?!login$).*"
             :handler logged-user}
            {:uri "/api/login"
             :handler any-user}])

(s/defschema NewLogin
  {:username s/Str
   :password s/Str})

(def app-routes
  (api
    {:swagger
     {:ui "/api-docs"
      :spec "/swagger.json"
      :data {:info {:title "Sample API"
                    :description "Compojure Api example"}
             :tags [{:name "api", :description "some apis"}]}}}

   (GET "/" []
        (resp/content-type
         (resp/resource-response "index.html" {:root "public"}) "text/html"))
   (context "/api" []
      (POST "/login" []
            :body [user NewLogin]
            :summary "Login"
            (login user))
      (context "/messages" []
        (GET "/" request
             :header-params [authorization :- (describe String "Token")]
             (let [my-id (-> request :identity :id)]
                  (str my-id)))))))

;(defroutes app-routes
;  (GET "/" [] (resp/content-type (resp/resource-response "index.html" {:root "public"}) "text/html"))
;
;  (context "/api" []
;    (POST "/login" [] login)
;    (context "/messages" []
;      (GET "/" request
;        (let [my-id (-> request :identity :id)]
;          (resp/response {:messages (messages/get-messages-to-user my-id)})))
;      (POST "/" request
;        (let [my-id (-> request :identity :id)
;              to (get-in request [:body :to])
;              message (get-in request [:body :message])
;              subject (get-in request [:body :subject])
;              parent_id (get-in request [:body :parent_id])]
;          (resp/response {:messages (messages/send-message my-id to message subject parent_id)})))
;      (context "/sent" []
;        (GET "/" request
;          (let [my-id (-> request :identity :id)]
;            (resp/response {:messages (messages/get-messages-from-user my-id)})
;          )
;        )
;      )
;      (context "/:id" [id]
;        (GET "/" request
;          (let [my-id (-> request :identity :id)]
;            (resp/response {:message (messages/get-message (read-string id) my-id)})))
;        (GET "/replies" request
;          (let [my-id (-> request :identity :id)]
;            (resp/response {:message (messages/get-replies (read-string id) my-id)})))))
;
;    (context "/profile" []
;      (GET "/" request
;        (let [info (-> request :identity)]
;          (resp/response {:message info}))))
;    (ANY "*" [] "Not found"))
;  (route/resources "/")
;  (route/not-found "Page not found"))


(def app
  (->
   app-routes
   (acl/wrap-access-rules {:rules rules
                           :on-error on-error})
   (wrap-authentication auth-backend)
   (wrap-defaults api-defaults)))
