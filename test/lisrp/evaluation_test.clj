(ns lisrp.evaluation-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.expressions :refer [evaluate]]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(deftest test-things
  (testing "integers evaluate to themselves"
    (= (clj->lisrp 1)
       (evaluate (clj->lisrp 1) (make-environment {}))))
  (testing "integers evaluate to themselves"
    (= (clj->lisrp 1)
      (evaluate (clj->lisrp 'a) (make-environment {:bindings {(clj->lisrp 'a) (clj->lisrp 1)}}))))
  (testing "builtin functions work"
    (= (clj->lisrp 2) (evaluate (clj->lisrp '(+ 1 1)) default-env))))
