package main

import (
	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/data/binding"
	"fyne.io/fyne/v2/widget"
	"image/color"
	"sda-multiplatform/custom_ui"
	"strings"
	"time"
)

var ACCOUNT_LOGINS []string

func BuildUI(dataLogin []string, currentPbValue int64) {
	app := app.New()
	mainWindow := app.NewWindow("Steam Desktop Authenticator")
	mainWindow.Resize(fyne.NewSize(500, 600))

	// account logins start
	data := binding.BindStringList(
		&dataLogin,
	)

	ACCOUNT_LOGINS = dataLogin

	cList := custom_ui.CustomList{}
	cList.List = widget.NewListWithData(data,
		func() fyne.CanvasObject {
			return widget.NewLabel("logins")
		},
		func(i binding.DataItem, o fyne.CanvasObject) {
			o.(*widget.Label).Bind(i.(binding.String))
		})

	cList.List.Move(fyne.NewPos(58, 210))
	cList.List.Resize(fyne.NewSize(390, 350))
	// account logins end

	// text with key start
	c := canvas.NewText("", color.White)
	c.Resize(fyne.NewSize(265, 80))
	c.Move(fyne.NewPos(58, 22))
	c.TextSize = 50
	c.Alignment = fyne.TextAlignCenter
	c.TextStyle = fyne.TextStyle{Bold: true}
	// text with key end

	// search field start
	w := widget.NewEntry()
	w.Resize(fyne.NewSize(390, 51))
	w.Move(fyne.NewPos(58, 143))
	w.SetPlaceHolder("Search...")

	w.OnChanged = func(s string) {
		data.Set(filterLogins(&ACCOUNT_LOGINS, s))
	}
	// search field end

	// progress bar start
	p := widget.NewProgressBar()
	p.Max = 30
	p.Resize(fyne.NewSize(390, 18))
	p.Move(fyne.NewPos(58, 109))

	go addProgressBar(currentPbValue, p, &cList, c)
	// progress bar end

	// selecting account start
	cList.List.OnSelected = func(id widget.ListItemID) {
		login, _ := data.GetValue(id)
		c.Text = GetLoginKey(login)
		c.Refresh()
		cList.SetCurrentSelected(login)
	}

	// selecting account end

	// copy button start
	copyBtn := widget.NewButton("Copy", func() {
		mainWindow.Clipboard().SetContent(c.Text)
	})

	copyBtn.Resize(fyne.NewSize(104, 80))
	copyBtn.Move(fyne.NewPos(344, 22))
	// copy button end

	mainWindow.SetContent(container.NewWithoutLayout(c, copyBtn, cList.List, p, w))
	mainWindow.ShowAndRun()
}

func filterLogins(data *[]string, target string) []string {
	var result []string

	for _, e := range *data {
		if strings.HasPrefix(e, target) {
			result = append(result, e)
		}
	}

	return result
}

func addProgressBar(currentMax int64, p *widget.ProgressBar, l *custom_ui.CustomList, c *canvas.Text) {
	for {
		for i := currentMax; i >= 0; i -= 1 {
			p.SetValue(float64(i))
			time.Sleep(time.Second)
		}
		RefreshLoginKeys()
		c.Text = GetLoginKey(l.Selected)
		c.Refresh()
		currentMax = 30
	}
}
