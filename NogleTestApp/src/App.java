import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App extends Thread {
	private ServerSocket server;
	public App(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("IOException :" + e.toString());
		}
	}

	public void run() {
		Socket socket;
		System.out.println("Server Running");
		while (true) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("connect from: InetAddress = "
						+ socket.getInetAddress());

				afterConnect(socket);
				socket.close();
				System.out.println("socket closed.");
			} catch (IOException e) {
				System.out.println("IOException :" + e.toString());
			} catch (InterruptedException e) {
				System.out.println("Sleep Error :" + e.toString());
			}
		}
	}

	private void afterConnect(Socket socket) throws InterruptedException, IOException {
		Thread.sleep(60000);
		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		out.write("Message from App after sleep 1 min".getBytes());
		out.flush();
		out.close();
	}
	
	public static void main(String args[]) {
		new App(12345).start();
		System.out.println("Stop");
	}

}