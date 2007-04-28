package org.pgist.funding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class used to hold all of the methods used in the creation of tax calculator report peices
 * 
 * @author Matt Paulin
 */
public class TaxCalcUtils {

    /**
     * Used to format the price
     */
    public static final NumberFormat PRICE_FORMAT = new DecimalFormat( "$########.00" );    
    public static final NumberFormat NUM_FORMAT = new DecimalFormat( "########" );    
    public static final NumberFormat TAX_FORMAT = new DecimalFormat( "###.0%" );  	
	
    public static final float DEFAULT_ESTIMATED_PEAK_TRIPS = 20;
    public static final float DEFAULT_ESTIMATED_OFF_PEAK_TRIPS = 20;
    public static final float EMPLOYER_PERCENTAGE = .8f;
    
    //------------------ Methods for making estimates ----------------------
	public static float estimatePeakTrips(UserCommute commute, FundingSource source) {
		float estimate = DEFAULT_ESTIMATED_PEAK_TRIPS;
		Iterator<UserFundingSourceToll> i = commute.getTolls().iterator();
		UserFundingSourceToll tempToll;
		while(i.hasNext()) {
			tempToll = i.next();
			if(tempToll.getFundingSource().getId() == source.getId()) {
				estimate = tempToll.getPeakTrips();
				break;
			}
		}
		return estimate;
	}

	public static float estimateOffPeakTrips(UserCommute commute, FundingSource source) {
		float estimate = DEFAULT_ESTIMATED_OFF_PEAK_TRIPS;
		Iterator<UserFundingSourceToll> i = commute.getTolls().iterator();
		UserFundingSourceToll tempToll;
		while(i.hasNext()) {
			tempToll = i.next();
			if(tempToll.getFundingSource().getId() == source.getId()) {
				estimate = tempToll.getOffPeakTrips();
				break;
			}
		}
		return estimate;
	}   

    //------------------ Methods that do the actual calculations ----------------------
      
	/**
	 * Calculates the user cost for a sales tax
	 * 
	 * @param taxRate		The amount of tax
	 * @param consumption	The consumption
	 * @return	The expected total cost for that user
	 */
	public static float calcUserSalesTaxCost(float taxRate, float consumption) {
		float cost = (taxRate * consumption)/100;
		return cost;
	}	
	
	/**
	 * Calculates the cost of a gas sales tax alternative to the user
	 * 
	 * @param 	taxRate		The cost of tax on gas
	 * @param	gasCost		The cost of gas
	 * @param	milesDriven	The total number of miles driven in a year
	 * @param	mpg			The total number of miles per gallon
	 * @return	The cost the user should expect
	 */
	public static float calcUserGasSalesTaxCost(float taxRate, float gasCost, float milesDriven, float mpg) {
		float cost = 0;
		if(mpg != 0) {
			cost = taxRate * gasCost * milesDriven / mpg;			
		}
		return cost;
	}	
	
	/**
	 * Calculates a motor alternative cost to the user
	 * 
	 * @param 	taxRate		The cost of the tax per vehicle
	 * @param 	numVehicles	The total number of vehicles
	 * @return	The total cost of the tax to the user
	 */
	public static float calcUserVehicleLicenseCost(float taxRate, float numVehicles) {
		return taxRate * numVehicles;
	}

	
	/**
	 * Calculates the cost to the user for a vehicle excise alternative
	 * 
	 * @param taxRate		The cost of the tax for the vehicle
	 * @param value			The value of the vehicle
	 * @return				The total worth of the vehicle
	 */
	public static float calcUserVehicleExciseCost(float taxRate, float vehicleValue) {
		return taxRate * vehicleValue;
	}
	
	/**
	 * Calculates the cost to the user for a new gas tax alternative
	 * 
	 * @param mpg		The miles per gallon the user uses
	 * @param taxRate	The tax rate for the gallon cost
	 * @param milesDriven	The number of miles driven
	 * @return	The total cost in a year
	 */
	public static float calcUserGasTaxCost(float mpg, float taxRate, float milesDriven) {
		float cost = 0;
		if(mpg != 0) {
			cost = taxRate * milesDriven/ mpg;
		}
		return cost;
	}

	/**
	 * Calculates the employer exciste cost to the user
	 */
	public static float calcUserEmployerExciseAlternativeCost(float taxRate, float expectedPercentage) {
		return taxRate*expectedPercentage;
	}

	/**
	 * Calculates the parking toll alternative 
	 * 
	 * @param	parkingToll	The tax rate for the parking
	 * @param	peakTrips	The number of peak trips
	 * @param	offPeakTrips	The number of off peak trips
	 */
	public static float calcUserParkingCost(float parkingToll, float peakTrips, float offPeakTrips) {
		float total = parkingToll * (peakTrips + offPeakTrips);
		return total;
	}
	
	/**
	 * Calculates a toll alternative
	 * 
	 * @param	peakHourRates	The rate of the toll at peak hour
	 * @param	peakHourTrips	The number of trips at peak hour
	 * @param	offPeakHourRates	The off peak rate of the toll at peak hour
	 * @param	offPeakHourTrips	The number of off peak trips at peak hour
	 * @return	The personal cost
	 */
	public static float calcUserTollAlternatives(float peakHourRates, float peakHourTrips, float offPeakHourRates, float offPeakHourTrips) {
		float total = peakHourRates * peakHourTrips + offPeakHourRates * offPeakHourTrips;
		return total;
	}	
	
	
	//------------------Methods for generating a report --------------------------
	
	/**
	 * Creates the proper report using the provided funding source
	 * 
	 * @param	source	The funding source
	 * @param	costs	The costs object to put the report into
	 */
	public static void createReport(FundingSource source,
			List<PersonalFundingCostDTO> costs, float consumption,
			int numVehicles, float vehicleValue, float milesDriven, float mpg,
			float gasCost) {		
		if(source.getAlternatives().size() > 0) {
			switch (source.getType()) {

			case FundingSource.TYPE_EMPLOYER_EXCISE_TAX:	
				costs.add(createEmployerExciseTaxReport(source));
				break;
			case FundingSource.TYPE_GAS_TAX:			
				costs.add(createGasTaxReport(source, milesDriven, mpg));
				break;
			case FundingSource.TYPE_LICENSE:			
				costs.add(createVehicleLicenseTaxReport(source, numVehicles));
				break;
			case FundingSource.TYPE_MOTOR_TAX:			
				costs.add(createVehicleExciseReport(source, vehicleValue));
				break;
			case FundingSource.TYPE_PARKING_TAX:
				//TODO I'm ignoring this type because you can only calculate it if it is already
				//loaded as a UserFundingSourceToll
				//costs.add(createParkingTaxReport(source));
				break;
			case FundingSource.TYPE_SALES_GAS_TAX:			
				costs.add(createGasSalesTaxReport(source, gasCost, milesDriven, mpg));
				break;
			case FundingSource.TYPE_SALES_TAX:			
				costs.add(createSalesTaxReport(source, consumption));
				break;
			case FundingSource.TYPE_TOLLS:			
				//TODO I'm ignoring this type because you can only calculate it if it is already
				//loaded as a UserFundingSourceToll
				//costs.add(createTollsTaxReport(source));
				break;

			default:
				break;
			} 			
		}
	}
	
	
	
	private static PersonalFundingCostDTO createSalesTaxReport(FundingSource source, float consumption) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("tax rate");
		headers.add(" ");
		headers.add("consumption");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createSalesTaxAlternative(tempAlt, consumption));
		}			
		
		return pfcost;
	}
	
	private static PersonalFundingCostAlternativeDTO createSalesTaxAlternative(FundingSourceAlternative alt, float consumption) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();
		float cost = calcUserSalesTaxCost(alt.getTaxRate(), consumption);
		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(cost));
		data.add("=");
		data.add(TAX_FORMAT.format(alt.getTaxRate()));
		data.add("X");
		data.add(PRICE_FORMAT.format(consumption));
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		
		return pfcost;
	}	
	

	
	private static PersonalFundingCostDTO createGasSalesTaxReport(FundingSource source, float gasCost, float milesDriven, float mpg) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("tax rate");
		headers.add(" ");
		headers.add("cost of gas");
		headers.add(" ");
		headers.add("miles driven/yr");
		headers.add(" ");
		headers.add("mpg");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createGasSalesTaxAlternative(tempAlt,gasCost, milesDriven, mpg));
		}			
		
		return pfcost;
	}
	
	private static PersonalFundingCostAlternativeDTO createGasSalesTaxAlternative(FundingSourceAlternative alt, float gasCost, float milesDriven, float mpg) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		float cost =  calcUserGasSalesTaxCost(alt.getTaxRate(), gasCost, milesDriven, mpg);
		
		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(cost));
		data.add("=");
		data.add(TAX_FORMAT.format(alt.getTaxRate()));
		data.add("X");
		data.add(PRICE_FORMAT.format(gasCost));
		data.add("X");
		data.add(NUM_FORMAT.format(milesDriven));
		data.add("/");
		data.add(NUM_FORMAT.format(mpg));
		
		return pfcost;
	}	
	
	private static PersonalFundingCostDTO createVehicleLicenseTaxReport(FundingSource source, int numVehicles) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("Tax Rate");
		headers.add("X");
		headers.add("# Vehicles");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createMotorAlternative(tempAlt, numVehicles));
		}			
		
		return pfcost;
	}
	
	private static PersonalFundingCostAlternativeDTO createMotorAlternative(FundingSourceAlternative alt, int numVehicles) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		float cost = calcUserVehicleLicenseCost(alt.getTaxRate(),numVehicles);
		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(cost));
		data.add("=");
		data.add(PRICE_FORMAT.format(alt.getTaxRate()));
		data.add("X");
		data.add(NUM_FORMAT.format(numVehicles));
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		
		return pfcost;
	}	
			
	private static PersonalFundingCostDTO createVehicleExciseReport(FundingSource source, float vehicleValue) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("tax rate");
		headers.add(" ");
		headers.add("vehicle value");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createVehicleExciseAlternative(tempAlt, vehicleValue));
		}			
		
		return pfcost;
	}
	
	private static PersonalFundingCostAlternativeDTO createVehicleExciseAlternative(FundingSourceAlternative alt, float vehicleValue) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		float cost = calcUserVehicleExciseCost(alt.getTaxRate(),vehicleValue);
		
		data.add(alt.getName());
		data.add("=");
		data.add(PRICE_FORMAT.format(cost));
		data.add("X");
		data.add(NUM_FORMAT.format(vehicleValue));
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		
		return pfcost;
	}	
	
	private static PersonalFundingCostDTO createGasTaxReport(FundingSource source, float milesDrive, float mpg) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("tax rate");
		headers.add(" ");
		headers.add("miles drive/yr");
		headers.add(" ");
		headers.add("mpg");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createGasTaxAlternative(tempAlt, milesDrive, mpg));
		}			
		
		return pfcost;
	}
	
	private static PersonalFundingCostAlternativeDTO createGasTaxAlternative(FundingSourceAlternative alt, float milesDriven, float mpg) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		
		float cost = calcUserGasTaxCost(mpg, alt.getTaxRate().floatValue(), milesDriven);

		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(cost));
		data.add("=");
		data.add(PRICE_FORMAT.format(alt.getTaxRate()));
		data.add("X");
		data.add(NUM_FORMAT.format(milesDriven));
		data.add("/");
		data.add(NUM_FORMAT.format(mpg));
		data.add(" ");
		data.add(" ");
		
		return pfcost;
	}	
		
	private static PersonalFundingCostDTO createEmployerExciseTaxReport(FundingSource source) {
		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();		
		List<String> headers = new ArrayList<String>();
		headers.add(source.getName());						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("Tax Rate");
		headers.add("*");
		headers.add("Percentage");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createEmployerExciseAlternative(tempAlt));
		}			
		
		return pfcost;
	}
	
	/**
	 * Fills in the line with all the data for a parking toll
	 * 
	 * @param alt		The alternative
	 * @param source	The userFundingSourceToll that contains the peak times and off peak times
	 * @return	A filled out PersonalFundingCostAlternativeDTO
	 */
	private static PersonalFundingCostAlternativeDTO createEmployerExciseAlternative(FundingSourceAlternative alt) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		data.add(alt.getName());
		if(calcUserEmployerExciseAlternativeCost(alt.getTaxRate(), EMPLOYER_PERCENTAGE) == 0 ) {
			data.add("No direct cost to you");			
		} else {
			data.add(calcUserEmployerExciseAlternativeCost(alt.getTaxRate(), EMPLOYER_PERCENTAGE));						
		}
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		data.add(" ");
		
		return pfcost;
	}	
	
	
	/**
	 * Creates the proper report using the provided funding source
	 * 
	 * @param	source	The funding source
	 * @param	costs	The costs object to put the report into
	 */
	public static void createParkingTollReport(UserFundingSourceToll source, List<PersonalFundingCostDTO> costs) {

		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();
		
		List<String> headers = new ArrayList<String>();
		headers.add("Commercial parking tax");						
		headers.add("Cost to you");
		headers.add("=");
		headers.add("tax rate");
		headers.add(" ");
		headers.add("# peak trips");
		headers.add(" ");
		headers.add("# off-peak trips");
		headers.add(" ");
		headers.add(" ");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getFundingSource().getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createParkingTollAlternative(tempAlt, source));
		}			
		
		//Only add it if there are alternatives to show
		if(datas.size() > 0) {
			costs.add(pfcost);									
		}
	}		
	
	
	/**
	 * Creates the proper report using the provided funding source
	 * 
	 * @param	source	The funding source
	 * @param	costs	The costs object to put the report into
	 */
	public static void createTollReport(UserFundingSourceToll source, List<PersonalFundingCostDTO> costs) {

		PersonalFundingCostDTO pfcost = new PersonalFundingCostDTO();
		List<PersonalFundingCostAlternativeDTO> datas = pfcost.getAlternatives();
		
		List<String> headers = new ArrayList<String>();
		headers.add("Toll on " + source.getName());			
		headers.add("Cost to you");
		headers.add("=");
		headers.add("peak toll");
		headers.add(" ");
		headers.add("# peak trips");
		headers.add(" ");
		headers.add("off peak toll");
		headers.add(" ");
		headers.add("# off-peak trips");
		pfcost.setHeaders(headers);

		Iterator<FundingSourceAlternative> alt = source.getFundingSource().getAlternatives().iterator();
		FundingSourceAlternative tempAlt;
		PersonalFundingCostAlternativeDTO p1;
		while(alt.hasNext()) {
			
			tempAlt = alt.next();
			datas.add(createTollAlternative(tempAlt, source));
		}			
		if(datas.size() > 0) {
			costs.add(pfcost);									
		}
	}	

	/**
	 * Fills in the line with all the data for a parking toll
	 * 
	 * @param alt		The alternative
	 * @param source	The userFundingSourceToll that contains the peak times and off peak times
	 * @return	A filled out PersonalFundingCostAlternativeDTO
	 */
	public static PersonalFundingCostAlternativeDTO createParkingTollAlternative(FundingSourceAlternative alt, UserFundingSourceToll source) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		float total = calcUserParkingCost(alt.getTaxRate(),(float)source.getPeakTrips(),(float)source.getOffPeakTrips());
		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(total));
		data.add("=");
		data.add(PRICE_FORMAT.format(alt.getTaxRate()));
		data.add("X (");
		data.add(NUM_FORMAT.format(source.getPeakTrips()));
		data.add("+");
		data.add(NUM_FORMAT.format(source.getOffPeakTrips()));
		data.add(")");
		data.add(" ");
		
		return pfcost;
	}

	
	/**
	 * Fills in the line with all the data for a toll
	 * 
	 * @param alt		The alternative
	 * @param source	The userFundingSourceToll that contains the peak times and off peak times
	 * @return	A filled out PersonalFundingCostAlternativeDTO
	 */
	private static PersonalFundingCostAlternativeDTO createTollAlternative(FundingSourceAlternative alt, UserFundingSourceToll source) {

		PersonalFundingCostAlternativeDTO pfcost = new PersonalFundingCostAlternativeDTO();
		List data = pfcost.getData();

		float total = calcUserTollAlternatives(alt.getPeakHourTripsRate(),(float)source.getPeakTrips(),alt.getOffPeakTripsRate(),(float)source.getOffPeakTrips());
		data.add(alt.getName());
		data.add(PRICE_FORMAT.format(total));
		data.add("=");
		data.add(PRICE_FORMAT.format(alt.getPeakHourTripsRate()));
		data.add("X");
		data.add(NUM_FORMAT.format(source.getPeakTrips()));
		data.add("+");
		data.add(PRICE_FORMAT.format(alt.getOffPeakTripsRate()));
		data.add("X");
		data.add(NUM_FORMAT.format(source.getOffPeakTrips()));
		
		return pfcost;
	}

}
