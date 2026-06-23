package est.uespi.views;

import javax.swing.*;

import est.uespi.client.ClientHttp;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoAluno extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputTurmaId;
    private int idAluno;
    private int widthSize = 400, heightSize = 350;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=aluno";


    public ViewFormAtualizacaoAluno(int id, String nomeAluno) {
        super("Atualização de Aluno - " + nomeAluno);
        this.idAluno = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(idAluno);
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + idAluno, "GET");
            String response = client.request();

            JSONObject responseObject = new JSONObject(response);
            inputNome.setText(responseObject.getString("nome"));
            inputEmail.setText(responseObject.getString("email"));
            inputTelefone.setText(responseObject.getString("telefone"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter aluno: " + e.getMessage());
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

        add(this.getLabel("ID da Turma:"));
        inputTurmaId = this.getInput(10);
        add(inputTurmaId);

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
                    String turmaIdStr = inputTurmaId.getText().trim();

                    if (nome.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Os campos Nome e Email são obrigatórios!", 
                            "Aviso", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JSONObject payload = new JSONObject();
                    payload.put("nome", nome);
                    payload.put("email", email);
                        
                    if (!telefone.isEmpty()) {
                        payload.put("telefone", telefone);
                    }
                    if (!turmaIdStr.isEmpty()) {
                        payload.put("turma_id", Integer.parseInt(turmaIdStr)); 
                    }

                    ClientHttp client = new ClientHttp(baseUrl + "&id=" + idAluno, "PUT", payload.toString());
                    String response = client.request();
                    JSONObject responseObject = new JSONObject(response);
                    JOptionPane.showMessageDialog(null, responseObject, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
                catch(Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar aluno", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return updateButton;
    }
}