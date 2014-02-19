package common.networking;

import java.net.InetAddress;

public class MulticastConfig {
	public static final InetAddress GROUP;
	public static final int PORT;

	static {
		try {
			GROUP = InetAddress.getByName("239.3.5.9");
			PORT = 1337;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
