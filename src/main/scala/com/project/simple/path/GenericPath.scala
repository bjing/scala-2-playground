package com.project.simple.path

/**
 * Generic interface to a filesystem path.
 *
 * The path can be on a local machine (Windows, MacOS, Linux, etc),
 * or it can be an S3 path, or on any other medium.
 *
 * If you need support for a new filesystem/medium, simply create a new
 * class/trait in this package and extend [[GenericPath]].
 *
 */
trait GenericPath {

  /**
   * Path to a file, directory or any other format
   */
  def path: String

  def fileSystem: String
}
