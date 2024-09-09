(require '[reagent.core :as r]
         '[reagent.dom :as rdom])

(defn my-component []
  [:div 
   [:div
    [:pre
     [:code.sourceCode.language-clojure.source-clojure.bg-light
      "(ns macro1\n  (:require \n   [scicloj.kindly.v4.kind :as kind]))"]]]
   [:p "some comment here"]
   [:div {:style {:background-color "#eeddee" :border-style "solid"}}
    [:p {:style {:background-color "#ccddcc" :border-style "solid"}} "hello"]
    [:div {} [:p "hello " [:em "hello"] " " [:strong "hello"]]]
    [:div {}
     [:pre
      [:code.sourceCode.language-clojure.source-clojure.bg-light
       "(defn f [x] (+  x 9))"]]]]
   [:div
    [:div
     [:script
      "katex.render(\"x^2=\\\\alpha\", document.currentScript.parentElement, {throwOnError: false});"]]]
   [:p "If $x$ equals 9, then $$x^2+9=90$$"]
   ""
   [:div
    [:pre
     [:code.sourceCode.language-clojure.source-clojure.bg-light "(def x 1)"]]]
   [:div [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light "1"]]]
   [:div [:pre [:code.sourceCode.language-clojure.printed-clojure "1\r\n"]]]
   [:div
    [:pre
     [:code.sourceCode.language-clojure.source-clojure.bg-light
      "&quot;str&quot;"]]]
   [:div
    [:pre
     [:code.sourceCode.language-clojure.printed-clojure "&quot;str&quot;\r\n"]]]
   [:div [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light "1.1"]]]
   [:div [:pre [:code.sourceCode.language-clojure.printed-clojure "1.1\r\n"]]]
   [:div
    [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light ":keyword"]]]
   [:div
    [:pre [:code.sourceCode.language-clojure.printed-clojure ":keyword\r\n"]]]
   [:div [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light "nil"]]]
   [:div [:pre [:code.sourceCode.language-clojure.printed-clojure "nil\r\n"]]]
   [:div
    [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light "true"]]]
   [:div [:pre [:code.sourceCode.language-clojure.printed-clojure "true\r\n"]]]
   [:div
    [:pre [:code.sourceCode.language-clojure.source-clojure.bg-light "[1 2 3]"]]]
   [:div [:pre [:code.sourceCode.language-clojure.printed-clojure "[1 2 3]\r\n"]]]
   [:div
    [:pre [:code.sourceCode.language-clojure.printed-clojure "{:a 1, :b 2}\r\n"]]]
   [:div {:style {:height "2px" :width "100%" :background-color "grey"}}]
   [:div nil]])

(rdom/render [my-component] (.getElementById js/document "app"))