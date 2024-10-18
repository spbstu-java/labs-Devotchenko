package lab_1;

public class ByFoot implements Moving {
    @Override
    public void move(String action) {
        System.out.println("Moving by foot: " + action);
    }
}
