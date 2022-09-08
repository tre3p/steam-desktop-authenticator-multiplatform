package util

import (
	"io"
	"log"
	"os"
	"path/filepath"
)

func Copy(src, dest string) {
	source, srcErr := os.Open(src)
	if srcErr != nil {
		log.Fatal(srcErr)
	}

	srcFileName := filepath.Base(src)
	dst, dstErr := os.Create(filepath.Join(dest, srcFileName))
	if dstErr != nil {
		log.Fatal(dstErr)
	}

	_, cpErr := io.Copy(dst, source)
	if cpErr != nil {
		log.Fatal(cpErr)
	}
}

func IsFolderContain(folderPath, fileName string) bool {
	if _, err := os.Stat(filepath.Join(folderPath, fileName)); err != nil {
		return !os.IsNotExist(err)
	}
	return true
}
