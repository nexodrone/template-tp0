package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class Point extends Token {
    private Random random;

    public Point() {
        random = new Random();
    }

    public String generate() {
        StringBuilder result = new StringBuilder("");
        int cant = quantifier.getNumber();

        for (int i = 0; i < cant; i++) {
            int number = random.nextInt(256);
            while (number <= 31 || (127 <= number && number <= 160)) {  // avoiding control characters
                number = random.nextInt(256);
            }
            result.append((char) number);
        }
        return result.toString();
    }
}
