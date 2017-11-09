(ns lisrp.environments)

(defrecord Environment [parent bindings])

(defn scoped-get [env key]
  (cond
    (contains? (:bindings env) key) ((:bindings env) key)
    (not (nil? (:parent env))) (recur (:parent env) key)
    :else nil))
