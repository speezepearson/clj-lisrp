(ns lisrp.syntax.symbol
  (:require [lisrp.types :refer [->LType]])
  (:require [lisrp.expressions :refer [evaluate expr->source]])
  (:require [lisrp.environments :refer [scoped-get]])
  )

(def LSymbolT (->LType "symbol"))
(defrecord LSymbol [val]
  lisrp.types.LValue
    (ltype [_] LSymbolT)
  lisrp.expressions/Expression
    (evaluate [sym env]
      (let [result (scoped-get :bindings env sym)]
        (when (not result) (throw (Exception. (str "undefined variable " sym))))
        result))
    (expr->source [sym] (str sym)))
