package server.logic;

import server.MainServer;
import server.objects.Channel;

import java.awt.*;
import java.util.Random;

public class EffectThread implements Runnable {
    public static int speed = 0;
    public static boolean keep = true;

    public void run() {
        System.out.println("Executed");

        for (int i = 0; i < 10000; i++) {
            if (!keep) {
                break;
            }

            for (Channel channel : UniverseHandler.getSelectedChannels()) {
                Random random = new Random();
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);

                Color color = new Color(r, g, b);

                channel.setColor(color);
                MainServer.getMulticastServer().broadcastState();
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
