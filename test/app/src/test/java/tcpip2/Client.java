package tcpip2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	Socket socket;
	Sender sender;

	public Client() {
	}

	public Client(String address, int port) throws Exception {
		try {
			socket = new Socket(address, port);
		} catch (Exception e) { // ���� �õ� �ؼ� ��������
			while (true) { // ���� ��
				System.out.println("Retry..");
				try {
					Thread.sleep(1000); // 1�� �� �ٽ� �õ�
					socket = new Socket(address, port);

					break; // ���� �����ϱ� �ٽ� �õ��ϴ� while�� Ż��
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		}

		System.out.println("Connected Server : " + address); // ���� �Ǿ�� ����
		sender = new Sender(socket);// ������ ������ -> Sender �ʿ� -> Sender ��ü ����
		new Receiver(socket).start();
	}

	public void startClient() {
		
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Input Msg & IP");
			String txt = sc.nextLine();
			String ip = sc.nextLine();
			Msg msg = null;
			if(ip==null || ip.equals("")) {
				msg = new Msg("Jean",txt,null);
			}
			else {
				msg = new Msg("Yeojin", txt,ip);
			}
			
			sender.setmsg(msg);
			new Thread(sender).start(); // ������ ���߿� ���� Ŭ�����ع���->���� ���ų�
			if (txt.equals("q")) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		// q �� ������ �� �ڵ� ����
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("End Client.");
		sc.close();

	}

	class Sender extends Thread {
		OutputStream os;
		ObjectOutputStream oos;
		Msg msg;

		public Sender(Socket socket) throws IOException {
		
				os = socket.getOutputStream();
				oos = new ObjectOutputStream(os);
		
		}

		public void setmsg(Msg msg) {
			this.msg = msg;
		}

		public void run() {
			if (oos != null)
				try {
					oos.writeObject(msg);
				} catch (IOException e) {
				}
		}
	}

	class Receiver extends Thread{
		
		InputStream is;
		ObjectInputStream ois;
		
		public Receiver() {}
		
		public Receiver(Socket socket) throws IOException {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);

		}
		
		public void run() {
			while(ois!=null) {
				
				Msg msg = null;
				try {
					msg = (Msg) ois.readObject();
					System.out.println(msg.getId()+":"+msg.getMsg());
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
					System.out.println("���� ���");
					break;
				}
			}
			try {
			if(ois!=null) ois.close();
			if(socket!=null) socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	

	public static void main(String[] args){
		Client client = null;
		try {
			client = new Client("70.12.229.135",8888);
		} catch (Exception e) {
		}
		client.startClient();
		
	}

}
