import java.util.Random;

public class werewolves_solution{
	private werewolves[] people;
	private int number;

	public werewolves_solution(int x){
		number = x;
		people = random_generator(x);
	}

	public werewolves random_one(){
		Random r = new Random();
		int index = r.nextInt(number);
		return people[index];
	}

	public werewolves_solution(int x, werewolves[] people){
		number = x;
		this.people = people;
	}

	private werewolves[] random_generator(int x){
		int citizens = 0;
		werewolves[] result = new werewolves[number];
		for(int i = 0; i < x; i++){
			double r = Math.random();
			if(r > 0.4){
				result[i] = new werewolves(true);
				citizens += 1;
			}
			else{
				result[i] = new werewolves(false);
			}
		}
		if(citizens > x / 2){
			return result;
		}
		return random_generator(x);
	}

	public boolean part_a(werewolves w){
		if(number == 1){
			return true;
		}
		int citizens = 0;
		int num = number - 1;
		for(int i = 0; i < number; i++){
			if(people[i] != w){
				if(people[i].is_citizen(w)){
					citizens += 1;
				}
			}
		}
		return citizens >= (num +1) / 2;
	}

	public werewolves find_citizen(){
		for(int i = 0; i < number; i++){
			if(part_a(people[i])){
				return people[i];
			}
		}
		return people[0];
	}

	public werewolves part_c(){
		if(number % 2 == 1){
			werewolves last = people[number - 1];
			if(part_a(last)){
				return last;
			}
			number = number - 1;
			// discard that wolf
		}

		int next_number = 0;
		werewolves[] next_people = new werewolves[number];
		int index = 0;

		for(int i = 0; i < number; i += 2){
			werewolves partner1 = people[i];
			werewolves partner2 = people[i + 1];
			boolean p1 = partner1.is_citizen(partner2);
			boolean p2 = partner2.is_citizen(partner1);
			if(p1 && p2){
				double r = Math.random();
				if(r > 0.5){
					next_people[index] = partner1;
				}
				else{
					next_people[index] = partner2;
				}
				index += 1;
				next_number += 1;
			}
		}

		werewolves_solution next = new werewolves_solution(next_number, next_people);
		return next.part_c();
	}

	public static void main(String[] args){
		werewolves_solution s = new werewolves_solution(8);
		System.out.println(s.part_a(s.people[0]));
		System.out.println(s.people[0].is_citizen());
	}
	
}