package ar.fiuba.tdd.template.tp0;

import java.util.List;

/* for manual testing only */
public class Main {

    public static void main(String[] args) {
        String regEx = "[aaaaaaaaaaaaaaaaaaaaaaaaaab\\*\\*\\*\\*\\*\\*c]";  // statistical test for brackets
        RegExGenerator regExGenerator = new RegExGenerator(4);
        List<String> strings = regExGenerator.generate(regEx, 20);
        strings.forEach(System.out::println);
    }

}
