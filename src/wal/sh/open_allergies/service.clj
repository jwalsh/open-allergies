(ns wal.sh.open-allergies.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [ring.util.response :as ring-resp]
            [ring.middleware.json :refer [wrap-json-response]]
            [clojure.data.json :as json]
            [ring.util.response :as response]))

(deftype Food [^String name ^String description ^Integer rake ^Boolean display_like ^Boolean display_dislike])

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

(defn transform-name-entity [name]
  (let [entity {
                 :created 0
                 :updated 0
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

(defn foods-list
  [request]
  (let [foods [{:name "Pizza"}]]
    (ring-resp/response (json/write-str (map transform-name-entity FOODS)))))

(defn cuisines-list
  [request]
  (ring-resp/response (json/write-str (map transform-name-entity CUISINES))))

(defn allergies-list
  [request]
  (ring-resp/response [1 2 3]))

(defroutes routes
  [[["/" {:get about-index}
     ["/foods" {:get foods-list}]
     ["/cuisines" {:get cuisines-list}]]]])

(def service {:env :prod
              ::bootstrap/routes routes
              ::bootstrap/allowed-origins ["http://localhost:8080"]
              ::bootstrap/resource-path "/public"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
