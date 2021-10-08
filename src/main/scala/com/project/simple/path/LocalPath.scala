package com.project.simple.path

import cats.implicits._

import java.nio.file.{Files, Paths}

/**
 * This trait represents the path to a directory or file on a local machine.
 *
 * For example: /usr/local and /bin/bash
 */
trait LocalPathLike extends GenericPath {

  /**
   * Path to a directory or a file
   *
   * This will get allocated when instantiating the [[LocalPath]] case class
   * that inherits this trait.
   */
  def path: String

  def fileSystem: String = Paths.get(path).getFileSystem.toString

  /**
   * This is for creating a complete path to a local file (directory + file)
   *
   * @param filename Name of the file
   * @return [[LocalPath]] of a file
   */
  def withFilename(filename: String): LocalPathLike

  /**
   * Get the file name from the current path
   *
   * @return File name if the file path represents a file,
   *         None if path is a directory
   */
  def getFilename: Option[String] =
    if (isFile) Paths.get(path).getFileName.toString.some
    else None

  /**
   * Whether the path points to a directory
   */
  def isDirectory: Boolean = Files.isDirectory(Paths.get(path))

  /**
   * Whether the path points to a file
   */
  def isFile: Boolean = !isDirectory
}

object LocalPath {

  /**
   * This is the only constructor for the LocalPath class.
   *
   * This is to make sure the class only gets instantiated
   * with a directory path.
   *
   * @param directory A path to a directory
   * @return [[LocalPathLike]] representation of a directory
   */
  def withDirectory(directory: String): LocalPathLike = {
    val path = Paths.get(directory)
    assert(Files.isDirectory(path))

    LocalPath(path.toAbsolutePath.normalize.toString)
  }

  /**
   * Concrete class that represents a local path.
   */
  private case class LocalPath(path: String) extends LocalPathLike {
    def withFilename(filename: String): LocalPath = {
      assert(this.isDirectory)

      val filePath = Paths.get(s"$path/$filename")
      assert(this.isFile)

      LocalPath(filePath.toAbsolutePath.normalize.toString)
    }
  }
}
