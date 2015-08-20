(ns utils.database.migrations
  (:require [migratus.core :as migratus]
            [qlma.settings :as settings]))

(def config
  (let [db-params (-> (settings/get-settings) (:db))]
            {:store                 :database
             :migration-dir         "migrations/"
             :migration-table-name  "migration"
             :db {:classname        "com.postgresql.Driver"
                  :subprotocol      "postgresql"
                  :subname          (str "//" (:host db-params) "/" (:db db-params))
                  :user             (:user db-params)
                  :password         (:password db-params)}}))
