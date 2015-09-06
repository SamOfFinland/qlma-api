(ns qlma.db.messages
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]))

(defn get-all-messages []
  (sql/select messages))

(defn get-message [id]
  (sql/select messages
          (sql/where {:id id})))

(defn get-messages-to-user [user-id]
  (sql/select messages
          (sql/where {:to_user_id user-id})))

(defn send-message
  [from to message]
  (sql/insert messages (sql/values {:from_user_id from
                                   :to_user_id to
                                   :message message})))
