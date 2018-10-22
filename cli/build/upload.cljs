
(ns build.upload
  (:require ["child_process" :as child-process]
            [app.config :as config]))

(def configs {:orgization "Lumo"
              :name "lumo-cljs.org"
              :cdn "lumo-cljs-org"})

(defn sh! [command]
  (println command)
  (println (.toString (child-process/execSync command))))

(defn -main []
  (sh! (str "rsync -avr --progress dist/* " (:cdn-folder config/site)))
  (sh! (str "rsync -avr --progress dist/{index.html,manifest.json} "
            (:upload-folder config/site))))
