package se.trantor.supporter;

import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class EsbeShunt {

	GpioController gpio ;
	GpioPinDigitalOutput incPin;
	GpioPinDigitalOutput decPin;

	private Logger logger = Logger.getLogger(EsbeShunt.class.getName());
	
	public EsbeShunt()
	{
		// create gpio controller
		gpio = GpioFactory.getInstance();

		// provision gpio pin #01 as an output pin and turn on
		incPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "INC", PinState.LOW);

		// set shutdown state for this pin
		incPin.setShutdownOptions(true, PinState.LOW);
		decPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "DEC", PinState.LOW);

		// set shutdown state for this pin
		decPin.setShutdownOptions(true, PinState.LOW);

		
	}

	
	public void increase()
	{
		logger.info("Increasing ...");

		doIncrease();
	}
	
	public void decrease()
	{
		logger.info("Decreasing ...");
		doDecrease();
	}

	private void doIncrease()
	{

		incPin.pulse(3000, false); // set second argument to 'true' use a blocking call

	}
	
	private void doDecrease()
	{

		decPin.pulse(3000, false); // set second argument to 'true' use a blocking call

	}
	
	

}
