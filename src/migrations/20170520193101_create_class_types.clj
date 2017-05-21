(ns migrations.20170520193101-create-class-types
  (:require [clojure.java.jdbc :as j])
  (:use [config.migrate-config :only [db]]))

(defn up
  "Migrates the database up to version 20170520193101."
  []
  (do (println "migrations.20170520193101-create-class-types up...")
      (j/execute! db ["CREATE TABLE class_types (id BIGINT NOT NULL AUTO_INCREMENT, ts TIMESTAMP NOT NULL, name VARCHAR(255) NOT NULL, package VARCHAR(1023), parent BIGINT NULL, PRIMARY KEY (id))"])))

(defn down
  "Migrates the database down from version 20170520193101."
  []
  (do (println "migrations.20170520193101-create-class-types down...")
      (j/execute! db ["DROP TABLE class_types"])))
