(ns lisrp.types)

(defprotocol LValue
  (ltype [v] "Get the value's type"))
(defrecord LType [name])

(def LIntegerT (->LType "integer"))
(def LSymbolT (->LType "symbol"))
(def LBooleanT (->LType "boolean"))
(def LListT (->LType "list"))
(def LNilT (->LType "boolean"))
(defrecord LInteger [val] LValue (ltype [_] LIntegerT))
(defrecord LSymbol [val] LValue (ltype [_] LSymbolT))
(defrecord LBoolean [val] LValue (ltype [_] LBooleanT))
(defrecord LList [vals] LValue (ltype [_] LListT))
(defrecord LNil [val] LValue (ltype [_] LNilT))
