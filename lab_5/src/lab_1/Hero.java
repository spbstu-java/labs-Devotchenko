package lab_1;

import lab_1.Moving;

public class Hero {
    private Moving moving;

    // Метод для установки стратегии перемещения
    public void setMoving(Moving moving) {
        this.moving = moving;
    }

    // Метод для выполнения перемещения
    public void move(String move) {
        if (moving != null) {
            moving.move(move);
        } else {
            System.out.println("Moving strategy is not set!"); //test
        }
    }
}
