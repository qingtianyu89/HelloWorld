package com.test.action.netty.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by pangming on 2017/6/26.
 */
public class Client {

    public static void main(String[] args) {
        PrintWriter printWriter = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8090);
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write("hello, world");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(printWriter != null){
                printWriter.close();
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
