(ns italianverbs)

(defn wrapchoice [word]
  ;; FIXME: url-encode word.
  (let [href_prefix "/quiz?"]
       (html [:h3 [:a {:href (str href_prefix "guess=" word)} word]])))

(defn guess ^{:impure true}  [lexicon remaining answer show-true-at]
  (html
   (if (= remaining show-true-at)
       (wrapchoice (str answer "(true)")))
   (if (> remaining 0)
       (let [choice (rand-int (count lexicon))]
            (html (wrapchoice (str (get lexicon (nth (keys lexicon) choice))))
                  (guess (dissoc lexicon (nth (keys lexicon) choice)) (- remaining 1) answer show-true-at))))))

(defn guesses ^{:impure true} [question answer lexicon true-index]
  (let [multiple-choices 3]
       (html
        ;; 4 choices; show true answer at a random position amongst the false ones.
        ;; TODO: remove true answer from lexicon in (guess) call.
        (guess (dissoc lexicon question)  multiple-choices answer (rand-int (+ 1 multiple-choices))))))

(slice next-question [index]
       (html [:div {:style "width:100%;float:left;border:0px dashed blue"} [:h2 [:i (nth (keys lexicon) index)]]
                [:div {:style "padding-left:1em"}
                   (guesses (nth (keys lexicon) index)
                            (get lexicon (nth (keys lexicon) index))
                            lexicon index)]]))

(slice quiz ^{:impure true} []
       (next-question (rand-int (count lexicon))))



