package HotelBooking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHOPP {

	protected Socket mySocket;
	protected BufferedReader reader;
	protected PrintStream writer;

	public ClientHOPP(String server) throws UnknownHostException, IOException {

		InetAddress address = InetAddress.getByName(server);
		mySocket = null;
		InputStream inStream = null;
		OutputStream outStream = null;

		mySocket = new Socket(address, TextConstants.PORT);
		inStream = mySocket.getInputStream();
		outStream = mySocket.getOutputStream();

		reader = new BufferedReader(new InputStreamReader(inStream));
		writer = new PrintStream(outStream);
	}

	public void quit() {
		try {
			writer.print(TextConstants.QUIT + TextConstants.CR_LF);
			reader.close();
			writer.close();
			mySocket.close();
		} catch (Exception e) {

		}
	}

	public String[] getCityList() {

		writer.print(TextConstants.GETCITYLIST + "#" + TextConstants.CR_LF);
		ArrayList<String> tempList = new ArrayList<String>();
		String line = null;
		while (true) {

			try {
				line = reader.readLine();
			} catch (IOException e) {
				break;
			}
			if (line.equals("")) {
				break;
			}
			tempList.add(line);
		}
		String[] cityList = new String[tempList.size()];
		tempList.toArray(cityList);
		return cityList;
	}

	public String[] getHotelList(String city) {

		writer.print(TextConstants.GETHOTELLIST + "#" + city
				+ TextConstants.CR_LF);
		ArrayList<String> tempList = new ArrayList<String>();
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				break;
			}
			if (line.equals("")) {
				break;
			}
			tempList.add(line);
		}
		String[] hotelList = new String[tempList.size()];
		tempList.toArray(hotelList);
		return hotelList;
	}

	public String[] getRoomRateList(String city, String hotel) {

		writer.print(TextConstants.GETROOMRATELLIST + "#" + city + "#" + hotel
				+ TextConstants.CR_LF);
		ArrayList<String> tempList = new ArrayList<String>();
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				break;
			}
			if (line.equals("")) {
				break;
			}
			tempList.add(line);
		}
		String[] roomrateList = new String[tempList.size()];
		tempList.toArray(roomrateList);
		return roomrateList;
	}

	public String getVancancy(String city, String hotel, String room,
			String indate, String outdate) {

		writer.print(TextConstants.GETVACANCY + "#" + city + "#" + hotel + "#"
				+ room + "#" + indate + "#" + outdate + TextConstants.CR_LF);
		String vac = "";
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			System.out.println("error");
		}
		if (line.equals("yes")) {
			vac = "yes";
		}

		if (line.equals("no")) {
			vac = "no";
		}
		return vac;
	}

	public void bookRoom(String city, String hotel, String room, String indate,
			String outdate) {

		writer.print(TextConstants.BOOKROOM + "#" + city + "#" + hotel + "#"
				+ room + "#" + indate + "#" + outdate + TextConstants.CR_LF);

	}

	public void Book(String total) {
		writer.print(TextConstants.BOOK + " Details:" + total
				+ TextConstants.CR_LF);

	}
}
