package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.useDirectoryEntries

open class MaFileReader {
    companion object : MaFileReader()

    private val jsonParser = Json { ignoreUnknownKeys = true }

    fun readMaFileDir(maFileDirPath: Path): List<MaFile> {
        return maFileDirPath.useDirectoryEntries("*.maFile") { seq ->
            seq.map { readMaFile(it) }.toList()
        }
    }

    fun readMaFile(maFilePath: Path): MaFile {
        return maFilePath.readText().let {
            jsonParser.decodeFromString<MaFile>(it)
        }
    }
}
