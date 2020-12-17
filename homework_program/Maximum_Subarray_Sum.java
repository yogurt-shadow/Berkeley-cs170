public class Maximum_Subarray_Sum{
	public int[] max_subarray(int[] input){
		int n = input.length;
		if(n == 1){
			if(input[0] > 0){
				return new int[]{input[0]};
			}
			return new int[]{0};
		}

		int[] a = new int[(n + 1) / 2];
		int[] b = new int[n / 2];
		System.arraycopy(input, 0, a, 0, (n + 1) / 2);
		System.arraycopy(input, (n + 1) / 2, b, 0, n / 2);

		int[] s1 = max_subarray(a);
		int[] s2 = max_subarray(b);
		int[] s3 = max_board(a, b);

		return max(s1, s2, s3);
	}

	private int[] max_board(int[] a, int[] b){
		int sum1 = a[a.length - 1];
		int max_sum1 = sum1;
		int left = a.length - 1;
		
		for(int i = a.length - 2; i >= 0; i--){
			sum1 += a[i];
			if(sum1 >= max_sum1){
				left = i;
				max_sum1 = sum1;
			}
		}

		int sum2 = b[0];
		int max_sum2 = sum2;
		int right = 0;
		for(int j = 1; j < b.length; j++){
			sum2 += b[j];
			if(sum2 >= max_sum2){
				max_sum2 = sum2;
				right = j;
			}
		}

		int[] result = new int[a.length - left + right + 1];
		System.arraycopy(a, left, result, 0, a.length - left);
		System.arraycopy(b, 0, result, a.length - left, right + 1);
		return result;
	}

	private int sum(int[] x){
		int sum = 0;
		for(int i = 0; i < x.length; i++){
			sum += x[i];
		}
		return sum;
	}

	private int[] max(int[] s1, int[] s2, int[] s3){
		int sum1 = sum(s1);
		int sum2 = sum(s2);
		int sum3 = sum(s3);

		if(sum1 >= sum2 && sum1 >= sum3){
			return s1;
		}
		if(sum2 >= sum1 && sum2 >= sum3){
			return s2;
		}
		else{
			return s3;
		}
	}

	public static void main(String[] args){
		Maximum_Subarray_Sum m = new Maximum_Subarray_Sum();
		int[] input = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
		int[] result = m.max_subarray(input);
		for(int i = 0; i < result.length; i++){
			System.out.print(result[i] + " ");
		}
	}


}