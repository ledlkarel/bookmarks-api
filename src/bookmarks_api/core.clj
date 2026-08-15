(ns bookmarks-api.core
  (:gen-class))

(def bookmarks (atom {}))
(def bookmark-id (atom 0))

(defn create-bookmark
  [bookmark]
  (swap! bookmarks assoc (inc @bookmark-id) bookmark))

(defn read-bookmark
  []
  (println @bookmarks))

(defn read-bookmark-by-id
  [id]
  (println (get @bookmarks id)))

(defn delete-bookmark
  [id]
  (swap! bookmarks dissoc @bookmarks id))

(defn -main
  []
  (create-bookmark  {:id 1
                     :title "Clojure Docs"
                     :url "https://clojure.org"
                     :tags ["clojure"]})
  (read-bookmark))
