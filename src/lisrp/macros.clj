(ns lisrp.macros
  (:require [lisrp.environments :refer [make-environment]])
  (:require [lisrp.expressions :refer [evaluate]])
  )

(defprotocol LMacro
  (expand [mac args] "Expand an expression"))

(defrecord BuiltinLMacro [name transform]
  LMacro
  (expand [mac args] ((:transform mac) args)))

(defrecord DefinedLMacro [name closure arg-names body]
  LMacro
  (expand [mac args]
    (if (= (count (:arg-names mac)) (count args))
      (evaluate (:body mac)
                (make-environment {:parent (:closure mac)
                                   :bindings (zipmap (:arg-names mac) args)}))
      nil)))
