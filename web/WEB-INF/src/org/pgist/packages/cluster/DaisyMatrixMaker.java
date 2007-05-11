package org.pgist.packages.cluster;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DaisyMatrixMaker {

	public static final NumberFormat NUM_FORMAT = new DecimalFormat( "#####0.00" );
	public static List<List<Float>> computeMatrix(List<List<Boolean>> input) {
		List<List<Float>> rows = new ArrayList<List<Float>>();
		List<Float> values;
		
		Iterator<List<Boolean>> iInRows = input.iterator();
		List<Boolean> item1;
		List<Boolean> item2;
		
		List<Float> row;
		for(int i = 0; i < input.size(); i++) {
			row = new ArrayList<Float>();
			for(int j = 0; j < input.size(); j++) {
				item1 = input.get(i);
				item2 = input.get(j);
				row.add(calcDist(item1, item2));
			}			
			rows.add(row);
		}
				
		return rows;
	}

	public static Float calcDist(List<Boolean> item1, List<Boolean> item2) {
		if(item1.size() != item2.size()) throw new RuntimeException("Attempting to compare two items with a different number of variables");
		float num = 0;
		float den = 0;
		float tempD;
		for(int i = 0; i < item1.size(); i++) {
			tempD = calcD(item1.get(i), item2.get(i));
			num = num + calcDelta(item1.get(i), item2.get(i)) * tempD;
			den = den + tempD;
		}
		
		if(den == 0) return new Float(0);
		return new Float(num/den);
	}
	
	public static float calcDelta(Boolean b1, Boolean b2) {
		float result = 0;
		if(b1 == b2) {
			result = 0;
		} else {
			result = 1;
		}
				
		return result;
	}
	public static float calcD(Boolean b1, Boolean b2) {
		float result = 0;
		if(!b1 && !b2 ) {
			result = 0;
		} else {
			result = 1;
		}
//		if(b1 || b2 ) {
//			result = 1;
//		} else {
//			result = 0;
//		}
		
		return result;
	}
	
	
	public static List<Boolean> createVars(int x1, int x2, int x3, int x4, int x5) {
		List<Boolean> vars = new ArrayList<Boolean>();
		vars.add(convert(x1));
		vars.add(convert(x2));
		vars.add(convert(x3));
		vars.add(convert(x4));
		vars.add(convert(x5));
		return vars;
	}
	public static boolean convert(int i) {
		if(i == 0) return false;
		return true;
	}
	
	public static String toNum(Boolean b) {
		if(b) return "1";
		return "0";
	}
	
	public static void printInput(List<List<Boolean>> input) {
		System.out.println("Got an Input of...");
		Iterator<List<Boolean>> iRow = input.iterator();
		List<Boolean> values;
		
		Iterator<Boolean> iValue;
		Boolean value;
		String row;
		int count = 0;
		while(iRow.hasNext()) {
			count++;
			values = iRow.next();
			iValue = values.iterator();
			row = "Row " + count + ": ";
			while(iValue.hasNext()) {
				value = iValue.next();
				row = row + DaisyMatrixMaker.toNum(value) + ", ";
			}
			System.out.println(row);
		}
		System.out.println("\n\n");
	}
	public static void printOutput(List<List<Float>> output) {
		System.out.println("Got an Output of...");
		Iterator<List<Float>> iRow = output.iterator();
		List<Float> values;
		
		Iterator<Float> iValue;
		Float value;
		String row;
		int count = 0;
		while(iRow.hasNext()) {
			count++;
			values = iRow.next();
			iValue = values.iterator();
			row = "Row " + count + ": ";
			while(iValue.hasNext()) {
				value = iValue.next();
				row = row + NUM_FORMAT.format(value) + ", ";
			}
			System.out.println(row);
		}
	}
	
	public static void main(String[] args) {
		List<List<Boolean>> input = new ArrayList<List<Boolean>>();
//		input.add(DaisyMatrixMaker.createVars(0,0,0,0,0));
//		input.add(DaisyMatrixMaker.createVars(0,0,0,0,1));
//		input.add(DaisyMatrixMaker.createVars(0,0,0,1,0));
//		input.add(DaisyMatrixMaker.createVars(0,0,0,1,1));
//		input.add(DaisyMatrixMaker.createVars(0,0,1,0,0));
//		input.add(DaisyMatrixMaker.createVars(0,0,1,0,1));

		input.add(DaisyMatrixMaker.createVars(0,1,1,0,1));
		input.add(DaisyMatrixMaker.createVars(0,0,0,1,1));
		input.add(DaisyMatrixMaker.createVars(0,1,1,0,0));
		input.add(DaisyMatrixMaker.createVars(1,0,0,1,0));
		input.add(DaisyMatrixMaker.createVars(1,1,0,1,0));
		input.add(DaisyMatrixMaker.createVars(1,1,1,0,0));		
		
		List<List<Float>> result = DaisyMatrixMaker.computeMatrix(input);
		printInput(input);		
		printOutput(result);
	}
}