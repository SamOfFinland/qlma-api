(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response]]
            [qlma.db.users :as user]
            [cemerick.friend :as friend]
            [cemerick.friend.workflows :as workflows]
            ))

(defroutes app-routes
  (GET "/" [] (response "Qlma Api"))
  (GET "/login" [] "Please login")
  (GET "/secret" []
    (friend/authenticated "Logged!"))
  (route/not-found "Not Found"))

(def app
  (-> (handler/site
        (friend/authenticate app-routes {:login-uri "/login"
                                         :default-landing-uri "/"
                                         :credential-fn #(user/credential-check %)
                                         :workflows [(workflows/interactive-form)]}))
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))
