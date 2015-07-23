(ns qlma.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [qlma.api.v1.index :as root]))

(defroutes app-routes
  (GET "/" [] (root/index))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
