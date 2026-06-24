package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormAtualizacaoTurma extends JFrame {

    private JTextField inputBlocoAtual;
    private JComboBox<String> selectCurso;
    private int idTurma;
    private int widthSize = 450, heightSize = 250;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=turma";
    private String identificacaoTurma;


    public ViewFormAtualizacaoTurma(int id, String identificacaoTurma) {
        super("Atualização de Turma - " + identificacaoTurma);
        this.identificacaoTurma = identificacaoTurma;
        this.idTurma = id;
        setLayout(new BorderLayout(10,10));
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
        this.carregarDados(idTurma);
    }

    public JComboBox<String> getSelectBoxCursos() {
        String[] cursos = {"", "Sistemas para Internet", "Ciência da Computação"};
        JComboBox<String> selectBox = new JComboBox<String>(cursos);
        return selectBox;
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + id, "GET");
            String response = client.request();
            JSONObject responseObject = new JSONObject(response);
            selectCurso.setSelectedItem(responseObject.optString("curso", ""));
            inputBlocoAtual.setText(responseObject.optString("bloco_atual", ""));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        JLabel titulo = new JLabel("Turma " + this.identificacaoTurma, SwingConstants.CENTER);
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
        selectCurso = this.getSelectBoxCursos();
        painelFormulario.add(selectCurso, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Bloco:"), gbc);
        gbc.gridx = 1;
        inputBlocoAtual = new JTextField(20);
        painelFormulario.add(inputBlocoAtual, gbc);
        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));        
        JButton btnSubmit = this.getUpdateButton();
        JButton btnClose = this.getCloseButton();
        painelBotoes.add(btnSubmit);
        painelBotoes.add(btnClose);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        
        updateButton.addActionListener(e -> {
            try {
                String curso = (String) selectCurso.getSelectedItem();
                String bloco = inputBlocoAtual.getText().trim();

                if (curso.isEmpty()) {
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