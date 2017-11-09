(ns lisrp.translation
  (:require [lisrp.syntax
    [symbol :refer [->LSymbol]]
    [integer :refer [->LInteger]]
    [list :refer [->LList]]]))

(defn clj->lisrp [x]
  (cond
    (symbol? x) (->LSymbol x)
    (integer? x) (->LInteger x)
    (list? x) (->LList (map clj->lisrp x))))

(defn lisrp->clj [x]
  (cond
    (instance? lisrp.syntax.integer.LInteger x) (:val x)
    (instance? lisrp.syntax.symbol.LSymbol x) (:val x)
    (instance? lisrp.syntax.list.LList x) (:vals x)))
