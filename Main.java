package cma;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		String param1 = args[0];
		String param2 = args[1];

		FileOutputStream out = new FileOutputStream("CMA94" +  "_" + param1 + "_" + param2 + ".xls");
		Workbook wb = new HSSFWorkbook();

		try {
			Sheet s = wb.createSheet("CMA94");
			Row r = null;
			r = s.createRow(0);
			Cell cellulenom = r.createCell(0);
			cellulenom.setCellValue("Raison sociale");
			Cell celluleadresse = r.createCell(1);
			celluleadresse.setCellValue("Adresse");
			Cell cellulecp = r.createCell(2);
			cellulecp.setCellValue("Ville");
			Cell celluletelephone = r.createCell(3);
			celluletelephone.setCellValue("Téléphone");
			Cell celluleactivite = r.createCell(4);
			celluleactivite.setCellValue("Activité");
			Cell celluledate = r.createCell(5);
			celluledate.setCellValue("Date");

			int index = 1;
			for (int i = Integer.valueOf(param1); i < Integer.valueOf(param2); i++) {
				if (i % 5 == 0) {
					Thread.sleep(5000);
				}

				r = s.createRow(index++);

				cellulenom = r.createCell(0);
				celluleadresse = r.createCell(1);
				cellulecp = r.createCell(2);
				celluletelephone = r.createCell(3);
				celluleactivite = r.createCell(4);
				celluledate = r.createCell(5);

				String reponse = readURL(
						"http://www.trouver-une-entreprise-artisanale.cma94.com/entreprise.php?id=" + i);

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

				// Le nom :
				cellulenom.setCellValue(debut.substring(indexH1 + 4, indexFinH1));

				// L'adresse :
				celluleadresse.setCellValue(debut.substring(indexFinH1 + 5, indexfinpremiereAdresse).trim());

				cellulecp.setCellValue(debut2.substring(0, indexfindeuxiemeAdresse).trim());

				// Le téléphone :
				if (indextel != -1) {
					String debut3 = debut2.substring(indextel);
					int indexfintel = debut3.indexOf("<br />");
					celluletelephone.setCellValue(debut3.substring(12, indexfintel));
				}

				// L'activité :

				celluleactivite.setCellValue(debut4.substring(0, indexfinactivite).trim());

				// La date :

				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy h:mm:ss"));
				celluledate.setCellValue(new Date());
				celluledate.setCellStyle(cellStyle);

			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			wb.write(out);
			wb.close();
			out.close();
			System.out.println("finish");
		}
	}

	public static String readURL(String url) throws IOException {
		try (InputStream is = new URL(url).openConnection().getInputStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

			StringBuilder builder = new StringBuilder();
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				builder.append(line + "\n");
			}
			return builder.toString();
		}
	}

}
