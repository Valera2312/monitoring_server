package com.vp.monitoring_server.network;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Component
public class Socketimpl {
    String serverAddress = "127.0.0.1";
    int port = 8080;

    public String poll(String request) {
        try (
                Socket socket = new Socket(serverAddress, port);
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            byte[] buffer = new byte[1024];
            int bytesRead = bufferedInputStream.read(buffer);
            if (bytesRead != -1) {
                return new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);

            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

