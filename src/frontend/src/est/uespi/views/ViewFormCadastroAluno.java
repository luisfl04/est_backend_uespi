package est.uespi.views;

import javax.swing.*;

import est.uespi.client.ClientHttp;
import utils.org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormCadastroAluno extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputTurmaId;
    private int widthSize = 400, heightSize = 350;

    public ViewFormCadastroAluno() {
        super("Cadastro de Alunos");
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

        JLabel labelTurmaId = this.getLabel("ID da Turma:");
        inputTurmaId = this.getInput(10);
        add(labelTurmaId);
        add(inputTurmaId);

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
                        // 1. Coletar os dados a partir dos inputs
                        String nome = inputNome.getText().trim();
                        String email = inputEmail.getText().trim();
                        String telefone = inputTelefone.getText().trim();
                        String turmaIdStr = inputTurmaId.getText().trim();

                        // Validação básica de frontend para evitar requisições desnecessárias
                        if (nome.isEmpty() || email.isEmpty()) {
                            JOptionPane.showMessageDialog(null, 
                                "Os campos Nome e Email são obrigatórios!", 
                                "Aviso", 
                                JOptionPane.WARNING_MESSAGE);
                            return; // Para a execução aqui
                        }

                        // 2. Criar o objeto JSON (Payload)
                        JSONObject payload = new JSONObject();
                        payload.put("nome", nome);
                        payload.put("email", email);
                        
                        // Tratamento para campos opcionais (só envia se o usuário digitou algo)
                        if (!telefone.isEmpty()) {
                            payload.put("telefone", telefone);
                        }
                        if (!turmaIdStr.isEmpty()) {
                            // Converte a string do input para Inteiro, conforme esperado pelo banco
                            payload.put("turma_id", Integer.parseInt(turmaIdStr)); 
                        }

                        // 3. Criar o objeto ClientHttp e fazer a requisição (POST)
                        // ATENÇÃO: Ajuste "this.baseUrl" ou a URL de acordo com o seu ambiente
                        String urlApi = "http://localhost/seu_projeto/api.php?entidade=aluno";
                        ClientHttp client = new ClientHttp(urlApi, "POST", payload.toString());
                        
                        // Dispara a requisição e guarda a resposta do servidor PHP
                        String response = client.request();

                        // 4. Analisar o retorno do servidor e renderizar a confirmação
                        JSONObject jsonResponse = new JSONObject(response);

                        // Verifica qual chave o PHP retornou
                        if (jsonResponse.has("mensagem")) {
                            // Sucesso (Status 201)
                            JOptionPane.showMessageDialog(null, 
                                jsonResponse.getString("mensagem"), 
                                "Cadastro Realizado", 
                                JOptionPane.INFORMATION_MESSAGE);
                            
                            // Limpa os campos após o sucesso
                            inputNome.setText("");
                            inputEmail.setText("");
                            inputTelefone.setText("");
                            inputTurmaId.setText("");
                            
                        } else if (jsonResponse.has("erro")) {
                            // Tratamento de erros de negócio (Ex: Status 409 - Email já cadastrado)
                            JOptionPane.showMessageDialog(null, 
                                jsonResponse.getString("erro"), 
                                "Falha no Cadastro", 
                                JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (NumberFormatException nfe) {
                        // Trata o erro caso o usuário digite letras no campo de ID da Turma
                        JOptionPane.showMessageDialog(null, 
                            "O campo ID da Turma deve conter apenas números.", 
                            "Erro de Formato", 
                            JOptionPane.ERROR_MESSAGE);
                            
                        } catch (Exception e) {
                            // Trata erros de rede, como servidor PHP desligado (Connection Refused)
                            JOptionPane.showMessageDialog(null, 
                                "Erro ao se comunicar com o servidor: " + e.getMessage(), 
                                "Erro de Conexão", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        );

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
                    inputTurmaId.setText("");
                }
            }
        });

        return cleanTextButton;
    }
}