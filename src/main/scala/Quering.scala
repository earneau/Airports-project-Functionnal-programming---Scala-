
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
  def min (listCountries : List[(String,Int)]) : (String,Int) = listCountries match {
    case Nil => ("error", 0)
    case List(x) => x
    case head :: second :: tail => min ((if (head._2 < second._2) head else second) :: tail)
    case List(_) => ("error", 0)
  }

  def max (listCountries : List[(String,Int)]) : (String,Int) = listCountries match {
    case Nil => ("error", 0)
    case List(x) => x
    case head :: second :: tail => max ((if (head._2 > second._2) head else second) :: tail)
    case List(_) => ("error", 0)
  }

  def length (listCountries : List[(String,Int)]) : Int = listCountries match {
    case Nil => 0
    case List(x) => 1
    case head :: tail => 1 + length(tail)
  }

  def delete (listCountries : List[(String,Int)], toDelete : (String,Int)) : List[(String,Int)] = listCountries match {
    case Nil => List()
    case head :: tail => if (head == toDelete) {
      delete(tail, toDelete)
    } else {
      head :: delete(tail, toDelete)
    }
  }

  /* récupère les 10 pays avec le plus grand nombre d'aéroports */
  def show10highest (listCountries : List[(String,Int)]) : List[(String,Int)] = listCountries match {
    case Nil => List()
    case head :: tail if (length(tail) > 9) => show10highest(delete(listCountries, min(listCountries)))
    case _ => listCountries
  }

  /* récupère les 10 pays avec le plus petit nombre d'aéroports */
  def showLowestCountries (listCountries : List[(String,Int)], min : (String,Int)) : List[(String,Int)] = listCountries match {
    case Nil => List()
    case head :: tail => if (head._2 == min._2) {
      head :: showLowestCountries(tail, min)
    } else {
      showLowestCountries(tail, min)
    }
  }

  def showTop10(): List[(String,Int)] = {
    println("Do you want to display the top 10 countries with highest number of airports (1), or the countries with the lowest number of airports (2) ?")
    val country = scala.io.StdIn.readLine()
    if (country == "1"){
      showTop10Countries(true)
    } else if (country == "2") {
      showTop10Countries(false)
    } else {
      showTop10Countries(false)
    }
  }

  def showTop10Countries(top10: Boolean) : List[(String,Int)] = {
    val highest:HashMap[String, Int] = new HashMap()
    
    airports.foreach
    {
      case (key, value) => if (highest.contains(value.iso_country)) {
        highest.update(value.iso_country, highest.get(value.iso_country).getOrElse(-1000000) + 1)
      } else {
        highest.put(value.iso_country,1)
      }
    }

    if (top10){
      show10highest(highest.toList.sortBy(_._2))
    } else {
      showLowestCountries(highest.toList, min(highest.toList))
    }
  }

  /* TYPES OF RUNWAYS */

  def type_in_list(list : List[String], element : String) : Boolean = list match {
    case Nil => false
    case head :: tail => if (element == head){
      true
    } else {
      type_in_list(tail, element)
    }
  }

  def showTypeRunways(): HashMap[String, List[String]] = {
    println("loading...")
    val type_runways:HashMap[String, List[String]] = new HashMap()

    airports.foreach{
      case (key_airport, value_airport) => runways.foreach{
        case (key_runway, value_runway) => if (key_airport == value_runway.airport_ref) {
          if (type_runways.contains(value_airport.iso_country)) {
            if (type_in_list(type_runways.get(value_airport.iso_country).getOrElse(List()), value_runway.surface) == false){
              type_runways.update(value_airport.iso_country, value_runway.surface :: type_runways.get(value_airport.iso_country).getOrElse(List("error")))
            }
          } else {
            type_runways.put(value_airport.iso_country, List(value_runway.surface))
          }
        }
      }
    }

    type_runways
  }

  /* TOP 10 MOST COMMON RUNWAY LATITUDE */
  def showTop10RunwaysLatitude(): List[(String, Int)] = {
    val latitude_runways:HashMap[String, Int] = new HashMap()

    runways.foreach{
      case (key,value) => if (latitude_runways.contains(value.le_ident)) {
        latitude_runways.update(value.le_ident, latitude_runways.get(value.le_ident).getOrElse(-100000) + 1)
      } else {
        latitude_runways.put(value.le_ident, 1)
      }
    }

    show10highest(latitude_runways.toList.sortBy(_._2))
  }

  /***** QUERY OPTION *****/
  /************************/

  def showCountry(byIsoCode: Boolean = false): HashMap[String, List[Runway]] = {
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

  def tri_par_pays(list: List[(String, Airport)], country: String, empty_list: List[(String, Airport)]): List[(String, Airport)] = list match {
    case Nil => empty_list
    case (key,value) :: tail if (value.iso_country == country) => tri_par_pays(tail, country, (key,value) :: empty_list)
    case (key,value) :: tail => tri_par_pays(tail, country, empty_list)
  }

  def searchCountryAirportsAndRunways(country: String, byIsoCode: Boolean): HashMap[String, List[Runway]] = {
    val returnRunways:HashMap[String, List[Runway]] = new HashMap()
    if(byIsoCode == true){
      /* select from code column */
      airports.foreach{
        case (key_airport, value_airport) => if (country == value_airport.iso_country) {
          runways.foreach{
            case (key_runway, value_runway) => if (key_airport == value_runway.airport_ref) {   
              if (returnRunways.contains(value_airport.name)){
                returnRunways.update(value_airport.name, value_runway :: returnRunways.get(value_airport.name).getOrElse(Nil))
              } else{
                returnRunways.put(value_airport.name, List(value_runway))
              }
            }
          }
        }
      }
      returnRunways
    }
    else{
      /* select from name column */
      var empty: List[(String, Airport)] = List()
      var country_found = ""

      countries.foreach{
        case (key,value) => if (fuzzy_search(country,key)) {country_found = key}
      } 

      var current_country = countries.get(country_found) match { 
        case None => Country(0, "error", "error", "error", "error", "error")
        case Some(value) => value
      }

      var airports_tri: List[(String, Airport)] = tri_par_pays(airports.toList, current_country.code, empty)

      airports_tri.foreach{
        case (key_airport, value_airport) => if (current_country.code == value_airport.iso_country) {
          runways.foreach{
            case (key_runway, value_runway) => if (key_airport == value_runway.airport_ref) {   
              if (returnRunways.contains(value_airport.name)){
                returnRunways.update(value_airport.name, value_runway :: returnRunways.get(value_airport.name).getOrElse(Nil))
              } else{
                returnRunways.put(value_airport.name, List(value_runway))
              }
            }
          }
        }
      }

      returnRunways
    }
  }
}