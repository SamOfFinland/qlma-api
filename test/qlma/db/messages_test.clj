(ns qlma.db.messages-test
  (:require [clojure.test :refer :all]
            [qlma.db.messages :as messages]
            [qlma.db.users :as users]
            [utils.database.migrations :as mig]
            [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/tests.sql")

(def first-user-id
  (atom 0))

(def second-user-id
  (atom 0))

(deftest test-add-message
  (testing "Check if database is clean"
    (is (= 0 (count (messages/get-all-messages)))))

  (testing "Add users"
    (is (reset! first-user-id (:id (users/create-user {:username "matti"
                                                       :password "matti1"
                                                       :firstname "Matti"
                                                       :lastname "Mattila"}))))

    (is (reset! second-user-id (:id (users/create-user {:username "matti2"
                                                        :password "matti2"
                                                        :firstname "Matti"
                                                        :lastname "Mattila"})))))

  (testing "Add message from first user to second user"
    (is (= 6 (count (messages/send-message @first-user-id @second-user-id "Hello world")))))

  (testing "Check if user has message"
    (is (= 1 (count (messages/get-messages-to-user @second-user-id)))))

  (testing "Check if database has only one message"
    (is (= 1 (count (messages/get-all-messages))))))

(defn- clean-database []
  (delete-all-messages! db-spec)
  (delete-all-users! db-spec))

(use-fixtures :once
  (fn [f]
    (mig/migrate)
    (clean-database)
    (f)))

