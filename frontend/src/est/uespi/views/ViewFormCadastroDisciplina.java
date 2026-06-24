package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormCadastroDisciplina extends JFrame {

    private JTextField inputNome, inputCursoRelacionado, inputBlocoRelacionado;
    private int widthSize = 450, heightSize = 300;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=disciplina";


    public ViewFormCadastroDisciplina() {
        super("Cadastro de Disciplinas");
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

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Cadastro de Disciplina", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);
        
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome da disciplina:"), gbc);
        gbc.gridx = 1;
        inputNome = new JTextField(20);
        painelFormulario.add(inputNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Curso relacionado:"), gbc);
        gbc.gridx = 1;
        inputCursoRelacionado = new JTextField(20);
        painelFormulario.add(inputCursoRelacionado, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Bloco(Número):"), gbc);
        gbc.gridx = 1;
        inputBlocoRelacionado = new JTextField(20);
        painelFormulario.add(inputBlocoRelacionado, gbc);
        
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

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");

        submitButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText().trim();
                String curso = inputCursoRelacionado.getText().trim();
                String blocoStr = inputBlocoRelacionado.getText().trim();

                if (nome.isEmpty() || curso.isEmpty() || blocoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "Os campos Nome e Curso são obrigatórios!", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JSONObject payload = new JSONObject();
                payload.put("nome", nome);
                payload.put("curso_relacionado", curso);
                
                
                if (!blocoStr.isEmpty()) {
                    payload.put("bloco_relacionado", Integer.parseInt(blocoStr)); 
                }

                ClientHttp client = new ClientHttp(baseUrl, "POST", payload.toString());
                String response = client.request();
                
                JSONObject responseObject = new JSONObject(response);

                if (responseObject.has("mensagem")) {
                    JOptionPane.showMessageDialog(null, 
                        responseObject.getString("mensagem"), 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                        
                    dispose();
                    
                } else if (responseObject.has("erro")) {
                    JOptionPane.showMessageDialog(null, 
                        responseObject.getString("erro"), 
                        "Erro no Cadastro", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, 
                    "O campo Bloco deve conter apenas números.", 
                    "Erro de Formatação", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar disciplina.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return submitButton;
    }

    public JButton getCleanTextButton() {
        JButton cleanTextButton = new JButton("Limpar campos");
        
        cleanTextButton.addActionListener(e -> {
            inputNome.setText("");
            inputCursoRelacionado.setText("");
            inputBlocoRelacionado.setText("");
        });

        return cleanTextButton;
    }
}