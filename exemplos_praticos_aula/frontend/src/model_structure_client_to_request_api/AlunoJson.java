package model_structure_client_to_request_api;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.json.JSONObject;

public class AlunoJson {

    public JSONObject object;

    public AlunoJson(String nome, String telefone, String email, String token, String operacao) {
        object = new JSONObject();
        object.put("nome", nome);
        object.put("fone", telefone);
        object.put("email", email);
        object.put("token", token);
        object.put("op", operacao);
    }

}