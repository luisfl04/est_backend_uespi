package est.uespi.views;

import est.uespi.client.ClientHttp;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewCrudAluno extends JFrame {

    private int widthSize = 800, heightSize = 600;
    private String baseUrl = "http://localhost/backend/api/process_request.php?entidade=aluno";
    private Object[] tituloColunas = {"ID", "Nome", "Email", "Telefone", "ID Turma"};

    public ViewCrudAluno() {
        super("Gerenciamento de Alunos");
        setLayout(new FlowLayout());
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        this.addComponents();
    }

    public JTable getTabelaListagem() {
        Object[][] dados = this.getDados();
        return new JTable(dados, tituloColunas);
    }

    public Object[] getTitulosColunas() {
        return this.tituloColunas;
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
            Object[][] dados = {};
            ClientHttp client = new ClientHttp(this.baseUrl, "GET");
            String response = client.request(); 
            
            if(response == null || response.trim().isEmpty()) {
                return dados;
            }

            JSONArray arrayJson = new JSONArray(response);
            dados = new Object[arrayJson.length()][this.getTitulosColunas().length];
            
            for(int i = 0; i < arrayJson.length(); i++) {
                JSONObject obj = arrayJson.getJSONObject(i);
                dados[i][0] = obj.getInt("id");
                dados[i][1] = obj.getString("nome");
                dados[i][2] = obj.getString("email");
                dados[i][3] = obj.isNull("telefone") ? "Indefinido" : obj.getString("telefone");
                dados[i][4] = obj.isNull("turma_id") ? "Indefinida" : obj.getInt("turma_id");
            }

            return dados;
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados do backend: " + e.getMessage());
            return new Object[0][0];
        }
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar Novo Aluno");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ViewFormCadastroAluno form = new ViewFormCadastroAluno();
                form.setVisible(true);
            }
        });
        return submitButton;
    }
}