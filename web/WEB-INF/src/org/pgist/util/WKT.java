/**
 * 
 */
package org.pgist.util;

import org.postgis.Geometry;
import org.postgis.LineString;
import org.postgis.LinearRing;
import org.postgis.MultiLineString;
import org.postgis.MultiPoint;
import org.postgis.MultiPolygon;
import org.postgis.PGgeometry;
import org.postgis.Polygon;

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
	 */public static double[][][] geomToArray(PGgeometry geom){
		if(geom == null)return null;
		double[][][] coords = null; 
		
		if( geom.getGeoType() == Geometry.MULTILINESTRING){
			MultiLineString mls = (MultiLineString)geom.getGeometry();
			coords = new double[1][mls.getLines().length][];
			
			for(int i=0; i<mls.getLines().length; i++ ){
				LineString ls = mls.getLines()[i];
				coords[0][i] = new double[ls.getPoints().length*2];
				for(int j=0; j<ls.getPoints().length*2; j=j+2){
					coords[0][i][j] = ls.getPoints()[j/2].x;
					coords[0][i][j+1] = ls.getPoints()[j/2].y;
				}
			}
		}else if( geom.getGeoType() == Geometry.MULTIPOLYGON){
			MultiPolygon mply = (MultiPolygon)geom.getGeometry();
			
			int totalRings = 0;
			for(int i=0; i<mply.numPolygons(); i++){
				totalRings += ((Polygon)mply.getPolygon(i)).numRings();
			}

			/*this put polygons into a set of rings, no links between rings*/
			coords = new double[1][totalRings][]; 
			int ringCount = 0;
			for(int i=0; i<mply.numPolygons(); i++){
				Polygon po = mply.getPolygon(i);
				for(int j=0; j<po.numRings(); j++){
					LinearRing lr = po.getRing(j);
					coords[0][ringCount] = new double[lr.numPoints()*2];
					for(int n=0; n<lr.numPoints()*2; n=n+2){
						coords[0][ringCount][n] = lr.getPoints()[n/2].x;
						coords[0][ringCount][n+1] = lr.getPoints()[n/2].y;
					}
					ringCount++;
				}			
			}			
			
			/*this put polygons into a set of parts, each part have one or more rings 
			coords = new double[mply.numPolygons()][][];
			for(int i=0; i<mply.numPolygons(); i++){
				Polygon po = mply.getPolygon(i);
				coords[i] = new double[po.numRings()][];
				for(int j=0; j<po.numRings(); j++){
					LinearRing lr = po.getRing(j);
					coords[i][j] = new double[lr.numPoints()*2];
					for(int n=0; n<lr.numPoints()*2; n=n+2){
						coords[i][j][n] = lr.getPoints()[n/2].x;
						coords[i][j][n+1] = lr.getPoints()[n/2].y;
					}
				}				
			}
			*/			
		}else{	//for all other types, take as points
			MultiPoint mpnt = (MultiPoint)geom.getGeometry();
			coords = new double[1][1][mpnt.numPoints()*2];
			for(int n=0; n<mpnt.numPoints()*2; n=n+2){
				coords[0][0][n] = mpnt.getPoints()[n/2].x;
				coords[0][0][n+1] = mpnt.getPoints()[n/2].y;
			}
		}

		return coords;
	}

}
