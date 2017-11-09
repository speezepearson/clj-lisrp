(ns lisrp.syntax.integer
  (:require [lisrp.types :refer [->LType]])
  (:require [lisrp.expressions :refer [evaluate expr->source]])
  )

(def LIntegerT (->LType "integer"))
(defrecord LInteger [val]
  lisrp.types.LValue
    (ltype [_] LIntegerT)
  lisrp.expressions/Expression
    (evaluate [n env] n)
    (expr->source [n] (str n)))
