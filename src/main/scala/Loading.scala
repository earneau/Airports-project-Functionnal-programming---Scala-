
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
    val file = new File("./src/data/countries.csv")
    val reader = Source.fromFile(file)
    reader
      .getLines()
      .drop(1)
      .foreach(line => {
        val cols = line.split(",", -1).map(_.trim)
        val name_country = cols(2).slice(1, cols(2).length() - 1)
        val country_code = cols(1).slice(1,3)
        countries.put(name_country, Country(cols(0).toInt, country_code, cols(2), cols(3), cols(4), cols(5)))
      })
    reader.close()
  }

  def loadAirports(): Unit ={
    val file = new File("./src/data/airports.csv")
    val reader = Source.fromFile(file)
    reader
      .getLines()
      .drop(1)
      .foreach(line => {
        val cols = line.split(",").map(_.trim)
        val iso_country = cols(8).slice(1, cols(8).length() - 1)
        airports.put(cols(0), Airport(cols(3), cols(1), cols(2), cols(4), cols(5), cols(6), cols(7), iso_country, cols(9), cols(10), cols(11)))
      })
    reader.close()
  }

  def loadRunways(): Unit ={
    val file = new File("./src/data/runways.csv")
    val reader = Source.fromFile(file)
    reader
      .getLines()
      .drop(1)
      .foreach(line => {
        val cols = line.split(",", -1).map(_.trim)
        runways.put(cols(0), Runway(cols(1), cols(2), cols(3), cols(4), cols(5), cols(6), cols(7), cols(8)))
      })
    reader.close()
  }
}

