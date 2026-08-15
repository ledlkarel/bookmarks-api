(ns bookmarks-api.routes
  (:require [bookmarks-api.handlers :as handlers]
            [ring.middleware.json :as json]
            [reitit.ring :as ring]))

(def router
  (ring/router
   [["/bookmarks"
     {:get handlers/list-bookmarks}]]))

(def app
  (-> (ring/ring-handler router)
      (json/wrap-json-response)))
