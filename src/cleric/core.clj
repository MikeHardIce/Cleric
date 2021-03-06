(ns cleric.core
  (:require [strigui.core :as gui]
            [strigui.widget :as w])
  (:import [java.awt Color]
           [javax.swing JFileChooser])
  (:gen-class))

(defonce color-background (Color. 166 42 42))

(def state (atom {:mod-path ""
                  :bg3-path ""
                  :mods []}))

(defn directory-dialog! 
  [title]
  (let [dialog (doto (JFileChooser.)
                 (.setDialogTitle title)
                 (.setFileSelectionMode JFileChooser/DIRECTORIES_ONLY))
        result (.showOpenDialog dialog (-> @w/state :context :window))]
    (if (= result JFileChooser/APPROVE_OPTION)
      (.getAbsolutePath (.getSelectedFile dialog))
      "")))

(defn screen-mod-installer
  [widgets prev prev-prev]
  (-> widgets
      (gui/add-label "label-title" "Mod Installation"
                     {:x 150 :y 30 :color [Color/black] :font-size 28 :group "mod-installer"})
      (gui/add-label "label-hint" "Select the Mods that should be made available/unavailable in Baldurs Gate 3
                                   and press the button install or uninstall"
                     {:x 150 :y 60 :color [Color/black] :font-size 18 :group "mod-installer"})
      (gui/add-list "list-mods" [{:value "blabla mod.zip"} {:value "muhahaha mod.zip"}] {:x 150 :y 90 :width 600 :height 400 :color [(Color. 166 100 100) Color/black] :group "mod-installer"})
      (gui/add-button "previous" "<" {:x 10 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "mod-installer"})
      (gui/add-button "install" "Install" {:x 800 :y 280 :width 130 :color [(Color. 166 100 100) Color/black] :group "mod-installer"})
      (gui/add-button "uninstall" "Uninstall" {:x 800 :y 320 :width 130 :color [(Color. 166 100 100) Color/black] :group "mod-installer"})
      (gui/attach-event "previous" :mouse-clicked (fn [wdgs _]
                                                    (-> wdgs
                                                        (gui/remove-widget-group "mod-installer")
                                                        (prev prev-prev))))))

(defn screen-select-mod-folder
  [widgets prev]
  (-> widgets
      (gui/add-label "label-title" "Select your Mod folder"
                     {:x 150 :y 180 :color [Color/black] :font-size 28 :group "mod-home"})
      (gui/add-label "label-hint" "This is the location where you downloaded/collect
                                   all your mod zip files"
                     {:x 150 :y 220 :color [Color/black] :font-size 18 :group "mod-home"})
      (gui/add-input "input-path" (:mod-path @state) {:x 150 :y 280 :width 600 :color [(Color. 166 100 100) Color/black] :group "mod-home"})
      (gui/add-button "select-path" "..." {:x 760 :y 280 :width 50 :color [(Color. 166 100 100) Color/black] :group "mod-home"})
      (gui/add-button "previous" "<" {:x 10 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "mod-home"})
      (gui/add-button "next" ">" {:x 940 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "mod-home"})
      (gui/attach-event "previous" :mouse-clicked (fn [wdgs _]
                                                    (-> wdgs
                                                        (gui/remove-widget-group "mod-home")
                                                        prev)))
      (gui/attach-event "next" :mouse-clicked (fn [wdgs _]
                                                (swap! state assoc :mod-path (:value (get wdgs "input-path")))
                                                (-> wdgs
                                                    (gui/remove-widget-group "mod-home")
                                                    (screen-mod-installer screen-select-mod-folder prev))))
      (gui/attach-event "select-path" :mouse-clicked (fn [wdgs _]
                                                       (let [dir (directory-dialog! "Select Mod Directory")]
                                                         (assoc-in wdgs ["input-path" :value] dir))))))

(defn screen-select-bg3-home
  [widgets]
  (-> widgets
      (gui/add-label "label-title" "Select the Baldurs Gate 3 Home folder"
                     {:x 150 :y 180 :color [Color/black] :font-size 28 :group "bg3-home"})
      (gui/add-label "label-hint" "(Linux: ...\\Larian Studio\\Baldur's Gate 3
                                    Windows: Documents\\Larian Studio\\Baldur's Gate 3
                                   or C:\\Users\\<YourUserName>\\AppData\\Local\\Larian Studios\\Baldur's Gate 3)"
                     {:x 150 :y 220 :color [Color/black] :font-size 18 :group "bg3-home"})
      (gui/add-input "input-path" (:bg3-path @state) {:x 150 :y 280 :width 600 :color [(Color. 166 100 100) Color/black] :group "bg3-home"})
      (gui/add-button "select-path" "..." {:x 760 :y 280 :width 50 :color [(Color. 166 100 100) Color/black] :group "bg3-home"})
      (gui/add-button "next" ">" {:x 940 :y 35 :width 50 :height 500 :has-border? false :can-focus? false :color [(Color. 166 100 100 70) Color/black] :group "bg3-home"})
      (gui/attach-event "next" :mouse-clicked (fn [wdgs _]
                                                (swap! state assoc :bg3-path (:value (get wdgs "input-path")))
                                                (-> wdgs
                                                    (gui/remove-widget-group "bg3-home")
                                                    (screen-select-mod-folder screen-select-bg3-home))))
      (gui/attach-event "select-path" :mouse-clicked (fn [wdgs _]
                                                       (let [dir (directory-dialog! "Select BG3 Home Directory")]
                                                         (assoc-in wdgs ["input-path" :value] dir))))))

(defn -main
  [& args]
  (gui/window! 200 200 1000 600 "Cleric" color-background {java.awt.RenderingHints/KEY_ANTIALIASING java.awt.RenderingHints/VALUE_ANTIALIAS_ON
                                                      java.awt.RenderingHints/KEY_RENDERING java.awt.RenderingHints/VALUE_RENDER_SPEED})
  (gui/swap-widgets! screen-select-bg3-home))
