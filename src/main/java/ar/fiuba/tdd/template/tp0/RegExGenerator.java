package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegExGenerator {
    private int maxLength;
    private String regEx;
    private Random random = new Random();

    private ArrayList<Token> tokens = new ArrayList<>();

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<String> generate(String regExParam, int numberOfResults) {
        ArrayList<String> list = new ArrayList<>();
        regEx = regExParam;
        tokenize();
        for (int i = 0; i < numberOfResults; i++) {
            list.add(makeOneResult());
            //System.out.println(list.get(i));                                                    /* print for test */
        }
        return list;
    }

    private void checkQuantifierForToken(Token token) {
        if (regEx.matches("^[?*+].*$")) {    // check possible quantifier
            Quantifier quantifier;
            switch (regEx.charAt(0)) {
                case '?':
                    quantifier = () -> random.nextInt(2);
                    break;
                case '*':
                    quantifier = () -> random.nextInt(maxLength + 1);
                    break;
                case '+':
                    quantifier = () -> random.nextInt(maxLength) + 1;
                    break;
                default:
                    quantifier = () -> 1;
                    break;
            }

            token.setQuantifier(quantifier);
            regEx = regEx.substring(1); // remove quantifier
        }
    }

    /* detects ".", ".?", ".*" and ".+" */
    private boolean detectPoint() {
        if (regEx.substring(0, 1).equals(".")) { // starts with point
            Point point = new Point();
            regEx = regEx.substring(1); // remove point

            checkQuantifierForToken(point);
            tokens.add(point);
            return true;
        }
        return false;
    }

    /* detects escaped literal with quantifiers */
    private boolean detectEscape() {
        if (regEx.substring(0, 1).equals("\\")) { // starts with \
            Literal literal = new Literal(regEx.charAt(1));   // literal next to \
            regEx = regEx.substring(2); // remove \ and a literal

            checkQuantifierForToken(literal);
            tokens.add(literal);
            return true;
        }
        return false;
    }

    /* detects any non special character (such as \ or +) */
    private boolean detectNotSpecial() {
        if (regEx.matches("^[^\\.\\?\\*\\+\\[\\]\\\\].*$")) { // starts with any non special char
            Literal literal = new Literal(regEx.charAt(0));   // take that literal
            regEx = regEx.substring(1); // remove literal

            checkQuantifierForToken(literal);
            tokens.add(literal);
            return true;
        }
        return false;
    }

    private boolean formAlphabet(StringBuilder alphabet) {
        while (!regEx.substring(0, 1).equals("]")) { // until ] is detected
            if (regEx.matches("^[\\.\\?\\*\\+\\[].*$")) {   // not escaped special char detected = invalid syntax
                return false;
            }
            if (regEx.substring(0, 1).equals("\\")) {    // escaping is valid inside of brackets
                alphabet.append(regEx.substring(1, 2));
                regEx = regEx.substring(2);
            } else {                                    // just a common literal
                alphabet.append(regEx.substring(0, 1));
                regEx = regEx.substring(1);
            }
        }
        return true;    // no invalid syntax inside of brackets
    }

    private boolean detectBracket() {
        if (regEx.substring(0, 1).equals("[")) { // starts with [

            regEx = regEx.substring(1); // remove [

            StringBuilder alphabet = new StringBuilder("");

            if (!formAlphabet(alphabet) || alphabet.length() == 0) {   // alphabet is empty = invalid syntax
                return false;
            }

            regEx = regEx.substring(1); // remove ]

            Bracket bracket = new Bracket(alphabet.toString());
            checkQuantifierForToken(bracket);
            tokens.add(bracket);
            return true;
        }
        return false;
    }

    private boolean detectValidSyntax() {
        // java's || doesn't evaluate right value if left one is 'true'
        // every detect method tries to process a syntactically valid token and returns true if successful
        return detectPoint() || detectEscape() || detectNotSpecial() || detectBracket();
    }

    private void tokenize() {
        while (!regEx.isEmpty()) {
            if (detectValidSyntax()) {
                continue;
            }
            throw new RuntimeException("TP0 syntax error");
        }
    }

    private String makeOneResult() {
        StringBuilder result = new StringBuilder("");
        for (Token token : tokens) {
            result.append(token.generate());
        }
        return result.toString();
    }

}