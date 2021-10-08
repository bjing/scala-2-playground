package com.project.simple

import com.project.simple.Main.helloWorld
import org.scalatest.freespec.AnyFreeSpec

class MainTest extends AnyFreeSpec {
  "Hello world function" - {
    "should return Hello World!" in {
      val result = helloWorld
      assert(result == "Hello World!")
    }
  }
}
