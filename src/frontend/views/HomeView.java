package src.frontend.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class HomeView extends JFrame{

    private int widthSize = 1000, heightSize = 1000;

    public HomeView() {
        super("Cadastro acadêmico");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
    }

    public JMenu getMenuCadastrar() {
        JMenu menuCadastrar = new JMenu("Cadastrar");
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
        JMenuItem menuCadastrarAluno = new JMenuItem("Aluno");
        menuCadastrarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudAluno viewCrudAluno = new ViewCrudAluno();
                viewCrudAluno.setVisible(true);
            }
        });

        return menuCadastrarAluno;
    }


    public JMenu getMenuAjuda() {
        JMenu menuAjuda = new JMenu("Obter ajuda");
        JMenuItem menuAjudaManual = new JMenuItem("Manual");
        JMenuItem menuAjudaLicenca = new JMenuItem("Licença");
        menuAjuda.add(menuAjudaManual);
        menuAjuda.add(menuAjudaLicenca);
    
        return menuAjuda;
    }

    public void addComponents(){
        try {
            JMenuBar menuPrincipal = new JMenuBar();
            JMenu menuCadastrar = this.getMenuCadastrar(); 
            JMenu menuAjuda = this.getMenuAjuda(); 
            menuPrincipal.add(menuCadastrar);
            menuPrincipal.add(menuAjuda);
            add(menuPrincipal);
        }
        catch(Exception e) {
            System.out.println("Erro ao adicionar componentes: " + e.getMessage());
        }
    }



}
