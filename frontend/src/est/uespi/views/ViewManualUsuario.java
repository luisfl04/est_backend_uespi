package est.uespi.views;

import javax.swing.*;
import java.awt.*;

public class ViewManualUsuario extends JFrame {

    private int widthSize = 900, heightSize = 600;

    public ViewManualUsuario() {
        super("Manual do Usuário");
        
        setLayout(new BorderLayout(10, 10));
        setSize(this.widthSize, this.heightSize);
        setLocationRelativeTo(null);
        
        this.addComponents();
    }

    public void addComponents() {
        JLabel titulo = new JLabel("Manual do Sistema Acadêmico", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); 
        add(titulo, BorderLayout.NORTH);

        JEditorPane painelTexto = new JEditorPane();
        painelTexto.setContentType("text/html");
        painelTexto.setEditable(false); 
        painelTexto.setOpaque(false); 

        String conteudoHtml = "<html><body style='font-family: Segoe UI, sans-serif; font-size: 12px; padding: 10px 30px; color: #333;'>"
                + "<h2 style='color: #2c3e50;'>Sobre a Aplicação</h2>"
                + "<p>Este sistema foi desenvolvido para facilitar o controle e o fluxo de dados do ambiente acadêmico, conectando alunos, professores, turmas e disciplinas de forma ágil e segura.</p>"
                + "<hr>"
                
                + "<h2 style='color: #2c3e50;'>Entidades Gerenciadas</h2>"
                + "<ul>"
                + "<li><b>Alunos:</b> Registro do corpo discente, contendo dados de contato (email, telefone) e a qual turma o aluno pertence.</li>"
                + "<li><b>Professores:</b> Registro do corpo docente, armazenando informações de contato e o nível de formação acadêmica.</li>"
                + "<li><b>Disciplinas:</b> Cadastro das matérias ofertadas, relacionando-as ao curso e bloco correspondente.</li>"
                + "<li><b>Turmas:</b> Estruturas que agrupam alunos de acordo com seu curso e bloco/período atual.</li>"
                + "</ul>"
                + "<hr>"
                
                + "<h2 style='color: #2c3e50;'>Operações Disponíveis (CRUD)</h2>"
                + "<p>Em cada uma das telas de gerenciamento (acessíveis pelo menu inicial ou pelo topo das listagens), você pode realizar as seguintes ações:</p>"
                + "<ul>"
                + "<li><span style='color: #27ae60;'><b>Listar (Consultar):</b></span> Todos os registros são carregados automaticamente ao abrir a tela de gerenciamento de uma entidade.</li>"
                + "<li><span style='color: #2980b9;'><b>Cadastrar (Criar):</b></span> Utilize o botão 'Cadastrar Novo...' no rodapé da tabela para inserir novos dados no sistema.</li>"
                + "<li><span style='color: #f39c12;'><b>Atualizar (Editar):</b></span> Clique no botão 'Editar' na linha correspondente da tabela para corrigir ou mudar informações de um registro existente.</li>"
                + "<li><span style='color: #c0392b;'><b>Excluir (Remover):</b></span> Clique no botão 'Excluir' para apagar um registro. O sistema sempre pedirá uma confirmação de segurança antes de concluir a exclusão.</li>"
                + "</ul>"
                
                + "<br><p style='text-align: center; color: #7f8c8d;'><i>Desenvolvido para fins de Gestão Educacional.</i></p>"
                + "</body></html>";

        painelTexto.setText(conteudoHtml);
        
        JScrollPane barraRolagem = new JScrollPane(painelTexto);
        barraRolagem.setBorder(BorderFactory.createEmptyBorder());
        add(barraRolagem, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JButton btnClose = this.getCloseButton();
        painelBotoes.add(btnClose);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }

    public JButton getCloseButton() {
        JButton closeButton = new JButton("Entendido, Fechar!");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }
}