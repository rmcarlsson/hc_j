package se.trantor.supporter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FwdControl {

	class FwdControlTable {

		public double outDoorTemp;
		public double fwdTemp;

		FwdControlTable(double outT, double fwdT)
		{
			outDoorTemp = outT;
			fwdTemp = fwdT;
		}

	}

	private List<FwdControlTable> fwdPoints; 	
	private Logger logger = Logger.getLogger(FwdControl.class.getName());

	public FwdControl()
	{
		fwdPoints = new ArrayList<FwdControlTable>();
		fwdPoints.add(new FwdControlTable(-30, 42));
		fwdPoints.add(new FwdControlTable(-25, 40));
		fwdPoints.add(new FwdControlTable(-20, 38));
		fwdPoints.add(new FwdControlTable(-15, 36));
		fwdPoints.add(new FwdControlTable(-10, 34));
		fwdPoints.add(new FwdControlTable(-5, 32));
		fwdPoints.add(new FwdControlTable(0, 30));
		fwdPoints.add(new FwdControlTable(10, 25));
		fwdPoints.add(new FwdControlTable(15, 23));
		fwdPoints.add(new FwdControlTable(20, 20));
	}




	public double CalcFwd(double outdoor_temp, double fwd_temp)
	{

		double e = 0;

		double target_temp = calculateTarget(outdoor_temp);

		e = fwd_temp - target_temp;

		logger.info("Outdoor is " + outdoor_temp + " FWD is " + fwd_temp + " target is " + target_temp + " e is " + e);

		return e;
	}





	private double calculateTarget(double outdoor_temp)
	{

		int  pos = 0;
		double  x1, x2, y1, y2, index;
		double  ret = fwdPoints.get(0).fwdTemp;

		while( fwdPoints.get(pos).outDoorTemp < outdoor_temp )
		{
			if( pos > fwdPoints.size())
			{
				ret = fwdPoints.get(fwdPoints.size() - 1).fwdTemp;
				return ret;
			}
			pos++;

			x2 = fwdPoints.get(pos).outDoorTemp;
			x1 = fwdPoints.get(pos-1).outDoorTemp;

			y2 = fwdPoints.get(pos).fwdTemp;
			y1 = fwdPoints.get(pos-1).fwdTemp;

			index = ((outdoor_temp-x1)*100)/(x2 - x1);


			ret = ( y1 + (index*(y2-y1)/100));

		}

		return ret;

	}

}
