(ns cleric.core
  (:require [strigui.core :as gui])
  (:import [java.awt Color])
  (:gen-class))

(defonce color-background (Color. 166 42 42))

(defn screen-select-mod-folder
  [widgets prev]
  (-> widgets
      (gui/add-label "label-title" "Select your Mod folder"
                     {:x 150 :y 180 :color [Color/black] :font-size 28 :group "mod-home"})
      (gui/add-label "label-hint" "This is the location where you downloaded/collect
                                   all your mod zip files"
                     {:x 150 :y 220 :color [Color/black] :font-size 18 :group "mod-home"})
      (gui/add-input "input-path" "" {:x 150 :y 280 :width 600 :color [(Color. 166 100 100) Color/black] :group "mod-home"})
      (gui/add-button "select-path" "" {:x 760 :y 280 :width 50 :color [(Color. 166 100 100) Color/black] :group "mod-home"})
      (gui/add-button "previous" "<" {:x 10 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "mod-home"})
      (gui/add-button "next" ">" {:x 940 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "mod-home"})
      (gui/attach-event "previous" :mouse-clicked (fn [wdgs _]
                                                    (-> wdgs
                                                        (gui/remove-widget-group "mod-home")
                                                        prev)))))

(defn screen-select-bg3-home
  [widgets]
  (-> widgets
      (gui/add-label "label-title" "Select the Baldurs Gate 3 Home folder"
                     {:x 150 :y 180 :color [Color/black] :font-size 28 :group "bg3-home"})
      (gui/add-label "label-hint" "(Linux: ...\\Larian Studio\\Baldurgs Gate 3
                                    Windows: Documents\\Larian Studio\\Baldurgs Gate 3)"
                     {:x 150 :y 220 :color [Color/black] :font-size 18 :group "bg3-home"})
      (gui/add-input "input-path" "" {:x 150 :y 280 :width 600 :color [(Color. 166 100 100) Color/black] :group "bg3-home"})
      (gui/add-button "select-path" "" {:x 760 :y 280 :width 50 :color [(Color. 166 100 100) Color/black] :group "bg3-home"})
      (gui/add-button "next" ">" {:x 940 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "bg3-home"})
      (gui/attach-event "next" :mouse-clicked (fn [wdgs _]
                                                (-> wdgs
                                                    (gui/remove-widget-group "bg3-home")
                                                    (screen-select-mod-folder screen-select-bg3-home))))))

(defn -main
  [& args]
  (gui/window! 200 200 1000 600 "Cleric" color-background {java.awt.RenderingHints/KEY_ANTIALIASING java.awt.RenderingHints/VALUE_ANTIALIAS_ON
                                                      java.awt.RenderingHints/KEY_RENDERING java.awt.RenderingHints/VALUE_RENDER_SPEED})
  (gui/swap-widgets! screen-select-bg3-home))
