package est.uespi.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewFormAtualizacaoTurma extends JFrame {

    private JTextField inputCurso, inputBlocoAtual;
    private int idTurma;
    private int widthSize = 400, heightSize = 250;

    public ViewFormAtualizacaoTurma(int id) {
        super("Atualização de Turma - ID: " + id);
        this.idTurma = id;
        setLayout(new FlowLayout());
        this.addComponents();
        this.setSize(widthSize, heightSize);
        setLocationRelativeTo(null);
        
        // Carrega os dados da API logo após montar a tela
        this.carregarDados(id);
    }

    private void carregarDados(int id) {
        /* INTEGRAÇÃO FUTURA:
         * ClientHttp client = new ClientHttp("http://localhost/api.php?entidade=turma&id=" + id, "GET");
         * String json = client.request();
         * // Converter JSON para objeto Turma e setar nos inputs abaixo:
         */
         
        // Mock simulando o retorno da API
        inputCurso.setText("Sistemas para Internet");
        inputBlocoAtual.setText("1");
    }

    public JLabel getLabel(String label) { return new JLabel(label); }
    public JTextField getInput(int numberColumns) { return new JTextField(numberColumns); }

    public void addComponents() {
        add(this.getLabel("Curso:"));
        inputCurso = this.getInput(20);
        add(inputCurso);

        add(this.getLabel("Bloco Atual:"));
        inputBlocoAtual = this.getInput(10);
        add(inputBlocoAtual);

        add(this.getUpdateButton());
    }

    public JButton getUpdateButton() {
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // FUTURA CHAMADA HTTP (PUT):
                // ClientHttp client = new ClientHttp("http://localhost/api.php?entidade=turma&id=" + idTurma, "PUT", payloadJson);
                
                String confirmationMessage = "Dados atualizados:\nCurso: " + inputCurso.getText() + 
                                             "\nBloco Atual: " + inputBlocoAtual.getText();
                JOptionPane.showMessageDialog(null, confirmationMessage, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Fecha a janela após atualizar
            }
        });
        return updateButton;
    }
}