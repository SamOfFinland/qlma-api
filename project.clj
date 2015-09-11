(defproject qlma "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [yesql "0.5.0"]
                 [migratus "0.8.4"]
                 [hiccup "1.0.5"]
                 [metosin/ring-swagger "0.21.0"]
                 [buddy/buddy-hashers "0.6.0"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [com.cemerick/friend "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.0.1"]
                 ]
  :plugins [
            [lein-ring "0.8.13"]
            [lein-environ "1.0.1"]
            ]
  :ring {:handler qlma.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
