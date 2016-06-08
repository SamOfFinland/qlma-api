(defproject qlma "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://www.qlma.fi"

  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

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
                 [ring-cors "0.1.7"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [org.clojure/clojurescript "1.8.51"
                  :scope "provided"]
                 [reagent "0.5.0-alpha3"]
                 [reagent-forms "0.4.3"]
                 [reagent-utils "0.1.2"]]
  :plugins [
            [lein-ring "0.9.7"]
            [lein-environ "1.0.1"]
            [lein-cljsbuild "1.1.3"]]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :cache-analysis true
                                        :pretty-print  true}}}}

  :figwheel
  {:http-server-root "public"
   :server-port 3449
   :nrepl-port 7002
   :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
   :css-dirs ["resources/public/css"]
   :ring-handler myproject.handler/app}

  :ring {:handler qlma.handler/app
         :init    utils.database.migrations/migrate}

  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
