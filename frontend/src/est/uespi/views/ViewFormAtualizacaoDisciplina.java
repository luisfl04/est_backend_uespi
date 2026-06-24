package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormAtualizacaoDisciplina extends JFrame {

    private JTextField inputNome;
    private JComboBox<String> selectBloco, selectCurso;
    private int idDisciplina;
    private int widthSize = 450, heightSize = 300;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=disciplina";
    private String nomeDisciplina;


    public ViewFormAtualizacaoDisciplina(int id, String nomeDisciplina) {
        super("Atualização de Disciplina - " + nomeDisciplina);
        this.nomeDisciplina = nomeDisciplina;
        this.idDisciplina = id;
        setLayout(new BorderLayout(10,10));
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
        this.carregarDados(idDisciplina);
    }

    public JComboBox<String> getSelectBoxCursos() {
        String[] cursos = {"", "Sistemas para Internet", "Ciência da Computação"};
        JComboBox<String> selectBoxCursos = new JComboBox<String>(cursos);
        return selectBoxCursos;
    }

    public JComboBox<String> getSelectBoxBlocos() {
        String[] blocos = {"", "1", "2", "3", "4", "5", "6", "7", "8"};
        JComboBox<String> selectBoxBlocos = new JComboBox<String>(blocos);
        return selectBoxBlocos;
    }

    private void carregarDados(int id) {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl + "&id=" + id, "GET");
            String response = client.request();
            JSONObject responseObject = new JSONObject(response);
            
            inputNome.setText(responseObject.optString("nome", ""));
            selectBloco.setSelectedItem(responseObject.optString("bloco_relacionado", ""));
            selectCurso.setSelectedItem(responseObject.optString("curso_relacionado"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter disciplina: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        JLabel titulo = new JLabel("Disciplina - " + this.nomeDisciplina, SwingConstants.CENTER);
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
        painelFormulario.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        selectCurso = this.getSelectBoxCursos();
        painelFormulario.add(selectCurso, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Bloco:"), gbc);
        gbc.gridx = 1;
        selectBloco = this.getSelectBoxBlocos();
        painelFormulario.add(selectBloco, gbc);
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
        
        updateButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText().trim();
                String curso = (String) selectCurso.getSelectedItem();
                String bloco = (String) selectBloco.getSelectedItem();

                if (nome.isEmpty() || curso.isEmpty() || bloco.isEmpty() ) {
                    JOptionPane.showMessageDialog(null, 
                        "Campos obrigatórios ausentes, verifique e tente novamente.", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JSONObject payload = new JSONObject();
                payload.put("nome", nome);
                payload.put("curso_relacionado", curso);
                    
                if (!bloco.isEmpty()) {
                    payload.put("bloco_relacionado", bloco); 
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