package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MyThread extends Thread {
      
    private Socket s;
    private Random random;
    private String username;
    private String codice;
    private ArrayList<MyThread> contatti;
    private String password;

    public MyThread(Socket s, ArrayList<MyThread> lista){
        this.s = s;
        this.contatti = lista;
        random = new Random();
    }

    public void run() {
        String risposta;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String scelta;
            boolean fine = false;
            do {
                username = in.readLine();
                //controllo utente
                for (MyThread utente : contatti) {
                    if(utente.username.equals(this.username)){
                        out.writeBytes("\n!");
                        password = in.readLine();
                        for (MyThread user : contatti) {
                            if(user.password.equals(this.password)){
                                out.writeBytes("\n!");
                            }else{
                                out.writeBytes("\nv");
                            }
                        }
                    }else{
                        out.writeBytes("\nv");
                    }
                }
                
            } while (!fine);


            s.close();
        } catch (IOException e) {
            risposta = "!";
        }
    }
    
}