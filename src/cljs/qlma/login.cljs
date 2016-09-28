(ns qlma.login)

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
     [:input {:class "form-button" :type "submit" :value "KIRJAUDU"}]
     [:a {:href "#" :id "unohtuiko-text"} "UNOHTUIKO SALASANA"]
     ]]
   [:div {:id "school-text"}
    [:span (str schoolname)]]])
