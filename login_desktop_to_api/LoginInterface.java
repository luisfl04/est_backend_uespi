import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class LoginInterface extends JFrame{
    private JTextField inputLogin, inputSenha, inputRetorno;
    private JButton buttonLogar, buttonLimpar;
    private static final String ALGORITHM = "AES";

    public LoginInterface() throws Exception {
        super("Login Cript");
        setLayout(new FlowLayout());
        this.addComponentes();
    }

    public JTextField getInputLogin() {
        return inputLogin;
    }

    public JTextField getInputSenha() {
        return inputSenha;
    }

    public JTextField getInputRetorno() {
        return inputRetorno;
    }

    public void setInputLogin(JTextField inputLogin) {
        this.inputLogin = inputLogin;
    }

    public void setInputSenha(JTextField inputSenha) {
        this.inputSenha = inputSenha;
    }

    public void setInputRetorno(JTextField inputRetorno) {
        this.inputRetorno = inputRetorno;
    }

    public void addComponentes() throws Exception {

        JLabel labelLogin = new JLabel("Login:");
        add(labelLogin);
        this.setInputLogin(new JTextField(20));
        add(this.getInputLogin());

        JLabel labelSenha = new JLabel("Senha:");
        add(labelSenha);
        this.setInputSenha(new JTextField(20));
        add(this.getInputSenha());

        JLabel labelRetorno = new JLabel("Resposta:");
        add(labelRetorno);
        this.setInputRetorno(new JTextField(20));
        add(this.getInputRetorno());

        this.buttonLogar = new JButton("Logar");
        logarOnClick();
        
        this.buttonLimpar = new JButton("Limpar");
        limparOnClick();
    }   

    public void logarOnClick() {
            buttonLogar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    if(evento.getSource() == buttonLogar) {
                        try {
                            String key = "1234567890123456";
                            String encryptedString = encrypt(inputSenha.getText(), key);
                            ClientHttp conexao = new ClientHttp(inputLogin.getText(), encryptedString, "https://www.datse.com.br/dev/aulabackendv2.php");
                            String resposta = conexao.request();
                            inputRetorno.setText(resposta);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }   
                    }
                }
            }
        );

        add(buttonLogar);
    }

    public void limparOnClick() {
            buttonLimpar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    if(evento.getSource() == buttonLimpar){
                        inputLogin.setText("");
                        inputSenha.setText("");
                        inputRetorno.setText("");
                    }
                }
            }
        );
        add(buttonLimpar);
    }

    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

}