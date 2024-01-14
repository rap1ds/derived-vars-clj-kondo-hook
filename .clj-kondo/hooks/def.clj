(ns hooks.def
  (:require [clj-kondo.hooks-api :as api]))

(defn- fn-token-node? [{:keys [node lang]}]
  (when (and (api/token-node? node)
             (symbol? (:value node)))
    (let [{:keys [ns name]} (api/resolve {:name (:value node)})
          analysis (get-in (api/ns-analysis ns {:lang lang}) [lang name])]
      (when-not (#{'clojure.core
                   'cljs.core} ns)
        (some (set (keys analysis)) [:fixed-arities :varargs-min-arity])))))

(defn- traverse? [{:keys [node]}]
  (or
   (api/vector-node? node)
   (api/map-node? node)
   (api/set-node? node)))

(defn- analyze* [{:keys [node lang]}]
  (cond
    (fn-token-node? {:node node
                     :lang lang})
    (api/reg-finding! (assoc (meta node)
                             :type :fn-sym-in-def
                             :message (str "fn-sym-in-def: "
                                           node
                                           (if (= :cljs lang)
                                             " - use function wrapping"
                                             " - use var quoting"))))

    (traverse? {:node node})
    (doall (map #(analyze* {:node %
                            :lang lang})
                (:children node)))))

(defn analyze
  [{:keys [node lang]}]
  (when (< 1 (count (:children node)))
    (analyze* {:node (last (:children node))
               :lang lang}))
  node)
