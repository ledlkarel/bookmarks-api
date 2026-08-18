(ns bookmarks-api.handlers
  (:require [bookmarks-api.core :as bookmarks]))

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

(defn delete-bookmark [request]
  (let [id (parse-long (get-in request [:path-params :id]))
        bookmark (bookmarks/delete-bookmark id)]
    (if bookmark
      {:status 204}
      {:status 404
       :body {:error "Bookmark not found"}})))

(defn update_bookmark [request]
  (let [id (parse-long (get-in request [:path-params :id]))
        changes (:body request)
        bookmark (bookmarks/update-bookmark id changes)]
    (if bookmark
      {:status 200
       :body bookmark}
      {:status 404
       :body {:error "Bookmark not found"}})))

(defn list-bookmarks [request]
  (let [tag (get-in request (:query-params "tag"))]
    {:status 200
     :body (if tag
             (bookmarks/find-bookmarks-by-tag tag)
             (bookmarks/get-bookmarks))}))