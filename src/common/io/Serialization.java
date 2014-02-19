package common.io;

import java.io.*;

public class Serialization {
	//Returns true if success, false if otherwise.
	public static boolean writeObject(String path, Object object) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOutputStream);
			objectOut.writeObject(object);

			objectOut.close();
			fileOutputStream.close();

			System.out.println("Successfully wrote object " + object + " (type " + object.getClass() + ") to " + path);

			return true;
		} catch (IOException e) {
			System.out.println("Error serializing " + object + " (type " + object.getClass() + ")");
			e.printStackTrace();

			return false;
		}
	}

	//Reads and deserializes an object from path and returns it.
	public static Object readObject(String path) {
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInput = new ObjectInputStream(fileInputStream);
			Object object = objectInput.readObject();

			objectInput.close();
			fileInputStream.close();

			return object;
		} catch (Exception e) {
			System.out.println("Error deserializing: " + path);
			e.printStackTrace();

			return null;
		}
	}
}
