
import java.io.File
import scala.io.Source
import Loading._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

object Quering {
  /***** REPORT OPTION *****/
  /************************/
  
  /* HIGHEST AND LOWEST COUNTRIES */
  def showTop10Countries() = {}

  /* TYPES OF RUNWAYS */
  def showTypeRunways() = {}

  /* TOP 10 MOST COMMON RUNWAY LATITUDE */
  def showTop10RunwaysLatitude() = {}

  /***** QUERY OPTION *****/
  /************************/
  
  def showCountry(byIsoCode: Boolean = false): Unit = {
    if (byIsoCode){
      println("Type in the country code you want to browse")
    } else{
      println("Type in the country name you want to browse")
    }
    val country = scala.io.StdIn.readLine()
    searchCountryAirportsAndRunways(country, byIsoCode)
  }

  def fuzzy_search(string_1: String, string_2: String): Boolean = {
    val string_2_short = string_2.slice(0,string_1.length())
    if (string_1.toLowerCase() == string_2_short.toLowerCase()){
      true
    } else {
      false
    }
  }

  /* le problème vient du iso_country, rappel -> on fait par NOM de pays et non par code */
  def tri_par_pays(list: List[(String, Airport)], country: String, empty_list: List[(String, Airport)]): List[(String, Airport)] = list match {
    case Nil => empty_list
    case (key,value) :: tail if (value.iso_country == country) => tri_par_pays(tail, country, (key,value) :: empty_list)
    case (key,value) :: tail => tri_par_pays(tail, country, empty_list)
  }

  def searchCountryAirportsAndRunways(country: String, byIsoCode: Boolean): Unit = {
    if(byIsoCode == true){
      /* select from code column */
      println("You choose to browse by country code : $country")
      airports.foreach{
        case (key_airport, value_airport) => if (country == value_airport.iso_country) {
          print("This is airport : " + value_airport.name + " in country : " + value_airport.iso_country + "\n")
          runways.foreach{
            case (key_runway, value_runway) => if (key_airport == value_runway.airport_ref) {   /* vérifier qu'il existe des runway pour tel aéroport -> si on a pas de runway on affiche pas*/
              println(key_runway + " -> " + value_runway)
            }
          }
          println("\n")
        }
      }
      println("/// SOME AIRPORTS MIGHT HAVE NO RUNWAYS REGISTERED IN OUR DATABASE ///")
    }
    else{
    }
  }
}