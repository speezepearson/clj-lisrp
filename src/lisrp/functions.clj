(ns lisrp.functions)

(defprotocol LFunction
  (call [f args] "call the function with the given arguments"))

(defrecord BuiltinLFunction [name transform]
  LFunction
  (call [f args] (apply (:transform f) args)))

(defrecord DefinedLFunction [name closure arg-names body])
