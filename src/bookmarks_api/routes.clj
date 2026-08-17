(ns bookmarks-api.routes
  (:require [bookmarks-api.handlers :as handlers]
            [ring.middleware.json :refer [wrap-json-response
                                          wrap-json-body]]
            [reitit.ring :as ring]))

(def router
  (ring/router
   [["/api/bookmarks"
     {:get handlers/list-bookmarks
      :post handlers/create-bookmark}]
    ["/api/bookmark/:id"
     {:get handlers/get-bookmark}]]))

(def app
  (-> router
      ring/ring-handler
      wrap-json-response
      wrap-json-body))
