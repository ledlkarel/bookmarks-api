(ns bookmarks-api.core
  (:gen-class))

(def bookmarks (atom {}))
(def next-id (atom 0))

(defn create-bookmark
  [bookmark]
  (let [id (swap! next-id inc)
        bookmark (assoc bookmark :id id)]
    (swap! bookmarks assoc id bookmark)
    bookmark))

(defn get-bookmarks
  []
  @bookmarks)

(defn get-bookmark
  [id]
  (get @bookmarks id))

(defn delete-bookmark
  [id]
  (let [bookmark (get @bookmarks id)]
    (swap! bookmarks dissoc id)
    bookmark))

(defn update-bookmark
  [id changes]
  (let [existing (get @bookmarks id)]
    (when existing
      (let [updated (merge existing changes)]
        (swap! bookmarks assoc id updated)
        updated))))

(defn find-bookmarks-by-tag [tag]
  (filter
   #(some #{tag} (:tags %))
   (vals @bookmarks)))

(defn -main
  []
  (create-bookmark {:title "Clojure Docs"
                    :url "https://clojure.org"
                    :tags ["clojure"]})
  ((requiring-resolve 'ring.adapter.jetty/run-jetty)
   (requiring-resolve 'bookmarks-api.routes/app)
   {:port 3000
    :join? false}))
