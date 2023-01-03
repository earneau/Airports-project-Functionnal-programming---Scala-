
import java.io.File
import scala.io.Source
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

object Loading {
  case class Airport(name: String, ident: String, airport_type: String, latitude_deg: String, longitude_deg: String, elevation_ft: String, continent: String, iso_country: String, iso_region: String, municipality: String, scheduled_service: String)
  case class Country(id: Integer, code: String, name: String, continent : String, wikipedia_link: String, keywords: String)
  case class Runway(airport_ref: String, airport_ident: String, length_ft: String, width_ft: String, surface: String, lighted: String, closed: String, le_ident: String)

  var airports:HashMap[String, Airport]  = new HashMap()
  var countries:HashMap[String, Country] = new HashMap()
  var runways:HashMap[String, Runway] = new HashMap()

  def loadCountries(): Unit ={
  }

  def loadAirports(): Unit ={
  }

  def loadRunways(): Unit ={
  }
}

