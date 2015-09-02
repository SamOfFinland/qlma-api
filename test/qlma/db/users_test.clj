(ns qlma.db.users-test
  (:require [clojure.test :refer :all]
            [qlma.db.users :as users]
            [utils.database.migrations :as mig]
            [qlma.db.common :refer :all]
            [korma.core :as sql]
            ))

(deftest db-users-test
  (testing "create user"
    (is (= (count (users/get-all-users)) 0)))

  (testing "add user"
    (is (= (count (users/create-user {:username "woltage"
                                      :password "jeejee"
                                      :lastname "iiro"
                                      :firstname "matti"})) 5))))

(defn- clean-database []
  (sql/delete users))

(use-fixtures :once
              (fn [f]
                (mig/migrate)
                (clean-database)
                (f)))


