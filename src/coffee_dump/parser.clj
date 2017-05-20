(ns coffee-dump.parser
  (:require [clojure.java.io :as io])
  (:require [schema.core :as s
             :include-macros true ;; cljs only
             ])
)

(import com.github.javaparser.JavaParser)

;(defrecord CompilationUnit [package-name types])
;(defrecord Type [type-name fields types])
;(defrecord Field [name type])

(comment 
  (def CompilationUnit 
  "Compilation Unit"
  {:package-name s/Str :types s/Str}
  ))

(defn parsef
  "Parses a file"
  [file]
  (JavaParser/parse file))

(defn parse
  "Parses a file by name"
  [fname]
  (parsef (io/file fname)))

(defn package-name [cu] 
  (let [pkgOptional (.. cu (getPackageDeclaration))] 
     (if (.isPresent pkgOptional) 
         (.. pkgOptional (get) (getName) (asString))
         ("")
         )
     )
)

(defn type-declaration? 
  [node]
  (instance? com.github.javaparser.ast.body.ClassOrInterfaceDeclaration node)
)


(defn field-declaration? 
  [node]
  (instance? com.github.javaparser.ast.body.FieldDeclaration node)
)

(declare convert-node-lists)
(declare convert-fields)
(defn convert-type 
  "Converts a ClassOrInterfaceDeclaration to a map"
  [type]
  {:type-name (.. type (getName) (asString))
   :types (convert-node-lists (into [] (.getNodeLists type)))
   :fields (convert-fields (into [] (.getMembers type)))
   }
)

(defn convert-node [node] 
  (if (type-declaration? node) 
    (convert-type node) 
    {}
))

(defn convert-node-list
  [node-list-java]
  (filter (comp not empty?) (map convert-node (into [] node-list-java)))
)

(defn convert-node-lists
  [node-lists]
  (flatten (map convert-node-list node-lists))
)

(defn convert-field 
  [field]
  {:name (.. field (getName) (asString)) :type (.. field (getType) (asString))}
)

(defn convert-fields 
  [members]
  (map convert-field 
       (flatten (map #(seq (.getVariables %)) 
                               (filter field-declaration? members))))
)

(defn convert 
  "Converts a CompilationUnit into a map"
  [cu m]
  (assoc m :types (map convert-type (into [] (.getTypes cu))))
)

(defn convert-compilation-unit 
  "Converts a compilation unit into a map"
  [cu]
  (convert cu {:package-name (package-name cu)})
)
