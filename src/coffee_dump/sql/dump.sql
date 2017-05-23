-- :name insert-class-types
-- :command :insert
-- :result :raw
-- :doc Insert into class types table
INSERT INTO class_types (ts, name, package, parent) 
VALUES (:ts,:name,:package,:parent);
