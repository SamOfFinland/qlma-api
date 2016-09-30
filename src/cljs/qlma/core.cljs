(ns qlma.core
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as r]
            [qlma.login :as login]
            [qlma.app :as app]
            [clojure.string :as str]
            [cljs.core.async :refer  (chan put! <!)]))

(defn startpage [username]
      [:div
       [:p
        {:on-click (fn [event] (put! app/EVENTCHANNEL [:update-username "kissa"]))}
         (str "Welcome to qlma " username)]])

(defn qlma-client []
  (if-not (str/blank? (:token @app/app-state))
    [startpage (:username @app/app-state)]
    (login/form (:schoolname @app/app-state))))

(defn ^:export init []
  (r/render [qlma-client]
            (js/document.getElementById "app")))