import java.util.*;

public class Vertex {
	private String name;
	private int airportLocalization;
	private LinkedList<Edge> adjs;

	public Integer[] d;
	public Vertex[] pred;

	public Vertex(String name) {
		this.name = name;
		this.airportLocalization = 0;
		d = new Integer[100000];
		pred = new Vertex[100000];
		adjs = new LinkedList<Edge>();
	}

	public Vertex(String name, int airportLocalization) {
		this.name = name;
		this.airportLocalization = airportLocalization;
		d = new Integer[100000];
		pred = new Vertex[100000];
		adjs = new LinkedList<Edge>();
	}

	public String getName() {
		return name;
	}

	public int getAirportLocalization() {
		return airportLocalization;
	}

	public LinkedList<Edge> getAdjs() {
		return adjs;
	}

}