(ns qlma.settings)

;; Funktio kopioitu
;; https://github.com/Opetushallitus/clojure-utils/blob/master/src/clj/oph/common/infra/asetukset.clj
(defn read-settings-from-file
  "Lue asetukset tiedostosta"
  [path]
  (try
    (with-open [reader (clojure.java.io/reader path)]
      (doto (java.util.Properties.)
        (.load reader)))
    (catch java.io.FileNotFoundException _
      {})))

;; Funktio kopioitu
;; https://github.com/Opetushallitus/clojure-utils/blob/master/src/clj/oph/common/infra/asetukset.clj
(defn pisteavaimet->puu [m]
  (reduce #(let [[k v] %2
                 path (map keyword (.split (name k) "\\."))]
             (assoc-in %1 path v))
          {}
          m))

(def settings {:db {:host      "localhost"
                     :user      "root"
                     :password  ""
                     :db        "qlma"}})
