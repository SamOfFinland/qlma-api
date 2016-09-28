(ns qlma.core
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as r]
            [qlma.login :as login]
            [clojure.string :as str]
            [cljs.core.async :refer  (chan put! <!)]))

(defonce app-state
  (r/atom
    {:token ""
     :schoolname "Sylvään koulu, Sastamala"
     :username "Testi Esa"
     :message "Please login"}))

(def EVENTCHANNEL (chan))

(def EVENTS
  {:update-username (fn [username]
                        (swap! app-state assoc-in [:username] username))})

(go
  (while true
         (let [[event-name event-data] (<! EVENTCHANNEL)]
              ((event-name EVENTS) event-data))))


(defn startpage [username]
      [:div
       [:p
        {:on-click (fn [event] (put! EVENTCHANNEL [:update-username "kissa"]))}
         (str "Welcome to qlma " username)]])

(defn qlma-client []
  (if-not (str/blank? (:token @app-state))
    [startpage (:username @app-state)]
    (login/form (:schoolname @app-state))))

(defn ^:export init []
  (r/render [qlma-client]
            (js/document.getElementById "app")))