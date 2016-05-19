(ns wal.sh.open-allergies.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [ring.util.response :as ring-resp]
            [ring.middleware.json :refer [wrap-json-response]]
            [clojure.data.json :as json]
            [ring.util.response :as response]))

(def ALLERGIES [:Milk
                :Eggs
                :Fish
                :Shellfish
                :TreeNuts
                :Peanuts
                :Wheat
                :Soybean
                ])

(def CUISINES [:Italian
               :Chinese
               :Greek
               :Asian
               :French
               :German
               :Cuban
               :British
               :Thai
               :Polish
               :Moroccan
               :Lebanese
               :Japanese
               :Indian
               ])

(def FOODS [:Juice
            :Milk
            :Beans
            :Corn
            :Apples
            :Peas
            :Tomatoes
            :Pineapple
            :Vegetables
            :Grapes
            :Oranges
            :Butter
            :Broccoli
            :Peanuts
            :Carrots
            :Peas
            :Tomatoes
            :Raisins
            :Corn
            :Spinach
            ])

(def DIETS [:AtkinsDiet
            :TheZoneDiet
            :VegetarianDiet
            :VeganDiet
            :WeightWatchersDiet
            :SouthBeachDiet
            :RawFoodDiet
            :MediterraneanDiet
            ])

(defn epoch []
  (int (/ (System/currentTimeMillis) 1000)))


(defrecord Food [^String name ^String description ^Boolean popular ^Boolean display_like ^Boolean display_dislike])

(doseq [f FOODS]
  (hash f))

;;(def numbers [2  4 3 4 3 2 4 7 6 6 5 7 6 5 5 4])
;; (max-key count "asd" "bsd" "dsd" "long word")
;; (reduce ( fn [a b x] (+ a b)) 0 numbers)
;; (first (apply max-key second (map-indexed vector numbers)))
;; (key (apply max-key val {:a 3 :b 7 :c 9 :d 3}))
;; (apply val {:a 3})

(defn transform-food [name]
  (->Food name name true true true))

(defn transform-name-entity [name]
  (let [entity {
                :created (epoch)
                :updated (epoch)
                :name name
                }]
    entity))

(defn about-index
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-index))))

(defn home-page
  [request]
  (ring-resp/response "Open Allergy"))

(defn allergies-list
  [request]
  (ring-resp/response (json/write-str (map transform-name-entity ALLERGIES))))

(defn foods-list
  [request]
  (ring-resp/response (json/write-str (map transform-food FOODS))))

(defn cuisines-list
  [request]
  (ring-resp/response (json/write-str (map transform-name-entity CUISINES))))

(defn diets-list
  [request]
  (ring-resp/response (json/write-str (map transform-name-entity DIETS))))

;; Issue with reload from the repl
;; (defprotocol Draw)
;; (defrecord Rectangle [width height] Draw)

(defroutes routes
  [[["/" {:get about-index}
     ["/allergies" {:get allergies-list}]
     ["/foods" {:get foods-list}]
     ["/diets" {:get diets-list}]
     ["/cuisines" {:get cuisines-list}]]]])

(def service {:env :prod
              ::bootstrap/routes routes
              ::bootstrap/allowed-origins ["http://localhost:8080"]
              ::bootstrap/resource-path "/public"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
