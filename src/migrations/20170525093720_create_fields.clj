(ns migrations.20170525093720-create-fields
  (:require [clojure.java.jdbc :as j])
  (:use [config.migrate-config :only [db]]))

(defn up
  "Migrates the database up to version 20170525093720."
  []
  (do
    (println "migrations.20170525093720-create-fields up...")
    (j/execute! db ["CREATE TABLE fields (id BIGINT NOT NULL AUTO_INCREMENT, ts TIMESTAMP NOT NULL, name VARCHAR(255) NOT NULL, field_type BIGINT NULL, container_type BIGINT NOT NULL, PRIMARY KEY (id))"])))

(defn down
  "Migrates the database down from version 20170525093720."
  []
  (do
    (println "migrations.20170525093720-create-fields down...")
    (j/execute! db ["DROP TABLE fields"])))
