import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.json.JSONObject;

public class ViewCrudAluno extends JFrame{
    JTable tabela;
    JScrollPane barraRolagem;
    JButton cadastrar;
    JButton listar;

    Object [][] dados = {
        {"Ana Ana", "88 9999-7898", "ana @gmail.com"},
        {"Silva da Silva", "99 8800-0088", "silva@hotmail.com"},
        {"Pedro Pedro", "89 9898-1234", "pedro@gmail.com"}
    };
    
    String [] colunas = {"Nome", "Telefone", "Email"};

    public ViewCrudAluno(){
        super("Listar Cadastro de Alunos");
        setLayout(new FlowLayout());
        this.addCadastrar();
        this.addListar();
        this.addComponentes();
    }


    public void addComponentes(){
        tabela = new JTable(dados, colunas);
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

    public void addListar() {
        listar = new JButton("Listar");
        listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento){
                try {
                    AlunoJson JSON = new AlunoJson("", "", "", "");
                    String json_string = JSON.object.toString();
                    ClienteHTTP Conexao = new ClienteHTTP(json_string,"http://www.datse.com.br/dev/listaralunosv1.php");
                    String ret = Conexao.conecta();

                    JSONObject obj = new JSONObject(ret);   
                    dados[0][0] = obj.getString("nome");
                    dados[0][1] = obj.getString("fone");
                    dados[0][2] = obj.getString("email");
                    tabela = new JTable(dados, colunas);
                    barraRolagem = new JScrollPane(tabela);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro!");
                }
            } }
        );
    
        add(listar);
    }

    } 