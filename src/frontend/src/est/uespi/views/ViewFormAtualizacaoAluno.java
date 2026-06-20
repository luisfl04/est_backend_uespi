package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoAluno extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputTurmaId;
    private int idAluno;
    private int widthSize = 400, heightSize = 350;

    public ViewFormAtualizacaoAluno(int id) {
        super("Atualização de Aluno - ID: " + id);
        this.idAluno = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(id);
    }

    private void carregarDados(int id) {
        // Mock simulando o GET na API
        inputNome.setText("Ana Monteiro");
        inputEmail.setText("ana.monteiro@gmail.com");
        inputTelefone.setText("86 99923-7898");
        inputTurmaId.setText("1");
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Nome:"));
        inputNome = this.getInput(20);
        add(inputNome);

        add(this.getLabel("Email:"));
        inputEmail = this.getInput(20);
        add(inputEmail);

        add(this.getLabel("Telefone:"));
        inputTelefone = this.getInput(20);
        add(inputTelefone);

        add(this.getLabel("ID da Turma:"));
        inputTurmaId = this.getInput(10);
        add(inputTurmaId);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // FUTURA CHAMADA HTTP (PUT)
                String confirmationMessage = "Dados atualizados:\nNome: " + inputNome.getText() + 
                                             "\nEmail: " + inputEmail.getText() + 
                                             "\nTelefone: " + inputTelefone.getText() +
                                             "\nID Turma: " + inputTurmaId.getText();
                JOptionPane.showMessageDialog(null, confirmationMessage, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        return updateButton;
    }
}