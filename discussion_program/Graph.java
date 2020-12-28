import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Queue;

public class Graph{
	private int number;
	private List[] edges;
	
	private Set<Edge> tree_edges;
	private Set<Edge> forward_edges;
	private Set<Edge> cross_edges;
	private Set<Edge> back_edges;

	private Set<Edge> edge_set;

	private int[] pre_numbers;
	private int[] post_numbers;

	private int index;
	private boolean[] visited;

	private Queue<Integer> bfs_queue;


	public Graph(int n){
		number = n;
		edges = new LinkedList[n];
		for(int i = 0; i < n; i++){
			edges[i] = new LinkedList<Integer>();
		}
		visited = new boolean[n];
		pre_numbers = new int[n];
		post_numbers = new int[n];
		edge_set = new HashSet<>();
		bfs_queue = new LinkedList<>();
		refresh();
	}

	private void refresh(){
		index = 1;
		tree_edges = new HashSet<>();
		forward_edges = new HashSet<>();
		cross_edges = new HashSet<>();
		back_edges = new HashSet<>();
		for(int i = 0; i < number; i++){
			visited[i] = false;
		}
	}

	public List<Integer> neighbors(int x){
		return edges[x];
	}

	private boolean is_tree_edges(Edge edge){
		for(Edge tree_edge: tree_edges){
			if(edge.equals(tree_edge)){
				return true;
			}
		}
		return false;
	}

	private void fill_edges(){
		for(Edge edge: edge_set){
			if(is_tree_edges(edge)){
				continue;
			}

			int start = edge.start();
			int end = edge.end();
			if(pre_numbers[start] < pre_numbers[end] && post_numbers[start] > post_numbers[end]){
				forward_edges.add(edge);
				continue;
			}

			if(pre_numbers[start] > pre_numbers[end] && post_numbers[start] < post_numbers[end]){
				back_edges.add(edge);
				continue;
			}

			if(post_numbers[end] < pre_numbers[start]){
				cross_edges.add(edge);
			}


		}
	}

	private void dfs(){
		refresh();

		/** in case that the graph is unconnected */
		for(int i = 0; i < number; i++){
			if(!visited[i]){
				explore(i);
			}
		}

		// using pre and post numbers to diverse edges
		fill_edges();
	}

	private void explore(int x){
		visited[x] = true;
		pre_numbers[x] = index;
		index += 1;

		for(Integer neighbor: neighbors(x)){
			if(!visited[neighbor]){
				tree_edges.add(new Edge(x, neighbor));
				explore(neighbor);
			}
		}
		post_numbers[x] = index;
		index += 1;
	}

	public int[] pre_numbers(){
		return pre_numbers;
	}

	public int[] post_numbers(){
		return post_numbers;
	}


	public void add_directed_edge(int x, int y){
		if(edges[x].contains(y)){
			return;
		}
		edges[x].add(y);
		edge_set.add(new Edge(x, y));
		dfs();
	}

	public void add_directed_edge(int x, int y, int length){
		if(edges[x].contains(y)){
			return;
		}
		edges[x].add(y);
		edge_set.add(new Edge(x, y, length));
		dfs();
	}

	public void add_undirected_edge(int x, int y){
		add_directed_edge(x, y);
		add_directed_edge(y, x);
		dfs();
	}

	public void add_undirected_edge(int x, int y, int length){
		add_directed_edge(x, y, length);
		add_directed_edge(y, x, length);
		dfs();
	}

	public Set<Edge> back_edges(){
		return back_edges;
	}

	public Set<Edge> cross_edges(){
		return cross_edges;
	}

	public Set<Edge> forward_edges(){
		return forward_edges;
	}

	public Set<Edge> tree_edges(){
		return tree_edges;
	}

	public boolean is_neighbour(int x, int y){
		return edges[x].contains(y);
	}

	public boolean is_strong_connected(int x, int y){
		return is_connected(x, y) && is_connected(y, x);
	}

	public boolean is_connected(int x, int y){
		if(x == y){
			return true;
		}
		
		for(Integer neighbor: neighbors(x)){
			if(is_connected(neighbor, y)){
				return true;
			}
		}
		return false;
	}

	public boolean is_acyclic(){
		return back_edges.isEmpty();
	}

	public int[] Topological_sort(){
		return descending_order(post_numbers);
	}

	private int[] descending_order(int[] x){
		int[] result = new int[x.length];
		int index = 0;

		int[] copy = new int[x.length];
		System.arraycopy(x, 0, copy, 0, x.length);
		Arrays.sort(copy);
		for(int i = x.length - 1; i >= 0; i--){
			result[index] = index(x, copy[i]);
			index += 1;
		}

		return result;
	}

	private int index(int[] x, int num){
		for(int i = 0; i < x.length; i++){
			if(x[i] == num){
				return i;
			}
		}
		return -1;
	}

	public Graph reverse(){
		Graph result = new Graph(number);
		for(Edge edge: edge_set){
			int start = edge.start();
			int end = edge.end();
			result.add_directed_edge(end, start);
		}
		return result;
	}

	public int[] scc(){
		int scc = 0;
		int[] result = new int[number];
		for(int i = 0; i < number; i++){
			result[i] = 1000;
		}

		Graph reverse = reverse();
		int[] order = reverse.Topological_sort();
		refresh();
		for(int i = 0; i < number; i++){
			if(!visited[order[i]]){
				explore(order[i], result, scc);
				scc += 1;
			}
		}
		return result;
	}

	public Set<Integer> scc_members(int x){
		int[] scc = scc();
		Set<Integer> result = new HashSet<>();
		for(int i = 0; i < number; i++){
			if(scc[x] == scc[i]){
				result.add(i);
			}
		}
		return result;
	}

	private void explore(int x, int[] result, int scc){
		result[x] = scc;
		visited[x] = true;

		for(Integer neighbor: neighbors(x)){
			if(!visited[neighbor]){
				explore(neighbor, result, scc);
			}
		}
	}


	public int[] bfs(int start){
		bfs_queue = new LinkedList<>();
		int[] dist = new int[number];
		for(int i = 0; i < number; i++){
			dist[i] = 999999;
		}
		dist[start] = 0;

		bfs_queue.offer(start);
		while(!bfs_queue.isEmpty()){
			int current = bfs_queue.poll();
			for(Integer neighbor: neighbors(current)){
				if(dist[neighbor] == 999999){
					dist[neighbor] = dist[current] + 1;
					bfs_queue.offer(neighbor);
				}
			}
		}

		return dist;
	}

	public int[] weighted_dist_bfs(int start){
		Graph g_prime = prime();
		int[] dist = g_prime.bfs(start);

		int[] result = new int[number];
		System.arraycopy(dist, 0, result, 0, number);
		return result;
	}

	public Set<Edge> edge_set(){
		return edge_set;
	}

	private Graph prime(){
		int sum_weighted = 0;
		int edge_number = 0;
		for(Edge edge: edge_set){
			sum_weighted += edge.length();
			edge_number += 1;
		}
		Graph prime  = new Graph(number + sum_weighted - edge_number);
		int vertex = number;
		for(Edge edge: edge_set){
			int start = edge.start();
			int end = edge.end();
			if(edge.length() == 1){
				prime.add_directed_edge(start, end);
			}

			else{
				int[] vertices = new int[edge.length() + 1];
				vertices[0] = start;
				
				for(int index = 1; index < edge.length(); index++){
					vertices[index] = vertex;
					vertex += 1;
				}

				vertices[edge.length()] = end;

				for(int i = 0; i < vertices.length - 1; i++){
					prime.add_directed_edge(vertices[i], vertices[i + 1]);
				}
			}
		}

		return prime;
	}


	public static void main(String[] args){
		/**
		Graph g = new Graph(8);
		
		g.add_directed_edge(0, 1);
		g.add_directed_edge(0, 2);
		g.add_directed_edge(2, 3);
		g.add_directed_edge(1, 4);
		g.add_directed_edge(4, 5);
		g.add_directed_edge(5, 6);
		g.add_directed_edge(4, 7);

		g.add_directed_edge(0, 5);
		g.add_directed_edge(5, 1);
		g.add_directed_edge(4, 6);
		g.add_directed_edge(7, 6);
		g.add_directed_edge(3, 0);
		g.add_directed_edge(3, 7);

		int[] pre = g.pre_numbers();
		int[] post = g.post_numbers();

		for(int i = 0; i < 8; i++){
			System.out.println(i + ":  pre: " + pre[i] + " post: " + post[i]);
		}

		System.out.println("forward:");
		for(Edge edge: g.forward_edges()){

			edge.print();
		}

		System.out.println("tree:");

		for(Edge edge: g.tree_edges()){
			edge.print();
		}

		System.out.println("back:");

		for(Edge edge: g.back_edges()){
			edge.print();
		}

		System.out.println("cross:");

		for(Edge edge: g.cross_edges()){
			edge.print();
		}
		
		Graph g2 = new Graph(3);
		g2.add_directed_edge(0, 1);
		g2.add_directed_edge(0, 2);
		g2.add_directed_edge(2, 1);
		for(int i = 0; i < 3; i++){
			System.out.println(g2.Topological_sort()[i]);
		}
		*/

		/**
		Graph g = new Graph(6);
		g.add_directed_edge(0, 1);
		g.add_directed_edge(1, 2);
		g.add_directed_edge(2, 3);
		g.add_directed_edge(3, 4);
		g.add_directed_edge(4, 2);
		g.add_directed_edge(2, 5);
		g.add_directed_edge(5, 4);

		int[] scc = g.scc();
		for(int i = 0; i < 6; i++){
			System.out.println(scc[i]);
		}
		 */

/**
		Graph g = new Graph(12);
		g.add_directed_edge(0, 1);
		g.add_directed_edge(1, 2);
		g.add_directed_edge(1, 3);
		g.add_undirected_edge(1, 4);
		g.add_undirected_edge(2, 5);
		g.add_directed_edge(4, 5);
		g.add_directed_edge(4, 6);
		g.add_directed_edge(5, 7);
		g.add_directed_edge(6, 7);
		g.add_directed_edge(8, 6);
		g.add_directed_edge(9, 8);
		g.add_directed_edge(11, 9);
		g.add_directed_edge(6, 9);
		g.add_directed_edge(10, 11);
		g.add_directed_edge(7, 10);


		int[] scc = g.scc();
		for(int i = 0; i < 12; i++){
			System.out.println(scc[i]);
		}

		System.out.println();

		System.out.println(g.scc_members(2));
		System.out.println(g.scc_members(7));
		*/

/**
		Graph g = new Graph(5);
		g.add_undirected_edge(0, 1);
		g.add_undirected_edge(0, 3);
		g.add_undirected_edge(1, 2);
		g.add_undirected_edge(1, 3);
		g.add_undirected_edge(2, 3);
		g.add_undirected_edge(3, 4);

		int[] dist = g.bfs(0);
		for(int i = 0; i < 5; i++){
			System.out.println(dist[i]);
		}
		*/

	Graph g = new Graph(10);
	g.add_directed_edge(0, 1);
	g.add_directed_edge(0, 4);
	g.add_directed_edge(2, 0);
	g.add_directed_edge(7, 0);
	g.add_directed_edge(6, 1);
	g.add_directed_edge(8, 4);
	g.add_directed_edge(7, 8);
	g.add_directed_edge(6, 7);
	g.add_directed_edge(5, 7);
	g.add_directed_edge(5, 3);
	g.add_directed_edge(9, 5);
	g.add_directed_edge(2, 9);
	g.add_directed_edge(3, 2);
	g.add_directed_edge(8, 6);

	int[] pre = g.pre_numbers();
	int[] post = g.post_numbers();

	for(int i = 0; i < 10; i++){
		System.out.println(i + ": " + pre[i] + " , " + post[i]);
	}

	System.out.println();
	int[] scc = g.scc();
	for(int i = 0; i < 10; i++){
		System.out.println(scc[i]);
	}




	}
}
