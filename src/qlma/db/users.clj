(ns qlma.db.users
  (:require [qlma.db.core :refer :all]
            [buddy.hashers :as password]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/users.sql")

(defn get-all-users []
  "Get all users from DB"
  (select-all-users db-spec))

(defn create-user
  [{:keys [username firstname lastname password]}]
  (insert-new-user<! db-spec username firstname lastname (password/encrypt password)))

(defn- get-user-password
  "Get user password from DB"
  [username]
  (-> (select-user-password db-spec username) first :password))

(defn username-and-password-ok?
  "Check that username and password match"
  [{:keys [password username]}]
  (password/check password (get-user-password username)))
