package main

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"log"
	"os"
	"sda-multiplatform/steam"
	"sda-multiplatform/structs"
	"strings"
)

const MAFILES_DIR = "maFiles/"

var (
	LOGIN_TO_KEY    = make(map[string]string)
	LOGIN_TO_SHARED = make(map[string]string)
)

func main() {
	maFiles := listMaFilesInDir()
	maFilesContent := readFiles(&maFiles)
	maFilesStruct := mapMaFilesToStruct(&maFilesContent)
	var accountNames []string

	for _, t := range maFilesStruct {
		accountNames = append(accountNames, t.AccountName.(string))
	}

	extractSharedSecret(&maFilesStruct)

	LOGIN_TO_KEY = steam.GetTwoFactor(&LOGIN_TO_SHARED)

	BuildUI(accountNames)
}

func RefreshLoginKeys() {
	log.Println("refreshing keys")
	LOGIN_TO_KEY = steam.GetTwoFactor(&LOGIN_TO_SHARED)
}

func listMaFilesInDir() []string {
	if _, err := os.Stat("./maFiles"); errors.Is(err, os.ErrNotExist) {
		err := os.Mkdir("./maFiles", os.ModePerm)
		if err != nil {
			log.Fatal(err.Error())
		}
	}

	files, err := ioutil.ReadDir("maFiles/")

	if err != nil {
		log.Fatal(err)
	}

	var result []string
	for _, file := range files {
		if strings.HasSuffix(file.Name(), ".maFile") && !file.IsDir() {
			result = append(result, file.Name())
		}
	}

	return result
}

func readFiles(data *[]string) []string {
	var result []string

	for _, d := range *data {
		content, err := ioutil.ReadFile(MAFILES_DIR + d)

		if err != nil {
			log.Fatal(err)
		}

		result = append(result, string(content))
	}

	return result
}

func mapMaFilesToStruct(data *[]string) []structs.MaFile {
	var result []structs.MaFile

	for _, d := range *data {
		tmp := structs.MaFile{}

		err := json.Unmarshal([]byte(d), &tmp)

		if err != nil {
			log.Println("Error while reading json" + err.Error())
			continue
		}

		result = append(result, tmp)
	}

	return result
}

func extractSharedSecret(data *[]structs.MaFile) {
	for _, t := range *data {
		LOGIN_TO_SHARED[t.AccountName.(string)] = t.SharedSecret.(string)
	}
}

func GetLoginKey(login string) string {
	return LOGIN_TO_KEY[login]
}
