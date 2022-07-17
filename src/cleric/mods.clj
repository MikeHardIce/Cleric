(ns cleric.mods)

(defn copy-file
  "Copy a file to the given path"
  [path-from path-to])

(defn unzip-file
  "Unzips a file of the given path"
  [path])

(defn remove-non-pak-files
  "Removes all non pak files"
  [path])

(defn list-files
  "List all files at given path"
  [path])

(defn load-lsx-file
  "Loads and parses the larian studio xml file"
  [path])

(defn add-mod->lsx
  "Add a mod entry to the lsx structure"
  [lsx mod])

(defn remove-mod->lsx
  "Remove a specific mod from the lsx structure"
  [lsx mod])

(defn save-lsx-file
  "Saves the current lsx structure at the specific path.
   If the file exists, then it will get overridden"
  [path lsx])
