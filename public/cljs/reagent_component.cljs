(require '[reagent.core :as r]
         '[reagent.dom :as rdom])

(def app (.getElementById js/document "app"))
(def code (.getElementById js/document "clojure_code"))
(def button (.getElementById js/document "re_render_button"))

(.addEventListener button "click"
                   (fn []
                     (let [code (.-value code)
                           form (read-string code)
                           _ (println form)] 
                       (rdom/render form (.getElementById js/document "app")))))