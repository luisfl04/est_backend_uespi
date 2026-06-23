package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudProfessor extends JFrame {

    private int widthSize = 800, heightSize = 600;

    public ViewCrudProfessor() {
        super("Gerenciamento de Professores");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[] tituloColunas = {"ID", "Nome", "Email", "Telefone", "Formação"};
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
            // Futura integração com ClientHttp ("GET" na URL do professor)
            return new Object[][]{
                {1, "Carlos Mendes", "carlos.mendes@uespi.br", "86 91234-5678", "Doutorado em Ciência da Computação"},
                {2, "Luciana Costa", "luciana.costa@uespi.br", "86 98765-4321", "Mestrado em Redes de Computadores"}
            };
        } catch (Exception e) {
            return new Object[0][0];
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Novo Professor");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // ViewFormCadastroProfessor form = new ViewFormCadastroProfessor();
                // form.setVisible(true);
            }
        });
        return submitButton;
    }
}