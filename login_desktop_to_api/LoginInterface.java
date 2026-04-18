import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.crypto.Cipher; import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class LoginInterface extends JFrame{
    private JTextField tlogin, tsenha, tretorno;
    private JButton logar, limpar;
    private JLabel llogin, lsenha, lretorno;
    private static final String ALGORITHM = "AES";

    public LoginInterface() throws Exception {
        super("Login Cript");
        setLayout(new FlowLayout());
        this.addComponentes();
    }

    public void addComponentes() throws Exception {
        llogin = new JLabel("Login:");
        add(llogin);
        tlogin = new JTextField(20);
        add(tlogin);

        lsenha = new JLabel("Senha:");
        add(lsenha);
        tsenha = new JTextField(20);
        add(tsenha);

        lretorno = new JLabel("Resposta:");
        add(lretorno);
        tretorno = new JTextField(20);
        add(tretorno);

        logar = new JButton("Logar");
        logarOnClick();
        
        limpar = new JButton("Limpar");
        limparOnClick();
    }   

    public void logarOnClick() {
            logar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    if(evento.getSource() == logar) {
                        try {
                            String key = "1234567890123456";
                            String encryptedString = encrypt(tsenha.getText(), key);
                            ClientHttp Conexao = new ClientHttp(tlogin.getText(), encryptedString, "http://www.datse.com.br/dev/aulabackendv2.php");
                            String ret = Conexao.conecta();
                            tretorno.setText(ret);
                        } catch (Exception e) {
                            System.out.println(e);
                        }   
                    }
                }
            }
        );

        add(logar);
    }


    public void limparOnClick() {
            limpar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    if(evento.getSource() == limpar){
                        tlogin.setText("");
                        tsenha.setText("");
                        tretorno.setText("");
                    }
                }
            }
        );
        add(limpar);
    }

    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

}