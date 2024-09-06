(require '[reagent.core :as r]
         '[reagent.dom :as rdom])

(def state (r/atom {:clicks 0}))

(defn my-component []
  [:div
   [:p "Clicks: " (:clicks @state)]
   [:p [:button {:on-click #(swap! state update :clicks inc)}
        "Click me!"]]])

(println (r/reactify-component my-component))

(rdom/render [my-component] (.getElementById js/document "app"))