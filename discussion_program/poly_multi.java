public class poly_multi{
	private double[] poly_a;
	private double[] poly_b;
	private int dimension;

	public poly_multi(double[] a, double[] b){
		poly_a = a;
		poly_b = b;
		dimension = a.length;
	}

	public double[] result(){
		double[] result = new double[2 * dimension - 1];
		for(int i = 0; i < 2 * dimension - 1; i++){
			double sum = 0;
			for(int j = 0; j <= i; j++){
				int k = i - j;
				// if k, j >= dimension, it is zero
				if(k < dimension && j < dimension){
					sum += poly_a[j] * poly_b[k];
				}
			}
			result[i] = sum;
		}
		return result;
	}

	public void print(){
		double[] result = result();
		for(int i = 0; i < result.length; i++){
			System.out.print(result[i] + ", ");
		}
		System.out.println();
	}

	public static void main(String[] args){
		poly_multi p = new poly_multi(new double[]{1, 2, 4}, new double[]{2, 4, 5});
		p.print();

	}

}