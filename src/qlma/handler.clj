(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [qlma.api.root-handler :as root]
            [qlma.db.users :as user]
            ))

(defroutes app-routes
  (GET "/" [] "Hello world")
  (POST "/session" {body :body} (str (user/username-and-password-ok? body)))
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))
