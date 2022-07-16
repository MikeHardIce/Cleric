(defproject cleric "0.1.0-SNAPSHOT"
  :description "A Baldurs Gate 3 Mod installer"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://choosealicense.com/licenses/mit"
            :key "mit"
            :comment "MIT License"
            :year 2022}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [strigui "0.0.1-alpha31"]]
  :main ^:skip-aot cleric.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
