package fileReader.service

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters.IteratorHasAsScala



final case class ReadResult[A](lines: Iterator[A], nbInvalidLine: Int)

object CSV {

  //fonction non recursive
  def read[A](fileName: String, parseLine: Array[String] => Option[A], regex: String = ","): ReadResult[A] = ???

    //ReadResult(parsedLine.flatten, invalidLine.size)
}
