package ar.fiuba.tdd.template.tp0;

public class Literal extends Token {
    private char character;

    public Literal(char characterParam) {
        character = characterParam;
    }

    public String generate() {
        StringBuilder result = new StringBuilder("");
        int cant = 1;
        if (quantifier != null) {
            cant = quantifier.getNumber();
        }
        for (int i = 0; i < cant; i++) {
            result.append(character);
        }
        return result.toString();
    }
}
