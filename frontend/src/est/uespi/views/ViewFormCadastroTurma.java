package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormCadastroTurma extends JFrame {

    private JTextField inputCurso, inputBlocoAtual;
    private int widthSize = 400, heightSize = 250;

    public ViewFormCadastroTurma() {
        super("Cadastro de Turmas");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null); // Centraliza a janela
    }

    public JLabel getLabel(String label) {
        return new JLabel(label);
    }

    public JTextField getInput(int numberColumns) {
        return new JTextField(numberColumns);
    }

    public void addComponents() {
        JLabel labelCurso = this.getLabel("Curso:");
        inputCurso = this.getInput(20);
        add(labelCurso);
        add(inputCurso);

        JLabel labelBlocoAtual = this.getLabel("Bloco Atual (Número):");
        inputBlocoAtual = this.getInput(10);
        add(labelBlocoAtual);
        add(inputBlocoAtual);

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
                    // Futura chamada HTTP via ClientHttp (POST) entrará aqui
                    String confirmationMessage = "Curso: " + inputCurso.getText() + 
                                                 " \nBloco Atual: " + inputBlocoAtual.getText();
                    JOptionPane.showMessageDialog(null, confirmationMessage, "Mock - Turma", JOptionPane.INFORMATION_MESSAGE);
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
                    inputCurso.setText("");
                    inputBlocoAtual.setText("");
                }
            }
        });

        return cleanTextButton;
    }
}