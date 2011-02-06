(ns slice.example
  (:use slice.core
        slice.compojure5                ; make slices render automatically
        uteal.core                      ; defs FTW
        compojure.core                  ; slice isn't tied to a web "framework"
        ring.adapter.jetty
        hiccup.form-helpers))

(defn guess ^{:impure true}  [lexicon remaining answer show-true-at]
  (if (> remaining 0)
      (html [:h3 (get lexicon (nth (keys lexicon) (rand-int (count lexicon))))]
            (if (= remaining show-true-at)
                [:h3 answer])
            ;; TODO : remove this guess from lexicon for recursive call.
            (guess lexicon (- remaining 1) answer show-true-at))))

(defn guesses ^{:impure true} [question answer lexicon true-index]
  (html
   ;; 4 choices; show true answer at a random position amongst the false ones.
   ;; TODO: remove true answer from lexicon in (guess) call.
   (guess lexicon 4 answer (rand-int 4))))

(slice next-question [index]
       (html [:div [:h2 (nth (keys lexicon) index)]
                [:div {:style "padding-left:1em"}
                   (guesses (nth (keys lexicon) index)
                            (get lexicon (nth (keys lexicon) index))
                            lexicon index)]]))


(slice quiz ^{:impure true} []
       (html [:h2 "Choose the correct english verb for:"])
       (next-question (rand-int (count lexicon))))



