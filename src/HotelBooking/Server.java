package HotelBooking;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Iterator;

public class Server {

	public static void main(String argv[]) {
		ServerSocket s = null;
		try {
			s = new ServerSocket(TextConstants.PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server is Running...");
		while (true) {
			Socket incoming = null;
			try {
				incoming = s.accept();
			} catch (IOException e) {
				System.out.println(e);
				continue;

			}

			new SocketHandler(incoming).start();
		}
	}
}

class SocketHandler extends Thread {

	Socket incoming;
	ServerHOPP fileServer = new ServerHOPP();

	BufferedReader reader;
	PrintStream writer;

	SocketHandler(Socket incoming) {
		this.incoming = incoming;
	}

	public void run() {
		try {
			writer = new PrintStream(incoming.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					incoming.getInputStream()));
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				// GETHOTELLIST#beijing#rujia#322#18:00-22:00
				System.out.println("Received request: " + line);
				String result[] = line.split("#");

				if (line.startsWith(TextConstants.GETCITYLIST)) {
					getCityList();
				} else if (line.startsWith(TextConstants.GETHOTELLIST)) {
					getHotelList(result[1]);
				} else if (line.startsWith(TextConstants.GETROOMRATELLIST)) {
					getRoomRate(result[1], result[2]);
				} else if (line.startsWith(TextConstants.BOOKROOM)) {
					bookRoom(result[1], result[2], result[3], result[4],
							result[5]);
				} else if (line.startsWith(TextConstants.GETVACANCY)) {
					getVancancy(result[1], result[2], result[3], result[4],
							result[5]);
				} else if (line.startsWith(TextConstants.BOOK)) {
					Book(line);

				} else {
					writer.print(TextConstants.ERROR + TextConstants.CR_LF);
				}

			}
			incoming.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCityList() {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet = fileServer.getCityList();
		Iterator<String> ir = hashSet.iterator();
		while (ir.hasNext()) {
			String retString = ir.next();
			System.out.println("server=" + retString);
			writer.print(TextConstants.SUCCEEDED + retString
					+ TextConstants.CR_LF);
		}
		writer.print(TextConstants.CR_LF);
	}

	public void getHotelList(String city) {
		System.out.println("server gethotel list...");
		HashSet<String> hashSet = new HashSet<String>();
		hashSet = fileServer.getHotelList(city);
		Iterator<String> ir = hashSet.iterator();
		while (ir.hasNext()) {
			writer.print(TextConstants.SUCCEEDED + " " + ir.next()
					+ TextConstants.CR_LF);
		}
		writer.print(TextConstants.CR_LF);
	}

	public void getRoomRate(String city, String hotel) {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet = fileServer.getRoomRate(city, hotel);
		Iterator<String> ir = hashSet.iterator();
		while (ir.hasNext()) {
			writer.print(TextConstants.SUCCEEDED + " " + ir.next()
					+ TextConstants.CR_LF);
		}
		writer.print(TextConstants.CR_LF);
	}

	public void getVancancy(String city, String hotel, String room,
			String indate, String outdate) {

		String count = fileServer.getVancancy(city, hotel, room, indate,
				outdate);
		writer.println(count);
		if (count.equals("yes")) {
			System.out.println("hava room");
		} else {
			System.out.println("no room");
		}
	}

	public void bookRoom(String city, String hotel, String room, String indate,
			String outdate) throws IOException {

		fileServer.bookRoom(city, hotel, room, indate, outdate);
		System.out.println("bookroom");
	}

	public void Book(String total) throws IOException {
		try {
			fileServer.Book(total);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}