package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    static void Log(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) throws IOException {
        System.in.read();

        Controller controller = new Controller();

        ServerSocket listener;

        try {
            listener = new ServerSocket(37152, 1, InetAddress.getByName("127.0.0.1"));
            Log("server is started");
        } catch (Exception e) {
            Log("failed to start server: " + e.getMessage());
            return;
        }

        while (true) {


            Log("server is listening");

            Socket talking = null;

            boolean isRun = false;

            DataInputStream in = null;
            DataOutputStream out = null;

            try {
                talking = listener.accept();
                Log("client is connected");

                in = new DataInputStream(talking.getInputStream());
                out = new DataOutputStream(talking.getOutputStream());
                isRun = true;
            } catch (Exception e) {
                Log("client error: " + e.getMessage());
                continue;
            }

            while (isRun) {
                try {
                    String request = in.readUTF();
                    Log("from client: " + request);

                    String response = "";

                    switch (request) {
                        case "/hello":
                            response = "hi";
                            break;

                        case "/start":
                            response = controller.StartRound();
                            break;

                        case "/reset":
                            controller.ResetGame();
                            response = controller.StartRound();
                            break;

                        case "/points":
                            response = Integer.toString(controller.GetPoints());
                            break;

                        default:
                            if (request.length() > 7) {
                                response = controller.CheckAnswer(Integer.parseInt(request.substring(7)));
                            } else {
                                response = "incorrect input";
                            }
                            break;
                    }

                    out.writeUTF(response);
                    Log("to client: " + response);
                } catch (Exception e) {
                    Log("client  error: " + e.getMessage());
                    isRun = false;
                }
            }

            talking.close();

        }


    }

}
