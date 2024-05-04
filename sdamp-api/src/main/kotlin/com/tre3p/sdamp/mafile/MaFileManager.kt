package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import java.nio.file.Path

class MaFileManager(
    private val maFileReader: MaFileReader,
    private val maFileDirManager: MaFileDirManager,
) {
    private var maFilesState: MutableList<MaFile>? = null

    fun loadMaFiles(): List<MaFile> {
        val loadedMaFiles = maFileReader.readMaFileDir(maFileDirManager.maFileDirPath)
        maFilesState = loadedMaFiles.toMutableList()
        return loadedMaFiles
    }

    /**
     * Imports maFiles to inner storage.
     * Returns only maFiles which wasn't in inner storage before
     */
    fun importMaFiles(maFilePaths: List<Path>): List<MaFile> {
        if (maFilesState == null) loadMaFiles()

        val importedMaFiles = mutableListOf<MaFile>()
        maFilePaths.forEach {
            val readMaFile = maFileReader.readMaFile(it)
            if (!maFilesState!!.contains(readMaFile)) {
                maFileDirManager.copyMaFileToBaseDir(it)
                maFilesState!!.add(readMaFile)
                importedMaFiles.add(readMaFile)
            }
        }

        return importedMaFiles
    }

    /**
     * Imports maFiles from specified directory to inner storage.
     * Returns only maFiles which wasn't in inner storage before.
     */
    fun importMaFileDir(maFileDirPath: Path): List<MaFile> {
        if (maFilesState == null) loadMaFiles()

        val importedMaFiles = mutableListOf<MaFile>()

        maFileDirPath.toFile().listFiles()?.forEach {
            if (it.extension == "maFile") {
                val readMaFile = maFileReader.readMaFile(it.toPath())
                if (!maFilesState!!.contains(readMaFile)) {
                    maFileDirManager.copyMaFileToBaseDir(it.toPath())
                    maFilesState!!.add(readMaFile)
                    importedMaFiles.add(readMaFile)
                }
            }
        }

        return importedMaFiles
    }
}