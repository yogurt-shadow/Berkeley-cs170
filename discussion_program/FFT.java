public class FFT{
	private double[] poly;
	private int degree;
	private complex_number[] omega;

	public FFT(double[] poly, int degree){
		if(poly.length != degree){
			System.out.println("degree is wrong. Please have a check");
		}
		else{
			this.poly = poly;
			this.degree = degree;
			this.omega = new complex_number[degree];
			for(int i = 0; i < degree; i++){
				complex_number cc = new complex_number(1, Math.PI * 2 / degree * i, "polar");
				omega[i] = cc;
			}
		}
	}

	public FFT(double[] poly){
		this.degree = poly.length;
		this.poly = poly;
		this.omega = new complex_number[degree];
		for(int i = 0; i < degree; i++){
			complex_number cc = new complex_number(1, Math.PI * 2 / degree * i, "polar");
			omega[i] = cc;
		}
	}

	public complex_number[] result(){
		if(degree == 1){
			double x = poly[0];
			complex_number[] result = new complex_number[1];
			complex_number a;
			if(x == 0) {
				 a = new complex_number(1, 1, "zero");
			}
			else {
				 a = new complex_number(x, 0, "rect");
			}
			result[0] = a;
			return result;
		}
		else{
			double[] even = new double[(degree + 1) / 2];
			double[] odd = new double[degree / 2];
			int even_index = 0;
			for(int i = 0; i < degree; i += 2){
				even[even_index] = poly[i];
				even_index += 1;
			}

			int odd_index = 0;
			for(int j = 1; j < degree; j += 2){
				odd[odd_index] = poly[j];
				odd_index += 1;
			}

			FFT odd_fft = new FFT(odd);
			FFT even_fft = new FFT(even);

			complex_number[] odd_result = odd_fft.result();
			complex_number[] even_result = even_fft.result();

			complex_number[] result = new complex_number[degree];
			for(int i = 0; i < degree; i++){
				int index;
				if(i >= degree / 2){
					index = i - degree / 2;
				}
				else{
					index = i;
				}

				complex_number current = even_result[index].add(odd_result[index].times(omega[i]));
				result[i] = current;
			}
			return result;

		}
	}

	public void print_result(){
		complex_number[] result = result();
		System.out.println(degree + " numbers below:");
		for(int i = 0; i < result.length; i++){
			result[i].print();
		}
		System.out.println();
	}

	public static void main(String[] args){

		FFT fft1 = new FFT(new double[]{1, 4});
		fft1.print_result();

		FFT fft2 = new FFT(new double[]{3, 2});
		fft2.print_result();

		FFT fft3 = new FFT(new double[]{1, 3, 4, 2});
		fft3.print_result();

		FFT fft4 = new FFT(new double[]{1, 0, 0, 0});
		fft4.print_result();
	}
}