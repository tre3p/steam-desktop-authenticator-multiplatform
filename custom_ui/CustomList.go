package custom_ui

import "fyne.io/fyne/v2/widget"

type CustomList struct {
	List     *widget.List
	Selected string
}

func (c *CustomList) SetCurrentSelected(s string) {
	c.Selected = s
}
