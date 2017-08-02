
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws IOException {
		
			for(int i = 1; i < 10; i++) {
			
				String reponse = readURL("http://www.trouver-une-entreprise-artisanale.cma94.com/entreprise.php?id=" + i);
				
								
				int indexDebut = reponse.indexOf("<div id=\"podCoordonnee\">");
				
				String debut = reponse.substring(indexDebut);
				
				int indexH1 = debut.indexOf("<h1>");
				int indexFinH1 = debut.indexOf("</h1>");
				int indexfinpremiereAdresse = debut.indexOf("<br />");
				String debut2 = debut.substring(indexfinpremiereAdresse + 6);
				int indexfindeuxiemeAdresse = debut2.indexOf("<br />");
				int indextel = debut2.indexOf("Téléphone : ");
				int indexactivite = debut2.indexOf("<div class=\"activite\">");
				String debut4 = debut2.substring(indexactivite + "<div class=\"activite\">".length()).trim();
				int indexfinactivite = debut4.indexOf("</div>");
				

//				int indexfintel = debut.indexOf("</div>");
//				int indexactivite = debut.indexOf("<div class=\"activite\">");
//				int indexfinactivite = debut.indexOf("<div class=\"clear\">");
				
				
							
				// Le nom :
				System.out.println(debut.substring(indexH1 + 4, indexFinH1));
				
				// L'adresse :
				System.out.println(debut.substring(indexFinH1 + 5, indexfinpremiereAdresse).trim());
							
				System.out.println(debut2.substring(0, indexfindeuxiemeAdresse).trim());
				
				// Le téléphone :
				if(indextel != -1) {
					String debut3 = debut2.substring(indextel);
					int indexfintel = debut3.indexOf("<br />");
					System.out.println(debut3.substring(12, indexfintel));
				}
				
//				System.out.println(debut.substring(indextel + 12, indexfintel - 8).trim());
				
//				
//				// L'activité :
//				
//				System.out.println(debut.substring(indexactivite + 22, indexfinactivite - 10).trim());
				System.out.println(debut4.substring(0, indexfinactivite).trim());
				System.out.println();
				
		}
	}

	
	public static String readURL(String url) throws IOException {
		  try(InputStream is = new URL(url).openConnection().getInputStream()) {			
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		 
		    StringBuilder builder = new StringBuilder();
		    for(String line = reader.readLine(); line != null; line = reader.readLine()) {
		      builder.append(line + "\n");
		    }
		    return builder.toString();
		  }
		}

}
