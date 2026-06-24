package est.uespi.views;

import javax.swing.*;
import java.awt.*;


public class HomeView extends JFrame {

    private int widthSize = 1200, heightSize = 800;

    public HomeView() {
        super("Sistema de Gerenciamento Acadêmico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 
        this.addComponents();
    }

    public void addComponents() {
        setJMenuBar(this.getMenuPrincipal());
        JPanel painelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 30, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Gerenciamento Acadêmico", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        gbc.gridy = 0;
        painelCentral.add(titulo, gbc);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 20, 20));
        JButton btnAluno = new JButton("Gerenciar Alunos");
        JButton btnProfessor = new JButton("Gerenciar Professores");
        JButton btnDisciplina = new JButton("Gerenciar Disciplinas");
        JButton btnTurma = new JButton("Gerenciar Turmas");

        Font fonteBotao = new Font("Segoe UI", Font.PLAIN, 16);
        btnAluno.setFont(fonteBotao);
        btnProfessor.setFont(fonteBotao);
        btnDisciplina.setFont(fonteBotao);
        btnTurma.setFont(fonteBotao);
        btnAluno.setPreferredSize(new Dimension(220, 60));

        btnAluno.addActionListener(e -> new ViewCrudAluno().setVisible(true));
        btnProfessor.addActionListener(e -> new ViewCrudProfessor().setVisible(true));
        btnDisciplina.addActionListener(e -> new ViewCrudDisciplina().setVisible(true));
        btnTurma.addActionListener(e -> new ViewCrudTurma().setVisible(true));

        painelBotoes.add(btnAluno);
        painelBotoes.add(btnProfessor);
        painelBotoes.add(btnDisciplina);
        painelBotoes.add(btnTurma);

        gbc.gridy = 1;
        painelCentral.add(painelBotoes, gbc);

        add(painelCentral, BorderLayout.CENTER);
    }

    public JMenuBar getMenuPrincipal() {
        JMenuBar menuPrincipal = new JMenuBar();
        JMenu menuGerenciar = new JMenu("Gerenciar");
        JMenuItem menuAluno = new JMenuItem("Alunos");
        JMenuItem menuProfessor = new JMenuItem("Professores");
        JMenuItem menuDisciplina = new JMenuItem("Disciplinas");
        JMenuItem menuTurma = new JMenuItem("Turmas");

        menuAluno.addActionListener(e -> new ViewCrudAluno().setVisible(true));
        menuProfessor.addActionListener(e -> new ViewCrudProfessor().setVisible(true));
        menuDisciplina.addActionListener(e -> new ViewCrudDisciplina().setVisible(true));
        menuTurma.addActionListener(e -> new ViewCrudTurma().setVisible(true));

        menuGerenciar.add(menuAluno);
        menuGerenciar.add(menuProfessor);
        menuGerenciar.add(menuDisciplina);
        menuGerenciar.add(menuTurma);

        JMenu menuAjuda = new JMenu("Obter ajuda");
        JMenuItem menuAjudaManual = new JMenuItem("Manual");
        JMenuItem menuAjudaLicenca = new JMenuItem("Licença");
        menuAjuda.add(menuAjudaManual);
        menuAjuda.add(menuAjudaLicenca);

        menuPrincipal.add(menuGerenciar);
        menuPrincipal.add(menuAjuda);

        return menuPrincipal;
    }
}
