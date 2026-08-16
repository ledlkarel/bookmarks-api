(ns bookmarks-api.handlers
  (:require [bookmarks-api.core :as bookmarks]))

(defn list-bookmarks [_request]
  {:status 200
   :body (bookmarks/get-bookmarks)})

(defn get-bookmark [_request]
  {:status 200
   :body (bookmarks/get-bookmark _request)})

(defn create-bookmark [request]
  (let [bookmark (:body request)]
    (bookmarks/create-bookmark bookmark)
    {:status 201
     :body bookmark}))