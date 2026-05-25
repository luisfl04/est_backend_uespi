import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeView extends JFrame{
    private JMenuBar menuprincipal;

    public HomeView(){
        super("Cadastro Academico");
        setLayout(new FlowLayout());
        this.addMenu();
    }

    public void addMenu() {

        menuprincipal = new JMenuBar();
        JMenu menuCadastrar = new JMenu("Cadastrar");


        JMenuItem menuCadastrarAluno = new JMenuItem("Aluno");
        menuCadastrarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ViewCrudAluno viewCrudAluno = new ViewCrudAluno();
                viewCrudAluno.setSize(600,500);
                viewCrudAluno.setVisible(true);
            }
        });

        JMenuItem menuCadastrarProfessor = new JMenuItem("Professor");
        JMenuItem menuCadastrarDisciplina = new JMenuItem("Disciplina");
        JMenuItem menuCadastrarCurso = new JMenuItem("Curso");

        menuCadastrar.add(menuCadastrarAluno);
        menuCadastrar.add(menuCadastrarProfessor);
        menuCadastrar.add(menuCadastrarDisciplina);
        menuCadastrar.add(menuCadastrarCurso);
        menuprincipal.add(menuCadastrar);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem menuAjudaManual = new JMenuItem("Manual");
        JMenuItem menuAjudaLicenca = new JMenuItem("Licença");
        menuAjuda.add(menuAjudaManual);
        menuAjuda.add(menuAjudaLicenca);
        menuprincipal.add(menuAjuda);

        setJMenuBar(menuprincipal);
    }

}