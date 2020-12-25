public class Edge{
	private int start;
	private int end;
	private int length;

	public int start(){
		return start;
	}

	public int end(){
		return end;
	}

	public int length(){
		return length;
	}

	public Edge(int x, int y){
		start = x;
		end = y;
		length = 1;
	}

	public Edge(int x, int y, int length){
		start = x;
		end = y;
		this.length = length;
	}

	public void print(){
		System.out.println(" start: " + start + " end: " + end + " length: " + length);
	}

	public boolean equals(Edge edge){
		return start == edge.start() && end == edge.end() && length == edge.length();
	}

	public static void main(String[] args){
		Edge edge1 = new Edge(1, 2);
		Edge edge2 = new Edge(1, 2);

		System.out.println(edge1.equals(edge2));
	}
}