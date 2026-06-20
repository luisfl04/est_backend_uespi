package model_structure_client_to_request_api;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        HomeView homeAluno = new HomeView();
        homeAluno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeAluno.setSize(800,600);
        homeAluno.setVisible(true);
        homeAluno.setLocationRelativeTo(null);
    }

}   