package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoDisciplina extends JFrame {

    private JTextField inputNome, inputCursoRelacionado, inputBlocoRelacionado;
    private int idDisciplina;
    private int widthSize = 400, heightSize = 300;

    public ViewFormAtualizacaoDisciplina(int id) {
        super("Atualização de Disciplina - ID: " + id);
        this.idDisciplina = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        this.carregarDados(id);
    }

    private void carregarDados(int id) {
        // Mock simulando o GET na API
        inputNome.setText("Programação Orientada a Objetos");
        inputCursoRelacionado.setText("Sistemas para Internet");
        inputBlocoRelacionado.setText("2");
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Nome da Disciplina:"));
        inputNome = this.getInput(20);
        add(inputNome);

        add(this.getLabel("Curso Relacionado:"));
        inputCursoRelacionado = this.getInput(20);
        add(inputCursoRelacionado);

        add(this.getLabel("Bloco Relacionado (Número):"));
        inputBlocoRelacionado = this.getInput(10);
        add(inputBlocoRelacionado);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // FUTURA CHAMADA HTTP (PUT)
                String confirmationMessage = "Dados atualizados:\nDisciplina: " + inputNome.getText() + 
                                             "\nCurso: " + inputCursoRelacionado.getText() + 
                                             "\nBloco: " + inputBlocoRelacionado.getText();
                JOptionPane.showMessageDialog(null, confirmationMessage, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        return updateButton;
    }
}