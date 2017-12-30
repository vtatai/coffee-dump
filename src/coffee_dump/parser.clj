(ns coffee-dump.parser
  (:require [clojure.java.io :as io])
)

(import com.github.javaparser.JavaParser)

(defrecord JavaCompilationUnit [package-name types])
(defrecord Type [type-name fields types])
(defrecord Field [name type])

(defn parsef
  "Parses a file"
  [file]
  (JavaParser/parse file))

(defn parse
  "Parses a file by name"
  [fname]
  (parsef (io/file fname)))

(defn type-declaration? 
  [node]
  (instance? com.github.javaparser.ast.body.ClassOrInterfaceDeclaration node)
)

(defn field-declaration? 
  [node]
  (instance? com.github.javaparser.ast.body.FieldDeclaration node)
)

(declare convert-type)

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
  (map->Field {:name (.. field (getName) (asString)) :type (.. field (getType) (asString))})
)

(defn convert-fields 
  [members]
  (map convert-field 
       (flatten (map #(seq (.getVariables %)) 
                     (filter field-declaration? members))))
)

(defn convert-type 
  "Converts a ClassOrInterfaceDeclaration to a map"
  [jp-type]
  (map->Type 
   {:type-name (.. jp-type (getName) (asString))
    :types (convert-node-lists (into [] (.getNodeLists jp-type)))
    :fields (convert-fields (into [] (.getMembers jp-type)))
    })
)

(defn convert-nested-types
  "Converts nested types inside a JP CU into a map"
  [jp-cu]
  (map convert-type (into [] (.getTypes jp-cu)))
)

(defn package-name [cu] 
  (let [pkgOptional (.. cu (getPackageDeclaration))] 
    (if (.isPresent pkgOptional) 
      (.. pkgOptional (get) (getName) (asString))
      "")))

(defn convert-compilation-unit 
  "Converts a compilation unit into a map"
  [jp-cu]
  (map->JavaCompilationUnit 
   {
    :package-name (package-name jp-cu) 
    :types (convert-nested-types jp-cu)
    })
)

(defn parse-to-map [filename] 
  (convert-compilation-unit (parse filename))
  )
