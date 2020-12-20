public class IFFT{
	private complex_number[] input;
	private int degree;
	private complex_number[][] matrix;

	public IFFT(complex_number[] input){
		this.input = input;
		degree = input.length;
		matrix = matrix_generator(degree);
	}

	private complex_number[][] matrix_generator(int x){
		complex_number[][] result = new complex_number[x][x];
		for(int i = 0; i < x; i++){
			complex_number current = new complex_number(1, Math.PI * 2/ degree * i, "polar");
			for(int j = 0; j < x; j++){
				complex_number this_one = current.pow(j);
				result[i][j] = this_one;
			}
		}
		return conjugate(result);
	}

	private complex_number[][] conjugate(complex_number[][] input){
		complex_number[][] result = new complex_number[input.length][input.length];
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < input.length; j++){
				result[i][j] = input[i][j].conjugate();
			}
		}
		return result;
	}

	private void print_matrix(){

		for(int i = 0; i < degree; i++){
			System.out.println();
			for(int j = 0; j < degree; j++){
				matrix[i][j].print();
			}
		}
	}

	public double[] result(){
		double[] result = new double[degree];
		for(int i = 0; i < degree; i++){
			complex_number current = new complex_number(1, 1, "zero");
			for(int j = 0; j < degree; j++){
				complex_number one = matrix[i][j].times(input[j]);
				current = current.add(one);
			}
			result[i] = current.x() / degree;
		}
		return result;
	}

	public static void main(String[] args){
		complex_number[] input = new complex_number[4];
		input[0] = new complex_number(6, 0, "rect");
		input[1] = new complex_number(-1, 3, "rect");
		input[2] = new complex_number(1, 1, "zero");
		input[3] = new complex_number(-1, -3, "rect");
		IFFT ifft = new IFFT(input);
		double[] result = ifft.result();
		for(int i = 0; i < result.length; i++){
			System.out.println(result[i]);
		}
	}
}