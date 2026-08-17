(ns bookmarks-api.handlers
  (:require [bookmarks-api.core :as bookmarks]))

(defn list-bookmarks [_request]
  {:status 200
   :body (bookmarks/get-bookmarks)})

(defn get-bookmark [request]
  (let [id (parse-long (get-in request [:path-params :id]))
        bookmark (bookmarks/get-bookmark id)]
    (if bookmark
      {:status 200
       :body bookmark}
      {:status 404
       :body {:error "Bookmark not found"}})))

(defn create-bookmark [request]
  (let [bookmark (:body request)]
    (bookmarks/create-bookmark bookmark)
    {:status 201
     :body bookmark}))