package interface_multitelas_04_03;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudAluno extends JFrame{

    private int widthSize = 1000, heightSize = 1000;

    public ViewCrudAluno() {
        super("Gerenciamento de Alunos");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[] tituloColunas = {"Nome", "Email", "Telefone"};
        Object[][] dados = this.getDados();
        return new JTable(dados, tituloColunas);
    }

    public void addComponents() {
        JTable tabelaListagem = this.getTabelaListagem();
        JScrollPane barraRolagem = new JScrollPane(tabelaListagem);
        JButton submitButton = this.getSubmitButton();
        add(barraRolagem);
        add(submitButton);
    }

    public Object[][] getDados(){
        try {
            return new Object[][]{
                {"Ana Monteiro", "48 9923-7898", "ana.monteiro@gmail.com"},
                {"João da Silva", "48 8890-3345", "joaosilva@hotmail.com"},
                {"Pedro Cascaes", "48 9870-5634", "pedrinho@gmail.com"}
            };
        }
        catch (Exception e){
            return null;
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ViewFormCadastroAluno formCadastroAluno = new ViewFormCadastroAluno();
                formCadastroAluno.setVisible(true);
            }
        });

        return submitButton;
    }
}
