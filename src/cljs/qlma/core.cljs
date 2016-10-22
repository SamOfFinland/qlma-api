(ns qlma.core
  (:require [reagent.core :as r]
            [qlma.login :as login]))

(def token (r/atom ()))

(defn qlma-client []
  (if (not (empty @token))
    "no empty"
    (login/form)))

(defn ^:export init []
  (r/render [qlma-client]
            (js/document.getElementById "app")))
