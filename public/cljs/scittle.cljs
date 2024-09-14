(require '[reagent.dom :as rdom])

(defn- get-tag [hiccup]
  (if (vector? hiccup)
    (if (map? (second hiccup))
      (let [[tag attrs & children] hiccup]
        tag)
      (let [[tag & children] hiccup]
        tag))
    (do
      (println "not a vector? " hiccup)
      nil)))

(defn- get-attr [hiccup]
  (if (vector? hiccup)
    (if (map? (second hiccup))
      (let [[tag attrs & children] hiccup]
        attrs)
      (let [[tag & children] hiccup]
        {}))
    nil))

(defn- get-children [hiccup]
  (if (vector? hiccup)
    (if (map? (second hiccup))
      (let [[tag attrs & children] hiccup]
        children)
      (let [[tag & children] hiccup]
        children))
    []))

(defn use-script 
  "a custom hook to add script tags to the dom after the component rendered.
   scripts is a collection of hiccup form [:script {:src '...' :type '...'} js-code]
   this function return a ref, pass this ref to the dom element you want to add script tags."
  [{:keys [scripts]}]
  (let [ref (js/React.useRef)
        _ (js/React.useEffect
           (fn []
             (when (.-current ref) ;; when the DOM element contains this ref is rendered
               (let [;; create script elements
                     script-elems 
                     (map (fn [_x]
                            (js/document.createElement "script")) scripts)]
                 ;; set attributes and content for each script element, then add to the dom
                 (doseq [[script-elem script] (map vector script-elems scripts)]
                   (let [attrs (get-attr script)
                         content (first (get-children script))]
                     (doseq [[attr val] attrs]
                       (.setAttribute script-elem (name attr) val))
                     (set! (.-textContent script-elem) content)
                     (.appendChild (.-current ref) script-elem)))
                 ;; clean up
                 (fn []
                   (doseq [script-elem script-elems]
                     (.removeChild (.-current ref) script-elem))))))
           [scripts ref])]
    ref))

(defn wrap-scripts
  "given a hiccup form, add script tags to the root"
  [{:keys [scripts] :as props} & children]
  (let [ref (use-script {:scripts scripts})
        child (first children)]
    (if (nil? child)
      [:div {:ref ref}]
      (let [tag (get-tag child)
            new-attrs (assoc (get-attr child) :ref ref)
            new-child [tag new-attrs (get-children child)]]
        new-child))))

(defn my-component []
  [:div
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML
       {:__html
        "(ns macro1\n  (:require\n   [scicloj.kindly.v4.kind :as kind]))"}}]]]
   [:p "values"]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "&quot;str&quot;"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "&quot;str&quot;\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "1"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "1\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "1.1"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "1.1\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html ":keyword"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html ":keyword\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "nil"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "nil\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "true"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "true\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "[1 2 3]"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "[1 2 3]\r\n"}}]]]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure printed-clojure"
       :dangerouslySetInnerHTML {:__html "{:a 1, :b 2}\r\n"}}]]]
   [:p "tex"]
   [:div
    [:f> wrap-scripts
     {:scripts
      [[:script
        "katex.render(\"x^2=\\\\alpha\", document.currentScript.parentElement, {throwOnError: false});"]]}
     ["div" {}]]]
   [:p "md"]
   [:p "If $x$ equals 9, then $$x^2+9=90$$"]
   [:p "hidden"]
   ""
   [:p "code"]
   [:div
    [:pre
     [:code
      {:class "sourceCode language-clojure source-clojure bg-light"
       :dangerouslySetInnerHTML {:__html "(def x 1)"}}]]]
   [:p "image"]
   [:img {:src "macro1_files/3.png"}]
   [:p "reagent"]
   [:f> wrap-scripts
    {:scripts
     [[:script {:type "application/x-scittle"}
       "(reagent.dom/render [(fn [{:keys [initial-value background-color]}] (let [*click-count (reagent.core/atom initial-value)] (fn [] [:div {:style {:background-color background-color}} \"The atom \" [:code \"*click-count\"] \" has value: \" (clojure.core/deref *click-count) \". \" [:input {:type \"button\", :value \"Click me!\", :on-click (fn* [] (swap! *click-count inc))}]]))) {:initial-value 9, :background-color \"#d4ebe9\"}] (js/document.getElementById \"id3\"))"]]}
    ["div" {:id "id3"}]]
   [:p "echarts"]
   [:f> wrap-scripts
    {:scripts
     [[:script
       "\n{\n  var myChart = echarts.init(document.currentScript.parentElement);\n  myChart.setOption({\"title\":{\"text\":\"Echarts Example\"},\"tooltip\":{},\"legend\":{\"data\":[\"sales\"]},\"xAxis\":{\"data\":[\"Shirts\",\"Cardigans\",\"Chiffons\",\"Pants\",\"Heels\",\"Socks\"]},\"yAxis\":{},\"series\":[{\"name\":\"sales\",\"type\":\"bar\",\"data\":[5,20,36,10,10,20]}]});\n};"]]}
    ["div" {:style {:height "400px" :width "100%"}}]]
   [:p "vega-lite"]
   [:f> wrap-scripts
    {:scripts
     [[:script
       "vegaEmbed(document.currentScript.parentElement, {\"encoding\":{\"y\":{\"field\":\"y\",\"type\":\"quantitative\"},\"size\":{\"value\":400},\"x\":{\"field\":\"x\",\"type\":\"quantitative\"}},\"mark\":{\"type\":\"circle\",\"tooltip\":true},\"width\":400,\"background\":\"floralwhite\",\"height\":100,\"data\":{\"url\":\"macro1_files\\/1.csv\",\"format\":{\"type\":\"csv\"}}});"]]}
    ["div" {}]]
   [:p "highcharts"]
   [:f> wrap-scripts
    {:scripts
     [[:script
       "Highcharts.chart(document.currentScript.parentElement, {\"title\":{\"text\":\"Line chart\"},\"subtitle\":{\"text\":\"By Job Category\"},\"yAxis\":{\"title\":{\"text\":\"Number of Employees\"}},\"series\":[{\"name\":\"Installation & Developers\",\"data\":[43934,48656,65165,81827,112143,142383,171533,165174,155157,161454,154610]},{\"name\":\"Manufacturing\",\"data\":[24916,37941,29742,29851,32490,30282,38121,36885,33726,34243,31050]},{\"name\":\"Sales & Distribution\",\"data\":[11744,30000,16005,19771,20185,24377,32147,30912,29243,29213,25663]},{\"name\":\"Operations & Maintenance\",\"data\":[null,null,null,null,null,null,null,null,11164,11218,10077]},{\"name\":\"Other\",\"data\":[21908,5548,8105,11248,8989,11816,18274,17300,13053,11906,10073]}],\"xAxis\":{\"accessibility\":{\"rangeDescription\":\"Range: 2010 to 2020\"}},\"legend\":{\"layout\":\"vertical\",\"align\":\"right\",\"verticalAlign\":\"middle\"},\"plotOptions\":{\"series\":{\"label\":{\"connectorAllowed\":false},\"pointStart\":2010}},\"responsive\":{\"rules\":[{\"condition\":{\"maxWidth\":500},\"chartOptions\":{\"legend\":{\"layout\":\"horizontal\",\"align\":\"center\",\"verticalAlign\":\"bottom\"}}}]}});"]]}
    ["div" {}]]
   [:p "cytoscape"]
   [:f> wrap-scripts
    {:scripts
     [[:script
       "\n{\n  value = {\"elements\":{\"nodes\":[{\"data\":{\"id\":\"a\",\"parent\":\"b\"},\"position\":{\"x\":215,\"y\":85}},{\"data\":{\"id\":\"b\"}},{\"data\":{\"id\":\"c\",\"parent\":\"b\"},\"position\":{\"x\":300,\"y\":85}},{\"data\":{\"id\":\"d\"},\"position\":{\"x\":215,\"y\":175}},{\"data\":{\"id\":\"e\"}},{\"data\":{\"id\":\"f\",\"parent\":\"e\"},\"position\":{\"x\":300,\"y\":175}}],\"edges\":[{\"data\":{\"id\":\"ad\",\"source\":\"a\",\"target\":\"d\"}},{\"data\":{\"id\":\"eb\",\"source\":\"e\",\"target\":\"b\"}}]},\"style\":[{\"selector\":\"node\",\"css\":{\"content\":\"data(id)\",\"text-valign\":\"center\",\"text-halign\":\"center\"}},{\"selector\":\"parent\",\"css\":{\"text-valign\":\"top\",\"text-halign\":\"center\"}},{\"selector\":\"edge\",\"css\":{\"curve-style\":\"bezier\",\"target-arrow-shape\":\"triangle\"}}],\"layout\":{\"name\":\"preset\",\"padding\":5}};\n  value['container'] = document.currentScript.parentElement;\n  cytoscape(value);\n};"]]}
    ["div" {:style {:height "400px" :width "100%"}}]]
   [:p "plotly"]
   [:f> wrap-scripts
    {:scripts
     [[:script
       "Plotly.newPlot(document.currentScript.parentElement,\n              [{\"x\":[1.2339249495715812,2.241807260223138,3.206331843159038,4.488899258206231,5.124434032238843,6.473559948945006,7.124367519391906,8.54799253147057,10.034324601233102,11.482520869230274,12.487200119774533,13.17330630861756,14.45050506681567,15.020046163164675,15.75359788253432,17.24541405760069,17.790495879420458,18.662356795341573,20.131260367478625,20.755731390377715],\"y\":[-0.5968488838501987,-1.6306113562416424,-2.2646807543471774,-3.278497742483512,-4.516362154434404,-5.05187213598083,-6.391857168509864,-7.1163935468007,-7.720937449773031,-8.47512098361236,-9.020907448164442,-9.689992665865802,-10.62574261380436,-11.29092461105493,-12.449857485910098,-13.009413373977301,-14.246304188433754,-14.766012699669666,-16.193628386058734,-17.190875511519643],\"z\":[3.1446947914741146,12.425937611246404,29.23888690191469,48.348518140641865,73.4620828379239,106.0930292264382,144.99887258219675,204.47828153609527,272.21323338604105,347.86534685484895,417.2303498443034,496.81027709585356,608.405249585372,722.6158280069326,852.2631025197927,966.2852641156027,1093.1802217103193,1232.9270653395229,1344.0957548647473,1505.1528421003839],\"type\":\"scatter3d\",\"mode\":\"lines+markers\",\"opacity\":0.2,\"line\":{\"width\":10},\"marker\":{\"size\":20,\"colorscale\":\"Viridis\"}}], {}, {});"]]}
    ["div" {:style {:height "400px" :width "100%"}}]]
   [:p "\n# table"]
   [:table {:class "table table-hover table-responsive"}
    [:thead [:tr [:th :x] [:th :y]]]
    [:tbody [:tr [:td 0] [:td :A]] [:tr [:td 1] [:td :B]] [:tr [:td 2] [:td :C]]
     [:tr [:td 3] [:td :A]] [:tr [:td 4] [:td :B]] [:tr [:td 5] [:td :C]]]]
   [:p "video"]
   [:iframe
    {:src "https://www.youtube.com/embed/DAQnvAgBma8" :allowfullscreen true}]
   [:div {:style {:height "2px" :width "100%" :background-color "grey"}}]
   [:div nil]])


(rdom/render [:f> my-component] (.getElementById js/document "app"))