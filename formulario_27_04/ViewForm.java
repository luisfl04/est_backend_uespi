package formulario_27_04;
import javax.swing.*;
import java.awt.*;


public class ViewForm extends JFrame {

    public static void main(String[] args) {
        ViewForm view = new ViewForm();
        view.setVisible(true);
    }

    public ViewForm(){
        super("Formulário de cadastro");
        setLayout(new FlowLayout());
        this.addComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
    }

    private JLabel getLabel(String titleLabel) throws Exception {
        if(titleLabel.isEmpty()){
            throw new Exception("O título do label é obrigatório!");
        }

        return new JLabel(titleLabel);
    }

    private JTextField getInput(int numberColumns) throws Exception{
        if(numberColumns < 1){
            throw new Exception("O número de colunas do input deve ser maior que 0");
        }

        return new JTextField(numberColumns);
    }

    private JButton getButton(String titleButton) throws Exception{
        if(titleButton.isEmpty()){
            throw new Exception("O título do button é obrigatório!");
        }

        return new JButton(titleButton);
    }

    private JTextArea getTextArea(int rows, int columns) throws Exception{
        if(rows < 1 || columns < 1){
            throw new Exception("Os valores de colunas e linhas devem ser positivos!");
        }

        return new JTextArea(rows, columns);
    }
    public void addComponents() {
        try {
            JLabel labelNome = this.getLabel("Nome:");
            add(labelNome);
            JTextField inputNome = this.getInput(20);
            add(inputNome);
            JLabel labelFone = this.getLabel("Telefone:");
            add(labelFone);
            JTextField inputFone = this.getInput(20);
            add(inputFone);
            JButton subimitButton = this.getButton("Cadastrar");
            add(subimitButton);
            JTextArea boxText = this.getTextArea(10, 30);
            add(boxText);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }        
    } 

}