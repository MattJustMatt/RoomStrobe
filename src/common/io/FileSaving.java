package common.io;

public class FileSaving {
	//Returns true on succcss, false otherwise.
	public static boolean saveObjectToFile(String path, String extension, Object object) {
		return Serialization.writeObject(path + "." + extension, object);
	}
}
