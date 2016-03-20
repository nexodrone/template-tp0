package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class Question implements Quantifier {
    private Random random = new Random();

    public int getNumber() {
        return random.nextInt(2);
    }
}
