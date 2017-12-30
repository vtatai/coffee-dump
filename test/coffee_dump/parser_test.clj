(ns coffee-dump.parser-test
  (:require [clojure.test :refer :all]
            [coffee-dump.parser :refer :all :as parser]
            )
  (:import [com.github.javaparser.ast CompilationUnit])
  )

(deftest test-parse
  (is (instance? CompilationUnit 
                 (parser/parse "test/sample/DataStructure.java")))
  )

(def parsed-data-structure
  (parser/convert-compilation-unit (parser/parse "test/sample/DataStructure.java")))

(deftest test-convert
  (let [clazz (nth (:types parsed-data-structure) 0)]
    (is (= "sample" (:package-name parsed-data-structure)))
    (is (= "DataStructure" (:type-name clazz)))
    (is (= "DataStructure" (:type-name clazz)))
    )
  )
