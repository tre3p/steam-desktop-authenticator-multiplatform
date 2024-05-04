package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.readText
import kotlin.io.path.useDirectoryEntries

open class MaFileReader {
    companion object : MaFileReader()

    private val jsonParser = Json { ignoreUnknownKeys = true }

    fun readMaFileDir(maFileDirPath: Path): Set<MaFile> {
        println("got dir path: " + maFileDirPath)
        println(maFileDirPath.isDirectory())
        return maFileDirPath.useDirectoryEntries("*.maFile") { seq ->
            seq.map { println(it); readMaFile(it) }.toSet()
        }
    }

    fun readMaFile(maFilePath: Path): MaFile {
        println("reading" + maFilePath)
        return maFilePath.readText().let {
            jsonParser.decodeFromString<MaFile>(it)
        }
    }
}
