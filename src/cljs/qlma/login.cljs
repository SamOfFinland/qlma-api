(ns qlma.login
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer  (chan put! <!)]
            [qlma.app :as app]))



(defn input-element
  "An input element which updates its value on change"
  [id name type value]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn username-input
  [username-input-atom]
  (input-element "username" "username" "username" username-input-atom))




(defn form [schoolname]
  (let [username])
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
      {:class "form-button" :type "submit" :value "KIRJAUDU" :onClick (fn [event] (. event preventDefault) (put! app/EVENTCHANNEL [:login ["admin" "admin"]]))}]
     [:a
      {:href "#/" :id "unohtuiko-text" }
      "UNOHTUIKO SALASANA"]
     ]]
   [:div {:id "school-text"}
    [:span (str schoolname)]]])
