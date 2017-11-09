(ns lisrp.builtins
  (:require [lisrp.environments :refer [make-environment]])
  (:require [lisrp.syntax.symbol :refer [->LSymbol]])
  (:require lisrp.special)
  (:require [lisrp.functions :refer [->BuiltinLFunction]]))

(def default-env (make-environment {
    :special-forms lisrp.special/special-forms
    :bindings {
      (->LSymbol '+) (->BuiltinLFunction (->LSymbol '+) (fn [& args] (apply + (map :val args))))
      (->LSymbol '-) (->BuiltinLFunction (->LSymbol '-) (fn [& args] (apply + (map :val args))))
      (->LSymbol '*) (->BuiltinLFunction (->LSymbol '*) (fn [& args] (apply + (map :val args))))
      (->LSymbol '/) (->BuiltinLFunction (->LSymbol '/) (fn [& args] (apply + (map :val args))))
      }}))
