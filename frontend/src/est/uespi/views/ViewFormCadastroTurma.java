package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormCadastroTurma extends JFrame {

    private JTextField inputCurso, inputBlocoAtual;
    private int widthSize = 450, heightSize = 250;
    
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=turma";

    public ViewFormCadastroTurma() {
        super("Cadastro de Turmas");
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
        JLabel titulo = new JLabel("Cadastro de Turmas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        inputCurso = new JTextField(20);
        painelFormulario.add(inputCurso, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Bloco:"), gbc);
        gbc.gridx = 1;
        inputBlocoAtual = new JTextField(20);
        painelFormulario.add(inputBlocoAtual, gbc);
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

        submitButton.addActionListener(e -> {
            try {
                String curso = inputCurso.getText().trim();
                String bloco = inputBlocoAtual.getText().trim();

                if (curso.isEmpty() || bloco.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "O campo Curso é obrigatório!", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JSONObject payload = new JSONObject();
                payload.put("curso", curso);
                
                if (!bloco.isEmpty()) {
                    payload.put("bloco_atual", bloco); 
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
                    "O campo Bloco Atual deve conter apenas números.", 
                    "Erro de Formatação", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar turma.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return submitButton;
    }

    public JButton getCleanTextButton() {
        JButton cleanTextButton = new JButton("Limpar campos");
        
        cleanTextButton.addActionListener(e -> {
            inputCurso.setText("");
            inputBlocoAtual.setText("");
        });

        return cleanTextButton;
    }
}