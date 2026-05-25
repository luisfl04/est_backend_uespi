import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewCadastrarAluno extends JFrame{
        
    private JTextField temail, tnome, ttelefone;
    private JButton cadastrar, limpa;
    private JLabel lemail, lnome, ltelefone;

    public ViewCadastrarAluno(){
        super("Cadastrar Aluno");
        setLayout(new FlowLayout());
        this.addComponentes();
    }

    public void addComponentes() {
        lnome = new JLabel("Nome: ");
        add(lnome);

        tnome = new JTextField(20);
        add(tnome);

        ltelefone = new JLabel("Telefone: ");
        add(ltelefone);

        ttelefone = new JTextField(20);
        add(ttelefone);

        lemail = new JLabel("email: ");
        add(lemail);

        temail = new JTextField(20);
        add(temail);

        cadastrar = new JButton("Cadastrar");
        cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento){
                if(evento.getSource() == cadastrar) {
                    try {
                        AlunoJson JSON = new AlunoJson(tnome.getText(), ttelefone.getText(), temail.getText(), "colocar matricula aqui");
                        String json_string = JSON.object.toString();
                        ClienteHTTP Conexao = new ClienteHTTP(json_string,"http://www.datse.com.br/dev/cadastroalunov1.php");
                        String ret = Conexao.conecta();
                        JOptionPane.showMessageDialog(null, ret);   
                    } 
                    catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                }
            }
        });
        add(cadastrar);

        limpa = new JButton("Limpar");
        limpa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento){
                if(evento.getSource() == limpa){
                    temail.setText("");
                    tnome.setText("");
                    ttelefone.setText("");
                }
            }
        });
        add(limpa);
    
    }

}