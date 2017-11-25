package web.scraping.jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import web.scraping.jsoup.vo.Proceeding;
import web.scraping.jsoup.ProceedingUtils;


public class WebCrawler {

	private static final String URL_BASE = "https://investigacion.us.es/sisius/sis_showpub.php?idpers=4051";
	private static final String TEXT_INI = "Otra participación en Libros de Actas";
	private static final String TEXT_FIN = "Aportaciones a Congresos";

	public static void main(String[] args) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(URL_BASE), 10000);
		doc.select("br").append("\\n");
		
		Element div = doc.getElementsByAttributeValue("style", "margin-left:1cm").first();
		String div_text = div.text();
		
		String plain_text = div_text.subSequence(div_text.indexOf(TEXT_INI) + TEXT_INI.length(), 
				div_text.indexOf(TEXT_FIN)).toString();
		
		List<String> splitted_text = Arrays.asList(plain_text.split("\\\\n"));
		List<Proceeding> proceedings = new ArrayList<Proceeding>();
		
		int i = 1;
		String editors = "";
		for(String str: splitted_text) {
			if(!str.matches("\\s*")) {
				if(i%2 == 0) {
					proceedings.add(ProceedingUtils.createProceeding(editors.trim(), str.trim()));
				} else {
					editors = str;
					System.out.println(proceedings);
				}
				i++;
			}
			
		}
		
		
		MongoUtils.populateDB(proceedings);
	}

}
