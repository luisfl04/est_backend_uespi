package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormAtualizacaoTurma extends JFrame {

    private JTextField inputCurso, inputBlocoAtual;
    private int idTurma;
    private int widthSize = 400, heightSize = 250;
    
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=turma";

    public ViewFormAtualizacaoTurma(int id, String nomeCurso) {
        super("Atualização de Turma - " + nomeCurso);
        this.idTurma = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(idTurma);
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + id, "GET");
            String response = client.request();
            JSONObject responseObject = new JSONObject(response);
            
            inputCurso.setText(responseObject.optString("curso", ""));
            inputBlocoAtual.setText(responseObject.optString("bloco_atual", ""));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Curso:"));
        inputCurso = this.getInput(20);
        add(inputCurso);

        add(this.getLabel("Bloco Atual:"));
        inputBlocoAtual = this.getInput(10);
        add(inputBlocoAtual);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        
        updateButton.addActionListener(e -> {
            try {
                String curso = inputCurso.getText().trim();
                String blocoStr = inputBlocoAtual.getText().trim();

                if (curso.isEmpty()) {
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

                ClientHttp client = new ClientHttp(baseUrl + "&id=" + idTurma, "PUT", payload.toString());
                String response = client.request();
                JSONObject responseObject = new JSONObject(response);
                
                String mensagem = responseObject.has("mensagem") ? 
                                  responseObject.getString("mensagem") : "Turma atualizada com sucesso!";
                                  
                JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, 
                    "O campo Bloco Atual deve conter apenas números.", 
                    "Erro de Formatação", 
                    JOptionPane.ERROR_MESSAGE);
            } catch(Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao atualizar turma", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return updateButton;
    }
}