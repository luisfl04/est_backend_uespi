package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCrudTurma extends JFrame {

    private int widthSize = 800, heightSize = 600; // Tamanho reduzido levemente para melhor visualização padrão

    public ViewCrudTurma() {
        super("Gerenciamento de Turmas");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[] tituloColunas = {"ID", "Curso", "Bloco Atual"};
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
            /* * INTEGRAÇÃO FUTURA COM A API:
             * ClientHttp client = new ClientHttp("http://localhost/api.php?entidade=turma", "GET");
             * String json = client.request();
             * // Aqui você usaria o Gson/Jackson para converter a String json na Matriz de Objetos abaixo.
             */
            
            return new Object[][]{
                {1, "Sistemas para Internet", 1},
                {2, "Ciência da Computação", 3},
                {3, "Engenharia de Software", 2}
            };
        } catch (Exception e) {
            return new Object[0][0]; // Retorna tabela vazia em caso de erro
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Nova Turma");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Supondo que você criará esta classe futuramente
                // ViewFormCadastroTurma form = new ViewFormCadastroTurma();
                // form.setVisible(true);
            }
        });
        return submitButton;
    }
}