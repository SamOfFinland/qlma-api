(ns qlma.db.common
  (:require [korma.db :refer :all]
            [korma.core :as sql]
            [qlma.settings :as settings]))

(defdb db (mysql (:db (settings/get-settings "app.properties"))))

(sql/defentity messages)
