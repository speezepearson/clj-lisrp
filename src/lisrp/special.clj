(ns lisrp.special
  (:require [lisrp.environments :refer [make-environment]])
  (:require [lisrp.expressions :refer [evaluate]])
  (:require [lisrp.syntax.symbol :refer [->LSymbol]])
  (:require [lisrp.nil :refer [lnil]])
  (:require [lisrp.functions :refer [map->DefinedLFunction]])
  )

(defrecord SpecialForm [name run])

(def special-forms (into {} (map (fn [sf] [(:name sf) sf]) (list
  (->SpecialForm (->LSymbol 'let1)
    (fn [env [id expr body :as subexprs]]
      (when (or (not (= (count subexprs) 3))
                (not (instance? lisrp.syntax.symbol.LSymbol id)))
        (throw (Exception. (str "usage: (let1 ID EXPR BODY)"))))
      (evaluate body (make-environment {:parent env
                                        :bindings {id (evaluate expr env)}}))))
  (->SpecialForm (->LSymbol 'def)
    (fn [env [arg-id expr :as subexprs]]
      (when (or (not (= (count subexprs) 2))
                (not (instance? lisrp.syntax.symbol.LSymbol arg-id)))
        (throw (Exception. (str "usage: (def ARG_ID EXPR)"))))
      (assoc! (:bindings env) arg-id (evaluate expr env))
      lnil))
  (->SpecialForm (->LSymbol 'make-fn)
    (fn [env [name arg-names body :as subexprs]]
      (when (not (= (count subexprs) 3)) (throw (Exception. (str "usage: (make-fn NAME (ARG_ID ...) bODY)"))))
      (when (not (instance? lisrp.syntax.symbol.LSymbol name)) (throw (Exception. (str "make-fn name must be a symbol"))))
      (when (not (instance? lisrp.syntax.list.LList arg-names)) (throw (Exception. (str "make-fn arg-names must be a list (of symbols)"))))
      (when (not (every? #(instance? lisrp.syntax.symbol.LSymbol %) (:vals arg-names))) (throw (Exception. (str "make-fn arg-names must be a list of SYMBOLS"))))
      (map->DefinedLFunction {
        :closure env
        :name name
        :arg-names (:vals arg-names)
        :body body
        })))
  (->SpecialForm (->LSymbol 'do)
    (fn [env exprs]
      (def result lnil)
      (doseq [expr exprs]
        (def result (evaluate expr env)))
      result))
  ))))
