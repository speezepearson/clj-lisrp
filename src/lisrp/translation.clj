(ns lisrp.translation
  (:require [lisrp.types :refer :all]))

(defn clj->lisrp [x]
  (cond
    (symbol? x) (->LSymbol x)
    (integer? x) (->LInteger x)
    (or (true? x) (false? x)) (->LBoolean x)
    (list? x) (->LList (map clj->lisrp x))))

(defn lisrp->clj [x]
  (cond
    (instance? lisrp.types.LInteger x) (:val x)
    (instance? lisrp.types.LBoolean x) (:val x)
    (instance? lisrp.types.LSymbol x) (:val x)
    (instance? lisrp.types.LList x) (:vals x)))
