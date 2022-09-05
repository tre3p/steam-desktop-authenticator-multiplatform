package main

import (
	"log"
	"os"
	"path/filepath"
	"sda-multiplatform/steam"
)

var maFilesFolderPath = buildMaFilesDirectoryPath()

func main() {
	maFilesList := steam.ListMaFilesInDir(maFilesFolderPath)
	maFilesJson := steam.ReadMaFilesToJson(&maFilesList)
	maFilesStructs := steam.MapMaFilesJsonToStructs(&maFilesJson)
	currentPbValue := steam.GetCurrentSteamChunk()
	steam.InitStorage(&maFilesStructs)

	BuildUI(steam.AccountNames, currentPbValue)
}

func buildMaFilesDirectoryPath() string {
	workingDirectory, err := os.Getwd()
	if err != nil {
		log.Fatal("Error while getting working directory")
	}

	return filepath.Join(workingDirectory, "/maFiles/")
}
