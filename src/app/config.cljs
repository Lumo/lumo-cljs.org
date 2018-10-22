
(ns app.config (:require [app.util :refer [get-env!]]))

(def bundle-builds #{"release" "local-bundle"})

(defn dev? [] )

(def site
  {:storage "lumo",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/lumo-cljs-org/",
   :cdn-folder "tiye.me:cdn/lumo-cljs-org",
   :title "Lumo: Fast, cross-platform, standalone ClojureScript environment.",
   :icon "http://cdn.tiye.me/logo/lumo.png",
   :upload-folder "tiye.me:repo/Lumo/lumo-cljs.org/"})
