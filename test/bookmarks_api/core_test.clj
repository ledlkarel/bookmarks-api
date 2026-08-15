(ns bookmarks-api.core-test
  (:require [clojure.test :refer :all]
            [bookmarks-api.core :refer :all]))

(defn reset-state!
  []
  (reset! bookmarks {})
  (reset! next-id 0))

(use-fixtures :each (fn [f]
                      (reset-state!)
                      (f)))

(deftest create-bookmark-test
  (testing "returns the created bookmark with an id"
    (reset-state!)
    (let [b (create-bookmark {:title "Clojure Docs"
                              :url "https://clojure.org"
                              :tags ["clojure"]})]
      (is (= 1 (:id b)))
      (is (= "Clojure Docs" (:title b)))))
  (testing "stores the bookmark under its id"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (is (contains? @bookmarks 1)))
  (testing "creates distinct ids for multiple bookmarks"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (create-bookmark {:title "B" :url "https://b.org"})
    (is (= 2 (count @bookmarks)))
    (is (not= (get @bookmarks 1) (get @bookmarks 2)))))

(deftest get-bookmarks-test
  (testing "returns an empty map when nothing is stored"
    (reset-state!)
    (is (= {} (get-bookmarks))))
  (testing "returns all stored bookmarks"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (create-bookmark {:title "B" :url "https://b.org"})
    (is (= 2 (count (get-bookmarks))))))

(deftest get-bookmark-test
  (testing "returns the bookmark for an existing id"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (is (= {:id 1 :title "A" :url "https://a.org"} (get-bookmark 1))))
  (testing "returns nil for a missing id"
    (reset-state!)
    (is (nil? (get-bookmark 99)))))

(deftest update-bookmark-test
  (testing "partial update preserves other fields"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org" :tags ["x"]})
    (update-bookmark 1 {:title "B"})
    (is (= {:id 1 :title "B" :url "https://a.org" :tags ["x"]}
           (get-bookmark 1))))
  (testing "returns the updated bookmark"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (is (= {:id 1 :title "B" :url "https://b.org"}
           (update-bookmark 1 {:title "B" :url "https://b.org"}))))
  (testing "returns nil and leaves state unchanged for a missing id"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (is (nil? (update-bookmark 99 {:title "B"})))
    (is (= 1 (count @bookmarks)))))

(deftest delete-bookmark-test
  (testing "removes an existing bookmark"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (delete-bookmark 1)
    (is (nil? (get-bookmark 1))))
  (testing "returns the map without the deleted id"
    (reset-state!)
    (create-bookmark {:title "A" :url "https://a.org"})
    (is (= {} (delete-bookmark 1))))
  (testing "is a harmless no-op for a missing id"
    (reset-state!)
    (is (= {} (delete-bookmark 99)))))
