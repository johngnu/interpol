package rest.client;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author desktop
 */
public class RestClient {

    private static final String URL = "https://ws-public.interpol.int/notices/v1/red";
    private static final String OUTPUT_CSV_FILE = "/redalerts.csv";
    private static Map<String, Object> alerts = new HashMap<>();

    // Country codes from https://www.interpol.int
    private static final String[] COUNTRIES = {"AF", "AL", "DZ", "AS", "AD", "AO", "AI", "AG", "AR", "AM", "AU", "AT", "AZ", "BS", "BH", "BD",
        "BB", "BY", "BE", "BZ", "BJ", "BT", "BO", "BA", "BW", "BR", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "CF",
        "TD", "CL", "CN", "CO", "KM", "CG", "CD", "CR", "HR", "CU", "CY", "CZ", "CI", "DK", "DJ", "DM", "DO", "EC", "EG",
        "SV", "GQ", "ER", "EE", "SZ", "ET", "FJ", "FI", "FR", "GA", "GM", "GE", "DE", "GH", "GR", "GD", "GT", "GN", "GW",
        "GY", "HT", "HN", "HU", "914", "IS", "IN", "ID", "IR", "IQ", "IE", "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KI",
        "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MG", "MW", "MY", "MV", "ML",
        "MT", "MH", "MR", "MU", "MX", "FM", "MD", "MC", "MN", "ME", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "NZ", "NI",
        "NE", "NG", "MK", "NO", "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "QA", "RO", "RU", "RW",
        "KN", "LC", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "SS", "ES",
        "LK", "916", "SD", "SR", "SE", "CH", "SY", "TJ", "TZ", "TH", "TL", "TG", "TO", "TT", "TN", "TR", "TM", "TC", "TV",
        "UG", "UA", "922", "UNK", "AE", "GB", "US", "UY", "UZ", "VU", "VA", "VE", "VN", "YE", "ZM", "ZW"};

    public static void main(String[] args) {
        try {

            // create a CSV printer
            CSVPrinter printer = new CSVPrinter(new FileWriter(OUTPUT_CSV_FILE), CSVFormat.DEFAULT);
            // create header row
            printer.printRecord("Alias", "FullName", "Fec_nac", "EntityId");
            int total = 0;
            for (String c : COUNTRIES) {
                System.out.println(c);

                //URL url = new URL(URL + "?nationality=PE&resultPerPage=160&page=1");
                URL url = new URL(URL + "?nationality=" + c + "&resultPerPage=160&page=1");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output = br.readLine(); // se obtiene en 1 linea no necesario while
                /*System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {                
                }*/

                //System.out.println(output);
                Gson gson = new Gson();
                Result data = gson.fromJson(output, Result.class);

                //System.out.println(gson.toJson(data));
                int count = 0;
                // create data rows            
                for (Notice n : data.getEmbedded().getNotices()) {
                    if (insert(n.getEntity_id(), n)) {
                        printer.printRecord(n.getForename(), n.getName(), n.getDate_of_birth(), n.getEntity_id());
                        count++;
                    }
                }
                if (data.getTotal() >= 160) {
                    System.out.println("Total: " + data.getTotal());
                    System.out.println("count: " + count);
                    System.out.println("country: " + c);
                }
                total += data.getTotal();
                conn.disconnect();
            }

            System.out.println("Total leidos: " + total);
            // inter.flush();
            printer.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean insert(String id, Notice n) {
        if (alerts.get(id) == null) {
            alerts.put(id, n);
            return true;
        }
        return false;
    }
}
