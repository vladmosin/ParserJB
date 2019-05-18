package calculator;

import exceptions.CalculationException;
import exceptions.ParsingException;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Parser parser = new Parser();

    @Test
    public void testNumberCalculation() throws ParsingException, CalculationException {
        assertEquals(12, parser.parse("12").calculate());
        assertEquals(2, parser.parse("2").calculate());
    }

    @Test
    public void testConstantExpressionCalculation() throws ParsingException, CalculationException {
        assertEquals(-211, parser.parse("-211").calculate());
        assertEquals(-1, parser.parse("-1").calculate());
    }

    @Test
    public void testAddCalculation() throws ParsingException, CalculationException {
        assertEquals(10, parser.parse("(6+4)").calculate());
        assertEquals(29, parser.parse("(18+11)").calculate());
    }

    @Test
    public void testSubCalculation() throws ParsingException, CalculationException {
        assertEquals(2, parser.parse("(6-4)").calculate());
        assertEquals(-200, parser.parse("(11-211)").calculate());
    }

    @Test
    public void testMulCalculation() throws ParsingException, CalculationException {
        assertEquals(24, parser.parse("(6*4)").calculate());
        assertEquals(198, parser.parse("(18*11)").calculate());
    }

    @Test
    public void testDivCalculation() throws ParsingException, CalculationException {
        assertEquals(1, parser.parse("(6/4)").calculate());
        assertEquals(17, parser.parse("(187/11)").calculate());
    }

    @Test
    public void testAddModCalculation() throws ParsingException, CalculationException {
        assertEquals(2, parser.parse("(6%4)").calculate());
        assertEquals(11, parser.parse("(11%18)").calculate());
    }

    @Test
    public void testLessCalculation() throws ParsingException, CalculationException {
        assertEquals(1, parser.parse("(4<6)").calculate());
        assertEquals(0, parser.parse("(11<11)").calculate());
    }

    @Test
    public void testGreaterCalculation() throws ParsingException, CalculationException {
        assertEquals(1, parser.parse("(6>4)").calculate());
        assertEquals(0, parser.parse("(11>18)").calculate());
    }

    @Test
    public void testEqualCalculation() throws ParsingException, CalculationException {
        assertEquals(0, parser.parse("(6=4)").calculate());
        assertEquals(1, parser.parse("(181=181)").calculate());
    }

    @Test
    public void testComplexExpression() throws ParsingException, CalculationException {
        assertEquals(23, parser.parse("((6*4)+(12-13))").calculate());
        assertEquals(1, parser.parse("((181%2)=((4/3)+(187%17)))").calculate());
    }

    @Test
    public void testIfExpression() throws ParsingException, CalculationException {
        assertEquals(23, parser.parse("[123]?(23):(1)").calculate());
        assertEquals(11, parser.parse("[(133>121)]?((1+10)):(15)").calculate());
    }
}
