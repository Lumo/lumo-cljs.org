
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros
             :refer
             [defcomp cursor-> action-> mutation-> list-> <> div button textarea span a]]
            [verbosely.core :refer [verbosely!]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md comp-md-block]]
            [app.schema :refer [dev?]]
            [respo.util.list :refer [map-with-idx]]))

(defcomp
 comp-hero
 ()
 (div
  {:style (merge ui/center {:height 600, :background-color "rgb(32, 42, 49)"})}
  (div
   {:style (merge ui/row-center)}
   (div
    {:style {:background-image "url(http://cdn.tiye.me/logo/lumo.png)",
             :background-size :cover,
             :width 200,
             :height 200}})
   (=< 80 nil)
   (div
    {:style {:padding "8px 16px", :background-color (hsl 230 80 70 0.2)}}
    (comp-md-block
     "```\n$ npm install -g lumo-cljs\n$ lumo\nLumo 1.7.0\nClojureScript 1.9.908\nNode.js v8.4.0\n\ncljs.user=> (println (+ 1 2 3))\n6\nnil\ncljs.user=>\n```"
     {:style {},
      :css "pre code {\ncolor: white;\nline-height: 1.6em;\nfont-family: Source Code Pro, Menlo, Courier, monospace;\n}"})))
  (=< nil 40)
  (div
   {:style {:font-family ui/font-fancy, :color :white, :font-size 24, :font-weight 100}}
   (<>
    "Lumo is a standalone ClojureScript environment that runs on Node.js and the V8 JavaScript engine."))))

(defcomp
 comp-intro
 ()
 (div
  {:style (merge ui/center {:font-size 16, :padding 16})}
  (comp-md-block
   "Lumo has many features. I just don't have enough time to list all of them..."
   {})))

(def site-links
  [{:title "GitHub", :url "https://github.com/anmonteiro/lumo"}
   {:title "Blog", :url "https://anmonteiro.com/"}])

(defcomp
 comp-links-bar
 ()
 (list->
  {:style (merge ui/row-center {:padding 16, :background-color (hsl 240 40 94)})}
  (->> site-links
       (map-with-idx
        (fn [link]
          (a
           {:href (:url link),
            :style {:font-size 16,
                    :text-decoration :none,
                    :color (hsl 240 80 70),
                    :padding "0 16px"},
            :target "_blank"}
           (<> (:title link))))))))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   (div
    {:style (merge ui/global)}
    (comp-hero)
    (comp-links-bar)
    (comp-intro)
    (div {:style {:height 200, :background-color "rgb(32, 42, 49)"}})
    (when dev? (cursor-> :reel comp-reel states reel {})))))
