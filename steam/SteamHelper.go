package steam

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"sda-multiplatform/structs"
	"strings"
)

func ReadMaFilesToJson(maFilePaths *[]string) []string {
	var result []string

	for _, file := range *maFilePaths {
		maFileContent, err := ioutil.ReadFile(file)
		if err != nil {
			log.Fatal(err)
		}

		result = append(result, string(maFileContent))
	}

	return result
}

func MapMaFilesJsonToStructs(data *[]string) []structs.MaFile {
	var result []structs.MaFile

	for _, jsonContent := range *data {
		tmpStruct := structs.MaFile{}
		err := json.Unmarshal([]byte(jsonContent), &tmpStruct)

		if err != nil {
			continue
		}

		result = append(result, tmpStruct)
	}

	return result
}

func ListMaFilesInDir(maFilesWD string) []string {
	if _, err := os.Stat(maFilesWD); errors.Is(err, os.ErrNotExist) {
		err := os.Mkdir(maFilesWD, os.ModePerm)
		if err != nil {
			log.Fatal(err.Error())
		}
	}

	files, err := ioutil.ReadDir(maFilesWD)

	if err != nil {
		log.Fatal(err)
	}

	var result []string
	for _, file := range files {
		if strings.HasSuffix(file.Name(), ".maFile") && !file.IsDir() {
			result = append(result, filepath.Join(maFilesWD, file.Name()))
		}
	}

	return result
}
