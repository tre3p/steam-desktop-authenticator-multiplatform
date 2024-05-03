package com.tre3p.sdamp

import com.tre3p.sdamp.mafile.MaFileReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class MaFileReaderTest {
    @Test
    fun `should correctly parse single maFile to struct`() {
        val singleMaFile = MaFileReader.readMaFile(Paths.get("${TEST_RESOURCES_PATH}fixtures/first.maFile").toAbsolutePath())

        assertEquals("somename", singleMaFile.accountName)
        assertEquals("Gg0P+T7I/8wtorbG3H42kbY2PW0=", singleMaFile.sharedSecret)
    }

    @Test
    fun `should correctly parse maFiles from directory`() {
        val maFiles = MaFileReader.readMaFileDir(Paths.get("${TEST_RESOURCES_PATH}fixtures/"))

        assertTrue { maFiles.size == 2 }

        assertEquals("somename2", maFiles[0].accountName)
        assertEquals("shared", maFiles[0].sharedSecret)

        assertEquals("somename", maFiles[1].accountName)
        assertEquals("Gg0P+T7I/8wtorbG3H42kbY2PW0=", maFiles[1].sharedSecret)
    }
}