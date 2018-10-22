
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros
             :refer
             [defcomp cursor-> action-> mutation-> list-> <> div button textarea span a img]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md comp-md-block]]
            [app.schema :refer [dev?]]
            [respo.util.list :refer [map-with-idx]]
            ["highlight.js" :as hljs]
            ["escape-html" :as escape-html]
            [build.util :refer [inline-resource]]))

(defcomp
 comp-footer
 ()
 (div
  {:style {:height 200, :background-color "rgb(32, 42, 49)", :padding "8px"}}
  (div
   {:style {:width 800, :margin :auto}}
   (a
    {:href "https://github.com/Lumo/lumo-cljs.org",
     :style {:color (hsl 240 80 80)},
     :target "_blank",
     :inner-text "Site built with ClojureScript."}))))

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
             :background-position :center,
             :width 200,
             :height 200}})
   (=< 80 nil)
   (div
    {:style {:padding "8px 16px",
             :border (str "1px solid " (hsl 0 0 100 0.2)),
             :border-radius "8px"}}
    (comp-md-block
     "```\n$ npm install -g lumo-cljs\n$ lumo\nLumo 1.8.0\nClojureScript 1.9.946\nNode.js v9.2.0\n\ncljs.user=> (println (+ 1 2 3))\n6\nnil\ncljs.user=>\n```"
     {:style {},
      :css "pre code {\ncolor: white;\nline-height: 1.6em;\nfont-family: Source Code Pro, Menlo, Courier, monospace;\n}"})))
  (=< nil 40)
  (div
   {:style {:font-family ui/font-fancy, :color :white, :font-size 24, :font-weight 100}}
   (<>
    "Lumo is a standalone ClojureScript environment that runs on Node.js and the V8 JavaScript engine.")
   (=< 8 nil)
   (img {:src "https://img.shields.io/npm/v/lumo-cljs.svg"}))))

(def supported-langs #{"clojure" "bash" "js"})

(defcomp
 comp-intro
 ()
 (div
  {:style (merge ui/center {:font-size 16, :padding 16}), :class-name "intro"}
  (comp-md-block
   (inline-resource "content.md")
   {:highlight (fn [code lang]
      (if (contains? supported-langs lang)
        (.-value (.highlight hljs lang code))
        (escape-html code)))})))

(def site-links
  [{:title "GitHub", :url "https://github.com/anmonteiro/lumo"}
   {:title "Slack", :url "https://clojurians.slack.com/messages/lumo"}
   {:title "Blog", :url "https://anmonteiro.com/"}
   {:title "Wiki", :url "https://github.com/anmonteiro/lumo/wiki"}])

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
                    :color (hsl 240 30 70),
                    :padding "0 16px"},
            :target "_blank"}
           (<> (:title link))))))))

(defcomp
 comp-video
 ()
 (div
  {:style {:margin 16}}
  (div
   {:style {:margin "auto", :width 800, :background-color (hsl 0 0 90)},
    :alt "Youtube Video",
    :innerHTML "<iframe width=\"800\" height=\"450\" src=\"https://www.youtube.com/embed/jH1oJiLV7_0\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"})))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   (div
    {:style (merge ui/global {:background-color :white})}
    (comp-hero)
    (comp-links-bar)
    (comp-video)
    (comp-intro)
    (comp-footer)
    (when dev? (cursor-> :reel comp-reel states reel {})))))
