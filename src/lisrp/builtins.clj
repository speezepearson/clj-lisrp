(ns lisrp.builtins
  (:require [lisrp.environments :refer [->Environment]])
  (:require [lisrp.types :refer [->LSymbol]])
  (:require [lisrp.functions :refer [->BuiltinLFunction]]))

(def default-env
  (->Environment nil
    (into {}
      (map
        (fn [op] [op (->BuiltinLFunction op (fn [& args] (apply + (map :val args))))])
        (map ->LSymbol '(+ - * /))))))
