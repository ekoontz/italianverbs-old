(ns slice.example
  (:use slice.core
        slice.compojure5                ; make slices render automatically
        uteal.core                      ; defs FTW
        compojure.core                  ; slice isn't tied to a web "framework"
        ring.adapter.jetty
        hiccup.form-helpers))

(slice verb-row [italian]
       (html  
               [:tr 
                  [:td italian] [:td (get lexicon italian)] 
               ]))

;; figure out differences between hash-map and hash-set
(def lexicon (hash-map))

(defn add-verb [italian english]
  (def lexicon (assoc lexicon italian english)))

(add-verb "dire" "to say")
(add-verb "fare" "to do")
(add-verb "scrivere" "to write")
(add-verb "correggere" "to correct")
(add-verb "leggere" "to read")
(add-verb "parlere" "to speak")

(slice verb-table
       (html [:table 
             (verb-row "dire")
             (verb-row "fare")
             (verb-row "scrivere")
             (verb-row "correggere")
             (verb-row "leggere")
             (verb-row "parlere")]))
