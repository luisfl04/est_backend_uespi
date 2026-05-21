import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FormularioCadastro extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JButton btnEnviar;

    
    public FormularioCadastro() {
        setTitle("Cadastro de Usuário");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.addComponents();
    }
    
    private void addComponents() {
        JLabel labelNome = new JLabel("Nome:");
        txtNome = new JTextField(20);
        
        JLabel labelEmail = new JLabel("E-mail:");
        txtEmail = new JTextField(20);
        
        JLabel labelTelefone = new JLabel("Telefone:");
        txtTelefone = new JTextField(20);
        
        
        JPanel panel = this.getBasePanel();
        panel.add(labelNome);
        panel.add(txtNome);
        panel.add(labelEmail);
        panel.add(txtEmail);
        panel.add(labelTelefone);
        panel.add(txtTelefone);
        btnEnviar = this.getSubmitButton();
        panel.add(btnEnviar);
        add(panel);
    }
    
    private String getEndpointUrl() {
        return "http://www.datse.com.br/dev/cadastroalunov1.php"; 
    }   

    private String getMatricula() {
        return "2023045778";
    }

    private JButton getSubmitButton() {
        btnEnviar = new JButton("Enviar dados");
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData();
            }
        });

        return btnEnviar;
    }

    private JPanel getBasePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JLabel getLabel(String textLabel) {
        return new JLabel(textLabel);
    }

    private JTextField getInput(int numberColumns) {
        return new JTextField(numberColumns);
    }

    private String getJsonBody() {
        return String.format(
            "{\"nome\": \"%s\", \"fone\": \"%s\", \" email\": \"%s\", \" token\": \"%s\"}",
            this.txtNome.getText().trim(), this.txtTelefone.getText().trim(), this.txtEmail.getText().trim(), this.getMatricula()
        );
    }

    private void sendData() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String jsonBody = this.getJsonBody();

        btnEnviar.setEnabled(false);
        btnEnviar.setText("Enviando...");
        
        HttpClient client = HttpClient.newHttpClient();
        String endPointUrl = this.getEndpointUrl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endPointUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    SwingUtilities.invokeLater(() -> {
                        btnEnviar.setEnabled(true);
                        btnEnviar.setText("Cadastrar");
                        JOptionPane.showMessageDialog(this, "Resposta do servidor: " + body, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    });
                })
                .exceptionally(e -> {
                    SwingUtilities.invokeLater(() -> {
                        btnEnviar.setEnabled(true);
                        btnEnviar.setText("Cadastrar");
                        JOptionPane.showMessageDialog(this, "Erro de conexão:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    });
                    return null;
                });
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FormularioCadastro().setVisible(true);
        });
    }
}