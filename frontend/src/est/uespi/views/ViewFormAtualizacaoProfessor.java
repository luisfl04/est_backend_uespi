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
    private int widthSize = 450, heightSize = 350;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=professor";
    private String nomeProfessor;

    public ViewFormAtualizacaoProfessor(int id, String nomeProfessor) {
        super("Atualização de Professor - " + nomeProfessor);
        this.nomeProfessor = nomeProfessor;
        this.idProfessor = id;
        setLayout(new BorderLayout(10, 10));
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
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

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Professor - " + this.nomeProfessor, SwingConstants.CENTER);
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
        painelFormulario.add(new JLabel("Formacao:"), gbc);
        gbc.gridx = 1;
        inputFormacao = new JTextField(10);
        painelFormulario.add(inputFormacao, gbc);
        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));        
        JButton btnSubmit = this.getUpdateButton();
        JButton btnClose = this.getCloseButton();
        painelBotoes.add(btnSubmit);
        painelBotoes.add(btnClose);
        add(painelBotoes, BorderLayout.SOUTH);
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