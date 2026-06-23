package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormAtualizacaoDisciplina extends JFrame {

    private JTextField inputNome, inputCursoRelacionado, inputBlocoRelacionado;
    private int idDisciplina;
    private int widthSize = 400, heightSize = 300;
    
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=disciplina";

    public ViewFormAtualizacaoDisciplina(int id, String nomeDisciplina) {
        super("Atualização de Disciplina - " + nomeDisciplina);
        this.idDisciplina = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(idDisciplina);
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + id, "GET");
            String response = client.request();
            JSONObject responseObject = new JSONObject(response);
            
            inputNome.setText(responseObject.optString("nome", ""));
            inputCursoRelacionado.setText(responseObject.optString("curso_relacionado", ""));
            inputBlocoRelacionado.setText(responseObject.optString("bloco_relacionado", ""));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter disciplina: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Nome da Disciplina:"));
        inputNome = this.getInput(20);
        add(inputNome);

        add(this.getLabel("Curso Relacionado:"));
        inputCursoRelacionado = this.getInput(20);
        add(inputCursoRelacionado);

        add(this.getLabel("Bloco Relacionado (Número):"));
        inputBlocoRelacionado = this.getInput(10);
        add(inputBlocoRelacionado);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        
        updateButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText().trim();
                String curso = inputCursoRelacionado.getText().trim();
                String blocoStr = inputBlocoRelacionado.getText().trim();

                if (nome.isEmpty() || curso.isEmpty() || blocoStr.isEmpty() ) {
                    JOptionPane.showMessageDialog(null, 
                        "Campos obrigatórios ausentes, verifique e tente novamente.", 
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

                ClientHttp client = new ClientHttp(baseUrl + "&id=" + idDisciplina, "PUT", payload.toString());
                String response = client.request();                
                JSONObject responseObject = new JSONObject(response);
                
                String mensagem = responseObject.has("mensagem") ? 
                                  responseObject.getString("mensagem") : "Disciplina atualizada com sucesso!";
                                  
                JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, 
                    "O campo Bloco deve conter apenas números.", 
                    "Erro de Formatação", 
                    JOptionPane.ERROR_MESSAGE);
            } catch(Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao atualizar disciplina", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return updateButton;
    }
}