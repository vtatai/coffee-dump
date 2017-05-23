(ns coffee-dump.db
  (:require [hugsql.core :as hugsql]))

(def db {:dbtype "mysql"
         :dbname "coffee_dump_dev"
         :user "root"})
