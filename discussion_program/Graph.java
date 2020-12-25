import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

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

	public void add_undirected_edge(int x, int y){
		add_directed_edge(x, y);
		add_directed_edge(y, x);
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

	public boolean is_connected(int x, int y){
		return edges[x].contains(y);
	}


	public static void main(String[] args){
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
		
	}
}
