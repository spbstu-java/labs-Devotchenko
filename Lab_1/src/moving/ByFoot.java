package moving;

public class ByFoot implements Moving {
    @Override
    public void move(String move) {
        System.out.printf("%s герой идет%n", move);  //test
    }
}