package controladores;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import sistema.Conexion;
import sistema.Kruskal;
import sistema.Localidad;

public class DisenioMapa {

	public static void crearVertices(ArrayList<MapMarker> marcas, List<Localidad> localidades) {
	    for (Localidad localidad : localidades) {
	    	Coordinate coordenadas = new Coordinate(localidad.getLatitud(), localidad.getLongitud());
			MapMarker marca = new MapMarkerDot(localidad.getNombre(), coordenadas);
	    	marcas.add(marca);
	    }
	}
	public static void cargarVertices(JMapViewer mapa, ArrayList<MapMarker> marcas) {
		for (MapMarker marca : marcas) {
			mapa.addMapMarker(marca);			
		}
	}
	public static JMapViewer mostrarAristasAGM(JMapViewer mapa, List<Localidad> localidades) {
		ArrayList<Coordinate> coords = new ArrayList<>();
		JMapViewer mapaRet = mapa;
		for (Localidad local : localidades) {
			coords.add(new Coordinate(local.getLatitud(), local.getLongitud()));
			MapPolygon polygon = new MapPolygonImpl(coords);
			polygon.getStyle().setBackColor(null);
			polygon.getStyle().setColor(Color.BLUE);
			mapaRet.addMapPolygon(polygon);
		}
		return mapaRet;
	}
	public static List<Conexion> crearConexiones(List<Localidad> localidades) {
		ArrayList<Conexion> conexiones = new ArrayList<>();
		for (Localidad origen : localidades) {
			for (Localidad destino : localidades) {
				if (origen != destino) {
					Conexion conexion = new Conexion(origen, destino);
					conexiones.add(conexion);
				}
			}
		}
		return conexiones;
	}
//	public static JMapViewer mostrarAristas(JMapViewer mapa, List<Localidad> localidades) {
//		JMapViewer mapaRet = mapa;
//		ArrayList<Coordinate> coords = new ArrayList<>();
//		for (Localidad origen : localidades) {
//			for (Localidad destino : localidades) {
//				if (origen != destino) {
//					coords.add(new Coordinate(origen.getLatitud(), origen.getLongitud()));
//					coords.add(new Coordinate(destino.getLatitud(), destino.getLongitud()));
//				}
//			}
//		}
//		MapPolygon polygon = new MapPolygonImpl(coords);
//		polygon.getStyle().setBackColor(null);
//		polygon.getStyle().setColor(Color.BLUE);
//		mapaRet.addMapPolygon(polygon);
//		return mapaRet;
//	}
	public static JMapViewer mostrarAGM(JMapViewer mapa, List<Localidad> localidades, List<Conexion> conexiones) {
	    JMapViewer mapaRet = mapa;
	    List<Conexion> result = Kruskal.arbolGeneradorMinimo(localidades, conexiones);
	    ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
	    
	    for (Conexion conex : result) {
	        Coordinate coord1 = new Coordinate(conex.getDestino().getLatitud(), conex.getDestino().getLongitud());
	        Coordinate coord2 = new Coordinate(conex.getOrigen().getLatitud(), conex.getOrigen().getLongitud());
	        //A partir de las 4 localidades hace un ciclo entre la loc 2 y la ultima
	        coords.add(coord1);
	        coords.add(coord2);
	    }	        
	    MapPolygon polygon = new MapPolygonImpl(coords);
	    polygon.getStyle().setBackColor(null);
	    polygon.getStyle().setColor(Color.RED);
	    mapaRet.addMapPolygon(polygon);
	    
	    return mapaRet;
	}
	public static double mostrarCostoTotal(List<Localidad> localidades, List<Conexion> conexiones) {
		double ret = 0;
	    List<Conexion> result = Kruskal.arbolGeneradorMinimo(localidades, conexiones);
	    for (Conexion conex : result) {
	    	ret += conex.getCostoTotal();
	    }
	    return ret;
	}
	public static double mostrarCostoConAumento(List<Localidad> localidades, List<Conexion> conexiones) {
		double ret = 0;
	    List<Conexion> result = Kruskal.arbolGeneradorMinimo(localidades, conexiones);
	    for (Conexion conex : result) {
	    	ret += conex.getCostoConAum();
	    }
	    return ret;
	}
	public static double mostrarCostoFijo(List<Localidad> localidades, List<Conexion> conexiones) {
		double ret = 0;
	    List<Conexion> result = Kruskal.arbolGeneradorMinimo(localidades, conexiones);
	    for (Conexion conex : result) {
	    	ret += conex.getCostoFijo();
	    }
	    return ret;
	}
	public static double mostrarCostoPorKM(List<Localidad> localidades, List<Conexion> conexiones) {
		double ret = 0;
	    List<Conexion> result = Kruskal.arbolGeneradorMinimo(localidades, conexiones);
	    for (Conexion conex : result) {
	    	ret += conex.getCostoPorKM();
	    }
	    return ret;
	}
}