package com.tre3p.sdamp.mafile

import java.io.File
import java.nio.file.Path
import kotlin.io.path.copyTo

class MaFileDirManager(
    val maFileDirPath: Path
) {
    init {
        File(maFileDirPath.toUri()).mkdir()
    }

    fun copyMaFileToBaseDir(maFilePath: Path) {
        val fileNameToMove = maFilePath.fileName
        val targetPath = maFileDirPath.resolve(fileNameToMove)
        maFilePath.copyTo(targetPath)
    }
}