package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormCadastroTurma extends JFrame {

    private JTextField inputCurso, inputBlocoAtual;
    private int widthSize = 400, heightSize = 250;
    
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=turma";

    public ViewFormCadastroTurma() {
        super("Cadastro de Turmas");
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
        JLabel labelCurso = this.getLabel("Curso:");
        inputCurso = this.getInput(20);
        add(labelCurso);
        add(inputCurso);

        JLabel labelBlocoAtual = this.getLabel("Bloco Atual (Número):");
        inputBlocoAtual = this.getInput(10);
        add(labelBlocoAtual);
        add(inputBlocoAtual);

        JButton submitButton = this.getSubmitButton();
        JButton cleanButton = this.getCleanTextButton();
        add(submitButton);
        add(cleanButton);
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");

        submitButton.addActionListener(e -> {
            try {
                String curso = inputCurso.getText().trim();
                String blocoStr = inputBlocoAtual.getText().trim();

                if (curso.isEmpty() || blocoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "O campo Curso é obrigatório!", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JSONObject payload = new JSONObject();
                payload.put("curso", curso);
                
                if (!blocoStr.isEmpty()) {
                    payload.put("bloco_atual", Integer.parseInt(blocoStr)); 
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