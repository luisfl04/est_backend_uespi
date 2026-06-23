package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormCadastroDisciplina extends JFrame {

    private JTextField inputNome, inputCursoRelacionado, inputBlocoRelacionado;
    private int widthSize = 400, heightSize = 300;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=disciplina";


    public ViewFormCadastroDisciplina() {
        super("Cadastro de Disciplinas");
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
        JLabel labelNome = this.getLabel("Nome da Disciplina:");
        inputNome = this.getInput(20);
        add(labelNome);
        add(inputNome);

        JLabel labelCurso = this.getLabel("Curso Relacionado:");
        inputCursoRelacionado = this.getInput(20);
        add(labelCurso);
        add(inputCursoRelacionado);

        JLabel labelBloco = this.getLabel("Bloco Relacionado (Número):");
        inputBlocoRelacionado = this.getInput(10);
        add(labelBloco);
        add(inputBlocoRelacionado);

        JButton submitButton = this.getSubmitButton();
        JButton cleanButton = this.getCleanTextButton();
        add(submitButton);
        add(cleanButton);
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