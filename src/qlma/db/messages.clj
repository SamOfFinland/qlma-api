(ns qlma.db.messages
  (:require [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/messages.sql")

(defn get-all-messages []
  (select-all-messages db-spec))

(defn get-message [id my-id]
  (select-message-with-id db-spec id my-id))

(defn get-messages-to-user [user_id]
  (select-messages-to-user db-spec user_id))

(defn send-message
  ([from to message]
   (send-message from to message nil))
  ([from to message parent_id]
   (insert-new-message<! db-spec from to message parent_id)))
