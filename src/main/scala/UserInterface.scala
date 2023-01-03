
import scala.annotation.tailrec
import Loading._
import Quering._
import scala.collection.mutable.HashMap
/*import scala.compiletime.ops.int*/

object UserInterface {
  /* REPORT */
  def displayReports(errorMessage: String = ""): Unit = {
    if(errorMessage != ""){
      println(errorMessage)
    }

    println("Select an option :")
    println("0 -> Exit app")
    println("2.1 -> 10 countries with highest number of airports and countries  with lowest number of airports.")
    println("2.2 -> Type of runways per country")
    println("2.3 -> The top 10 most common runway latitude")
    val input = scala.io.StdIn.readLine()
    input match {
      case "0" => None /* EXIT APP*/
      case "2.1" => showTop10Countries()/* QUERY */
      case "2.2" => showTypeRunways()
      case "2.3" => showTop10RunwaysLatitude()
      case "_" => userInterface("ERROR: Choose a valid option !")
    }
    userInterface()
  }

  /* QUERY */
  def displayQueries(errorMessage: String = ""): Unit = {
    if(errorMessage != ""){
      println(errorMessage)
    }

    println("You can browse through airports and runways either by country code or country name :")
    println("0 -> Exit app")
    println("1.1 -> Browse by country ISO code")
    println("1.2 -> Browse by country name")
    val input = scala.io.StdIn.readLine()
    input match {
      case "0" => None /* EXIT APP*/
      case "1.1" => showCountry(byIsoCode = true)/* QUERY */
      case "1.2" => showCountry(byIsoCode = false)
      case "_" => userInterface("ERROR: Choose a valid option !")
    }
    userInterface()
  }

  @tailrec
  def userInterface(errorMessage: String = ""): Unit = {
    if(errorMessage != ""){
      println(errorMessage)
    }

    println("You can either browse through countries and display informations about them, or display reports on airports in the world:")
    println("0 -> Exit app")
    println("1 -> Queries")
    println("2 -> Reports")
    val input = scala.io.StdIn.readLine()
    input match {
      case "0" => None /* EXIT APP*/
      case "1" => displayQueries()
      case "2" => displayReports()/* QUERY */
      case "_" => userInterface("ERROR: Choose a valid option !")
    }
  }

  def main(args: Array[String]): Unit = {
    loadCountries()
    loadAirports()
    loadRunways()
    userInterface()
  }
}


