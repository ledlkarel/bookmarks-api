(ns bookmarks-api.core
  (:gen-class))

(def bookmarks (atom {}))

(defn create-bookmark
  [bookmark]
  (swap! bookmarks assoc 0 bookmark))

(defn get-bookmarks
  []
  (println @bookmarks))

(defn get-bookmark
  [id]
  (println (get @bookmarks id)))

(defn delete-bookmark
  [id]
  (swap! bookmarks dissoc id))

(defn update-bookmark
  [id bookmark]
  (println bookmark)
  (swap! bookmarks update id merge bookmark))

(defn -main
  []
  (create-bookmark  {:id 1
                     :title "Clojure Docs"
                     :url "https://clojure.org"
                     :tags ["clojure"]})
  (get-bookmarks)
  (update-bookmark 0 {:title "something new"})
  (get-bookmarks))
