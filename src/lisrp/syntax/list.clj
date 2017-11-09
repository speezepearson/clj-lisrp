(ns lisrp.syntax.list
  (:require [lisrp.types :refer [->LType]])
  (:require [lisrp.expressions :refer [evaluate expr->source]])
  (:require [lisrp.environments :refer [scoped-get]])
  (:require [lisrp.macros :refer [expand]])
  (:require [lisrp.functions :refer [call]])
  )

(def LListT (->LType "list"))
(defrecord LList [vals]
  lisrp.types.LValue
    (ltype [_] LListT)
  lisrp.expressions/Expression
    (evaluate [expr env]
      (let [[head & rest] (:vals expr)]
        (when (= 0 (count (:vals expr))) (throw "empty S-expr can never be evaluated"))
        (cond
          (scoped-get :special-forms env head) ((:run (scoped-get :special-forms env head)) env rest)
          (scoped-get :macros env head) (expand (scoped-get :macros env head) rest)
          :else (call (evaluate head env) (map #(evaluate % env) rest)))))
    (expr->source [expr] (str "(" (clojure.string/join " " (map expr->source (:vals expr))) ")")))
