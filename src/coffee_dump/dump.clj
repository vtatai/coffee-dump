(ns coffee-dump.dump
  (:require [clojure.java.io :as io]
            [hugsql.core :as hugsql]
            [coffee-dump.utils :as utils])
  (:use [coffee-dump.db :only [db]]))


(hugsql/def-db-fns "coffee_dump/sql/dump.sql")

(defn concat-pkg 
  [package-name type-name]
  (if (empty? package-name)
    type-name
    (str package-name "." type-name)    
    )
)

(defn dump-type 
  [ts package-name type]
  (do (print (:type-name type))
      (insert-class-types db
       {
        :ts ts 
        :name (:type-name type)
        :package package-name
        :parent nil
        }))
)

(defn dump-types
  [ts package-name mapped-types]
  (map (partial dump-type ts package-name) mapped-types)
)

(defn dump 
  ([ts cu]
   (dump-types ts (:package-name cu) (:types cu)))
  ([cu]
   (dump (utils/current-ts) cu))
)
