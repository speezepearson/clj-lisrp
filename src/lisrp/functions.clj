(ns lisrp.functions
  (:require [lisrp.environments :refer [make-environment]])
  (:require [lisrp.expressions :refer [evaluate]])
  )

(defprotocol LFunction
  (call [f args] "call the function with the given arguments"))

(defrecord BuiltinLFunction [name transform]
  LFunction
  (call [f args] (apply (:transform f) args)))

(defrecord DefinedLFunction [name closure arg-names body]
  LFunction
  (call [f args]
    (when (not (= (count (:arg-names f)) (count args)))
      (throw (Exception. (str "expected " (count (:arg-names f)) " arguments, but got " (count args)))))
    (evaluate (:body f)
              (make-environment {:parent (:closure f)
                                           :bindings (zipmap (:arg-names f) args)}))))
