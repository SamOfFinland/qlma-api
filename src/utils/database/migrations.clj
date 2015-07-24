(ns utils.database.migrations
  (:require [migratus.core :as migratus]))

(def config {:store                 :database
             :migration-dir         "migrations/"
             :migration-table-name  "migration"
             :db {:classname        "com.mysql.jdbc.Driver"
                  :subprotocol      "mysql"
                  :subname          "//localhost/qlma"
                  :user             "root"
                  :password         ""}})
