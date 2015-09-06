(ns qlma.db.users-test
  (:require [clojure.test :refer :all]
            [qlma.db.users :as users]
            [utils.database.migrations :as mig]
            [qlma.db.common :refer :all]
            [korma.core :as sql]
            ))

(deftest test-add-user
  (testing "Check if database is clean"
    (is (= 0 (count (users/get-all-users)))))

  (testing "Add user to database"
    (is (= 5 (count(users/create-user {:username "woltage"
                                      :password "jeejee"
                                      :lastname "iiro"
                                      :firstname "matti"})))))

  (testing "Password and username match"
    (is (= true (users/username-and-password-ok? {:username "woltage"
                                                  :password "jeejee"}))))

  (testing "Check user found from db"
    (is (= 1 (count (users/get-all-users))))))

(defn- clean-database []
  (sql/delete messages)
  (sql/delete users))

(use-fixtures :once
              (fn [f]
                (mig/migrate)
                (clean-database)
                (f)))


