(ns qlma.db.users
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]
            [buddy.hashers :as password]))

(defn get-all-users []
  "Haetaan kaikki käyttäjät käyttäjä taulusta"
  (sql/select users))

(defn create-user
  "Luodaan käyttäjä tauluun"
  [{:keys [username firstname lastname password]}]
  (sql/insert users (sql/values {:username username
                                :firstname firstname
                                :lastname lastname
                                :password (password/encrypt password)})))

(defn user-and-password-ok?
  "Tarkista käyttäjänimi ja salasana"
  [username password]
  (password/check password (get-user-password username)))

(defn- get-user-password
  "Haetaan käyttäjän salasana tietokannasta tarkistusta varten"
  [username]
  (-> (sql/select users (sql/fields [:password]) (sql/where {:username username})) first :password))

