(ns utils.database.migrations
  (:require [migratus.core :as migratus]
            [qlma.settings :as settings]))

(defn config
  ([] (config :db))
  ([d]
    (let [db-params (-> (settings/get-settings) (d))]
      {:store                 :database
       :migration-dir         "migrations/"
       :migration-table-name  "migration"
       :db {:classname        "com.postgresql.Driver"
            :subprotocol      "postgresql"
            :subname          (str "//" (:host db-params) "/" (:db db-params))
            :user             (:user db-params)
            :password         (:password db-params)}})))

(defn migrate []
  (migratus/migrate (config)))
