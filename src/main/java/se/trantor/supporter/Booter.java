package se.trantor.supporter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Booter {

	public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


        Runner o = new Runner();
        
        o.setOwfsHostname("localhost");
        o.setOwfsPort(4304);
        
        o.constructOwfsClient();
        Runnable task = o;
        
        int initialDelay = 1;
        int periodicDelay = 30;

        scheduler.scheduleAtFixedRate(task, initialDelay, periodicDelay, TimeUnit.SECONDS);

	}

}
