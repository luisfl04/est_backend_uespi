package interface_multitelas_04_03;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;


public class Main extends JFrame{
    
    private JMenuBar menuPrincipal;

    public Main() {
        super("Cadastro acadêmico");
        setLayout(new FlowLayout());
        this.addComponents();
    }  

    public JMenu getMenuCadastrar() {
        JMenu menuCadastrar = new JMenu();
        JMenuItem menuCadastrarAluno = this.getMenuCadastrarAluno();
        JMenuItem menuCadastrarProfessor = new JMenuItem("Professor");
        JMenuItem menuCadastrarDisciplina = new JMenuItem("Disciplina");
        JMenuItem menuCadastrarCurso = new JMenuItem("Curso");
        menuCadastrar.add(menuCadastrarAluno);
        menuCadastrar.add(menuCadastrarProfessor);
        menuCadastrar.add(menuCadastrarDisciplina);
        menuCadastrar.add(menuCadastrarCurso);
        
        return menuCadastrar;
    }

    public JMenuItem getMenuCadastrarAluno() {
        JMenuItem menuCadastrarAluno = JMenuItem("Aluno");
        menuCadastrarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudAluno viewCrudAluno = new ViewCrudAluno(1000, 1000);
                viewCrudAluno.setVizible(true);
            }
        });
    }


    public JMenu getMenuAjuda() {
        JMenu menuAjuda = new JMenu("Obter ajuda");
        JMenuItem menuAjudaManual = new JMenuItem("Manual");
        JMenuItem menuAjudaLicenca = new JMenuItem("Licença");
        menuAjuda.add(menuAjudaManual);
        menuAjuda.add(menuAjudaLicenca);
    
        return menuAjuda;
    }

    public void addComponents() throws Exception{
        try {
            JMenuBar menuPrincipal = new JMenuBar();
            JMenu menuCadastrar = this.getMenuCadastrar(); 
            JMenu menuAjuda = this.getMenuAjuda(); 
            menuPrincipal.add(menuCadastrar);
            menuPrincipal.add(menuAjuda);
        }
        catch(Exception e) {
            
        }
    }



}
