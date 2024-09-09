(require '[reagent.core :as r]
         '[reagent.dom :as rdom])

(defn my-custom-hook []
  (let [[count set-count] (js/React.useState 0)]
    {:count count
     :set-count set-count}))

(defn my-stateful-component []
  (let [{:keys [count set-count]} (my-custom-hook)]
     [:div
      [:p "You clicked " count " times"]
      [:button
       {:on-click #(set-count inc)}
       "Click"]]))

(defn use-script [{:keys [url code]}]
  (let [ref (js/React.useRef)
        _ (js/React.useEffect
           (fn []
             (when (.-current ref)
               (let [script (js/document.createElement "script")]
                 (when url
                   (.setAttribute script "src" url)
                   (.setAttribute script "type" "application/javascript"))
                 (when code
                   (set! (.-textContent script) code)
                   (.setAttribute script "type" "application/javascript"))
                 (.appendChild (.-current ref) script)
                 (fn []
                   (.removeChild (.-current ref) script)))))
           [url code ref])]
    ref))

(defn vega-component []
  (let [ref (use-script {:code "vegaEmbed(document.currentScript.parentElement, {\"encoding\":{\"y\":{\"field\":\"y\",\"type\":\"quantitative\"},\"size\":{\"value\":400},\"x\":{\"field\":\"x\",\"type\":\"quantitative\"}},\"mark\":{\"type\":\"circle\",\"tooltip\":true},\"width\":400,\"background\":\"floralwhite\",\"height\":100,\"data\":{\"url\":\"macro1_files\\/0.csv\",\"format\":{\"type\":\"csv\"}}});"})]
    [:div {:ref ref}]))

(defn my-component [] 
  [:div
   [:f> vega-component]
   [:h1 {:ref (fn [el] (println "h1" el))} "Scittle"]
   [:f> my-stateful-component]
   [(fn [{:keys [initial-value
                 background-color]}]
      (let [*click-count (reagent.core/atom initial-value)]
        (fn []
          [:div {:style {:background-color background-color}}
           "The atom " [:code "*click-count"] " has value: "
           @*click-count ". "
           [:input {:type "button" :value "Click me!"
                    :on-click #(swap! *click-count inc)}]])))
    {:initial-value 9
     :background-color "#d4ebe9"}]])


(rdom/render [:f> my-component] (.getElementById js/document "app"))