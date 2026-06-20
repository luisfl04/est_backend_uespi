package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoProfessor extends JFrame {

    private JTextField inputNome, inputEmail, inputTelefone, inputFormacao;
    private int idProfessor;
    private int widthSize = 400, heightSize = 350;

    public ViewFormAtualizacaoProfessor(int id) {
        super("Atualização de Professor - ID: " + id);
        this.idProfessor = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(id);
    }

    private void carregarDados(int id) {
        // Mock simulando o GET na API
        inputNome.setText("Carlos Mendes");
        inputEmail.setText("carlos.mendes@uespi.br");
        inputTelefone.setText("86 91234-5678");
        inputFormacao.setText("Doutorado em Ciência da Computação");
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

        add(this.getLabel("Formação:"));
        inputFormacao = this.getInput(20);
        add(inputFormacao);

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
                                             "\nFormação: " + inputFormacao.getText();
                JOptionPane.showMessageDialog(null, confirmationMessage, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        return updateButton;
    }
}