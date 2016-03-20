package ar.fiuba.tdd.template.tp0;

public abstract class Token {
    Quantifier quantifier = null;

    abstract String generate();

    void setQuantifier(Quantifier quantifierParam) {
        quantifier = quantifierParam;
    }
}
