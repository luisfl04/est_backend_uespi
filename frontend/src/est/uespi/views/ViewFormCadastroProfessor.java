package est.uespi.views;

import javax.swing.*;

import est.uespi.client.ClientHttp;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormCadastroProfessor extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputFormacao;
    private String baseURL = "http://localhost/backend/api/process_request.php?entidade=professor";
    private String method = "POST";
    private int widthSize = 400, heightSize = 350;

    public ViewFormCadastroProfessor() {
        super("Cadastro de Professores");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
    }

    public JLabel getLabel(String label) {
        return new JLabel(label);
    }

    public JTextField getInput(int numberColumns) {
        return new JTextField(numberColumns);
    }

    public void addComponents() {
        JLabel labelNome = this.getLabel("Nome:");
        inputNome = this.getInput(20);
        add(labelNome);
        add(inputNome);

        JLabel labelEmail = this.getLabel("Email:");
        inputEmail = this.getInput(20);
        add(labelEmail);
        add(inputEmail);

        JLabel labelTelefone = this.getLabel("Telefone:");
        inputTelefone = this.getInput(20);
        add(labelTelefone);
        add(inputTelefone);

        JLabel labelFormacao = this.getLabel("Formação:");
        inputFormacao = this.getInput(20);
        add(labelFormacao);
        add(inputFormacao);

        JButton submitButton = this.getSubmitButton();
        JButton cleanButton = this.getCleanTextButton();
        add(submitButton);
        add(cleanButton);
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == submitButton) {
                    try {
                        String nome = inputNome.getText().trim();
                        String email = inputEmail.getText().trim();
                        String telefone = inputTelefone.getText().trim();
                        String formacao = inputFormacao.getText().trim();

                        if (nome.isEmpty() || email.isEmpty() || formacao.isEmpty()) {
                            JOptionPane.showMessageDialog(null, 
                                "Campos obrigatórios não foram informados.", 
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

                        ClientHttp client = new ClientHttp(baseURL, method, payload.toString());
                        String response = client.request();
                        JSONObject jsonResponse = new JSONObject(response);

                        if (jsonResponse.has("mensagem")) {
                            JOptionPane.showMessageDialog(null, 
                                jsonResponse.getString("mensagem"), 
                                "Cadastro Realizado", 
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        
                            inputNome.setText("");
                            inputEmail.setText("");
                            inputTelefone.setText("");
                            inputFormacao.setText("");
                                
                        } else if (jsonResponse.has("erro")) {
                            JOptionPane.showMessageDialog(null, 
                                jsonResponse.getString("erro"), 
                                "Falha no Cadastro", 
                                JOptionPane.ERROR_MESSAGE);
                        }

                    } 
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, 
                            "Erro ao persistir dados: " + e.getMessage(), 
                            "Erro de Conexão", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                
                }
            }
        });

        return submitButton;
    }

    public JButton getCleanTextButton() {
        JButton cleanTextButton = new JButton("Limpar campos");
        cleanTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == cleanTextButton) {
                    inputNome.setText("");
                    inputEmail.setText("");
                    inputTelefone.setText("");
                    inputFormacao.setText("");
                }
            }
        });

        return cleanTextButton;
    }
}