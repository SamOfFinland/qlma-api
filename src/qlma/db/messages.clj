(ns qlma.db.messages
  (:require [korma.core :as sql])
  (:use [qlma.db.common]))

(defn get-all-messages []
  (sql/select messages))
