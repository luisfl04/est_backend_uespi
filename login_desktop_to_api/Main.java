import java.io.*;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        LoginInterface interface_login = new LoginInterface();
        interface_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interface_login.setSize(800,1000);
        interface_login.setVisible(true);        
    } 
}