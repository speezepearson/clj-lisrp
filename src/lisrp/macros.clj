(ns lisrp.macros)

(defprotocol LMacro
  (expand [mac args] "Expand an expression"))

(defrecord BuiltinLMacro [name transform]
  LMacro
  (expand [mac args] ((:transform mac) args)))

(defrecord DefinedLMacro [name closure arg-names body]
  LMacro
  (expand [mac args]
    (if (= (count (:arg-names mac)) (count args))
      (evaluate (->Environment (:closure mac) (zipmap (:arg-names mac) args)) (:body mac))
      nil)))
