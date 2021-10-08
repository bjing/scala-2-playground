package com.project.simple

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO { println(helloWorld) }
    } yield ExitCode.Success

  def helloWorld: String = "Hello World!"
}
