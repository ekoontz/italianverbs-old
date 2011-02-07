(ns italianverbs
  (:use slice.core
        slice.compojure5                ; make slices render automatically
        uteal.core                      ; defs FTW
        compojure.core                  ; slice isn't tied to a web "framework"
        ring.adapter.jetty
        hiccup.form-helpers))

;; when deploying use
;; (slice-memoize! true)

(defs
  ;; strings
  company-name*    "foofers.org presents Verbi Italiani"
  app-name*        "Verbi Italiani"

  ;; colors
  site-color*      "blue"
  important-color* "lightgreen"

  ;; ids
  logo-id*         "#logo"
  download-id*     "#download"
  subscribe-id*    "#subscribe"

  ;; classes
  buttons*         "button"

  ;; mixins
  rounded-corners* (list :-moz-border-radius :5px
                         :-webkit-border-radius :5px)
  big-text*        (list :font-size "100%")
  special-button*  (list rounded-corners*))

(slice awesome-effect
  jquery
  (js (fn awesomeEffect [div]
        (.fadeOut ($ div))
        (.fadeIn ($ div)))))

(slice mouse-effect [id]
  awesome-effect
  (dom (.mouseover ($ ~id) (fn [] (awesomeEffect ~id)))))

(slice button [id text color]
  (html (submit-button {:id (wo# id) :class buttons*} text))
  (css [(wdot buttons*) rounded-corners* :color color]))

(slice on-click-alert [id msg]
  (dom (.click ($ ~id) (fn [] (alert ~msg)))))

(slice download-button
  (on-click-alert download-id* "Ain't slices cool?")
  (css [download-id* special-button*])
  (button download-id* "Download Android App!" important-color*))

(slice subscribe-button
  jquery
  (on-click-alert subscribe-id* (str "Subscribed to " company-name* " newsletter."))
  (button subscribe-id* "Subscribe!" important-color*))

(slice header [text & [id]]
       (title "Verbi Italiani")
       (html [:h1 {:id (wo# id)} text]))

(defn navigation []
  (html 
   [:div {:style "float:left;width:auto;padding:10px;"} [:a {:href "/"} "top"]]
   [:div {:style "float:left;width:auto;padding:10px;"} [:a {:href "/quiz/"} "take a quiz..."]]
   [:div {:style "float:left;width:auto;padding:10px;"} [:a {:href "http://github.com/ekoontz/italianverbs"} "source code"]]
))

(defn navigation []
  (html 
   [:div [:a {:href "/"} "top"]]
   [:div [:a {:href "quiz/"} "take a quiz..."]]
))

(slice site-header
  (mouse-effect logo-id*)
  (header company-name* logo-id*)
  ;; navigation
  (navigation)
  (css [logo-id*
        big-text*
        :color site-color*]))

(load-file "src/table.clj")

(load-file "src/table.clj")

(slice app-section
  (header app-name*)
  verb-table
  download-button)

(slice main-page
       ;;FIXME: title is not being displayed.
       (title company-name*)
       site-header
       app-section
       subscribe-button)

(load-file "src/quiz.clj")

(def sessions (hash-map))

(def sessions (assoc sessions 'reqid 0))

(slice update-state
       (let [update (def sessions (assoc sessions 'reqid (+ 1 (get sessions 'reqid))))]
            (html [:div {:style "float:left;border:1px dashed green"} (str "request_id: " (get sessions 'reqid))])))


(load-file "src/quiz.clj")

(defroutes app
      (GET "/"          _ (main-page))
      (GET "/subscribe" _ (slices site-header subscribe-button))
      (GET "/quiz*" _ (slices site-header quiz update-state))
      (GET "/test"      r (slices jquery
                                  (dom (alert ~(:remote-addr r)))
                                  (html [:h1 "Hi"])
                                  (css [:h1 :color "blue"]))))

(defonce server (run-jetty #'app {:port 8888 :join? false}))
