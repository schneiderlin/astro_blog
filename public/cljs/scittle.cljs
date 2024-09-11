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

(defn echart-comoponent []
  (let [ref (use-script {:code "\n{\n  var myChart = echarts.init(document.currentScript.parentElement);\n  myChart.setOption({\"title\":{\"text\":\"Echarts Example\"},\"tooltip\":{},\"legend\":{\"data\":[\"sales\"]},\"xAxis\":{\"data\":[\"Shirts\",\"Cardigans\",\"Chiffons\",\"Pants\",\"Heels\",\"Socks\"]},\"yAxis\":{},\"series\":[{\"name\":\"sales\",\"type\":\"bar\",\"data\":[5,20,36,10,10,20]}]});\n};"})]
    [:div {:style {:height "400px" :width "100%"}
           :ref ref}]))

(defn wrap-script [{:keys [url]} & children]
  (let [ref (use-script {:url url})]
    [:div {:ref ref}]))

(defn- get-tag [hiccup]
  (if (vector? hiccup)
    (let [[tag attrs & children] hiccup]
      tag)
    nil))

;; not necessary has options
(defn- get-attr [hiccup]
  (if (vector? hiccup)
    (let [[tag attrs & children] hiccup]
      attrs)
    nil))

(defn- get-children [hiccup]
  (if (vector? hiccup)
    (let [[tag attrs & children] hiccup]
      children)
    nil))

(defn wrap-script1 [{:keys [url] :as props} & children]
  (println "props" props)
  (println "children" children)
  (let [ref (use-script {:url url})]
    (if (nil? children)
      [:div {:ref ref}]
      (let [first-child (first children)
            first-tag (get-tag first-child)
            new-attrs (assoc (get-attr first-child) :ref ref)
            new-first-child [first-tag new-attrs (get-children first-child)]]
        (into [new-first-child] (rest children))))))

(defn my-component [] 
  [:div
   [:f> vega-component]
  ;;  [:f> wrap-script {:url "https://cdn.jsdelivr.net/npm/vega@5.20.2"}]
   [:f> wrap-script1 {:url "https://cdn.jsdelivr.net/npm/vega@5.20.2"} [:p "hello"]]
   [:f> echart-comoponent]
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