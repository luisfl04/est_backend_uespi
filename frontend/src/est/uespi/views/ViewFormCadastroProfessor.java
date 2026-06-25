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
    private int widthSize = 450, heightSize = 350;

    public ViewFormCadastroProfessor() {
        super("Cadastro de Professores");
        setLayout(new BorderLayout(10,10));
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JLabel getLabel(String label) {
        return new JLabel(label);
    }

    public JTextField getInput(int numberColumns) {
        return new JTextField(numberColumns);
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Cadastro de Professor", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        inputNome = new JTextField(20);
        painelFormulario.add(inputNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        inputEmail = new JTextField(20);
        painelFormulario.add(inputEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        inputTelefone = new JTextField(20);
        painelFormulario.add(inputTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painelFormulario.add(new JLabel("Formação: "), gbc);
        gbc.gridx = 1;
        inputFormacao = new JTextField(20);
        painelFormulario.add(inputFormacao, gbc);

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));        
        JButton btnSubmit = this.getSubmitButton();
        JButton btnClose = this.getCloseButton();
        JButton cleanButton = this.getCleanTextButton();
        painelBotoes.add(btnSubmit);
        painelBotoes.add(btnClose);
        painelBotoes.add(cleanButton);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
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
                            dispose();                            
                                
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