(ns lisrp.special-test
  (:require [clojure.test :refer :all]
            [lisrp.environments :refer :all]
            [lisrp.types :refer :all]
            [lisrp.nil :refer [lnil]]
            [lisrp.expressions :refer [evaluate]]
            [lisrp.special :refer [special-forms]]
            [lisrp.translation :refer [clj->lisrp]]
            [lisrp.builtins :refer :all]))

(def env (make-environment {:special-forms special-forms}))
(deftest test-special-forms
  (testing "let1"
    (is (= (clj->lisrp 1) (evaluate (clj->lisrp '(let1 x 1 x)) env))))
  (testing "do"
    (is (= (clj->lisrp 1) (evaluate (clj->lisrp '(do 0 0 0 1)) env)))
    (is (= lnil (evaluate (clj->lisrp '(do)) env))))
  (testing "def"
    (is (= (clj->lisrp 1) (evaluate (clj->lisrp '(do (def x 1) x)) env)))))
