package com.rest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClientHandler {
    private String ip = "127.0.0.1";
    private int port = 12346;
	public SocketClientHandler(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public String connect() {
		String result = "";
		Socket client = new Socket();
		BufferedInputStream in;
        InetSocketAddress isa = new InetSocketAddress(ip, port);
        try {
			client.connect(isa);
			in = new BufferedInputStream(client.getInputStream());
			byte[] b = new byte[1024];
			
			int len;
			while ((len = in.read(b)) > 0) {
				result += new String(b, 0, len);
			}
			
			in.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			result = "Fail";
		}
        return result;
	}
}
