package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class Plus implements Quantifier {
    private Random random = new Random();
    private int maxLength;

    public Plus(int maxLengthParam) {
        maxLength = maxLengthParam;
    }

    public int getNumber() {
        return random.nextInt(maxLength) + 1;
    }
}
