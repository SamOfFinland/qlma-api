(ns qlma.app
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer (chan put! <!)]))

(defonce app-state
         (r/atom
           {:token ""
            :schoolname "Sylvään koulu, Sastamala"
            :username "Testi Esa"
            :message "Please login"
            :page :home}))

(def EVENTCHANNEL (chan))

(defn dologin [username password]
  (js/console.log "DO login")
  (js/console.log (str "username:" username))
  (js/console.log (str "password: " password))
  (go (let [response (<! (http/post "http://localhost:5309/api/login" {:json-params {:username username :password password}}))]
        (swap! app-state assoc :token (:token (:body response)))
        (js/console.log (:status response))
        (let [response (:status response)]
          (if (= response 200) (put! EVENTCHANNEL [:navigate :qlma]) (put! EVENTCHANNEL [:navigate :login]))))))

(def EVENTS
  {:update-username (fn [username]
                      (swap! app-state assoc :username username))
   :login (fn [data]
            (let [[username password] data]
              (js/console.log "username:" username)
              (js/console.log "password:" password)
              (dologin username password)))


   :navigate (fn [page]
                 (swap! app-state assoc :page page))})

(go
  (while true
         (let [[event-name event-data] (<! EVENTCHANNEL)]
              ((event-name EVENTS) event-data))))
