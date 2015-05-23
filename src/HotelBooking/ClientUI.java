package HotelBooking;

import java.io.*;

public class ClientUI {

	protected BufferedReader console;
	protected ClientHOPP clientHOPP;

	public static void main(String args[]) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: Client address");
			System.exit(1);
		}
		ClientUI ui = new ClientUI(args[0]);
		ui.loop();
	}

	public ClientUI(String server) {

		clientHOPP = null;
		try {
			clientHOPP = new ClientHOPP(server);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		console = new BufferedReader(new InputStreamReader(System.in));
	}

	private boolean checkChooser(String list[], String dst) {
		return true;
	}

	public void loop() throws IOException {

		while (true) {
			String city = null;
			String hotel = null;
			String room = null;
			String indate = null;
			String outdate = null;
			String List[] = null;

			System.out.println("Welcome to Book Our Hotel!");

			List = getCityList();
			System.out.println("Select City:");
			city = console.readLine().trim();

			if (checkChooser(List, city)) {
				List = getHotelList(city);
				System.out.println("Select Hotel:");
				hotel = console.readLine().trim();
			}
			if (checkChooser(List, hotel)) {
				List = getRoomRateList(city, hotel);
				System.out.println("Select Roomrate:");
				room = console.readLine().trim();
			}
			if (checkChooser(List, room)) {
				// List = getVacancyList(city, hotel, room);
				System.out.println("Please input your check-in date:");
				indate = console.readLine().trim();
				System.out.println("Please input your check-out outdate:");
				outdate = console.readLine().trim();
				String vac = getVancancy(city, hotel, room, indate, outdate);
				if (vac.equals("yes")) {
					System.out.println("Have vacancy room! ");
					System.out.println("confirm : yes or no?");
					String confirm = console.readLine().trim();
					if (confirm.equals("yes")) {
						bookRoom(city, hotel, room, indate, outdate);
					} else {
						break;
					}

				}
				if (vac.equals("no")) {
					System.out.println("No available room!");
					System.out.println("Please try again!");
					System.out.println("");
					System.out.println("");
					continue;
				}

			}
			// }
			// else {
			// System.out.println("Please try again!");
			// System.out.println("");
			// System.out.println("");
			// continue;
			// }
			//
			// console.readLine();
		}
	}

	/**
	 * Given that the string starts with the prefix, get rid of the prefix and
	 * any whitespace
	 */
	public String losePrefix(String str, String prefix) {
		int index = prefix.length();
		String ret = str.substring(index).trim();
		return ret;
	}

	protected String[] getCityList() {
		String[] cityList = clientHOPP.getCityList();
		if (cityList.length == 0) {
			System.out.println("No city list available");
		} else {
			System.out.println("city listing is:");
			for (int n = 0; n < cityList.length; n++) {
				System.out.println(cityList[n]);
			}
			System.out.println();
		}
		return cityList;
	}

	protected String[] getHotelList(String city) {
		String[] hotelList = clientHOPP.getHotelList(city);
		if (hotelList.length == 0) {
			System.out.println("No hotel list available");
		} else {
			System.out.println("hotel listing is:");
			for (int n = 0; n < hotelList.length; n++) {
				System.out.println(hotelList[n]);
			}
		}
		return hotelList;
	}

	protected String[] getRoomRateList(String city, String hotel) {
		String[] roomratelList = clientHOPP.getRoomRateList(city, hotel);
		if (roomratelList.length == 0) {
			System.out.println("No room list available");
		} else {
			System.out.println("RoomRate is:");
			for (int n = 0; n < roomratelList.length; n++) {
				System.out.println(roomratelList[n]);
			}
		}
		return roomratelList;
	}

	protected void bookRoom(String city, String hotel, String room,
			String indate, String outdate) throws IOException {
		clientHOPP.bookRoom(city, hotel, room, indate, outdate);
		String name = null;
		String telphone = null;
		String address = null;
		String creditno = null;
		String total = null;

		System.out.print("please input yourname:");
		name = console.readLine().trim();
		System.out.print("please input telphone:");
		telphone = console.readLine().trim();
		System.out.print("please input address:");
		address = console.readLine().trim();
		System.out.print("please input credit card number:");
		creditno = console.readLine().trim();
		System.out.println("booking succeed!");
		System.out.println("");

		total = city + " | " + hotel + " | " + room + " | " + indate + " | "
				+ outdate + " | " + " " + name + " | " + telphone + " | "
				+ address + " | " + creditno;
		Book(total);
		System.out.println("The booking details:  " + total);
		System.out.println("");
		System.out.println("");

	}

	protected String getVancancy(String city, String hotel, String room,
			String indate, String outdate) {
		String vac = clientHOPP.getVancancy(city, hotel, room, indate, outdate);
		return vac;

	}

	protected void Book(String total) {
		clientHOPP.Book(total);

	}
}