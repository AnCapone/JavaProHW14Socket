import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    final int port = 8081;

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started! Listening on port " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            sendMessage(socket, TextStrings.HELLO.getTextString());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMessage = "";
            while(!(clientMessage = input.readLine()).equals("exit")) {
                if (checkRussianLetters(clientMessage)) {
                    sendMessage(socket, TextStrings.RUSSIAN.getTextString());
                } else {
                    sendMessage(socket, TextStrings.RIGHT_ANSWER.getTextString());
                }
            }

        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void sendMessage(Socket socket, String message) throws IOException {
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        output.println(message);
    }

    public boolean checkRussianLetters(String message) {
        Pattern pattern = Pattern.compile(TextStrings.RUSSIAN_LETTERS.getTextString());
        Matcher matcher = pattern.matcher(message.toLowerCase());
        return matcher.find();
    }
}
