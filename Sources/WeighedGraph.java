import java.util.*;

public class WeighedGraph {

	private LinkedList<Vertex> vertices;
	private LinkedList<Edge> edges;
	private LinkedList<LinkedList<Vertex>> airportsLocalization;

	private void setAdjacencies() {
		for (Edge e: edges) e.getSourceVertex().getAdjs().add(e);
	}

	private void setAirportsLocalization() {
		for (Vertex v: vertices)
			if (v.getAirportLocalization() > 0)
				airportsLocalization.get(v.getAirportLocalization()).add(v);

		for (LinkedList<Vertex> ll: airportsLocalization)
			for (Vertex v: ll)
				for (Vertex u: ll)
					if (v != u) edges.add(new Edge(vertices, v.getName(), u.getName(), 2));

		for (Vertex v: airportsLocalization.get(1)) {
			for (Vertex u: airportsLocalization.get(2)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
			for (Vertex u: airportsLocalization.get(3)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
		}

		for (Vertex v: airportsLocalization.get(2)) {
			for (Vertex u: airportsLocalization.get(4)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
		}

		for (Vertex v: airportsLocalization.get(3)) {
			for (Vertex u: airportsLocalization.get(4)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
			for (Vertex u: airportsLocalization.get(5)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
		}

		for (Vertex v: airportsLocalization.get(4)) {
			for (Vertex u: airportsLocalization.get(6)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
		}

		for (Vertex v: airportsLocalization.get(5)) {
			for (Vertex u: airportsLocalization.get(6)) {
				edges.add(new Edge(vertices, v.getName(), u.getName(), 4));
				edges.add(new Edge(vertices, u.getName(), v.getName(), 4));
			}
		}
	}

	public WeighedGraph() {
		vertices = new LinkedList<Vertex>();
		edges = new LinkedList<Edge>();
		airportsLocalization = new LinkedList<LinkedList<Vertex>>();
		for (int i = 0; i < 7; i++) airportsLocalization.add(new LinkedList<Vertex>());
	}

	public Integer w(Edge e) {
		return e.w();
	}

	public Vertex getVertexByName(String name) {
		for (Vertex v: vertices)
			if (v.getName().equals(name))
				return v;
		System.out.println("Errore nella selezione del seguente vertice: " + name);
		System.exit(1);
		return null;
	}

	public void setVertices(LinkedList<Vertex> vertices) {
		this.vertices = vertices;
		setAirportsLocalization();
	}

	public LinkedList<Vertex> getVertices() {
		return vertices;
	}

	public void setEdges(LinkedList<Edge> edges) {
		for (Edge e: edges) {
			this.edges.add(e);
			this.edges.add(new Edge(vertices, e.getDestinationVertex().getName(), e.getSourceVertex().getName(), w(e)));
		}
		setAdjacencies();
	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

}