(ns lisrp.special
  (:require [lisrp.environments :refer [make-environment]])
  (:require [lisrp.expressions :refer [evaluate]])
  (:require [lisrp.syntax.symbol :refer [->LSymbol]]))

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
      (assoc! (:bindings env) arg-id (evaluate expr env))))
  (->SpecialForm (->LSymbol 'do)
    (fn [env exprs]
      (def result nil)
      (doseq [expr exprs]
        (def result (evaluate expr env)))
      result))
  ))))
