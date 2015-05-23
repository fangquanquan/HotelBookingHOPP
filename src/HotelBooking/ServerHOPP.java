package HotelBooking;

import java.io.*;
import java.util.HashSet;
import java.util.Vector;

public class ServerHOPP {

	Vector<String> vec = new Vector<String>();
	private String file;

	public Vector<String> readConfig() throws IOException {

		File file = new File("src\\file\\infomation.txt");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		while ((tempString = reader.readLine()) != null) {
			vec.add(tempString);
		}
		reader.close();
		return vec;
	}

	public ServerHOPP() {
		try {
			readConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashSet<String> getCityList() {
		HashSet<String> hashSet = new HashSet<String>();
		for (int i = 0; i < vec.size(); i++) {
			String str = vec.elementAt(i);
			String result[] = str.split("#");
			if (!hashSet.contains(result[0]))
				hashSet.add(result[0]);
		}

		return hashSet;
	}

	public HashSet<String> getHotelList(String city) {
		HashSet<String> hashSet = new HashSet<String>();
		for (int i = 0; i < vec.size(); i++) {

			String str = vec.elementAt(i);
			String result[] = str.split("#");

			if (city.equals(result[0])) {
				hashSet.add(result[1]);
			}

		}
		return hashSet;
	}

	public HashSet<String> getRoomRate(String city, String hotel) {
		HashSet<String> hashSet = new HashSet<String>();
		for (int i = 0; i < vec.size(); i++) {
			String str = vec.elementAt(i);
			String result[] = str.split("#");
			if ((city.equals(result[0])) && (hotel.equals(result[1]))) {
				hashSet.add(result[2]);
			}

		}
		return hashSet;
	}

	public HashSet<String> getVacancyList(String city, String hotel, String room) {
		HashSet<String> hashSet = new HashSet<String>();
		for (int i = 0; i < vec.size(); i++) {
			String str = vec.elementAt(i);
			String result[] = str.split("#");
			if ((city.equals(result[0])) && (hotel.equals(result[1]))
					&& (room.equals(result[2]))) {
				hashSet.add(result[3]);
			}
		}
		return hashSet;
	}

	public String getVancancy(String city, String hotel, String room,
			String indate, String outdate) {
		String vac = "";
		int count = 0;
		int date = 0;
		int inday = Integer.parseInt(indate);
		int outday = Integer.parseInt(outdate);

		int num = inday;

		for (int i = 0; i < vec.size(); i++) {
			
			String str = vec.elementAt(i);
			String result[] = str.split("#");
			date = Integer.parseInt(result[3]);
			count = Integer.parseInt(result[4]);

			if ((city.equals(result[0])) && (hotel.equals(result[1]))
					&& (room.equals(result[2])) && (num == date)
					&& (num < outday)) {
				if (count > 0) {
					vac = "yes";
					num++;
				}

				else {
					vac = "no";
					break;
				}
			}
		}
		return vac;
	}

	public synchronized void bookRoom(String city, String hotel, String room,
			String indate, String outdate) throws IOException {

		int count = 0;
		int date = 0;
		int inday = Integer.parseInt(indate);
		int outday = Integer.parseInt(outdate);
		int num = inday;
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < vec.size(); i++) {
			String str = vec.elementAt(i);
			String result[] = str.split("#");
			date = Integer.parseInt(result[3]);
			count = Integer.parseInt(result[4]);

			if ((city.equals(result[0])) && (hotel.equals(result[1]))
					&& (room.equals(result[2])) && (num == date)
					&& (num < outday)) {
				str = result[0] + "#" + result[1] + "#" + result[2] + "#"
						+ result[3] + "#" + (count - 1);

			}
			buf.append(str).append("\r\n");
			num++;
		}
		FileWriter writer = new FileWriter("src\\file\\infomation.txt", false);
		writer.write(buf.toString());
		writer.close();
	}

	public synchronized void Book(String total) throws IOException {
		File outputfile = new File("src\\file\\bookinfo.txt");

		try {
			if (!outputfile.exists())
				outputfile.createNewFile();
			FileWriter fw = new FileWriter(outputfile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(total);// write to Info
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
