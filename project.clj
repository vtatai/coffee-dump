(defproject coffee-dump "0.1.0-SNAPSHOT"
  :description "Dumps Java source file structs into a database"
  :url "http://"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0.txt"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"] 
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [com.layerware/hugsql "0.4.7"]
                 [com.github.javaparser/javaparser-core "3.2.2"]
                 [com.github.javaparser/java-symbol-solver-logic "0.6.3"]
                 [mysql/mysql-connector-java "6.0.6"]
                 ]
  :java-source-paths ["src-java"]
  :plugins [[drift "1.5.3"]] 
)
