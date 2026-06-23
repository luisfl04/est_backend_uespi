package est.uespi.views;

import javax.swing.*;

import est.uespi.client.ClientHttp;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoProfessor extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputFormacao;
    private int idProfessor;
    private int widthSize = 400, heightSize = 350;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=professor";


    public ViewFormAtualizacaoProfessor(int id, String nomeProfessor) {
        super("Atualização de Professor - " + nomeProfessor);
        this.idProfessor = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(idProfessor);
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + idProfessor, "GET");
            String response = client.request();
            JSONObject responseObject = new JSONObject(response);
            
            inputNome.setText(responseObject.optString("nome", ""));
            inputEmail.setText(responseObject.optString("email", ""));
            inputTelefone.setText(responseObject.optString("telefone", ""));
            inputFormacao.setText(responseObject.optString("formacao", ""));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter professor: " + e.getMessage());
        }
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Nome:"));
        inputNome = this.getInput(20);
        add(inputNome);

        add(this.getLabel("Email:"));
        inputEmail = this.getInput(20);
        add(inputEmail);

        add(this.getLabel("Telefone:"));
        inputTelefone = this.getInput(20);
        add(inputTelefone);

        add(this.getLabel("Formação:"));
        inputFormacao = this.getInput(20);
        add(inputFormacao);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String nome = inputNome.getText().trim();
                    String email = inputEmail.getText().trim();
                    String telefone = inputTelefone.getText().trim();
                    String formacao = inputFormacao.getText().trim();

                    if (nome.isEmpty() || email.isEmpty() || formacao.isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Os campos Nome e Email são obrigatórios!", 
                            "Aviso", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JSONObject payload = new JSONObject();
                    payload.put("nome", nome);
                    payload.put("email", email);
                    payload.put("formacao", formacao);    

                    if (!telefone.isEmpty()) {
                        payload.put("telefone", telefone);
                    }

                    ClientHttp client = new ClientHttp(baseUrl + "&id=" + idProfessor, "PUT", payload.toString());
                    String response = client.request();
                    JSONObject responseObject = new JSONObject(response);
                    
                    String mensagem = responseObject.has("mensagem") ? 
                                      responseObject.getString("mensagem") : "Professor atualizado com sucesso!";
                                      
                    JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
                catch(Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar professor", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return updateButton;
    }
}