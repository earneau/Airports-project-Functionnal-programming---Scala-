
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

  def searchCountryAirportsAndRunways(country: String, byIsoCode: Boolean): Unit = {
    if(byIsoCode == true){

    }
    else{
    }
  }
}