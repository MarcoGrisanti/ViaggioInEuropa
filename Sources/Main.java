import java.util.*;

public class Main {

	public static void initializeSingleSource(WeighedGraph g, Vertex s) {
		for (int i = 0; i < g.getVertices().size(); i++) {
			s.d[i] = 100000;
			s.pred[i] = null;
		}
		s.d[g.getVertices().indexOf(s)] = 0;
	}

	public static void relax(WeighedGraph g, Edge e, Vertex s, HashMap<Vertex, Integer> hashMap) {
		if (s.d[g.getVertices().indexOf(e.getDestinationVertex())] > s.d[g.getVertices().indexOf(e.getSourceVertex())] + g.w(e)) {
			s.d[g.getVertices().indexOf(e.getDestinationVertex())] = s.d[g.getVertices().indexOf(e.getSourceVertex())] + g.w(e);
			s.pred[g.getVertices().indexOf(e.getDestinationVertex())] = e.getSourceVertex();
			hashMap.put(e.getDestinationVertex(), s.d[g.getVertices().indexOf(e.getDestinationVertex())]);
		}
	}

	public static void scan(WeighedGraph g, Vertex u, Vertex s, HashMap<Vertex, Integer> hashMap) {
		for (Edge e: u.getAdjs())
			relax(g, e, s, hashMap);
	}

	public static void Dijkstra(WeighedGraph g, Vertex s) {
		initializeSingleSource(g, s);
		HashMap<Vertex, Integer> hashMap = new HashMap<Vertex, Integer>();
		for (Vertex v: g.getVertices()) hashMap.put(v, s.d[g.getVertices().indexOf(v)]);
		while (!hashMap.isEmpty()) {
			Integer minimumDistance = Collections.min(hashMap.values());
			Vertex v = getKeyByValue(hashMap, minimumDistance);
			hashMap.remove(v);
			scan(g, v, s, hashMap);
		}
	}

	public static Vertex getKeyByValue(HashMap<Vertex, Integer> hashMap, Integer value) {
		for (HashMap.Entry<Vertex, Integer> entry : hashMap.entrySet())
			if (Objects.equals(value, entry.getValue()))
				return entry.getKey();

		return null;
	}

	public static void printPath(WeighedGraph g, Vertex u, Vertex v) {
		if (u.d[g.getVertices().indexOf(v)] == 100000) {
			System.out.println("Impossibile raggiungere " + v.getName());
			System.exit(1);
		}

		LinkedList<Vertex> path = new LinkedList<Vertex>();
		path.add(v);
		for (int i = g.getVertices().indexOf(v); u.pred[i] != null; i = g.getVertices().indexOf(u.pred[i]))
			path.add(u.pred[i]);
		for (int i = path.size(); i > 0; i--)
			System.out.print(path.get(i - 1).getName() + " -> ");
		System.out.print("OK!\n");
	}

	private static void swap(LinkedList<Vertex> vertices, int i, int j) {
		Vertex v = vertices.get(i);
		vertices.set(i, vertices.get(j));
	    vertices.set(j, v);
	}

	@SuppressWarnings("unchecked")
	public static LinkedList<LinkedList<Vertex>> combinePaths(LinkedList<Vertex> vertices) {
		LinkedList<LinkedList<Vertex>> combinedPaths = new LinkedList<LinkedList<Vertex>>();
		combinedPaths.add((LinkedList<Vertex>) vertices.clone());
		int[] p = new int[vertices.size()];
		int i = 1;
		while (i < vertices.size()) {
			if (p[i] < i) {
				int j = ((i % 2) == 0) ? 0 : p[i];
				swap(vertices, i, j);
				combinedPaths.add((LinkedList<Vertex>) vertices.clone());
				p[i]++;
				i = 1;
			}
			else {
				p[i] = 0;
				i++;
			}
		}
		return combinedPaths;
	}

	public static void playGame(WeighedGraph g, Vertex s, LinkedList<Vertex> vertices) {
		Dijkstra(g, s);
		for (Vertex v: vertices) Dijkstra(g, v);

		LinkedList<LinkedList<Vertex>> combinedPaths = combinePaths(vertices);
		for (LinkedList<Vertex> path: combinedPaths) {
			path.addFirst(s);
			path.addLast(s);
		}

		LinkedList<Vertex> minimumPath = combinedPaths.get(0);
		int miniumDistance = 100000;
		for (int i = 0; i < combinedPaths.size(); i++) {
			int currentDistance = 0;
			for (int j = 0; j < combinedPaths.get(i).size() - 1; j++) {
				currentDistance = currentDistance + combinedPaths.get(i).get(j).d[g.getVertices().indexOf(combinedPaths.get(i).get(j + 1))];
			}
			if (currentDistance < miniumDistance) {
				minimumPath = combinedPaths.get(i);
				miniumDistance = currentDistance;
			}
		}

		for (int i = 0; i < minimumPath.size() - 1; i++) printPath(g, minimumPath.get(i), minimumPath.get(i + 1));
	}

	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("Errore! Specificare almeno una citta' di partenza ed una citta' da visitare.");
			System.exit(1);
		}

		WeighedGraph g = new WeighedGraph();
		g.setVertices(new LinkedList<Vertex>(Arrays.asList(new Vertex("La Coruna"),
														   new Vertex("Porto"),
														   new Vertex("Coimbra"),
														   new Vertex("Lisbona", 5),
														   new Vertex("Santander"),
														   new Vertex("Valladolid"),
														   new Vertex("Madrid", 5),
														   new Vertex("Cordoba"),
														   new Vertex("Siviglia"),
														   new Vertex("Cadice"),
														   new Vertex("Malaga"),
														   new Vertex("Granada"),
														   new Vertex("Alicante"),
														   new Vertex("Valencia"),
														   new Vertex("Barcellona", 5),
														   new Vertex("Saragozza"),
														   new Vertex("San Sebastiano"),
														   new Vertex("Bordeaux", 5),
														   new Vertex("Tolosa"),
														   new Vertex("Montpellier"),
														   new Vertex("Marsiglia"),
														   new Vertex("Nizza"),
														   new Vertex("Limoges"),
														   new Vertex("Clemont-Ferrand"),
														   new Vertex("Lione"),
														   new Vertex("Nantes"),
														   new Vertex("Brest"),
														   new Vertex("Tours"),
														   new Vertex("Digione"),
														   new Vertex("Strasburgo"),
														   new Vertex("Nancy"),
														   new Vertex("Reims"),
														   new Vertex("Parigi", 3),
														   new Vertex("Le Havre"),
														   new Vertex("Lilla"),
														   new Vertex("Calais"),
														   new Vertex("Orleans"),
														   new Vertex("Torino"),
														   new Vertex("Milano", 5),
														   new Vertex("Bologna"),
														   new Vertex("Bolzano"),
														   new Vertex("Venezia"),
														   new Vertex("Trieste"),
														   new Vertex("Genova"),
														   new Vertex("Pisa"),
														   new Vertex("Firenze"),
														   new Vertex("Ancona"),
														   new Vertex("Roma", 6),
														   new Vertex("Pescara"),
														   new Vertex("Napoli"),
														   new Vertex("Bari"),
														   new Vertex("Messina"),
														   new Vertex("Palermo"),
														   new Vertex("Ginevra"),
														   new Vertex("Berna"),
														   new Vertex("Basilea"),
														   new Vertex("Zurigo", 3),
														   new Vertex("Bruxelles", 3),
														   new Vertex("Lussemburgo"),
														   new Vertex("Rotterdam"),
														   new Vertex("Amsterdam", 3),
														   new Vertex("Groninga"),
														   new Vertex("Colonia"),
														   new Vertex("Francoforte", 3),
														   new Vertex("Stoccarda"),
														   new Vertex("Monaco di Baviera", 3),
														   new Vertex("Norimberga"),
														   new Vertex("Kassel"),
														   new Vertex("Hannover"),
														   new Vertex("Brema"),
														   new Vertex("Amburgo"),
														   new Vertex("Lubecca"),
														   new Vertex("Kiel"),
														   new Vertex("Rostock"),
														   new Vertex("Berlino", 4),
														   new Vertex("Magdeburgo"),
														   new Vertex("Dresda"),
														   new Vertex("Lipsia"),
														   new Vertex("Arthus"),
														   new Vertex("Innsbruck"),
														   new Vertex("Salisburgo"),
														   new Vertex("Linz"),
														   new Vertex("Vienna", 4),
														   new Vertex("Graz"),
														   new Vertex("Zagabria"),
														   new Vertex("Spalato"),
														   new Vertex("Sarajevo"),
														   new Vertex("Belgrado", 6),
														   new Vertex("Ragusa"),
														   new Vertex("Tirana", 6),
														   new Vertex("Skopje"),
														   new Vertex("Salonicco"),
														   new Vertex("Atene", 6),
														   new Vertex("Patrasso"),
														   new Vertex("Sofia", 6),
														   new Vertex("Varna"),
														   new Vertex("Edirne"),
														   new Vertex("Instanbul", 6),
														   new Vertex("Bucarest", 6),
														   new Vertex("Costanza"),
														   new Vertex("Cluj Napoca"),
														   new Vertex("Timisoara"),
														   new Vertex("Budapest", 4),
														   new Vertex("Miskolc"),
														   new Vertex("Plzen"),
														   new Vertex("Praga", 4),
														   new Vertex("Brno"),
														   new Vertex("Stettino"),
														   new Vertex("Poznan"),
														   new Vertex("Danzica"),
														   new Vertex("Varsavia", 4),
														   new Vertex("Lodz"),
														   new Vertex("Breslavia"),
														   new Vertex("Cracovia"),
														   new Vertex("Odessa"),
														   new Vertex("Cernovcy"),
														   new Vertex("Leopoli"),
														   new Vertex("Kiev"),
														   new Vertex("Harkov"),
														   new Vertex("Mosca", 2),
														   new Vertex("Smolensk"),
														   new Vertex("Minsk"),
														   new Vertex("Riga"),
														   new Vertex("Tallinn"),
														   new Vertex("Leningrado", 2),
														   new Vertex("Arcangelo"),
														   new Vertex("Helsinki", 2),
														   new Vertex("Tampere"),
														   new Vertex("Turku"),
														   new Vertex("Oulu"),
														   new Vertex("Rovaniemi", 2),
														   new Vertex("Lulea"),
														   new Vertex("Kiruna"),
														   new Vertex("Sundsvall"),
														   new Vertex("Uppsala"),
														   new Vertex("Stoccolma", 2),
														   new Vertex("Jonkoping"),
														   new Vertex("Goteborg"),
														   new Vertex("Malmoe"),
														   new Vertex("Copenaghen", 2),
														   new Vertex("Hammerfest"),
														   new Vertex("Narvik"),
														   new Vertex("Trondheim"),
														   new Vertex("Oslo", 2),
														   new Vertex("Bergen"),
														   new Vertex("Stavanger"),
														   new Vertex("Palma di Majorca", 5),
														   new Vertex("Ajaccio"),
														   new Vertex("Sassari"),
														   new Vertex("Cagliari"),
														   new Vertex("Candia"),
														   new Vertex("Dover"),
														   new Vertex("Londra", 3),
														   new Vertex("Plymouth"),
														   new Vertex("Bristol"),
														   new Vertex("Birmingham"),
														   new Vertex("Liverpool"),
														   new Vertex("Leeds"),
														   new Vertex("Newcastle"),
														   new Vertex("Glasgow", 1),
														   new Vertex("Aberdeen"),
														   new Vertex("Inverness"),
														   new Vertex("Belfast"),
														   new Vertex("Dublino", 1),
														   new Vertex("Cork"),
														   new Vertex("Isole Shetland"),
														   new Vertex("Faroer"),
														   new Vertex("Reykjavik", 1))));

		g.setEdges(new LinkedList<Edge>(Arrays.asList(// Archi via Terra.
													  new Edge(g.getVertices(), "La Coruna", "Porto", 1),
													  new Edge(g.getVertices(), "La Coruna", "Santander", 1),
													  new Edge(g.getVertices(), "La Coruna", "Valladolid", 1),
													  new Edge(g.getVertices(), "Porto", "Coimbra", 1),
													  new Edge(g.getVertices(), "Coimbra", "Lisbona", 1),
													  new Edge(g.getVertices(), "Lisbona", "Madrid", 2),
													  new Edge(g.getVertices(), "Lisbona", "Siviglia", 2),
													  new Edge(g.getVertices(), "Santander", "Valladolid", 1),
													  new Edge(g.getVertices(), "Santander", "San Sebastiano", 1),
													  new Edge(g.getVertices(), "Valladolid", "Madrid", 1),
													  new Edge(g.getVertices(), "Madrid", "Siviglia", 2),
													  new Edge(g.getVertices(), "Madrid", "Cordoba", 1),
													  new Edge(g.getVertices(), "Madrid", "Granada", 1),
													  new Edge(g.getVertices(), "Madrid", "Alicante", 1),
													  new Edge(g.getVertices(), "Madrid", "Valencia", 1),
													  new Edge(g.getVertices(), "Madrid", "Saragozza", 1),
													  new Edge(g.getVertices(), "Cordoba", "Siviglia", 1),
													  new Edge(g.getVertices(), "Cordoba", "Granada", 1),
													  new Edge(g.getVertices(), "Siviglia", "Cadice", 1),
													  new Edge(g.getVertices(), "Siviglia", "Malaga", 1),
													  new Edge(g.getVertices(), "Malaga", "Granada", 1),
													  new Edge(g.getVertices(), "Granada", "Alicante", 1),
													  new Edge(g.getVertices(), "Alicante", "Valencia", 1),
													  new Edge(g.getVertices(), "Valencia", "Barcellona", 1),
													  new Edge(g.getVertices(), "Barcellona", "Saragozza", 1),
													  new Edge(g.getVertices(), "Barcellona", "Montpellier", 1),
													  new Edge(g.getVertices(), "Saragozza", "San Sebastiano", 1),
													  new Edge(g.getVertices(), "San Sebastiano", "Tolosa", 1),
													  new Edge(g.getVertices(), "San Sebastiano", "Bordeaux", 1),
													  new Edge(g.getVertices(), "Montpellier", "Clemont-Ferrand", 1),
													  new Edge(g.getVertices(), "Montpellier", "Marsiglia", 1),
													  new Edge(g.getVertices(), "Montpellier", "Tolosa", 1),
													  new Edge(g.getVertices(), "Marsiglia", "Lione", 1),
													  new Edge(g.getVertices(), "Marsiglia", "Nizza", 1),
													  new Edge(g.getVertices(), "Lione", "Digione", 1),
													  new Edge(g.getVertices(), "Lione", "Clemont-Ferrand", 1),
													  new Edge(g.getVertices(), "Lione", "Torino", 1),
													  new Edge(g.getVertices(), "Lione", "Ginevra", 1),
													  new Edge(g.getVertices(), "Digione", "Nancy", 1),
													  new Edge(g.getVertices(), "Digione", "Parigi", 1),
													  new Edge(g.getVertices(), "Digione", "Basilea", 1),
													  new Edge(g.getVertices(), "Nancy", "Strasburgo", 1),
													  new Edge(g.getVertices(), "Nancy", "Reims", 1),
													  new Edge(g.getVertices(), "Strasburgo", "Basilea", 1),
													  new Edge(g.getVertices(), "Reims", "Parigi", 1),
													  new Edge(g.getVertices(), "Reims", "Bruxelles", 1),
													  new Edge(g.getVertices(), "Parigi", "Clemont-Ferrand", 1),
													  new Edge(g.getVertices(), "Parigi", "Orleans", 1),
													  new Edge(g.getVertices(), "Parigi", "Lilla", 1),
													  new Edge(g.getVertices(), "Parigi", "Calais", 1),
													  new Edge(g.getVertices(), "Parigi", "Le Havre", 1),
													  new Edge(g.getVertices(), "Parigi", "Brest", 2),
													  new Edge(g.getVertices(), "Parigi", "Nantes", 2),
													  new Edge(g.getVertices(), "Lilla", "Bruxelles", 1),
													  new Edge(g.getVertices(), "Lilla", "Calais", 1),
													  new Edge(g.getVertices(), "Brest", "Nantes", 2),
													  new Edge(g.getVertices(), "Nantes", "Bordeaux", 1),
													  new Edge(g.getVertices(), "Nantes", "Tours", 1),
													  new Edge(g.getVertices(), "Tours", "Orleans", 1),
													  new Edge(g.getVertices(), "Tours", "Bordeaux", 1),
													  new Edge(g.getVertices(), "Nancy", "Lussemburgo", 1),
													  new Edge(g.getVertices(), "Bordeaux", "Limoges", 1),
													  new Edge(g.getVertices(), "Bordeaux", "Tolosa", 1),
													  new Edge(g.getVertices(), "Limoges", "Orleans", 1),
													  new Edge(g.getVertices(), "Limoges", "Clemont-Ferrand", 1),
													  new Edge(g.getVertices(), "Nizza", "Genova", 1),
													  new Edge(g.getVertices(), "Torino", "Milano", 1),
													  new Edge(g.getVertices(), "Milano", "Genova", 1),
													  new Edge(g.getVertices(), "Milano", "Bologna", 1),
													  new Edge(g.getVertices(), "Milano", "Zurigo", 1),
													  new Edge(g.getVertices(), "Bologna", "Bolzano", 1),
													  new Edge(g.getVertices(), "Bologna", "Venezia", 1),
													  new Edge(g.getVertices(), "Bologna", "Ancona", 1),
													  new Edge(g.getVertices(), "Bologna", "Firenze", 1),
													  new Edge(g.getVertices(), "Venezia", "Trieste", 1),
													  new Edge(g.getVertices(), "Genova", "Pisa", 1),
													  new Edge(g.getVertices(), "Pisa", "Firenze", 1),
													  new Edge(g.getVertices(), "Ancona", "Pescara", 1),
													  new Edge(g.getVertices(), "Pescara", "Bari", 1),
													  new Edge(g.getVertices(), "Roma", "Pisa", 1),
													  new Edge(g.getVertices(), "Roma", "Firenze", 1),
													  new Edge(g.getVertices(), "Roma", "Pescara", 1),
													  new Edge(g.getVertices(), "Roma", "Ancona", 1),
													  new Edge(g.getVertices(), "Roma", "Napoli", 1),
													  new Edge(g.getVertices(), "Napoli", "Bari", 1),
													  new Edge(g.getVertices(), "Napoli", "Messina", 1),
													  new Edge(g.getVertices(), "Messina", "Palermo", 1),
													  new Edge(g.getVertices(), "Ginevra", "Berna", 1),
													  new Edge(g.getVertices(), "Berna", "Basilea", 1),
													  new Edge(g.getVertices(), "Basilea", "Zurigo", 1),
													  new Edge(g.getVertices(), "Bruxelles", "Lussemburgo", 1),
													  new Edge(g.getVertices(), "Bruxelles", "Rotterdam", 1),
													  new Edge(g.getVertices(), "Rotterdam", "Amsterdam", 1),
													  new Edge(g.getVertices(), "Amsterdam", "Groninga", 1),
													  new Edge(g.getVertices(), "Groninga", "Brema", 1),
													  new Edge(g.getVertices(), "Bruxelles", "Colonia", 1),
													  new Edge(g.getVertices(), "Lussemburgo", "Colonia", 1),
													  new Edge(g.getVertices(), "Strasburgo", "Francoforte", 1),
													  new Edge(g.getVertices(), "Amsterdam", "Colonia", 1),
													  new Edge(g.getVertices(), "Colonia", "Brema", 1),
													  new Edge(g.getVertices(), "Colonia", "Kassel", 1),
													  new Edge(g.getVertices(), "Colonia", "Francoforte", 1),
													  new Edge(g.getVertices(), "Francoforte", "Stoccarda", 1),
													  new Edge(g.getVertices(), "Francoforte", "Norimberga", 1),
													  new Edge(g.getVertices(), "Francoforte", "Kassel", 1),
													  new Edge(g.getVertices(), "Stoccarda", "Strasburgo", 1),
													  new Edge(g.getVertices(), "Stoccarda", "Monaco di Baviera", 1),
													  new Edge(g.getVertices(), "Monaco di Baviera", "Norimberga", 1),
													  new Edge(g.getVertices(), "Norimberga", "Kassel", 1),
													  new Edge(g.getVertices(), "Norimberga", "Lipsia", 1),
													  new Edge(g.getVertices(), "Lipsia", "Kassel", 1),
													  new Edge(g.getVertices(), "Lipsia", "Dresda", 1),
													  new Edge(g.getVertices(), "Dresda", "Berlino", 1),
													  new Edge(g.getVertices(), "Berlino", "Rostock", 1),
													  new Edge(g.getVertices(), "Berlino", "Amburgo", 1),
													  new Edge(g.getVertices(), "Berlino", "Magdeburgo", 1),
													  new Edge(g.getVertices(), "Magdeburgo", "Hannover", 1),
													  new Edge(g.getVertices(), "Hannover", "Kassel", 1),
													  new Edge(g.getVertices(), "Hannover", "Brema", 1),
													  new Edge(g.getVertices(), "Hannover", "Amburgo", 1),
													  new Edge(g.getVertices(), "Brema", "Amburgo", 1),
													  new Edge(g.getVertices(), "Amburgo", "Lubecca", 1),
													  new Edge(g.getVertices(), "Amburgo", "Kiel", 1),
													  new Edge(g.getVertices(), "Kiel", "Arthus", 1),
													  new Edge(g.getVertices(), "Innsbruck", "Zurigo", 1),
													  new Edge(g.getVertices(), "Innsbruck", "Bolzano", 1),
													  new Edge(g.getVertices(), "Innsbruck", "Monaco di Baviera", 1),
													  new Edge(g.getVertices(), "Innsbruck", "Salisburgo", 1),
													  new Edge(g.getVertices(), "Salisburgo", "Monaco di Baviera", 1),
													  new Edge(g.getVertices(), "Salisburgo", "Linz", 1),
													  new Edge(g.getVertices(), "Linz", "Norimberga", 1),
													  new Edge(g.getVertices(), "Linz", "Vienna", 1),
													  new Edge(g.getVertices(), "Vienna", "Graz", 1),
													  new Edge(g.getVertices(), "Zagabria", "Trieste", 1),
													  new Edge(g.getVertices(), "Zagabria", "Salisburgo", 1),
													  new Edge(g.getVertices(), "Zagabria", "Graz", 1),
													  new Edge(g.getVertices(), "Zagabria", "Sarajevo", 1),
													  new Edge(g.getVertices(), "Zagabria", "Spalato", 1),
													  new Edge(g.getVertices(), "Zagabria", "Belgrado", 1),
													  new Edge(g.getVertices(), "Trieste", "Spalato", 1),
													  new Edge(g.getVertices(), "Spalato", "Ragusa", 1),
													  new Edge(g.getVertices(), "Ragusa", "Sarajevo", 1),
													  new Edge(g.getVertices(), "Ragusa", "Tirana", 1),
													  new Edge(g.getVertices(), "Tirana", "Salonicco", 1),
													  new Edge(g.getVertices(), "Salonicco", "Skopje", 1),
													  new Edge(g.getVertices(), "Salonicco", "Atene", 1),
													  new Edge(g.getVertices(), "Atene", "Patrasso", 1),
													  new Edge(g.getVertices(), "Sofia", "Belgrado", 1),
													  new Edge(g.getVertices(), "Sofia", "Skopje", 1),
													  new Edge(g.getVertices(), "Sofia", "Varna", 1),
													  new Edge(g.getVertices(), "Edirne", "Salonicco", 1),
													  new Edge(g.getVertices(), "Edirne", "Sofia", 1),
													  new Edge(g.getVertices(), "Edirne", "Instanbul", 1),
													  new Edge(g.getVertices(), "Bucarest", "Sofia", 1),
													  new Edge(g.getVertices(), "Bucarest", "Costanza", 1),
													  new Edge(g.getVertices(), "Bucarest", "Varna", 1),
													  new Edge(g.getVertices(), "Bucarest", "Cluj Napoca", 1),
													  new Edge(g.getVertices(), "Cluj Napoca", "Timisoara", 1),
													  new Edge(g.getVertices(), "Timisoara", "Belgrado", 1),
													  new Edge(g.getVertices(), "Budapest", "Belgrado", 1),
													  new Edge(g.getVertices(), "Budapest", "Zagabria", 1),
													  new Edge(g.getVertices(), "Budapest", "Graz", 1),
													  new Edge(g.getVertices(), "Budapest", "Vienna", 1),
													  new Edge(g.getVertices(), "Budapest", "Miskolc", 1),
													  new Edge(g.getVertices(), "Budapest", "Cluj Napoca", 1),
													  new Edge(g.getVertices(), "Plzen", "Norimberga", 1),
													  new Edge(g.getVertices(), "Plzen", "Praga", 1),
													  new Edge(g.getVertices(), "Praga", "Dresda", 1),
													  new Edge(g.getVertices(), "Praga", "Brno", 1),
													  new Edge(g.getVertices(), "Praga", "Vienna", 1),
													  new Edge(g.getVertices(), "Praga", "Linz", 1),
													  new Edge(g.getVertices(), "Brno", "Vienna", 1),
													  new Edge(g.getVertices(), "Brno", "Miskolc", 1),
													  new Edge(g.getVertices(), "Stettino", "Rostock", 1),
													  new Edge(g.getVertices(), "Stettino", "Poznan", 1),
													  new Edge(g.getVertices(), "Poznan", "Berlino", 1),
													  new Edge(g.getVertices(), "Poznan", "Breslavia", 1),
													  new Edge(g.getVertices(), "Poznan", "Danzica", 1),
													  new Edge(g.getVertices(), "Poznan", "Lodz", 1),
													  new Edge(g.getVertices(), "Breslavia", "Dresda", 1),
													  new Edge(g.getVertices(), "Breslavia", "Praga", 1),
													  new Edge(g.getVertices(), "Breslavia", "Brno", 1),
													  new Edge(g.getVertices(), "Breslavia", "Cracovia", 1),
													  new Edge(g.getVertices(), "Breslavia", "Lodz", 1),
													  new Edge(g.getVertices(), "Danzica", "Varsavia", 1),
													  new Edge(g.getVertices(), "Varsavia", "Lodz", 1),
													  new Edge(g.getVertices(), "Cracovia", "Varsavia", 1),
													  new Edge(g.getVertices(), "Cracovia", "Brno", 1),
													  new Edge(g.getVertices(), "Odessa", "Leopoli", 2),
													  new Edge(g.getVertices(), "Odessa", "Kiev", 2),
													  new Edge(g.getVertices(), "Odessa", "Harkov", 1),
													  new Edge(g.getVertices(), "Leopoli", "Kiev", 2),
													  new Edge(g.getVertices(), "Leopoli", "Cracovia", 1),
													  new Edge(g.getVertices(), "Leopoli", "Miskolc", 1),
													  new Edge(g.getVertices(), "Leopoli", "Cernovcy", 1),
													  new Edge(g.getVertices(), "Kiev", "Harkov", 1),
													  new Edge(g.getVertices(), "Kiev", "Smolensk", 1),
													  new Edge(g.getVertices(), "Harkov", "Mosca", 1),
													  new Edge(g.getVertices(), "Mosca", "Arcangelo", 1),
													  new Edge(g.getVertices(), "Mosca", "Leningrado", 1),
													  new Edge(g.getVertices(), "Mosca", "Smolensk", 1),
													  new Edge(g.getVertices(), "Leningrado", "Arcangelo", 1),
													  new Edge(g.getVertices(), "Leningrado", "Tallinn", 1),
													  new Edge(g.getVertices(), "Leningrado", "Smolensk", 1),
													  new Edge(g.getVertices(), "Leningrado", "Helsinki", 1),
													  new Edge(g.getVertices(), "Tallinn", "Riga", 1),
													  new Edge(g.getVertices(), "Riga", "Varsavia", 1),
													  new Edge(g.getVertices(), "Riga", "Smolensk", 2),
													  new Edge(g.getVertices(), "Riga", "Leningrado", 2),
													  new Edge(g.getVertices(), "Minsk", "Varsavia", 1),
													  new Edge(g.getVertices(), "Helsinki", "Tampere", 1),
													  new Edge(g.getVertices(), "Helsinki", "Turku", 1),
													  new Edge(g.getVertices(), "Turku", "Tampere", 1),
													  new Edge(g.getVertices(), "Turku", "Oulu", 1),
													  new Edge(g.getVertices(), "Oulu", "Rovaniemi", 1),
													  new Edge(g.getVertices(), "Rovaniemi", "Lulea", 1),
													  new Edge(g.getVertices(), "Lulea", "Kiruna", 1),
													  new Edge(g.getVertices(), "Lulea", "Sundsvall", 1),
													  new Edge(g.getVertices(), "Sundsvall", "Uppsala", 1),
													  new Edge(g.getVertices(), "Uppsala", "Stoccolma", 1),
													  new Edge(g.getVertices(), "Stoccolma", "Jonkoping", 1),
													  new Edge(g.getVertices(), "Jonkoping", "Goteborg", 1),
													  new Edge(g.getVertices(), "Jonkoping", "Malmoe", 1),
													  new Edge(g.getVertices(), "Malmoe", "Goteborg", 1),
													  new Edge(g.getVertices(), "Malmoe", "Copenaghen", 1),
													  new Edge(g.getVertices(), "Hammerfest", "Rovaniemi", 1),
													  new Edge(g.getVertices(), "Narvik", "Kiruna", 1),
													  new Edge(g.getVertices(), "Trondheim", "Sundsvall", 1),
													  new Edge(g.getVertices(), "Trondheim", "Oslo", 1),
													  new Edge(g.getVertices(), "Oslo", "Stoccolma", 1),
													  new Edge(g.getVertices(), "Oslo", "Goteborg", 1),
													  new Edge(g.getVertices(), "Oslo", "Bergen", 1),
													  new Edge(g.getVertices(), "Oslo", "Stavanger", 1),
													  new Edge(g.getVertices(), "Londra", "Dover", 1),
													  new Edge(g.getVertices(), "Londra", "Plymouth", 1),
													  new Edge(g.getVertices(), "Londra", "Bristol", 1),
													  new Edge(g.getVertices(), "Londra", "Birmingham", 1),
													  new Edge(g.getVertices(), "Londra", "Leeds", 1),
													  new Edge(g.getVertices(), "Bristol", "Birmingham", 1),
													  new Edge(g.getVertices(), "Birmingham", "Liverpool", 1),
													  new Edge(g.getVertices(), "Liverpool", "Leeds", 1),
													  new Edge(g.getVertices(), "Liverpool", "Glasgow", 1),
													  new Edge(g.getVertices(), "Leeds", "Newcastle", 1),
													  new Edge(g.getVertices(), "Newcastle", "Glasgow", 1),
													  new Edge(g.getVertices(), "Glasgow", "Aberdeen", 1),
													  new Edge(g.getVertices(), "Glasgow", "Inverness", 1),
													  new Edge(g.getVertices(), "Aberdeen", "Inverness", 1),
													  new Edge(g.getVertices(), "Belfast", "Dublino", 1),
													  new Edge(g.getVertices(), "Dublino", "Cork", 1),
													  // Archi via Mare.
													  new Edge(g.getVertices(), "Porto", "Bordeaux", 1),
													  new Edge(g.getVertices(), "Porto", "Lisbona", 1),
													  new Edge(g.getVertices(), "Lisbona", "Cadice", 1),
													  new Edge(g.getVertices(), "Cadice", "Malaga", 1),
													  new Edge(g.getVertices(), "Malaga", "Valencia", 1),
													  new Edge(g.getVertices(), "Valencia", "Palma di Majorca", 1),
													  new Edge(g.getVertices(), "Palma di Majorca", "Barcellona", 1),
													  new Edge(g.getVertices(), "Barcellona", "Ajaccio", 1),
													  new Edge(g.getVertices(), "Barcellona", "Marsiglia", 1),
													  new Edge(g.getVertices(), "Marsiglia", "Ajaccio", 1),
													  new Edge(g.getVertices(), "Marsiglia", "Genova", 1),
													  new Edge(g.getVertices(), "Genova", "Napoli", 1),
													  new Edge(g.getVertices(), "Napoli", "Ajaccio", 1),
													  new Edge(g.getVertices(), "Napoli", "Cagliari", 1),
													  new Edge(g.getVertices(), "Ajaccio", "Sassari", 1),
													  new Edge(g.getVertices(), "Cagliari", "Palermo", 1),
													  new Edge(g.getVertices(), "Ancona", "Spalato", 1),
													  new Edge(g.getVertices(), "Ancona", "Patrasso", 1),
													  new Edge(g.getVertices(), "Bari", "Atene", 1),
													  new Edge(g.getVertices(), "Atene", "Candia", 1),
													  new Edge(g.getVertices(), "Atene", "Instanbul", 1),
													  new Edge(g.getVertices(), "Instanbul", "Varna", 1),
													  new Edge(g.getVertices(), "Varna", "Costanza", 1),
													  new Edge(g.getVertices(), "Costanza", "Odessa", 1),
													  new Edge(g.getVertices(), "Arcangelo", "Hammerfest", 1),
													  new Edge(g.getVertices(), "Hammerfest", "Narvik", 1),
													  new Edge(g.getVertices(), "Narvik", "Trondheim", 1),
													  new Edge(g.getVertices(), "Trondheim", "Bergen", 1),
													  new Edge(g.getVertices(), "Bergen", "Stavanger", 1),
													  new Edge(g.getVertices(), "Stoccolma", "Helsinki", 1),
													  new Edge(g.getVertices(), "Stoccolma", "Danzica", 1),
													  new Edge(g.getVertices(), "Stoccolma", "Lubecca", 1),
													  new Edge(g.getVertices(), "Lubecca", "Copenaghen", 1),
													  new Edge(g.getVertices(), "Copenaghen", "Arthus", 1),
													  new Edge(g.getVertices(), "Arthus", "Oslo", 1),
													  new Edge(g.getVertices(), "Le Havre", "Brest", 1),
													  new Edge(g.getVertices(), "Brest", "Bordeaux", 1),
													  new Edge(g.getVertices(), "Plymouth", "Porto", 1),
													  new Edge(g.getVertices(), "Plymouth", "Le Havre", 1),
													  new Edge(g.getVertices(), "Dover", "Calais", 1),
													  new Edge(g.getVertices(), "Londra", "Rotterdam", 1),
													  new Edge(g.getVertices(), "Londra", "Oslo", 1),
													  new Edge(g.getVertices(), "Liverpool", "Dublino", 1),
													  new Edge(g.getVertices(), "Glasgow", "Belfast", 1),
													  new Edge(g.getVertices(), "Newcastle", "Amburgo", 1),
													  new Edge(g.getVertices(), "Newcastle", "Bergen", 1),
													  new Edge(g.getVertices(), "Aberdeen", "Isole Shetland", 1),
													  new Edge(g.getVertices(), "Isole Shetland", "Faroer", 1),
													  new Edge(g.getVertices(), "Faroer", "Reykjavik", 1),
													  new Edge(g.getVertices(), "Faroer", "Copenaghen", 1))));

		Vertex s = g.getVertexByName(args[0]);
		LinkedList<Vertex> arguments = new LinkedList<Vertex>();
		for (int i = 1; i < args.length; i++) arguments.add(g.getVertexByName(args[i]));

		playGame(g, s, arguments);
	}

}