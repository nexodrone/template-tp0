package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private static final int MAX_LENGTH = 5;
    private static final int TEST_FEW = 1;
    private static final int TEST_SOME = 10;
    private static final int TEST_MANY = 100;

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);

        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                        (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                        (item1, item2) -> item1 && item2);
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", TEST_SOME));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", TEST_SOME));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", TEST_FEW));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", TEST_SOME));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", TEST_SOME));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", TEST_SOME));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", TEST_SOME));
    }

    /* extra tests (+ Main.java for manual testing) */
    @Test
    public void testAnyCharQuestion() {
        assertTrue(validate(".?", TEST_SOME));
    }

    @Test
    public void testAnyCharStar() {
        assertTrue(validate(".*", TEST_SOME));
    }

    @Test
    public void testAnyCharPlus() {
        assertTrue(validate(".+", TEST_SOME));
    }

    @Test
    public void testEscapedQuestionPlus() {
        assertTrue(validate("\\?+", TEST_SOME));
    }

    @Test
    public void testEscapedStarStar() {
        assertTrue(validate("\\**", TEST_SOME));
    }

    @Test
    public void testCombined1() {
        assertTrue(validate("..*\\??", TEST_MANY));
    }

    @Test
    public void testCombined2() {
        assertTrue(validate("\\%?\\+*.?.?\\??", TEST_MANY));
    }

    @Test
    public void testLiteralNotEscaped() {
        assertTrue(validate("U", TEST_FEW));
    }

    @Test
    public void testLiteralsNotEscaped() {
        assertTrue(validate("~3#0d&Z}", TEST_FEW));
    }

    @Test
    public void testCombined3() {
        assertTrue(validate("P\\??hello\\[\\]\\\\\\...?", TEST_MANY));
    }

    @Test
    public void testTP0Example() {
        assertTrue(validate("..+[ab]*d?c", TEST_MANY));
    }

    @Test
    public void testBracketsEscapedSpecials() {
        assertTrue(validate("[\\?\\*\\+\\\\\\.\\[\\]]*", TEST_SOME));
    }

    @Test
    public void testCombined4() {
        assertTrue(validate("G?./\\?+\\[abc\\]", TEST_MANY));
    }

    @Test
    public void testCombined5() {
        assertTrue(validate(".?\\+[123\\?]*\\+?[GTA4]?", TEST_MANY));
    }

    @Test
    public void testCombined6() {
        assertTrue(validate("\\\\[&\\#\\%]*.?.?[\\[\\]]", TEST_MANY));
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBugInBrackets1() {
        validate("[hoa[asd]", TEST_FEW);
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBugInBrackets2() {
        validate("[\\?*]", TEST_FEW);
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBugInBrackets3() {
        validate("hol[]a", TEST_FEW);
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBug1() {
        validate("..[hay]un]bug", TEST_FEW);
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBug2() {
        validate("*", TEST_FEW);
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testExceptionSyntaxBug3() {
        validate("1+??=3", TEST_FEW);
    }
}
