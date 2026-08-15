(ns bookmarks-api.handlers
  (:require [bookmarks-api.core :as bookmarks]))

(defn list-bookmarks [_request]
  {:status 200
   :body (bookmarks/get-bookmarks)})