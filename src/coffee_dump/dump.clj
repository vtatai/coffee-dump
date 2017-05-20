(ns coffee-dump.dump
  (:require [clojure.java.io :as io])
  (:require [qbits.alia :as alia])
)

(defn concat-pkg 
  [package-name type-name]
  (if (empty? package-name)
    type-name
    (str package-name "." type-name)    
    )
)

(defn dump-types
  [mapped-types package-name]
  (map #(print (concat-pkg package-name (:type-name %))) mapped-types)
)

(defn dump 
  [mapped-type]
  (dump-types (:types mapped-type) (:package-name mapped-type))
)

