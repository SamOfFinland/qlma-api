(ns qlma.login
  (:require [reagent.core :as r]
            [cljs.core.async :refer  (chan put! <!)]
            [qlma.app :as app]
            [clojure.string :as str]))

(defn form [schoolname]
  [:div {:id "login-container"}
   [:img {:id "logo"
          :src "img/qlma.png"
          :width 150}]
   [:div {:id "form-area"}
    [:form
     [:span {:class "form-text"} "KÄYTTÄJÄTUNNUS"]
     [:input {:class "form-input" :type "text"}]
     [:span {:class "form-text"} "SALASANA"]
     [:input {:class "form-input" :type "password"}]
     [:input
      {:class "form-button" :type "submit" :value "KIRJAUDU" :onClick #(js/console.log "ds")}]
     [:a
      {:on-click (fn [event] (.preventDefault event) (put! app/EVENTCHANNEL [:login ["kissa" "password"]])) :href "#" :id "unohtuiko-text" }
      "UNOHTUIKO SALASANA"]
     ]]
   [:div {:id "school-text"}
    [:span (str schoolname)]]])
