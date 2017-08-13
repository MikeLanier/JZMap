// TODO   [-] Convert Cells to Octagons and Diamonds
// TODO       [-] Octagons can be paths or rooms
// TODO       [-] Diamonds are paths only. They fill in gaps which are NE, NW, SE and SW of an octagon
// TODO       [x] Rather than origin, width, and height. A cell will be defined by center and radius.
// TODO           [x] Radius of a diamond will be 2/3 the radius of an octagon
// TODO       [x] The x/y index of a cell will be even for octagons and odd for diamonds
// TODO       [ ] When a path is defined in/out NE, NW, SE, and/or SW of an octagon, the neighbor path in will be given a path in the same direction
// TODO       [ ] When the cell definition dialog is showing, cells on the map do not display correctly, offset
// TODO   [-] Modify display for
// TODO       [-] path octagons
// TODO       [ ] room octagons
// TODO       [ ] path diamonds
// TODO   [-] Zoom In/Out
// TODO       [x] Buttons for zoom +/-
// TODO       [ ] Zoom using scroll wheel
// TODO       [ ] Zoom using slider
// TODO   [x] Size of frame and size of map not connected
// TODO       [x] border around the map
// TODO   [-] When add cells,
// TODO       [x] center all on map
// TODO       [x] resize map so all fit.
// TODO       [ ] after resize, scroll such that active/center cell is center of the display
// TODO   [ ] User mouse/finger to pan the map
// TODO   [ ] Tooltips on cell to view all info of a room cell
// TODO   [ ] Multiple layers.  Ex. Zork.  Layer for above ground paths and below ground paths
// TODO   [ ] 3D display option for display which shows up/down more clearly
// TODO       [ ] can OpenGL be called from Java and used for 3D display
// TODO   [ ] Cell Info Dialog
// TODO       [ ] Dismiss dialog on save and cancel
// TODO       [ ] keyin name and have it display on the map
// TODO       [ ] create a list of items found in the room
// TODO   [ ] panel on left to show details of hovered/selected octagon.  Do not show diamond cells
// TODO   [ ] Room's will have
// TODO       [ ] name
// TODO       [ ] description
// TODO       [ ] item list