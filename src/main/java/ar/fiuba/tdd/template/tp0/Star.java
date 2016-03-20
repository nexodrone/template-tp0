package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class Star implements Quantifier {
    private Random random = new Random();
    private int maxLength;

    public Star(int maxLengthParam) {
        maxLength = maxLengthParam;
    }

    public int getNumber() {
        return random.nextInt(maxLength + 1);   // "+1" is caused by functionality of .nextInt method
    }
}
