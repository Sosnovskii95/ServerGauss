package sample;

import Gauss.GaussSolution;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class Controller {


    @FXML
    private Label ipLabel;

    @FXML
    private Button startServer;

    @FXML
    private TextField portServer;

    private boolean isActivThread;

    @FXML
    void initialize() {
        isActivThread = false;
        startServer.setOnAction(event -> {
            try {
                ipLabel.setText(Inet4Address.getLocalHost().getHostAddress() + ":" + portServer.getText());
            }catch (IOException e) {
                e.printStackTrace();
            }
            IntegralServer integralServer = new IntegralServer(Integer.parseInt(portServer.getText()));
            Thread thread = new Thread(integralServer, "server");
            if (!isActivThread)
            {
                thread.start();
                isActivThread = true;
            }
            else {
                try {
                    Thread.sleep(100);

                    thread.interrupt();

                    Thread.sleep(100);
                }catch (InterruptedException e)
                {

                }
            }
        });
    }
}

class IntegralServer implements Runnable {
    int port;

    public IntegralServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();

                byte[] dataByte = new byte[1024];
                int readBytes = in.read(dataByte);
                String dataRecv = "";

                if (readBytes > 0) {
                    dataRecv = new String(dataByte, 0, readBytes);
                }

                if (!dataRecv.equals("")) {
                    String[] data = dataRecv.split(";");

                    GaussSolution solution = new GaussSolution(Double.parseDouble(data[0]), Double.parseDouble(data[1]),
                            Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                            Double.parseDouble(data[5]), data[6]);

                    double result = solution.Solution();

                    OutputStream out = socket.getOutputStream();
                    String str = String.valueOf(result);
                    out.write(str.getBytes());
                    out.flush();
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
