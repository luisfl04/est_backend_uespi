package interface_multitelas_04_03;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudAluno extends JFrame{
    private JTable table;
    private JScrollPane barraRolagem;
    JButton submit;

    public ViewCrudAluno() {
        super("Gerenciamento de Alunos");
        setLayout(new FlowLayout());
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        // Modelo de implementação:
        //      TableModel dataModel = new AbstractTableModel() {
        //       public int getColumnCount() { return 10; }
        //       public int getRowCount() { return 10;}
        //       public Object getValueAt(int row, int col) { return Integer.valueOf(row*col); }
        //   };
        //   JTable table = new JTable(dataModel);
        //   JScrollPane scrollpane = new JScrollPane(table);

        String[] tituloColunas = {"Nome", "Email", "Telefone"};
        Object dados = this.getDados(); 
        JTable tabaleListagem = new JTable(dados, tituloColunas);
    }

    public void addComponents() {
        JTable tabelaListagem = this.getTabelaListagem();
    }


    public Object[][] getDados(){
        try {
            Object[][] dados = {    
                {"Ana Monteiro", "48 9923-7898", "ana.monteiro@gmail.com"},
                {"João da Silva", "48 8890-3345", "joaosilva@hotmail.com"},
                {"Pedro Cascaes", "48 9870-5634", "pedrinho@gmail.com"}
            };

            return dados;
        }
        catch (Exception e){
            return null;
        }
    }

    String [] colunas = {"Nome", "Telefone", "Email"};




}
