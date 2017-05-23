(ns coffee-dump.utils
  (:import java.sql.Timestamp java.time.LocalDateTime))

(defn current-ts []
  (Timestamp/valueOf (LocalDateTime/now)))
