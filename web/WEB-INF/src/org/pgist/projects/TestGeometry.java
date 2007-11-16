package org.pgist.projects;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgresql.util.PGobject;


/*
 *  javac -classpath /home/guirong/PGIST/CVO/web/WEB-INF/lib/postgis_1_0_0.jar:/home/guirong/PGIST/CVO/web/WEB-INF/lib/postgresql-8.1-405.jdbc3.jar org/pgist/projects/TestGeometry.java
 * */
public class TestGeometry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("LINEARRING: " + Geometry.LINEARRING);
		System.out.println("LINESTRING: " + Geometry.LINESTRING);
		System.out.println("MULTILINESTRING: " + Geometry.MULTILINESTRING);
		System.out.println("MULTIPOINT: " + Geometry.MULTIPOINT);
		System.out.println("MULTIPOLYGON: " + Geometry.MULTIPOLYGON);
		System.out.println("POINT: " + Geometry.POINT);
		System.out.println("POLYGON: " + Geometry.POLYGON);
		try{
			Geometry geom = PGgeometry.geomFromString("POINT(-122 48)");
			System.out.println("Geomtry value: " + geom.toString() );
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
