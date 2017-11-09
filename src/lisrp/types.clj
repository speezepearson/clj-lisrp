(ns lisrp.types)

(defprotocol LValue
  (ltype [v] "Get the value's type"))
(defrecord LType [name])
