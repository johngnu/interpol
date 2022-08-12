package rest.client;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author desktop
 */
public class RestClient {

    private static final String URL = "https://ws-public.interpol.int/notices/v1/red";
    private static final String OUTPUT_CSV_FILE = "/redalerts.csv";

    public static void main(String[] args) {
        try {
            URL url = new URL(URL + "?nationality=BO&resultPerPage=100&page=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();
            /*System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {                
            }*/

            System.out.println(output);
            Gson gson = new Gson();
            Result data = gson.fromJson(output, Result.class);

            System.out.println(gson.toJson(data));

            // create a CSV printer
            CSVPrinter printer = new CSVPrinter(new FileWriter(OUTPUT_CSV_FILE), CSVFormat.DEFAULT);
            // create header row
            printer.printRecord("Alias", "FullName", "Fec_nac", "EntityId");

            // create data rows
            for (Notice n : data.getEmbedded().getNotices()) {
                printer.printRecord(n.getForename(), n.getName(), n.getDate_of_birth(), n.getEntity_id());
            }

            // inter.flush();
            printer.close();

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
