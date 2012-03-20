(defproject mrclean "1.0.0"
  :description "Mr. Clean gets rid of .class files in just about a second."
  :eval-in-leiningen true)

(when (not= "1.7.0" (System/getenv "LEIN_VERSION"))
  (println "Mr. Clean says \"This plugin is not for you.\""))
