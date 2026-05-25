import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ClienteHTTP {

    public String usuario, url, urlParameters, resposta;
    public int codretorno;
    
    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String usu) throws Exception {
        this.urlParameters = "post_json_aluno=" + URLEncoder.encode(usu, "UTF-8");
    }

    public ClienteHTTP(String us, String ur) throws Exception {
        this.setUrl(ur);
        this.setLogin(us);
    }

    public String conecta() throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set request method
        con.setRequestMethod("POST");

        // Set headers (optional)
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        // Get return Code
        int responseCode = con.getResponseCode();
        this.codretorno = responseCode;

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}