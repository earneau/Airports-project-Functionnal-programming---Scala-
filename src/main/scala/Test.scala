import scala.annotation.tailrec
import scala.collection.mutable.HashMap
import Loading._
import Quering._
/*import scala.compiletime.ops.int*/

object Test {
    def test_showTop10Countries(): Unit = {
        if (length(showTop10Countries(true)) != 10) {
            println("//REPORT : showTop10Countries// erreur: on ne retourne pas bien le top 10")
        }

        if (length(showTop10Countries(false)) < 1) {
            println("//REPORT : showTop10Countries// erreur: on ne retourne pas de pays")
        }
    }

    def test_showTop10RunwaysLatitude(): Unit = {

        if (length(showTop10RunwaysLatitude()) != 10){
            println("//REPORT : showTop10RunwaysLatitude// erreur: on ne retourne pas bien le top 10")
        }
    }

    def test_searchCountryAirportsAndRunways(): Unit = {
        if (searchCountryAirportsAndRunways("FR",true).isEmpty) {
            println("//QUERY : searchCountryAirportsAndRunways// erreur: aucun aéroports ni runway n'est retourné")
        } 

        if (searchCountryAirportsAndRunways("France",false).isEmpty) {
            println("//QUERY : searchCountryAirportsAndRunways// erreur: aucun aéroports ni runway n'est retourné")
        }

        if (searchCountryAirportsAndRunways("Fra",false).isEmpty) {
            println("//QUERY : searchCountryAirportsAndRunways// erreur: le name matching ne fonctionne pas")
        }
    }

    def testing(): Unit = {
        test_showTop10Countries()
        test_showTop10RunwaysLatitude()
        test_searchCountryAirportsAndRunways()
    }
}