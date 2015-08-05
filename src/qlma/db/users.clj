(ns qlma.db.users
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]))

(defn get-all-users []
  "Haetaan kaikki käyttäjät käyttäjä taulusta"
  (sql/select users))

(defn create-user
  "Luodaan käyttäjä tauluun"
  [{:keys [username firstname lastname password]}]
  (sql/insert users (sql/values {:username "jee"
                                :firstname "jou"
                                :lastname "moi"
                                :password "tataa"})))
