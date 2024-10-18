package lab_1;

public class Fly implements Moving {
    @Override
    public void move(String move) {
        System.out.printf("%s герой летит%n", move); //test
    }
}