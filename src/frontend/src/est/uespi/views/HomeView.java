package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class HomeView extends JFrame{

    private int widthSize = 1000, heightSize = 1000;

    public HomeView() {
        super("Gerenciamento Acadêmico");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
    }

    public JMenu getMenuGerenciar() {
        JMenu menuGerenciar = new JMenu("Gerenciar");
        JMenuItem menuGerenciarAluno = this.getMenuGerenciarAluno();
        JMenuItem menuGerenciarProfessor = new JMenuItem("Professor");
        JMenuItem menuGerenciarDisciplina = new JMenuItem("Disciplina");
        JMenuItem menuGerenciarTurma = new JMenuItem("Turma");
        menuGerenciar.add(menuGerenciarAluno);
        menuGerenciar.add(menuGerenciarProfessor);
        menuGerenciar.add(menuGerenciarDisciplina);
        menuGerenciar.add(menuGerenciarTurma);
        
        return menuGerenciar;
    }

    public JMenuItem getMenuGerenciarAluno() {
        JMenuItem menuGerenciarAluno = new JMenuItem("Aluno");
        menuGerenciarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudAluno viewCrudAluno = new ViewCrudAluno();
                viewCrudAluno.setVisible(true);
            }
        });

        return menuGerenciarAluno;
    }

    public JMenuItem getMenuGerenciarTurma() {
        JMenuItem menuGerenciarTurma = new JMenuItem("Turma");
        menuGerenciarTurma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudTurma viewCrudTurma = new ViewCrudTurma();
                viewCrudTurma.setVisible(true);
            }
        });

        return menuGerenciarTurma;
    }
    
    public JMenuItem getMenuGerenciarDisciplina() {
        JMenuItem menuGerenciarDisciplina = new JMenuItem("Disciplina");
        menuGerenciarDisciplina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudDisciplina viewCrudDisciplina = new ViewCrudDisciplina();
                viewCrudDisciplina.setVisible(true);
            }
        });

        return menuGerenciarDisciplina;
    }
    
    public JMenuItem getMenuGerenciarProfessor() {
        JMenuItem menuGerenciarProfessor = new JMenuItem("Professor");
        menuGerenciarProfessor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ViewCrudProfessor viewCrudProfessor = new ViewCrudProfessor();
                viewCrudProfessor.setVisible(true);
            }
        });

        return menuGerenciarProfessor;
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
            JMenu menuGerenciar = this.getMenuGerenciar(); 
            JMenu menuAjuda = this.getMenuAjuda(); 
            menuPrincipal.add(menuGerenciar);
            menuPrincipal.add(menuAjuda);
            add(menuPrincipal);
        }
        catch(Exception e) {
            System.out.println("Erro ao adicionar componentes: " + e.getMessage());
        }
    }



}
