(ns qlma.handler-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [ring.mock.request :as mock]
            [qlma.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (println response)
      (is (= (:status response) 200))
      (is (.contains (str (:body response)) "index.html"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
