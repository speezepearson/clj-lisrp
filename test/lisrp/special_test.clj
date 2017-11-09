(ns lisrp.special-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.evaluation :refer :all]
            [lisrp.special :refer [special-forms]]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(deftest test-special-forms
  (testing "let1"
    (= (->LInteger 1) (evaluate (make-environment {:special-forms special-forms}) (clj->lisrp '(let1 x 1 x)))))
  (testing "do"
    (= (->LInteger 1) (evaluate (make-environment {:special-forms special-forms}) (clj->lisrp '(do 0 0 0 1)))))
  (testing "def"
    (= (->LInteger 1) (evaluate (make-environment {:special-forms special-forms}) (clj->lisrp '(do (def x 1) x))))))
