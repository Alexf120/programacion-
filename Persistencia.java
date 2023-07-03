/// Configuracion JSOUP
<dependencies>
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.14.3</version>
    </dependency>
</dependencies>

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class AmazonScraper {
    public static void main(String[] args) {
        try {
            // Conectar con la página de resultados de Amazon
            Document doc = Jsoup.connect("https://www.amazon.com/s?k=juegos").get();

            // Seleccionar los elementos que contienen los productos
            Elements productElements = doc.select(".s-result-item");

            // Crear un archivo CSV para escribir los datos
            FileWriter writer = new FileWriter("productos.csv");

            // Escribir los datos de cada producto en el archivo CSV
            for (Element productElement : productElements) {
                String title = productElement.select(".a-size-base-plus").text();
                String price = productElement.select(".a-price-whole").text();

                // Escribir los datos en una nueva línea del archivo CSV
                writer.write(title + "," + price + "\n");
            }

            writer.close();
            System.out.println("El scraping se completó correctamente.");

        } catch (IOException e) {
            System.out.println("Ocurrió un error durante el scraping: " + e.getMessage());
        }
    }
}