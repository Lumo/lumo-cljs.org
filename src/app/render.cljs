
(ns app.render
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [app.comp.container :refer [comp-container]]
            [app.schema :as schema]
            [reel.schema :as reel-schema]
            [cljs.reader :refer [read-string]]
            [app.schema :refer [dev?]]))

(def base-info
  {:title "Lumo: Fast, cross-platform, standalone ClojureScript environment.",
   :icon "http://cdn.tiye.me/logo/lumo.png",
   :ssr nil,
   :inline-html nil,
   :inline-styles [(if dev? "" (slurp "./entry/main.css"))
                   (slurp "./node_modules/highlight.js/styles/github-gist.css")]})

(defn dev-page []
  (make-page
   ""
   (merge
    base-info
    {:styles (if dev?
       ["http://localhost:8100/main.css" "/entry/main.css"]
       ["http://localhost:8100/main.css"]),
     :scripts ["/main.js"]})))

(def preview? (= "preview" js/process.env.prod))

(defn prod-page []
  (let [reel (-> reel-schema/reel (assoc :base schema/store) (assoc :store schema/store))
        html-content (make-string (comp-container reel))
        assets (read-string (slurp "dist/assets.edn"))
        cdn (if preview? "" "http://cdn.tiye.me/lumo-cljs-org/")
        prefix-cdn (fn [x] (str cdn x))]
    (make-page
     html-content
     (merge
      base-info
      {:styles ["http://cdn.tiye.me/favored-fonts/main.css"],
       :scripts (map #(-> % :output-name prefix-cdn) assets),
       :ssr "respo-ssr"}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))
