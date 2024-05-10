package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.readText

open class MaFileReader {
    companion object : MaFileReader()

    private val jsonParser = Json { ignoreUnknownKeys = true }

    fun readMaFileDir(maFileDirPath: Path): List<MaFile> {
        val readMaFiles = mutableListOf<MaFile>()

        maFileDirPath.toFile().listFiles()?.forEach {
            if (it.extension == "maFile") {
                readMaFiles.add(readMaFile(it.toPath()))
            }
        }

        return readMaFiles
    }

    fun readMaFile(maFilePath: Path): MaFile {
        return maFilePath.readText().let {
            jsonParser.decodeFromString<MaFile>(it)
        }
    }
}
