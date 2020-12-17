public class Monotone{

	public int[] minimum(int[][] a){
		if(a.length == 0){
			int[] x = new int[0];
			return x;
		}

		int m = a.length;
		int n = a[0].length;
		int half = (m  - 1)/ 2;

		int index = 0;
		int half_min = a[half][0];
		for(int j = 1; j < n; j++){
			if(a[half][j] < half_min){
				half_min = a[half][j];
				index = j;
			}
		}

		if(a.length == 1){
			return new int[]{half_min};
		}

		int[][] upper = new int[half][index];
		int[][] lower = new int[m - half - 1][n - index - 1];

		for(int i = 0; i < half; i++){
			for(int j = 0; j < index; j++){
				upper[i][j] = a[i][j];
			}
		}

		for(int i = 0; i < m - half - 1; i++){
			for(int j = 0; j < n - index - 1; j++){
				lower[i][j] = a[half + i + 1][index + j + 1];
			}
		}

		int[] min1 = minimum(upper);
		int[] min2 = minimum(lower);
		int[] result = new int[m];

		System.arraycopy(min1, 0, result, 0, min1.length);
		result[min1.length] = half_min;
		System.arraycopy(min2, 0, result, min1.length + 1, min2.length);

		return result;
	}

	public static void main(String[] args){
		Monotone m = new Monotone();
		int[][] a = new int[][]{{1, 3, 4, 6, 5, 2}, {7, 3, 2, 5, 6, 4}, {7, 9, 6, 3, 10, 0}};
		int[] minimum = m.minimum(a);
		for(int i = 0; i < minimum.length; i++){
			System.out.print(minimum[i] + " ");
		}
	}
}