(ns qlma.db.messages
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]))

(defn get-all-messages []
  (sql/select messages))

(defn get-message [id]
  "Hae viesti id:n perusteella"
  (sql/select messages
          (sql/where {:id id})))
