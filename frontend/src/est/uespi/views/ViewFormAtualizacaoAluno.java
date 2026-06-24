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
    private int widthSize = 450, heightSize = 350;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=aluno";
    private String nomeAluno;


    public ViewFormAtualizacaoAluno(int id, String nomeAluno) {
        super("Atualização de Aluno - " + nomeAluno);
        this.nomeAluno = nomeAluno;
        this.idAluno = id;
        setLayout(new BorderLayout(10,10));
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
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

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Aluno - " + this.nomeAluno, SwingConstants.CENTER);
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
        painelFormulario.add(new JLabel("ID da Turma:"), gbc);
        gbc.gridx = 1;
        inputTurmaId = new JTextField(10);
        painelFormulario.add(inputTurmaId, gbc);
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