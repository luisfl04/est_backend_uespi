package est.uespi.views;

import javax.swing.*;
import est.uespi.client.ClientHttp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;


public class ViewFormCadastroAluno extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone;
    private JComboBox<ItemTurmaNew> comboTurma;
    private int widthSize = 450, heightSize = 400; 
    private String baseURL = "http://localhost/backend/api/process_request.php?entidade=aluno";
    private String turmaURL = "http://localhost/backend/api/process_request.php?entidade=turma";
    private String method = "POST"; 

    
    public ViewFormCadastroAluno() {
        super("Cadastro de Alunos");
        setLayout(new BorderLayout(10, 10));
        setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JComboBox<ItemTurmaNew> getSelectBoxTurmas() {
        JComboBox<ItemTurmaNew> combo = new JComboBox<>();
        combo.addItem(new ItemTurmaNew(0, "Selecione uma turma..."));
        
        try {
            ClientHttp client = new ClientHttp(turmaURL, "GET");
            String response = client.request();
            
            if(response != null && !response.trim().isEmpty()) {
                JSONArray arrayTurmas = new JSONArray(response);
                
                for(int i = 0; i < arrayTurmas.length(); i++) {
                    JSONObject obj = arrayTurmas.getJSONObject(i);
                    int id = obj.getInt("id");
                    String curso = obj.optString("curso", "Sem curso");
                    String bloco = obj.optString("bloco_atual", "0");
                    String descricao = curso + " - Bloco " + bloco; 
                    combo.addItem(new ItemTurmaNew(id, descricao));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar turmas no select: " + e.getMessage());
        }
        
        return combo;
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Cadastro de Aluno", SwingConstants.CENTER);
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
        painelFormulario.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        comboTurma = this.getSelectBoxTurmas();
        painelFormulario.add(comboTurma, gbc);

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

    public JButton getCleanTextButton() {
        JButton cleanTextButton = new JButton("Limpar campos");
        
        cleanTextButton.addActionListener(e -> {
            inputNome.setText("");
            inputEmail.setText("");
            inputTelefone.setText("");
            comboTurma.setSelectedIndex(0);
        });

        return cleanTextButton;
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");
        
        submitButton.addActionListener(e -> {
            try {
                String nome = inputNome.getText().trim();
                String email = inputEmail.getText().trim();
                String telefone = inputTelefone.getText().trim();
                ItemTurmaNew turmaSelecionada = (ItemTurmaNew) comboTurma.getSelectedItem();

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
                if (turmaSelecionada != null && turmaSelecionada.getId() > 0) {
                    payload.put("turma_id", turmaSelecionada.getId()); 
                }

                ClientHttp client = new ClientHttp(baseURL, method, payload.toString());
                String response = client.request();

                JSONObject jsonResponse = new JSONObject(response);

                if (jsonResponse.has("mensagem")) {
                    JOptionPane.showMessageDialog(null, 
                        jsonResponse.getString("mensagem"), 
                        "Cadastro Realizado", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                    inputNome.setText("");
                    inputEmail.setText("");
                    inputTelefone.setText("");
                    comboTurma.setSelectedIndex(0);
                    dispose();
                    
                } else if (jsonResponse.has("erro")) {
                    JOptionPane.showMessageDialog(null, 
                        jsonResponse.getString("erro"), 
                        "Falha no Cadastro", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, 
                    "O campo ID da Turma deve conter apenas números.", 
                    "Erro de Formato", 
                    JOptionPane.ERROR_MESSAGE);
                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao se comunicar com o servidor: " + ex.getMessage(), 
                    "Erro de Conexão", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        return submitButton;
    }

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }
}

class ItemTurmaNew {
    // Armazena dados da entidade 'Turma' para disponibilizá-los no formulário.
    
    private int id;
    private String descricao;

    public ItemTurmaNew(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

