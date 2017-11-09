(ns lisrp.evaluation
  (:require [lisrp.environments :refer [->Environment scoped-get]])
  (:require [lisrp.types :refer :all])
  (:require [lisrp.functions :refer [call]]))

(defn evaluate [env expr]
  (cond
    (instance? lisrp.types.LSymbol expr)
      (let [result (scoped-get env expr)]
        (when (not result) (throw (Exception. (str "undefined variable " expr))))
        result)
    (instance? lisrp.types.LInteger expr) expr
    (instance? lisrp.types.LBoolean expr) expr
    (instance? lisrp.types.LNil expr) expr
    (instance? lisrp.types.LList expr)
      (cond
        (= 0 (count (:vals expr))) nil
        ; (scoped-get :macro-bindings env (first expr))
        ;   (evaluate env (expand (scoped-get :macro-bindings env (first expr)) (rest expr)))
        :else
          (let [[f & args] (map (partial evaluate env) (:vals expr))]
            (call f args)))
    :else (throw (Exception. (str "unexpected expr-type of " expr ": " (type expr))))))

(extend lisrp.functions.DefinedLFunction
  lisrp.functions/LFunction
  {:call (fn [f args]
    (if (= (count (:arg-names f)) (count args))
      (evaluate (->Environment (:closure f) (zipmap (:arg-names f) args)) (:body f))
      [(str "expected " (count (:arg-names f)) " arguments, but got " (count args)), false]))})
