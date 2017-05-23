(ns coffee-dump.dump
  (:require [clojure.java.io :as io])
  (:require [hugsql.core :as hugsql])
  (:require [coffee-dump.utils :as utils]))


(hugsql/def-db-fns "coffee_dump/sql/dump.sql")

(defn concat-pkg 
  [package-name type-name]
  (if (empty? package-name)
    type-name
    (str package-name "." type-name)    
    )
)

(defn dump-type [type]
  (do (print (:type-name type))
      (insert-class-types (utils/current-ts) (:type-name type)))
)

(defn dump-types
  [mapped-types package-name]
  (map dump-type mapped-types)
)

(defn dump 
  [mapped-type]
  (dump-types (:types mapped-type) (:package-name mapped-type))
)

