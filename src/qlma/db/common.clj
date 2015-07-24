(ns qlma.db.common
  (:require [korma.db :refer :all]
            [korma.core :as sql]
            [qlma.settings :refer [settings]]))

(defdb db (mysql (:db settings)))

(sql/defentity messages)
