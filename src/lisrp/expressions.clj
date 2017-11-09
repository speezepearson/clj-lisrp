(ns lisrp.expressions)

(defprotocol Expression
  (evaluate [expr env] "Evaluate the expression down to a value")
  (expr->source [expr] "Return the expression's source code"))
