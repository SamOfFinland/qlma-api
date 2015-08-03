(ns qlma.db.users
  (:require [qlma.db.common :refer :all]
            [korma.core :as sql]))

(defn get-all-users []
  (sql/select users))
