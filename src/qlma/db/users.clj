(ns qlma.db.users
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]
            [buddy.hashers :as password]))

(defn get-all-users []
  "Get all users from DB"
  (sql/select users))

(defn create-user
  [{:keys [username firstname lastname password]}]
  (sql/insert users (sql/values {:username username
                                :firstname firstname
                                :lastname lastname
                                :password (password/encrypt password)})))

(defn- get-user-password
  "Get user password from DB"
  [username]
  (-> (sql/select users (sql/fields [:password]) (sql/where {:username username})) first :password))

(defn username-and-password-ok?
  "Check that username and password match"
  [{:keys [password username]}]
  (password/check password (get-user-password username))


