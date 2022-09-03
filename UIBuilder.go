package main

import (
	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/data/binding"
	"fyne.io/fyne/v2/widget"
	"image/color"
	"strings"
	"time"
)

var ACCOUNT_LOGINS []string

func BuildUI(dataLogin []string) {
	app := app.New()
	mainWindow := app.NewWindow("Steam Desktop Authenticator")
	mainWindow.Resize(fyne.NewSize(500, 600))

	// account logins start
	data := binding.BindStringList(
		&dataLogin,
	)

	ACCOUNT_LOGINS = dataLogin

	list := widget.NewListWithData(data,
		func() fyne.CanvasObject {
			return widget.NewLabel("logins")
		},
		func(i binding.DataItem, o fyne.CanvasObject) {
			o.(*widget.Label).Bind(i.(binding.String))
		})

	list.Move(fyne.NewPos(58, 210))
	list.Resize(fyne.NewSize(390, 350))
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
	p.Resize(fyne.NewSize(390, 18))
	p.Move(fyne.NewPos(58, 109))

	go addProgressBar(p)
	// progress bar end

	// selecting account start
	list.OnSelected = func(id widget.ListItemID) {
		login, _ := data.GetValue(id)
		c.Text = GetLoginKey(login)
		c.Refresh()
	}
	// selecting account end

	// copy button start
	copyBtn := widget.NewButton("Copy", func() {
		mainWindow.Clipboard().SetContent(c.Text)
	})

	copyBtn.Resize(fyne.NewSize(104, 80))
	copyBtn.Move(fyne.NewPos(344, 22))
	// copy button end

	mainWindow.SetContent(container.NewWithoutLayout(c, copyBtn, list, p, w))
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

func addProgressBar(p *widget.ProgressBar) {
	for {
		p.SetValue(1.0)
		for i := 0.9; i >= 0.0; i -= 0.1 {
			time.Sleep(time.Millisecond * 2500)
			p.SetValue(i)
		}
		RefreshLoginKeys()
	}
}
