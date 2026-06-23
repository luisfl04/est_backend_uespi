package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormCadastroDisciplina extends JFrame {

    private JTextField inputNome, inputCursoRelacionado, inputBlocoRelacionado;
    private int widthSize = 400, heightSize = 300;

    public ViewFormCadastroDisciplina() {
        super("Cadastro de Disciplinas");
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
        JLabel labelNome = this.getLabel("Nome da Disciplina:");
        inputNome = this.getInput(20);
        add(labelNome);
        add(inputNome);

        JLabel labelCurso = this.getLabel("Curso Relacionado:");
        inputCursoRelacionado = this.getInput(20);
        add(labelCurso);
        add(inputCursoRelacionado);

        JLabel labelBloco = this.getLabel("Bloco Relacionado (Número):");
        inputBlocoRelacionado = this.getInput(10);
        add(labelBloco);
        add(inputBlocoRelacionado);

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
                    String confirmationMessage = "Disciplina: " + inputNome.getText() + 
                                                 "\nCurso: " + inputCursoRelacionado.getText() + 
                                                 "\nBloco: " + inputBlocoRelacionado.getText();
                    JOptionPane.showMessageDialog(null, confirmationMessage, "Mock - Disciplina", JOptionPane.INFORMATION_MESSAGE);
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
                    inputCursoRelacionado.setText("");
                    inputBlocoRelacionado.setText("");
                }
            }
        });

        return cleanTextButton;
    }
}