(ns lisrp.evaluation-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.expressions :refer [evaluate]]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(deftest test-things
  (testing "integers evaluate to themselves"
    (is (= (clj->lisrp 1)
           (evaluate (clj->lisrp 1) (make-environment {})))))
  (testing "symbols evaluate to themselves"
    (is (= (clj->lisrp 1)
           (evaluate (clj->lisrp 'a) (make-environment {:bindings {(clj->lisrp 'a) (clj->lisrp 1)}})))))
  (testing "builtin functions work"
    (is (= (clj->lisrp 2) (evaluate (clj->lisrp '(+ 1 1)) default-env))))
  (testing "defined functions work"
    (is (= (clj->lisrp 2) (evaluate (clj->lisrp '((make-fn f (x y) (+ x y)) 1 1)) default-env)))))
