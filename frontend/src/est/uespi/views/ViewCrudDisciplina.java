package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.json.JSONObject;
import org.json.JSONArray;

public class ViewCrudDisciplina extends JFrame {

    private int widthSize = 800, heightSize = 600;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=disciplina";
    private Object[] tituloColunas = {"ID", "Nome", "Curso Relacionado", "Bloco Relacionado", "Editar", "Excluir"};

    public ViewCrudDisciplina() {
        super("Gerenciamento de Disciplinas");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[][] dados = this.getDados();
        
        DefaultTableModel model = new DefaultTableModel(dados, tituloColunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; 
            }
        };

        JTable tabela = new JTable(model);
        
        ActionListener acaoEditar = e -> {
            int linhaClicada = Integer.parseInt(e.getActionCommand());
            int idDisciplina = (int) tabela.getValueAt(linhaClicada, 0);
            String nomeDisciplina = (String) tabela.getValueAt(linhaClicada, 1);
            
            ViewFormAtualizacaoDisciplina viewEdicao = new ViewFormAtualizacaoDisciplina(idDisciplina, nomeDisciplina);
            viewEdicao.setVisible(true);
        };

        ActionListener acaoExcluir = e -> {
            int linhaClicada = Integer.parseInt(e.getActionCommand());
            int idDisciplina = (int) tabela.getValueAt(linhaClicada, 0);
            String nomeDisciplina = (String) tabela.getValueAt(linhaClicada, 1);

            int resposta = JOptionPane.showConfirmDialog(
                this, 
                "Deseja realmente excluir a disciplina " + nomeDisciplina + "?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    ClientHttp clientDelete = new ClientHttp(this.baseUrl + "&id=" + idDisciplina, "DELETE");
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

        tabela.getColumnModel().getColumn(4).setCellRenderer(new BotaoRenderizador());
        tabela.getColumnModel().getColumn(4).setCellEditor(new BotaoEditor(tabela, acaoEditar, "..."));
        
        tabela.getColumnModel().getColumn(5).setCellRenderer(new BotaoRenderizador());
        tabela.getColumnModel().getColumn(5).setCellEditor(new BotaoEditor(tabela, acaoExcluir, "..."));

        return tabela;
    }

    public Object[] getTitulosColunas() {
        return this.tituloColunas;
    }

    public void addComponents() {
        JTable tabelaListagem = this.getTabelaListagem();
        JScrollPane barraRolagem = new JScrollPane(tabelaListagem);
        JButton newObjectButton = this.getNewObjectButton();
        add(barraRolagem);
        add(newObjectButton);
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
                dados[i][1] = obj.getString("nome");
                dados[i][2] = obj.isNull("curso_relacionado") ? "Indefinido" : obj.getString("curso_relacionado");
                dados[i][3] = obj.isNull("bloco_relacionado") ? "Indefinido" : obj.getInt("bloco_relacionado");
                
                dados[i][4] = "Editar"; 
                dados[i][5] = "Excluir"; 
            }

            return dados;
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados do backend: " + e.getMessage());
            return new Object[0][0];
        }
    }

    public JButton getNewObjectButton() {
        JButton newObjectButton = new JButton("Cadastrar Nova Disciplina");
        newObjectButton.addActionListener(e -> {
            ViewFormCadastroDisciplina form = new ViewFormCadastroDisciplina();
            form.setVisible(true);
        });
        return newObjectButton;
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