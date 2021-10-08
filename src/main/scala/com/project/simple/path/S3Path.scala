package com.project.simple.path

import cats.implicits._

/**
 * This class represents the path to an S3 bucket or S3 object.
 *
 * For example: s3://bucket and s3://bucket/key
 */
trait S3PathLike extends GenericPath {

  /**
   * Path to an S3 bucket or S3 object
   *
   * This will get allocated when instantiating the [[S3Path]] case class that
   * inherits this trait.
   */
  def path: String

  def fileSystem: String = "AWS S3"

  /**
   * This is for creating a complete path to an S3 object (bucket + key)
   *
   * @param key key of an S3 object
   * @return [[S3Path]] of an S3 object
   */
  def withKey(key: String): S3PathLike

  def getBucket: Option[String] = path.split("/").filterNot(_ == "").take(2).lastOption

  /**
   * Get the S3 object key from the current path
   *
   * @return Object key if available in the path, None otherwise
   */
  def getKey: Option[String] = path.split("/").drop(3) match {
    case Array()      => None
    case Array(parts) => parts.mkString("/").some
  }

  /**
   * Whether the path contains a key.
   *
   * For example, the bucket in path "s3://bucket/key"
   * is "bucket"
   *
   * @return true if path contains a bucket, false otherwise
   */
  def pathContainsOnlyBucket: Boolean = path.endsWith("/")

  /**
   * Whether the path contains an object key
   *
   * For example, the key in path "s3://bucket/this/is/a/key"
   * is "this/is/a/key"
   *
   * @return true if path contains a key, false otherwise
   */
  def pathContainsKey: Boolean = !pathContainsOnlyBucket
}

object S3Path {

  /**
   * This is the only constructor for the S3Path class.
   *
   * This is to make sure the class only gets instantiated
   * with a bucket
   *
   * @param bucket bucket name
   * @return [[S3PathLike]] representation of a bucket
   */
  def withBucket(bucket: String): S3PathLike = S3Path(s"s3://$bucket/")

  private case class S3Path(path: String) extends S3PathLike {
    def withKey(key: String): S3PathLike = {
      assert(path.startsWith("s3://)"))
      S3Path(s"${path.stripSuffix("/")}/$key")
    }
  }
}
