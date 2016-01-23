(defproject qlma "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [yesql "0.4.2"]
                 [migratus "0.8.2"]
                 [hiccup "1.0.5"]
                 [metosin/ring-swagger "0.20.4"]
                 [buddy "0.7.2"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.0.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-cors "0.1.7"]]
  :plugins [
            [lein-ring "0.9.7"]
            [lein-environ "1.0.1"]]
  :ring {:handler qlma.handler/app
         :init    utils.database.migrations/migrate}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
