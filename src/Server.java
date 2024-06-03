import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
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
                    if (!input.readLine().equals(TextStrings.ANSWER.getTextString())) {
                        sendMessage(socket, TextStrings.WRONG_ANSWER.getTextString());
                        throw new IOException("Russian spy client disconected!");

                    } else {
                        sendMessage(socket, TextStrings.RIGHT_ANSWER.getTextString());
                        sendMessage(socket, TextStrings.NOW_IN_UA.getTextString() + getCurrentLocalDateAndTime());
                        sendMessage(socket, TextStrings.BYE.getTextString());
                    }
                } else {
                    sendMessage(socket, TextStrings.NOW_IN_UA.getTextString() + getCurrentLocalDateAndTime());
                }
            }
            sendMessage(socket, TextStrings.BYE.getTextString());
            socket.close();

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

    public String getCurrentLocalDateAndTime() {
        Locale locale = new Locale("uk", "UA");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale);

        return currentDateTime.format(formatter);
    }
}
