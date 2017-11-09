(ns lisrp.evaluation-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.evaluation :refer :all]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(deftest test-things
  (testing "integers evaluate to themselves"
    (= (->LInteger 1)
       (evaluate (make-environment {})
                 (->LInteger 1))))
  (testing "integers evaluate to themselves"
    (= (->LInteger 1)
      (evaluate (make-environment {:bindings {(->LSymbol 'a) (->LInteger 1)}})
                (->LSymbol 'a))))
  (testing "builtin functions work"
    (= (->LInteger 2) (evaluate default-env (clj->lisrp '(+ 1 1))))))
