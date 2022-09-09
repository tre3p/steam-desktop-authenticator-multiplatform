package main

import (
	"log"
	"os"
	"path/filepath"
	"sda-multiplatform/steam"
	"sda-multiplatform/structs"
	"sda-multiplatform/util"
	"strings"
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

func handleNewMaFile(srcFilePath string) {
	if !strings.HasSuffix(srcFilePath, ".maFile") {
		return
	}

	maFileContent := steam.ReadMaFilesToJson(&[]string{srcFilePath})
	maFileStruct := steam.MapMaFilesJsonToStructs(&maFileContent)

	if util.IsFolderContain(maFilesFolderPath, filepath.Base(srcFilePath)) || steam.AccountNamesContains(maFileStruct[0].AccountName.(string)) {
		return
	}

	util.Copy(srcFilePath, maFilesFolderPath)

	steam.InitStorage(&maFileStruct)
}

func handleNewMaFilesFolder(folderPath string) {
	maFilesList := steam.ListMaFilesInDir(folderPath)

	var uniqueMaFiles []string
	for _, maFilePath := range maFilesList {
		if util.IsFolderContain(maFilesFolderPath, filepath.Base(maFilePath)) {
			continue
		}

		uniqueMaFiles = append(uniqueMaFiles, maFilePath)
		util.Copy(maFilePath, maFilesFolderPath)
	}

	maFilesJson := steam.ReadMaFilesToJson(&uniqueMaFiles)
	maFilesStructs := steam.MapMaFilesJsonToStructs(&maFilesJson)

	var uniqueMaFilesStructs []structs.MaFile
	for _, maFileStruct := range maFilesStructs {
		if steam.AccountNamesContains(maFileStruct.AccountName.(string)) {
			continue
		}

		uniqueMaFilesStructs = append(uniqueMaFilesStructs, maFileStruct)
	}

	steam.InitStorage(&uniqueMaFilesStructs)
}
