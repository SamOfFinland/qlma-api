(ns qlma.app
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <!)]))

(defonce app-state
         (r/atom
           {:token ""
            :schoolname "Sylvään koulu, Sastamala"
            :username "Testi Esa"
            :message "Please login"}))

(def EVENTCHANNEL (chan))

(def EVENTS
  {:update-username (fn [username]
                      (swap! app-state assoc-in [:username] username))
   :login (fn [username password]
              (js/console.log "das"))})

(go
  (while true
         (let [[event-name event-data] (<! EVENTCHANNEL)]
              ((event-name EVENTS) event-data))))
