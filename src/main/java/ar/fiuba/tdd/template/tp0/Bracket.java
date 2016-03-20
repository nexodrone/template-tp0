package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class Bracket extends Token {
    private Random random;
    private String alphabet;

    public Bracket(String alphabetParam) {
        alphabet = alphabetParam.replaceAll("(.)\\1+", "$1");   // delete repetitions
        //System.out.println(alphabet);                                                         /* print for test */
        random = new Random();
    }

    public String generate() {
        StringBuilder result = new StringBuilder("");
        int cant = 1;
        if (quantifier != null) {
            cant = quantifier.getNumber();
        }

        int randomNum;
        for (int i = 0; i < cant; i++) {
            randomNum = random.nextInt(alphabet.length());
            result.append(alphabet.substring(randomNum, randomNum + 1));    // take random symbol from allowed alphabet
        }

        return result.toString();
    }
}
