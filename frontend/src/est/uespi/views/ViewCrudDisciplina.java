package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudDisciplina extends JFrame {

    private int widthSize = 800, heightSize = 600;

    public ViewCrudDisciplina() {
        super("Gerenciamento de Disciplinas");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[] tituloColunas = {"ID", "Nome", "Curso Relacionado", "Bloco Relacionado"};
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
            // Futura integração com ClientHttp ("GET" na URL da disciplina)
            return new Object[][]{
                {1, "Programação Orientada a Objetos", "Sistemas para Internet", 2},
                {2, "Estrutura de Dados", "Ciência da Computação", 3},
                {3, "Banco de Dados I", "Sistemas para Internet", 2}
            };
        } catch (Exception e) {
            return new Object[0][0];
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Nova Disciplina");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // ViewFormCadastroDisciplina form = new ViewFormCadastroDisciplina();
                // form.setVisible(true);
            }
        });
        return submitButton;
    }
}