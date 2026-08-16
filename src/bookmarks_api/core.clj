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
  (println id)
  (get @bookmarks id))

(defn delete-bookmark
  [id]
  (swap! bookmarks dissoc id))

(defn update-bookmark
  [id bookmark]
  (let [updated (swap! bookmarks #(if (contains? % id)
                                    (update % id merge bookmark)
                                    %))]
    (get updated id)))

(defn -main
  []
  (create-bookmark {:title "Clojure Docs"
                    :url "https://clojure.org"
                    :tags ["clojure"]})
  ((requiring-resolve 'ring.adapter.jetty/run-jetty)
   (requiring-resolve 'bookmarks-api.routes/app)
   {:port 3000
    :join? false}))
