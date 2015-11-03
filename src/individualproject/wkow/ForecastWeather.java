package individualproject.wkow;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by michaelklein on 11/3/15.
 */
@XmlRootElement(name = "forecast")
public class ForecastWeather {
    private String forecastDate = null;
    private String hiToday = null;
    private String loToday = null;

    // No arg constructor
    public ForecastWeather() {
    }

    // Constructor
    public ForecastWeather(String forecastDate, String hiToday, String loToday) {
        this.forecastDate = forecastDate;
        this.hiToday = hiToday;
        this.loToday = loToday;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    @XmlAttribute
    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getHiToday() {
        return hiToday;
    }

    @XmlElement
    public void setHiToday(String hiToday) {
        this.hiToday = hiToday;
    }

    public String getLoToday() {
        return loToday;
    }

    @XmlElement
    public void setLoToday(String loToday) {
        this.loToday = loToday;
    }

    @Override
    public String toString() {
        return new StringBuffer(" Forecast Date : ").append(this.forecastDate)
                .append(" High Today : ").append(this.hiToday)
                .append(" Low Today : ").append(this.loToday).toString();
    }


    public String ForecastedHigh() {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.wkow.com/global/interface/httprequest/hrproxy.asp?url=http%3A%2F%2Fdata-services.wsi.com%2F200904-01%2F426717546%2FWeather%2FReport%2F53706&rand=852947").get();

            Elements e = doc.select("DailyForecast Day[DayNum=1]");

            System.out.println("Date: " + e.attr("ValidDateLocal") + "\nHigh Temp: " + e.attr("HiTempF") + "\nLow Temp: " + e.attr("LoTempF"));
            hiToday = e.attr("HiTempF");
            System.out.println("High Temp Variable: " + hiToday);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return hiToday;
    }

    public String ForecastedLow() {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.wkow.com/global/interface/httprequest/hrproxy.asp?url=http%3A%2F%2Fdata-services.wsi.com%2F200904-01%2F426717546%2FWeather%2FReport%2F53706&rand=852947").get();

            Elements e = doc.select("DailyForecast Day[DayNum=1]");

            System.out.println("Date: " + e.attr("ValidDateLocal") + "\nHigh Temp: " + e.attr("HiTempF") + "\nLow Temp: " + e.attr("LoTempF"));
            loToday = e.attr("LoTempF");
            System.out.println("Low Temp Variable: " + loToday);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return loToday;
    }
}
