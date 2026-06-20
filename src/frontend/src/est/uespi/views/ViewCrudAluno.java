package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudAluno extends JFrame {

    private int widthSize = 800, heightSize = 600;

    public ViewCrudAluno() {
        super("Gerenciamento de Alunos");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[] tituloColunas = {"ID", "Nome", "Email", "Telefone", "ID Turma"};
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

    public Object[][] getDados() {
        try {
            // Futura integração com ClientHttp ("GET" na URL do aluno)
            return new Object[][]{
                {1, "Ana Monteiro", "ana.monteiro@gmail.com", "86 99923-7898", 1},
                {2, "João da Silva", "joaosilva@hotmail.com", "86 98890-3345", 1},
                {3, "Pedro Cascaes", "pedrinho@gmail.com", "86 99870-5634", 2}
            };
        } catch (Exception e) {
            return new Object[0][0];
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Novo Aluno");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // ViewFormCadastroAluno form = new ViewFormCadastroAluno();
                // form.setVisible(true);
            }
        });
        return submitButton;
    }
}