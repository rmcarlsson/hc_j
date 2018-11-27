package se.trantor.supporter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;

public class Runner implements Runnable {

	public static final String OWFS_HOSTNAME = "localhost";
	public static final String OWFS_PORT = "4304";
	
	public static final double FWDCTRL_HYSTERESIS = 2;
	protected OwfsConnection client;

	private String owfsHostname;
	private int owfsPort;
	
//	private TempProbe outDoorTempProbe;
//	private TempProbe fwdTempProbe;
	private EsbeShunt shunt;

	
	private Logger logger = Logger.getLogger(Runner.class.getName());

	public Runner()
	{
		shunt = new EsbeShunt();
//		outDoorTempProbe
	}
	
	//@BeforeClass
	//@Parameters(OWFS_HOSTNAME)
	public void setOwfsHostname(String owfsHostname) {
		logger.info("setOwfsHostname:" + owfsHostname);
		this.owfsHostname = owfsHostname;
	}

	//@BeforeClass
	//@Parameters(OWFS_PORT)
	public void setOwfsPort(int owfsPort) {
		logger.info("setOwfsPort:" + owfsPort);
		this.owfsPort = owfsPort;
	}

	//@BeforeMethod
	public void constructOwfsClient() {
		OwfsConnectionFactory owfsConnectionFactory = new OwfsConnectionFactory(owfsHostname, owfsPort);
		client = owfsConnectionFactory.createNewConnection();
		logger.log(Level.INFO, "Got  client" + client);
}
	
	@Override
	public void run() {

		double outTemp = 0;
		double indoorTemp = 0;
		double fwdTemp = 0;
		
		try {
			String outT = client.read("28.F67E05010000/temperature");
			outTemp = Double.parseDouble(outT);			
			
			String inT = client.read("28.237A05010000/temperature");
			indoorTemp = Double.parseDouble(inT);

				
			String fwdT = client.read("28.C7FEFB000000/temperature");
			fwdTemp = Double.parseDouble(fwdT);


			
			
		} catch (IOException e) {
			logger.severe("IO exception "+ e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OwfsException e) {
			logger.severe("OwfsException " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FwdControl calc = new FwdControl();
		
		
		double e = calc.CalcFwd(outTemp, fwdTemp);
		
		if( Math.abs(e) > FWDCTRL_HYSTERESIS ) {
		    if( e < 0 ) {
		      shunt.increase();
		    }
		    else {
		    	shunt.decrease();
		    }
		  }

		
	}
	


}
