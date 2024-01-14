(ns core-cljs)

(defn welcome-page-handler []
  {:status 200
   :body "Welcome!"})

(defn about-page-handler []
  {:status 200
   :body "About"})

(defn terms-of-service-page-handler []
  {:status 200
   :body "Terms of Service"})

(def routes
  {;; This will cause linter warning
   "/" welcome-page-handler

   ;; Function wrapping in use: no linter warning
   "/about" #(about-page-handler)

   ;; Var quoted: no linter warning (although, you should avoid var quoting with CLJS)
   "/terms" #'terms-of-service-page-handler})
