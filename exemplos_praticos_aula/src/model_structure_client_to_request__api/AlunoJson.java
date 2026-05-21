import org.json.JSONObject;

public class AlunoJson {

    public JSONObject object;

    public AlunoJson(String nome, String telefone, String email, String token) {
        JSONObject aluno = new JSONObject();
        aluno.put("nome", nome);
        aluno.put("fone", telefone);
        aluno.put("email", email);
        aluno.put("token", token);
    }
}