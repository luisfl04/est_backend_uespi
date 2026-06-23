package est.uespi.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ClientHttp {

    private String url;
    private String method;
    private String jsonBody;


    public ClientHttp(String url, String method) {
        this.url = url;
        this.method = method.toUpperCase();
        this.jsonBody = null;
    }

    public ClientHttp(String url, String method, String jsonBody) {
        this.url = url;
        this.method = method.toUpperCase();
        this.jsonBody = jsonBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private HttpURLConnection getUrlConnection() throws Exception {
        try {
            URI endpoint = new URI(this.getUrl());
            HttpURLConnection conexao = (HttpURLConnection) endpoint.toURL().openConnection();
            
            conexao.setRequestMethod(this.method);
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conexao.setConnectTimeout(5000); 
            conexao.setReadTimeout(5000);

            return conexao;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao estabelecer conexão com servidor: " + e.getMessage());
        }
    }

    public String request() throws Exception {
        HttpURLConnection conexao = null;
        try {
            conexao = this.getUrlConnection();

            if ((this.method.equals("POST") || this.method.equals("PUT")) && this.jsonBody != null && !this.jsonBody.isEmpty()) {
                conexao.setDoOutput(true);
                try (DataOutputStream writerBody = new DataOutputStream(conexao.getOutputStream())) {
                    writerBody.write(this.jsonBody.getBytes(StandardCharsets.UTF_8));
                    writerBody.flush();
                }
            }

            int responseCode = conexao.getResponseCode();
            InputStream is;

            if (responseCode >= 200 && responseCode < 300) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }

            if (is != null) {
                try (BufferedReader readerResponse = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = readerResponse.readLine()) != null) {
                        response.append(inputLine);
                    }
                    return response.toString();
                }
            }
            return "";

        } catch (Exception e) {
            throw new RuntimeException("Erro na requisição: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }
    }
}