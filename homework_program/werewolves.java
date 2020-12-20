public class werewolves{
	private boolean is_citizen;

	public werewolves(boolean x){
		is_citizen = x;
	}

	public boolean is_citizen(){
		return is_citizen;
	}

	public boolean is_citizen(werewolves other){
		if(is_citizen){
			return other.is_citizen();
		}
		else{
			double r = Math.random();
			if(r >= 0.5){
				return true;
			}
			else{
				return false;
			}
		}
	}
}