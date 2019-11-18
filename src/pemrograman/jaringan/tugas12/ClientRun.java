/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pemrograman.jaringan.tugas12;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hp
 */
public class ClientRun implements Runnable {

    private Client client;
    private portView portView;
    private msgView view;
    private String mess;

    public ClientRun() {
        this.client = new Client();
        this.portView = new portView();
        this.view = new msgView();
        this.portView.setTitle("Port");
        this.portView.setVisible(true);

        this.portView.getOkBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(portView.getPortField().getText()).equals("6666")) {
                    client.startConnection("127.0.0.1", Integer.valueOf(portView.getPortField().getText()));
                    portView.setVisible(false);
                    view.setTitle("Client Chat");
                    view.setVisible(true);
                } else {
                    portView.getPortField().setText(" ");
                }
            }
        });

        this.view.getKirimBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mess += time() + "=>" + String.valueOf(view.getjTextField1().getText() + "\n");
                String response = client.sendMessage(time() + "<=" + String.valueOf(view.getjTextField1().getText()));
                mess += response + "\n";
            }
        });

    }

    public String time() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        String time = simpleDateFormat.format(date);
        return time;
    }

    @Override
    public void run() {
        do {
            if (this.view.getjTextArea1().getText().equals(mess) == false) {
                this.view.getjTextArea1().setText(mess);
            }
        } while (true);
    }

    public static void main(String[] args) {
        new Thread(new ClientRun()).start();
    }
}
