(ns italianverbs)

;; figure out differences between hash-map and hash-set..
(def lexicon (hash-map))

(slice verb-row [italian]
       (html  
               [:tr 
                  [:td italian] [:td (get lexicon italian)] 
               ]))

(defn add-verb [italian english]
  (def lexicon (assoc lexicon italian english)))

(add-verb "dire" "to say")
(add-verb "fare" "to do")
(add-verb "scrivere" "to write")
(add-verb "correggere" "to correct")
(add-verb "leggere" "to read")
(add-verb "mangiere" "to eat")
(add-verb "parlere" "to speak")
(add-verb "pranzare" "to eat lunch")

(slice verb-table
       (html [:table 
             (verb-row "dire")
             (verb-row "fare")
             (verb-row "scrivere")
             (verb-row "correggere")
             (verb-row "leggere")
             (verb-row "parlere")]))
