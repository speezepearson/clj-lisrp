(ns lisrp.evaluation
  (:require [lisrp.environments :refer [make-environment scoped-get]])
  (:require [lisrp.types :refer :all])
  (:require [lisrp.macros :refer :all])
  (:require [lisrp.functions :refer [call]]))

(defn evaluate [env expr]
  (cond
    (instance? lisrp.types.LSymbol expr)
      (let [result (scoped-get :bindings env expr)]
        (when (not result) (throw (Exception. (str "undefined variable " expr))))
        result)
    (instance? lisrp.types.LInteger expr) expr
    (instance? lisrp.types.LBoolean expr) expr
    (instance? lisrp.types.LNil expr) expr
    (instance? lisrp.types.LList expr)
      (let [[head & rest] (:vals expr)]
        (when (= 0 (count (:vals expr))) (throw "empty S-expr can never be evaluated"))
        (cond
          (scoped-get :special-forms env head) ((:run (scoped-get :special-forms env head)) env rest)
          (scoped-get :macros env head) (expand (scoped-get :macros env head) rest)
          :else (call (evaluate env head) (map (partial evaluate env) rest))))
    :else (throw (Exception. (str "unexpected expr-type of " expr ": " (type expr))))))

(extend lisrp.functions.DefinedLFunction
  lisrp.functions/LFunction
  {:call (fn [f args]
    (when (not (= (count (:arg-names f)) (count args)))
      (throw (Exception. (str "expected " (count (:arg-names f)) " arguments, but got " (count args)))))
    (evaluate (make-environment {:parent (:closure f)
                                 :bindings (zipmap (:arg-names f) args)})
              (:body f)))})

(extend lisrp.macros.DefinedLMacro
  lisrp.macros/LMacro
  {:expand (fn [mac args]
    (if (= (count (:arg-names mac)) (count args))
      (evaluate (make-environment {:parent (:closure mac)
                                 :bindings (zipmap (:arg-names mac) args)})
                (:body mac))
      nil))})
