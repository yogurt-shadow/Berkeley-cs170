public class poly_multi{
	private double[] poly_a;
	private double[] poly_b;
	private int dimension;

	public poly_multi(double[] a, double[] b){
		poly_a = a;
		poly_b = b;
		dimension = a.length;
	}

	public double[] result1(){
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

	public double[] result2(){
		FFT fft_a = new FFT(poly_a);
		FFT fft_b = new FFT(poly_b);
		complex_number[] result_a = fft_a.result();
		complex_number[] result_b = fft_b.result();
		complex_number[] product = new complex_number[dimension];
		for(int i = 0; i < dimension; i++){
			product[i] = result_a[i].times(result_b[i]);
		}

		IFFT ifft = new IFFT(product);
		return ifft.result();
	}

	public void print1(){
		double[] result1 = result1();
		for(int i = 0; i < result1.length; i++){
			System.out.print(result1[i] + ", ");
		}
		System.out.println();
	}

	public void print2(){
		double[] result2 = result2();
		for(int i = 0; i < result2.length; i++){
			System.out.print(result2[i] + ", ");
		}
		System.out.println();
	}

	public static void main(String[] args){
		poly_multi p = new poly_multi(new double[]{1, 1, 2, 0}, new double[]{2, 3, 0, 0});
		p.print2();

	}

}