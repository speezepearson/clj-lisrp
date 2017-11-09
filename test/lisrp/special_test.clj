(ns lisrp.special-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.expressions :refer [evaluate]]
            [lisrp.special :refer [special-forms]]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(deftest test-special-forms
  (testing "let1"
    (= (clj->lisrp 1) (evaluate (clj->lisrp '(let1 x 1 x)) (make-environment {:special-forms special-forms}))))
  (testing "do"
    (= (clj->lisrp 1) (evaluate (clj->lisrp '(do 0 0 0 1)) (make-environment {:special-forms special-forms}))))
  (testing "def"
    (= (clj->lisrp 1) (evaluate (clj->lisrp '(do (def x 1) x)) (make-environment {:special-forms special-forms})))))
