(ns lisrp.nil
  (:require [lisrp.types :refer [->LType]])
  )

(def LNilT (->LType "NilT"))
(defrecord LNil []
  lisrp.types.LValue
    (ltype [_] LNilT))

(def lnil (->LNil))
