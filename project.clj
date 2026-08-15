(defproject bookmarks-api "0.1.0-SNAPSHOT" 
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [metosin/reitit-ring "0.9.1"]
                 [ring/ring-jetty-adapter "1.15.3"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot bookmarks-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
