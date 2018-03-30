
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
            [respo.util.list :refer [map-with-idx]]
            ["highlight.js" :as hljs]
            ["escape-html" :as escape-html]))

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
    {:style {:padding "8px 16px", :background-color (hsl 230 80 70 0.2)}}
    (comp-md-block
     "```\n$ npm install -g lumo-cljs\n$ lumo\nLumo 1.8.0\nClojureScript 1.9.946\nNode.js v9.2.0\n\ncljs.user=> (println (+ 1 2 3))\n6\nnil\ncljs.user=>\n```"
     {:style {},
      :css "pre code {\ncolor: white;\nline-height: 1.6em;\nfont-family: Source Code Pro, Menlo, Courier, monospace;\n}"})))
  (=< nil 40)
  (div
   {:style {:font-family ui/font-fancy, :color :white, :font-size 24, :font-weight 100}}
   (<>
    "Lumo is a standalone ClojureScript environment that runs on Node.js and the V8 JavaScript engine."))))

(def supported-langs #{"clojure" "bash" "js"})

(defcomp
 comp-intro
 ()
 (div
  {:style (merge ui/center {:font-size 16, :padding 16}), :class-name "intro"}
  (comp-md-block
   "Lumo is a standalone ClojureScript environment that runs on Node.js and the V8 JavaScript engine. It starts up instantaneously and has out-of-the-box access to the entire Node.js ecosystem.\n\nTo install:\n\n```bash\n$ brew install lumo\n# or\n$ npm install -g lumo-cljs\n```\n\nAfter installed, you will a CLI tool called `lumo`.\n\n### REPL\n\n```bash\n=>> lumo\nLumo 1.8.0\nClojureScript 1.9.946\nNode.js v9.2.0\n Docs: (doc function-name-here)\n       (find-doc \"part-of-name-here\")\n Source: (source function-name-here)\n Exit: Control+D or :cljs/quit or exit\n\ncljs.user=> (println (+ 1 2 3))\n6\nnil\ncljs.user=>\n```\n\n### Node.js & npm modules\n\nAccess Node.js built-in variables with js interop:\n\n```clojure\n(.log js/console js/process.argv)\n```\n\nLoad npm modules from `node_modules/` with `js/require`:\n\n```bash\ncljs.user=> (def escape-html (js/require \"escape-html\"))\n#'cljs.user/escape-html\ncljs.user=> (escape-html \"<div />\")\n\"&lt;div /&gt;\"\n```\n\nAlso possible to specify dependencies in side `ns` form:\n\n```clojure\n(ns app.main (:require [\"fs\" :as fs]))\n\n(fs/readFileSync \"package.json\" \"utf8\")\n```\n\n### Interpreter\n\nRun a file\n\n```bash\n$ lumo main.cljs\n```\n\n### CLI arguments\n\nTo reaRead\n\n```bash\n$ lumo -h\n```\n\n### More...\n\nFind out more on [Wiki](https://github.com/anmonteiro/lumo/wiki)..."
   {:highlight (fn [code lang]
      (if (contains? supported-langs lang)
        (.-value (.highlight hljs lang code))
        (escape-html code)))})))

(def site-links
  [{:title "GitHub", :url "https://github.com/anmonteiro/lumo"}
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
