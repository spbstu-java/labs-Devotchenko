package lab_2;

public class MyClass {

    @Repeat(3)
    private void privateMethod1(String message) {
        System.out.println("Private Method 1: " + message);
    }

    private void privateMethod2(int number) {
        System.out.println("Private Method 2: " + number);
    }

    @Repeat(2)
    protected void protectedMethod1() {
        System.out.println("Protected Method 1");
    }

    protected void protectedMethod2(double value) {
        System.out.println("Protected Method 2: " + value);
    }

    public void publicMethod1() {
        System.out.println("Public Method 1");
    }

    @Repeat(1)
    public void publicMethod2(boolean flag) {
        System.out.println("Public Method 2: " + flag);
    }
}
