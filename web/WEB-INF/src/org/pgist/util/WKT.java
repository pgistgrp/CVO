/**
 * 
 */
package org.pgist.util;

import org.postgis.*;

/**
 * This class provides some utilities to convert form coordinates to 
 * WKT, OGC's Well Know Text, a text representation of geometries.
 * @author Guirong
 *
 */
public class WKT {

	/**
	 * Generate WKT for multilines (lines that (may) have
	 * multiple disloint parts)
	 * @param coords: x y,x y ... be careful of Google's GLatLng object,
	 * which is lat long, lat long, the order needs to be reversed, i.e., 
	 * x=long y=lat
	 * @return
	 */
	public static String lineWKT(double[][] coords){
		StringBuffer wkt = new StringBuffer();
		if(coords == null)return wkt.toString();
		
		wkt.append("MULTILINESTRING(");
		for(int i=0; i<coords.length; i++){
			if( i>0 )wkt.append( "," );
			wkt.append( "(" );
			for(int j=0; j<coords[i].length; j++){
				if( j>0 )wkt.append( "," );
				wkt.append( coords[i][j] + " " + coords[i][++j] );
			}
			wkt.append( ")" );
		}
		wkt.append( ")" );
		
		return wkt.toString();
	}
	 
	/**
	 * Generate WKT for multi point
	 * @param coords: x y,x y ... be careful of Google's GLatLng object,
	 * which is lat long, lat long, the order needs to be reversed, i.e., 
	 * x=long y=lat
	 * @return
	 */
	public static String pointWKT(double[] coords){
		StringBuffer wkt = new StringBuffer();
		if(coords == null)return wkt.toString();
		
		wkt.append("MULTIPOINT(");
		for(int i=0; i<coords.length; i++){
			if( i>0 )wkt.append( "," );
			wkt.append( coords[i] + " " + coords[++i] );
		}
		wkt.append( ")" );
		
		return wkt.toString();
	}
	 
	/**
	 * Convert geomtry object into coordinates array.
	 * @param geom
	 * @return
	 */public static double[][] geomToArray(PGgeometry geom, int[] parts){
		if(geom == null)return null;
		double[][] coords = null; 
		
		if( geom.getGeoType() == Geometry.MULTILINESTRING){
			MultiLineString mls = (MultiLineString)geom.getGeometry();
			coords = new double[mls.getLines().length][];
			parts = new int[mls.getLines().length];	//for line strings, each part is only one segment
			
			for(int i=0; i<mls.getLines().length; i++ ){
				LineString ls = mls.getLines()[i];
				coords[i] = new double[ls.getPoints().length*2];
				parts[i] = 1;  //for line strings, each part is only one segment
				for(int j=0; j<ls.getPoints().length*2; j=j+2){
					coords[i][j] = ls.getPoints()[j/2].x;
					coords[i][j+1] = ls.getPoints()[j/2].y;
					//System.out.println(this.coords[i][j] + " " + this.coords[i][j+1] + "\n");
				}
			}
		}

		return coords;
	}

}
