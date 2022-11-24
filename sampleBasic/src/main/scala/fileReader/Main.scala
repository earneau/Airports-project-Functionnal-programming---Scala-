package fileReader

import fileReader.model.Point
import fileReader.service.CSV


object Main {

  def main(args: Array[String]): Unit = {
    val result = CSV.read("points.csv", Point.fromCsvLine)
    println(result.nbInvalidLine)
    result.lines.foreach(println)
  }
}
