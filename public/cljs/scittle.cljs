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

#_(defn vega-component []
  (let [ref (use-script {:code "vegaEmbed(document.currentScript.parentElement, {\"encoding\":{\"y\":{\"field\":\"y\",\"type\":\"quantitative\"},\"size\":{\"value\":400},\"x\":{\"field\":\"x\",\"type\":\"quantitative\"}},\"mark\":{\"type\":\"circle\",\"tooltip\":true},\"width\":400,\"background\":\"floralwhite\",\"height\":100,\"data\":{\"url\":\"macro1_files\\/0.csv\",\"format\":{\"type\":\"csv\"}}});"})]
    [:div {:ref ref}]))

#_(defn echart-comoponent []
  (let [ref (use-script {:code "\n{\n  var myChart = echarts.init(document.currentScript.parentElement);\n  myChart.setOption({\"title\":{\"text\":\"Echarts Example\"},\"tooltip\":{},\"legend\":{\"data\":[\"sales\"]},\"xAxis\":{\"data\":[\"Shirts\",\"Cardigans\",\"Chiffons\",\"Pants\",\"Heels\",\"Socks\"]},\"yAxis\":{},\"series\":[{\"name\":\"sales\",\"type\":\"bar\",\"data\":[5,20,36,10,10,20]}]});\n};"})]
    [:div {:style {:height "400px" :width "100%"}
           :ref ref}]))

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

;; not necessary has options
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

(defn use-script [{:keys [scripts]}]
  (let [ref (js/React.useRef)
        _ (js/React.useEffect
           (fn []
             (when (.-current ref)
               (let [script-elems
                     (map (fn [_x]
                            (js/document.createElement "script")) scripts)]
                 (doseq [[script-elem script] (map vector script-elems scripts)]
                   (let [attrs (get-attr script)
                         content (first (get-children script))
                         _ (println "attrs: " attrs)]
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
   [:f> wrap-scripts {:scripts [[:script
                                 {:type "application/x-scittle"}
                                 "(println \" from scittle \")"]
                                [:script
                                 {:type "application/javascript"}
                                 "console.log('from js')"]]}
    [:div "hello"]]])


(rdom/render [:f> my-component] (.getElementById js/document "app"))