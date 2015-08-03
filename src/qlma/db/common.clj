(ns qlma.db.common
  (:require [korma.db :refer :all]
            [korma.core :as sql]
            [qlma.settings :as settings]))

(defdb db (mysql (:db (settings/get-settings))))

(sql/defentity messages
  (sql/pk :id))

(sql/defentity users
  (sql/pk :id))
