package main

import (
	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/data/binding"
	"fyne.io/fyne/v2/dialog"
	"fyne.io/fyne/v2/widget"
	"image/color"
	"log"
	"os"
	"sda-multiplatform/custom_ui"
	"sda-multiplatform/steam"
	"strings"
	"time"
)

var ACCOUNT_LOGINS []string

func BuildUI(dataLogin []string, currentPbValue int64) {
	app := app.New()
	mainWindow := app.NewWindow("Steam Desktop Authenticator")
	mainWindow.Resize(fyne.NewSize(500, 650))

	accountImportingWindow := app.NewWindow("Account import")
	accountImportingWindow.Resize(fyne.NewSize(600, 600))

	ACCOUNT_LOGINS = dataLogin

	keyPlaceholder := buildKeyPlaceholder()

	data := binding.BindStringList(
		&ACCOUNT_LOGINS,
	)
	cList := buildCustomList(data, keyPlaceholder)

	searchEntry := buildSearchEntry(data)

	progressBar := buildProgressBar()
	go addProgressBar(currentPbValue, progressBar, cList, keyPlaceholder)

	copyBtn := widget.NewButton("Copy", func() {
		mainWindow.Clipboard().SetContent(keyPlaceholder.Text)
	})
	copyBtn.Resize(fyne.NewSize(104, 80))
	copyBtn.Move(fyne.NewPos(344, 69))

	dropDown := addImportDropDown(accountImportingWindow)

	mainWindow.SetContent(container.NewWithoutLayout(dropDown, keyPlaceholder, copyBtn, cList.List, progressBar, searchEntry))
	mainWindow.SetMaster()
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

func addImportDropDown(parentWindow fyne.Window) *widget.Select {
	dropDown := widget.NewSelect(
		[]string{"Import account", "Import accounts.."},
		func(s string) {
			switch s {
			case "Import account":
				handleImportSingleMaFile(parentWindow)
			case "Import accounts..":
				handleImportMaFilesFolder(parentWindow)
			}
		})

	dropDown.PlaceHolder = "Import"
	dropDown.Resize(fyne.NewSize(93, 33))
	dropDown.Move(fyne.NewPos(58, 22))

	return dropDown
}

func handleImportSingleMaFile(parentWindow fyne.Window) {
	parentWindow.Show()
	d := dialog.NewFileOpen(func(closer fyne.URIReadCloser, err error) {
		parentWindow.Hide()

		if closer == nil {
			return
		}

		f, fileErr := os.Open(closer.URI().Path())

		if fileErr != nil {
			log.Fatal(fileErr)
		}

		handleNewMaFile(f.Name())
	}, parentWindow)

	d.Resize(fyne.NewSize(600, 600))
	d.Show()
}

func handleImportMaFilesFolder(parentWindow fyne.Window) {
	parentWindow.Show()
	d := dialog.NewFolderOpen(func(uri fyne.ListableURI, err error) {
		parentWindow.Hide()

		if uri == nil {
			return
		}

		f, folderErr := os.Open(uri.Path())
		if folderErr != nil {
			log.Fatal(folderErr)
		}

		handleNewMaFilesFolder(f.Name())
		parentWindow.Hide()
	}, parentWindow)

	d.SetOnClosed(func() {
		parentWindow.Hide()
	})

	d.Resize(fyne.NewSize(600, 600))
	d.Show()
}

func buildProgressBar() *widget.ProgressBar {
	p := widget.NewProgressBar()
	p.Max = 30
	p.Resize(fyne.NewSize(390, 18))
	p.Move(fyne.NewPos(58, 166))

	return p
}

func buildCustomList(data binding.ExternalStringList, keyPlaceholder *canvas.Text) *custom_ui.CustomList {
	cList := custom_ui.CustomList{}
	cList.List = widget.NewListWithData(data,
		func() fyne.CanvasObject {
			return widget.NewLabel("logins")
		},
		func(i binding.DataItem, o fyne.CanvasObject) {
			o.(*widget.Label).Bind(i.(binding.String))
		})

	cList.List.Move(fyne.NewPos(58, 265))
	cList.List.Resize(fyne.NewSize(390, 350))

	cList.List.OnSelected = func(id widget.ListItemID) {
		login, _ := data.GetValue(id)
		keyPlaceholder.Text = steam.GetLoginKey(login)
		keyPlaceholder.Refresh()
		cList.SetCurrentSelected(login)
	}

	return &cList
}

func buildKeyPlaceholder() *canvas.Text {
	c := canvas.NewText("", color.White)
	c.Resize(fyne.NewSize(265, 80))
	c.Move(fyne.NewPos(58, 69))
	c.TextSize = 50
	c.Alignment = fyne.TextAlignCenter
	c.TextStyle = fyne.TextStyle{Bold: true}

	return c
}

func buildSearchEntry(data binding.ExternalStringList) *widget.Entry {
	w := widget.NewEntry()
	w.Resize(fyne.NewSize(390, 51))
	w.Move(fyne.NewPos(58, 199))
	w.SetPlaceHolder("Search...")

	w.OnChanged = func(s string) {
		data.Set(filterLogins(&steam.AccountNames, s))
	}

	return w
}

func addProgressBar(currentMax int64, p *widget.ProgressBar, l *custom_ui.CustomList, c *canvas.Text) {
	for {
		for i := currentMax; i >= 0; i -= 1 {
			p.SetValue(float64(i))
			time.Sleep(time.Second)
		}
		steam.RefreshLoginKeys()
		c.Text = steam.GetLoginKey(l.Selected)
		c.Refresh()
		currentMax = 30
	}
}
