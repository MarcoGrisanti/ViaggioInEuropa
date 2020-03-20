import java.util.*;

public class Edge {

	private Vertex sourceVertex;
	private Vertex destinationVertex;
	private int w;

	public Edge(LinkedList<Vertex> vertices, String sourceVertexName, String destinationVertexName, int w) {
		for (Vertex v: vertices) {
			if (v.getName().equals(sourceVertexName)) sourceVertex = v;
			if (v.getName().equals(destinationVertexName)) destinationVertex = v;
		}
		if (sourceVertex == null || destinationVertex == null) {
			System.out.println("Errore nella creazione del seguente arco: (" + sourceVertexName + ", " + destinationVertexName + ")");
			System.exit(1);
		}
		this.w = w;
	}

	public Vertex getSourceVertex() {
		return sourceVertex;
	}

	public Vertex getDestinationVertex() {
		return destinationVertex;
	}

	public int w() {
		return w;
	}

}