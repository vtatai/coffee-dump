(ns config.migrate-config
   (:require [clojure.java.jdbc :as j]))

(def db {:dbtype "mysql"
         :dbname "coffee_dump"
         :user "root"
         :host "localhost"
         :port "4417"})

(defn current-db-version []
  (let [res (j/query db
                     ["select version from schema_migrations"])]
       (or (:version (last res)) 0)))

(defn update-db-version [version]
  (j/update! db :schema_migrations {:version version} []))

(defn migrate-config []
  {:directory "/src/migrations"
   :current-version current-db-version
   :update-version update-db-version})
