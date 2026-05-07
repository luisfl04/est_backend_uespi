package src.frontend.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewFormCadastroAluno extends JFrame {

    private JTextField inputEmail, inputNome, inputTelefone;
    private int widthSize = 1000, heightSize = 1000;

    public ViewFormCadastroAluno() {
        super("Cadastro de Alunos");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
    }

    public JLabel getLabel(String label) {
        return new JLabel(label);
    }

    public JTextField getInput(int numberColumns) {
        return  new JTextField(numberColumns);
    }

    public void addComponents() {
        JLabel labelEmail = this.getLabel("Email:");
        inputEmail = this.getInput(20);
        add(labelEmail);
        add(inputEmail);

        JLabel labelNome = this.getLabel("Nome:");
        inputNome = this.getInput(20);
        add(labelNome);
        add(inputNome);

        JLabel labelTelefone = this.getLabel("Telefone:");
        inputTelefone = this.getInput(20);
        add(labelTelefone);
        add(inputTelefone);

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
                    String confirmationMessage = "Email: " + inputEmail.getText() + " --- Nome: " + inputNome.getText() + " --- Telefone: " + inputTelefone.getText();
                    JOptionPane.showMessageDialog(null, confirmationMessage);
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
                    inputEmail.setText("");
                    inputNome.setText("");
                    inputTelefone.setText("");
                }
            }
        });

        return cleanTextButton;
    }


}
