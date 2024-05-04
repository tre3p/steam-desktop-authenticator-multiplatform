package com.tre3p.sdamp.misc

import java.io.File

private val USER_HOME = System.getProperty("user.home")
private const val MAFILES_DIR_NAME = "/.SDAMP-maFiles"

//val MAFILES_DIR_PATH = File("$USER_HOME$MAFILES_DIR_NAME")
  //  .also { it.mkdir() }
val MAFILES_DIR_PATH = File("../maFiles/")
  .also { it.mkdir() }