package model_structure_client_to_request_api;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.json.JSONObject;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class ViewCrudAluno extends JFrame{
    DefaultTableModel modeloTabela;
    JTable tabela;
    JScrollPane barraRolagem;
    JButton cadastrar;
    JButton listar;

    Object [][] dados = {};
    
    String [] colunas = {"Nome", "Telefone", "Email", "Ação"};

    public ViewCrudAluno(){
        super("Listar Cadastro de Alunos");
        setLayout(new FlowLayout());
        this.addCadastrar();
        this.addListar();
        this.addComponentes();
    }

    public JButton getButtonRemove(String token, String fone) {
        JButton buttonRemove = new JButton("Remover");
        buttonRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("token", token);
                    json.put("fone", fone);
                    json.put("op", "EXCLUIR");
                    String jsonString = json.toString();
                    ClienteHTTP Conexao = new ClienteHTTP(jsonString);
                    String ret = Conexao.conecta();
                    String messageLog = "Retorno API: " + ret;
                    JOptionPane.showMessageDialog(null, messageLog);
                }
                catch (Exception e) {
                    System.out.println("Erro ao excluir aluno: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e);
                }
            }
        });
        return buttonRemove;
    }
    
    public void addComponentes(){
        modeloTabela = new DefaultTableModel(this.colunas, 0);
        tabela = new JTable(modeloTabela);
        barraRolagem = new JScrollPane(tabela);
        add(barraRolagem);
    }

    public void addCadastrar() {
        cadastrar = new JButton("Cadastrar");
        cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento){
                ViewCadastrarAluno meuPainel2 = new ViewCadastrarAluno();
                meuPainel2.setSize(250,400);
                meuPainel2.setVisible(true);
            }
        });
        
        add(cadastrar);
    }    

    public ActionListener getAcaoDeletar(String token, String fone) {
        ActionListener acaoDeletar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                try {
                    int linha = Integer.parseInt(evento.getActionCommand());                
                    JSONObject json = new JSONObject();
                    json.put("token", token);
                    json.put("fone", fone);
                    json.put("op", "EXCLUIR");
                    ClienteHTTP conexao = new ClienteHTTP(json.toString());
                    System.out.println("Json enviado para operação de exclusão: " + json.toString());
                    String retorno = conexao.conecta();
                    JOptionPane.showMessageDialog(null, "Retorno da operação de exclusão: " + retorno);
                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
                    model.removeRow(linha);
                    JOptionPane.showMessageDialog(null, "Aluno removido com sucesso!");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e.getMessage());
                }
            }
        };

        return acaoDeletar;
    }

    public void addListar() {
        listar = new JButton("Listar");
        listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento){
                try {
                    AlunoJson JSON = new AlunoJson("", "", "", "2023045778", "LISTAR");
                    String json_string = JSON.object.toString();
                    ClienteHTTP Conexao = new ClienteHTTP(json_string);
                    String ret = Conexao.conecta();
                    System.out.println("Retorno da API: " + ret);
                    modeloTabela.setRowCount(0);
                    JSONObject jsonRetorno = new JSONObject(ret);
                    System.out.println("Json de retorno: " + jsonRetorno);
                    String nomeRetorno = jsonRetorno.getString("nome");
                    String foneRetorno = jsonRetorno.getString("fone");
                    String emailRetorno = jsonRetorno.getString("email"); 
                    Object[] newRowTable = {nomeRetorno, foneRetorno, emailRetorno, "Excluir"};
                    modeloTabela.addRow(newRowTable);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(new BotaoTabelaCelular(tabela, getAcaoDeletar("2023045778", foneRetorno)));
                    tabela.getColumnModel().getColumn(3).setCellEditor(new BotaoTabelaCelular(tabela, getAcaoDeletar("2023045778",foneRetorno)));
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro!");
                }
            } }
        );
    
        add(listar);
    }

}

class BotaoTabelaCelular extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    
    private final JButton botao;
    private String label;
    private final JTable tabela;

    public BotaoTabelaCelular(JTable tabela, ActionListener acaoClique) {
        this.tabela = tabela;
        this.botao = new JButton();
        this.botao.setOpaque(true);

        this.botao.addActionListener(e -> {
            int linhaSelecionada = tabela.getEditingRow();
            acaoClique.actionPerformed(new ActionEvent(tabela, ActionEvent.ACTION_PERFORMED, String.valueOf(linhaSelecionada)));            
            fireEditingStopped(); 
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        botao.setText((value == null) ? "" : value.toString());
        return botao;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        botao.setText(label);
        return botao;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

 
}