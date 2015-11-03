package individualproject.wkow;

import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

/**
 * Created by michaelklein on 11/3/15.
 */
// The Java class will be hosted at the URI path "/wkowWeather"
@Path("/wkowWeather")
public class WeatherService {
    Document doc;
    ForecastWeather myForecast = new ForecastWeather();

    public WeatherService() {
        try {
            doc = Jsoup.connect("http://www.wkow.com/global/interface/httprequest/hrproxy.asp?url=http%3A%2F%2Fdata-services.wsi.com%2F200904-01%2F426717546%2FWeather%2FReport%2F53706&rand=852947").get();

            Elements e = doc.select("DailyForecast Day[DayNum=1]");

            myForecast.setForecastDate(e.attr("ValidDateLocal"));
            myForecast.setHiToday(e.attr("HiTempF"));
            myForecast.setLoToday(e.attr("LoTempF"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/xml")
    public ForecastWeather getDateWeatherHighLow() {
        // Return forecast
        return myForecast;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/wkowWeather");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
