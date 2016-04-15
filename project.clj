(defproject wal.sh/open-allergies "0.0.1-SNAPSHOT"
  :description "Open Allergies "
  :url "http://wal.sh/poc/open-allergies"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]
                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.12"]
                 [org.slf4j/jcl-over-slf4j "1.7.12"]
                 [org.slf4j/log4j-over-slf4j "1.7.12"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/clojurescript "0.0-2760"]
                 [org.omcljs/om "0.9.0"]
                 [ring/ring-json "0.4.0"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "wal.sh.open-allergies.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.4.1"]]}
             :uberjar {:aot [wal.sh.open-allergies.server]}}
  :main ^{:skip-aot true} wal.sh.open-allergies.server)
