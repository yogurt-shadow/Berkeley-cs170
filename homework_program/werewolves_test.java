import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class werewolves_test {
    @Test
    public void test_parta(){
        Random r = new Random();
        for(int i = 0; i < 1000; i++) {
            int number = r.nextInt(20) + 1;
            werewolves_solution w = new werewolves_solution(number);
            werewolves one = w.random_one();
            assertEquals(one.is_citizen(), w.part_a(one));
        }
    }

    @Test
    public void test_find_citizen(){
        Random r = new Random();
        for(int i = 0; i < 1000; i++){
            int number = r.nextInt(20) + 1;
            werewolves_solution w = new werewolves_solution(number);
            werewolves one = w.find_citizen();
            assertTrue(one.is_citizen());
        }
    }

    @Test
    public void test_partc(){
        Random r = new Random();
        for(int i = 0; i < 1000; i++){
            int number = r.nextInt(20) + 1;
            werewolves_solution w = new werewolves_solution(number);
           werewolves one = w.part_c();
           assertTrue(one.is_citizen());
        }
    }
}
