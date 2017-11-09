(ns lisrp.environments)

(defrecord Environment [parent special-forms macros bindings])

(defn make-environment [m]
  (map->Environment (into
    {:parent nil
     :special-forms {}
     :macros (transient {})
     :bindings (transient {})}
    m)))

(defn scoped-get [field env key]
  (cond
    (not (nil? ((field env) key))) ((field env) key)
    (not (nil? (:parent env))) (recur field (:parent env) key)
    :else nil))
