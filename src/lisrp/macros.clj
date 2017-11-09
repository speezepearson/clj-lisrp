(ns lisrp.macros
  (:require [lisrp.environments :refer [make-environment]]))

(defprotocol LMacro
  (expand [mac args] "Expand an expression"))

(defrecord BuiltinLMacro [name transform]
  LMacro
  (expand [mac args] ((:transform mac) args)))

(defrecord DefinedLMacro [name closure arg-names body])
