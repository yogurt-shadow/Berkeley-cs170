public class complex_number{
	private double x;
	private double y;
	private double r;
	private double theta;
	private boolean is_zero;  // radian measure

	public complex_number(double x, double y, String s){
		if(s == "rect"){
			this.x = x;
			this.y = y;
			this.r = Math.sqrt(x * x + y * y);
			this.theta = Math.atan(y / x);
			if(theta < 0 && y > 0){
				theta = theta + Math.PI;
			}
			else if(theta > 0 && y < 0){
				theta = theta - Math.PI;
			}
			is_zero = false;
		}

		else if(s == "polar"){
			this.r = x;
			this.theta = y;
			this.x = x * Math.cos(y);
			this.y = x * Math.sin(y);
			is_zero = false;
		}

		else if(s == "zero"){
			is_zero = true;
			this.r = 1;
			this.theta = 0;
			this.x = 0;
			this.y = 0;
		}

		else{
			System.out.println("s should be polar or rect");
		}
	}

	public complex_number pow(int x){
		if(x == 0){
			return new complex_number(1, 0, "rect");
		}
		return times(pow(x - 1));
	}

	public void print(){
		double x_print = x;
		double y_print = y;

		if(is_zero){
			System.out.print("0.0 + i * 0.0, ");
			return;
		}

		if(Math.abs(x) < Math.pow(10, -15)){
			x_print = 0;
		}
		if(Math.abs(y) < Math.pow(10, -15)){
			y_print = 0;
		}

		System.out.print(x_print + " + i * " + y_print + ", ");
	}

	public complex_number copy(){
		if(is_zero){
			return new complex_number(1, 1, "zero");
		}
		else{
			return new complex_number(this.x, this.y, "rect");
		}
	}

	public complex_number add(complex_number r){
		if(this.is_zero){
			return r.copy();
		}
		if(r.is_zero){
			return this.copy();
		}

		double new_x = this.x + r.x;
		double new_y = this.y + r.y;
		return new complex_number(new_x, new_y, "rect");
	}


	public complex_number conjugate(){
		if(is_zero){
			return new complex_number(1, 1, "zero");
		}
		return new complex_number(this.x, -this.y, "rect");
	}

	public complex_number times(complex_number r){
		if(this.is_zero || r.is_zero){
			return new complex_number(1, 1, "zero");
		}

		double new_theta = this.theta + r.theta;
		double new_r = this.r * r.r;
		return new complex_number(new_r, new_theta, "polar");
	}

	public complex_number square(){
		return this.times(this);
	}

	public double x(){
		return x;
	}

	public double y(){
		return y;
	}

	public double r(){
		return r;
	}

	public double theta(){
		return theta;
	}


	public static void main(String[] args){
		complex_number a = new complex_number(3.0, Math.PI, "polar");
		complex_number b = new complex_number(3, 0, "rect");
		complex_number c = a.add(b);
		System.out.println(c.x);
		System.out.println(c.theta);
		System.out.println(c.y);
	}


}