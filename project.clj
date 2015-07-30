(defproject qlma "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [korma "0.4.2"]
                 [migratus "0.8.2"]
                 [mysql/mysql-connector-java "5.1.36"]
                 [selmer "0.8.7"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler qlma.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
