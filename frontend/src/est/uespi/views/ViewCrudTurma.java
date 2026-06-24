package est.uespi.views;

import est.uespi.client.ClientHttp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.lang.reflect.Type;
import org.json.JSONObject;
import org.json.JSONArray;

public class ViewCrudTurma extends JFrame {

    private int widthSize = 900, heightSize = 600;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=turma";
    private Object[] tituloColunas = {"ID", "Curso", "Bloco Atual", "Editar", "Excluir"};

    public ViewCrudTurma() {
        super("Gerenciamento de Turmas");
        setLayout(new BorderLayout(10, 10));
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[][] dados = this.getDados();
        
        DefaultTableModel model = new DefaultTableModel(dados, tituloColunas) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 3 || column == 4; 
            }
        };

        JTable tabela = new JTable(model);
        
        ActionListener acaoEditar = e -> {
            int linhaClicada = Integer.parseInt(e.getActionCommand());
            int idTurma = (int) tabela.getValueAt(linhaClicada, 0);
            String nomeCurso = (String) tabela.getValueAt(linhaClicada, 1);
            String blocoCurso = String.valueOf(tabela.getValueAt(linhaClicada, 2));  
            String identificacaoTurma = nomeCurso.concat("-").concat(blocoCurso);           
            ViewFormAtualizacaoTurma viewEdicao = new ViewFormAtualizacaoTurma(idTurma, identificacaoTurma);
            viewEdicao.setVisible(true);
        };

        ActionListener acaoExcluir = e -> {
            int linhaClicada = Integer.parseInt(e.getActionCommand());
            int idTurma = (int) tabela.getValueAt(linhaClicada, 0);
            String nomeCurso = (String) tabela.getValueAt(linhaClicada, 1);

            int resposta = JOptionPane.showConfirmDialog(
                this, 
                "Deseja realmente excluir a turma de " + nomeCurso + "?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    ClientHttp clientDelete = new ClientHttp(this.baseUrl + "&id=" + idTurma, "DELETE");
                    String response = clientDelete.request();
                    JSONObject responseObject = new JSONObject(response);
                    
                    if (responseObject.has("mensagem")) {
                        JOptionPane.showMessageDialog(null, 
                            responseObject.getString("mensagem"), 
                            "Informando", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    
                        ((DefaultTableModel) tabela.getModel()).removeRow(linhaClicada);                        

                    } else if (responseObject.has("erro")) {
                        JOptionPane.showMessageDialog(null, 
                            responseObject.getString("erro"), 
                            "Falha na Exclusão", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
                }
            }
        };

        tabela.getColumnModel().getColumn(3).setCellRenderer(new BotaoRenderizador());
        tabela.getColumnModel().getColumn(3).setCellEditor(new BotaoEditor(tabela, acaoEditar, "..."));
        
        tabela.getColumnModel().getColumn(4).setCellRenderer(new BotaoRenderizador());
        tabela.getColumnModel().getColumn(4).setCellEditor(new BotaoEditor(tabela, acaoExcluir, "..."));

        return tabela;
    }

    public Object[] getTitulosColunas() {
        return this.tituloColunas;
    }

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose()); 
        return closeButton;
    }

    private JMenuBar criarMenuNavegacao() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuNavegar = new JMenu("Navegação");

        JMenuItem menuHome = new JMenuItem("Tela Inicial");
        JMenuItem menuProfessor = new JMenuItem("Professores");
        JMenuItem menuDisciplina = new JMenuItem("Disciplinas");
        JMenuItem menuTurma = new JMenuItem("Turmas");

        menuProfessor.addActionListener(e -> { dispose(); new ViewCrudProfessor().setVisible(true); });
        menuDisciplina.addActionListener(e -> { dispose(); new ViewCrudDisciplina().setVisible(true); });
        menuTurma.addActionListener(e -> { dispose(); new ViewCrudTurma().setVisible(true); });
        menuHome.addActionListener(e -> { dispose(); });


        menuNavegar.add(menuHome);
        menuNavegar.addSeparator(); 
        menuNavegar.add(menuProfessor);
        menuNavegar.add(menuDisciplina);
        menuNavegar.add(menuTurma);

        menuBar.add(menuNavegar);
        return menuBar;
    }

    public void addComponents() {
        setJMenuBar(this.criarMenuNavegacao());
        JTable tabelaListagem = this.getTabelaListagem();
        JScrollPane barraRolagem = new JScrollPane(tabelaListagem);
        barraRolagem.setPreferredSize(new Dimension(800, 400));
        
        JPanel painelCentral = new JPanel(); 
        painelCentral.add(barraRolagem);
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.add(this.getSubmitButton());
        painelBotoes.add(this.getCloseButton());
        
        add(painelBotoes, BorderLayout.SOUTH);
    }

    public Object[][] getDados() {
        try {
            ClientHttp client = new ClientHttp(this.baseUrl, "GET");
            String response = client.request(); 
            
            if(response == null || response.trim().isEmpty()) {
                return new Object[0][0];
            }

            JSONArray arrayJson = new JSONArray(response);
            Object[][] dados = new Object[arrayJson.length()][this.getTitulosColunas().length];
            
            for(int i = 0; i < arrayJson.length(); i++) {
                JSONObject obj = arrayJson.getJSONObject(i);
                
                dados[i][0] = obj.getInt("id");
                dados[i][1] = obj.isNull("curso") ? "Indefinido" : obj.getString("curso");
                dados[i][2] = obj.isNull("bloco_atual") ? "Indefinido" : obj.getInt("bloco_atual");
                
                dados[i][3] = "editar"; 
                dados[i][4] = "excluir"; 
            }

            return dados;
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados do backend: " + e.getMessage());
            return new Object[0][0];
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Nova Turma");
        submitButton.addActionListener(e -> {
            ViewFormCadastroTurma form = new ViewFormCadastroTurma();
            form.setVisible(true);
        });
        return submitButton;
    }

    class BotaoRenderizador extends JButton implements TableCellRenderer {
        public BotaoRenderizador() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class BotaoEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public BotaoEditor(JTable table, ActionListener actionListener, String labelTexto) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                isPushed = true;
                int linha = table.getEditingRow();
                fireEditingStopped();
                actionListener.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, String.valueOf(linha)));
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }
    }
}