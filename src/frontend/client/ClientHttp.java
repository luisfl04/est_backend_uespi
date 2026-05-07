package src.frontend.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;

public class ClientHttp {

    private String usuario, senha, url, urlParameters;

    public ClientHttp(String user, String senha, String url) throws Exception {
        try {
            this.setUrl(url);
            this.setUsuario(user);
            this.setSenha(senha);
            this.setUrlParameters();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao construir o cliente HTTP: " + e.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getCredentials() throws  Exception {
        try {
            return "usuario=" + URLEncoder.encode(this.getUsuario(), "UTF-8") + "&senha=" + URLEncoder.encode(this.getSenha(), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrlParameters() {
        return urlParameters;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setUrlParameters() throws Exception {
        this.urlParameters = this.getCredentials();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private HttpURLConnection getUrlConnection() throws Exception{
        try {
            URI endpoint = new URI(this.getUrl());
            return (HttpURLConnection) endpoint.toURL().openConnection();
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao estabelecer conexão com servidor: " + e.getMessage());
        }
    }

    public String request() throws Exception {
        try {
            // Criando conexão com servidor
            HttpURLConnection conexao = this.getUrlConnection();
            conexao.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexao.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Enviando requisição
            conexao.setDoOutput(true);
            DataOutputStream writerBody = new DataOutputStream(conexao.getOutputStream());
            writerBody.writeBytes(this.getUrlParameters());
            writerBody.flush();
            writerBody.close();
            int responseCode = conexao.getResponseCode();

            // Manipulando resposta:
            BufferedReader readerResponse = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = readerResponse.readLine()) != null) {
                response.append(inputLine);
            }
            readerResponse.close();

            return response.toString();
        }
        catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        }

    }
}