package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormCadastroAluno extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputTurmaId;
    private int widthSize = 400, heightSize = 350;

    public ViewFormCadastroAluno() {
        super("Cadastro de Alunos");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
    }

    public JLabel getLabel(String label) {
        return new JLabel(label);
    }

    public JTextField getInput(int numberColumns) {
        return new JTextField(numberColumns);
    }

    public void addComponents() {
        JLabel labelNome = this.getLabel("Nome:");
        inputNome = this.getInput(20);
        add(labelNome);
        add(inputNome);

        JLabel labelEmail = this.getLabel("Email:");
        inputEmail = this.getInput(20);
        add(labelEmail);
        add(inputEmail);

        JLabel labelTelefone = this.getLabel("Telefone:");
        inputTelefone = this.getInput(20);
        add(labelTelefone);
        add(inputTelefone);

        JLabel labelTurmaId = this.getLabel("ID da Turma:");
        inputTurmaId = this.getInput(10);
        add(labelTurmaId);
        add(inputTurmaId);

        JButton submitButton = this.getSubmitButton();
        JButton cleanButton = this.getCleanTextButton();
        add(submitButton);
        add(cleanButton);
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Cadastrar");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == submitButton) {
                    String confirmationMessage = "Nome: " + inputNome.getText() + 
                                                 "\nEmail: " + inputEmail.getText() + 
                                                 "\nTelefone: " + inputTelefone.getText() +
                                                 "\nID Turma: " + inputTurmaId.getText();
                    JOptionPane.showMessageDialog(null, confirmationMessage, "Mock - Aluno", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        return submitButton;
    }

    public JButton getCleanTextButton() {
        JButton cleanTextButton = new JButton("Limpar campos");
        cleanTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == cleanTextButton) {
                    inputNome.setText("");
                    inputEmail.setText("");
                    inputTelefone.setText("");
                    inputTurmaId.setText("");
                }
            }
        });

        return cleanTextButton;
    }
}