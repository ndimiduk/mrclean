(ns leiningen.clean
  "I AM A HACK DO NOT USE ME JUST UPGRADE TO 1.7.1!!
Code blatantly copied from various commits since 1.7.0."
  (:use [clojure.java.io :only [file delete-file]]))

;; grumble, grumble; why didn't this make it into clojure.java.io?
(defn delete-file-recursively
  "Delete file f. If it's a directory, recursively delete all its contents.
Raise an exception if any deletion fails unless silently is true."
  [f & [silently]]
  (let [f (file f)]
    (if (.isDirectory f)
      (doseq [child (.listFiles f)]
        (delete-file-recursively child silently)))
    (delete-file f silently)))

(defn clean-jar-pred [project]
  (let [default-regex (re-pattern (format "^%s-.*\\.jar$" (:name project)))]
    (fn [f]
      (re-find (:regex-to-clean project default-regex) (.getName f)))))

(defn files-to-clean [project]
  (concat [(:compile-path project)]
          (filter (clean-jar-pred project) (.listFiles (file (:root project))))
          (for [f (:extra-files-to-clean project)]
            (format f (:version project)))))

(defn clean
  "Remove compiled class files and jars from project.

Set :extra-files-to-clean in project.clj to delete other files. Dependency
jars are not deleted; run deps task to delete all jars and get fresh ones."
  [project]
  (println "Mr. Clean is sweaping. Remove when you upgrade lein.")
  (doseq [f (files-to-clean project)]
    (delete-file-recursively f :silently)))
