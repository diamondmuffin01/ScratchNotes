import java.util.Scanner;

public class Socket_Test
{
    public static void main(String[] args)
    {
        //Get port
        System.out.println("Enter port for this server to listen on: ");
        int iPort = new Scanner(System.in).nextInt();

        //Get IP and port of server
        System.out.println("Enter IP address of server to connect to: ");
        String sOtherServerIP = new Scanner(System.in).nextLine();
        System.out.println("Enter port of server to connect to: ");
        int iOtherServerPort = new Scanner(System.in).nextInt();

        //Start up server thread
        SocketServer oServer = new SocketServer(iPort);
        Thread oServerThread = new Thread(oServer);
        oServerThread.start();

        SocketClient oClient = new SocketClient();

        while(true)
        {
            System.out.println("Enter a message to send: ");
            String sMessage = new Scanner(System.in).nextLine();

            String sReplyFromServer = oClient.connectForOneMessage(sOtherServerIP, iOtherServerPort, sMessage);

            System.out.println("[client] Server reply: " + sReplyFromServer);
        }
    }
}




/*______________________________________________________________*/


import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
public class SocketServer implements Runnable
{
    private int thisServerPort;

    //constructor to recieve port #
    public SocketServer(int iPort)
    {
        thisServerPort = iPort;
    }


    public void run()
    {
        //Create ServerSocket obj 
        try(ServerSocket oServerSocket = new ServerSocket(thisServerPort))
        {
            System.out.println("Server is listening on port " + thisServerPort);

            while(true)
            {

                Socket oSocket = oServerSocket.accept();
                System.out.println("[Server]: New client connected: " +
                        oSocket.getRemoteSocketAddress());

                //Handle input
                InputStream input = oSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                //Prep output
                OutputStream output = oSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                String sReceivedMessage = reader.readLine();

                System.out.println("[server]: Server receied message: " + sReceivedMessage);

                writer.println("server received your message.");
                writer.flush();
            }
        }

        catch(IOException ex)
        {
            System.out.println("[server]: Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


/*________________________________________________________________________*/


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient
{
    public String connectForOneMessage(String sIP, int iPort, String sMessage)
    {
        try(Socket oSocket = new Socket())
        {
            //attempt to connect to server.
            oSocket.connect(new InetSocketAddress(sIP, iPort), 5000);

            //preparing for output.
            OutputStream output = oSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            //send message
            writer.println(sMessage);
            writer.flush();

            //get reply from server
            InputStream input = oSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            //???
            String sReturn = reader.readLine();

            oSocket.close();

            return sReturn;
        }
        catch(Exception ex)
        {
            System.out.println("[client]: Client exception: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }
}
























