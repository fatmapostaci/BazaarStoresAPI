package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;


public class BazaarStoresBaseUrl {
    /*
    swagger dökumani
    https://bazaarstores.com/api/documentation#/
    */
    public  static RequestSpecification spec;


 //   @Before  //her senaryondan önce çalışması gerekiyorsa Before tagi
   //her senaryodan önce çalışması gerekmiyorsa static blokla da çalışabiliriz
   static {  //bu class yüklendiğinde sadece bir kez çalışır. Böylece spec hiçbir zaman null kalmaz
       spec = new RequestSpecBuilder()
               .setBaseUri(ConfigReader.getProperty("base_url"))
               .setContentType(ContentType.JSON)
               .setAccept(ContentType.JSON)
               .build();

 }
}
