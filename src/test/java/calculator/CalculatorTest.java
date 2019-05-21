package calculator;

import exceptions.*;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Parser parser = new Parser();

    @Test
    public void testNumberCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(12, parser.parse("12").calculate());
        assertEquals(2, parser.parse("2").calculate());
    }

    @Test
    public void testConstantExpressionCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(-211, parser.parse("-211").calculate());
        assertEquals(-1, parser.parse("-1").calculate());
    }

    @Test
    public void testAddCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(10, parser.parse("(6+4)").calculate());
        assertEquals(29, parser.parse("(18+11)").calculate());
    }

    @Test
    public void testSubCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(2, parser.parse("(6-4)").calculate());
        assertEquals(-200, parser.parse("(11-211)").calculate());
    }

    @Test
    public void testMulCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(24, parser.parse("(6*4)").calculate());
        assertEquals(198, parser.parse("(18*11)").calculate());
    }

    @Test
    public void testDivCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(1, parser.parse("(6/4)").calculate());
        assertEquals(17, parser.parse("(187/11)").calculate());
    }

    @Test
    public void testAddModCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(2, parser.parse("(6%4)").calculate());
        assertEquals(11, parser.parse("(11%18)").calculate());
    }

    @Test
    public void testLessCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(1, parser.parse("(4<6)").calculate());
        assertEquals(0, parser.parse("(11<11)").calculate());
    }

    @Test
    public void testGreaterCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(1, parser.parse("(6>4)").calculate());
        assertEquals(0, parser.parse("(11>18)").calculate());
    }

    @Test
    public void testEqualCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(0, parser.parse("(6=4)").calculate());
        assertEquals(1, parser.parse("(181=181)").calculate());
    }

    @Test
    public void testComplexExpression() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(23, parser.parse("((6*4)+(12-13))").calculate());
        assertEquals(1, parser.parse("((181%2)=((4/3)+(187%17)))").calculate());
    }

    @Test
    public void testIfExpression() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException {
        assertEquals(23, parser.parse("[123]?(23):(1)").calculate());
        assertEquals(11, parser.parse("[(133>121)]?((1+10)):(15)").calculate());
        assertEquals(0, parser.parse("[((10+20)>(20+10))]?(1):(0)").calculate());
    }

    @Test
    public void testFunctionalExpressions() throws ParsingException, IllegalFunctionDeclarationException,
            FunctionRedefinitionException, FunctionNotFoundException, CalculationException {

        var input1 = new String[] {"f(x)={x}", "g(x)={f(x)}", "g(15)"};
        var input2 = new String[] {"f(x)={(x+13)}", "g(x)={(f(x)-7)}", "g((-12+3))"};
        var input3 = new String[] {"g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?((f((x-1))+f((x-2)))):(x)}", "g(10)"};
        var input4 = new String[] {"g(x,y)={(x+y)}", "f(x)={[(x>1)]?((f((x-1))+f((x-2)))):(x)}", "g(10,11)"};

        assertEquals(15, parser.parse(input1).calculate());
        assertEquals(-3, parser.parse(input2).calculate());
        assertEquals( 60, parser.parse(input3).calculate());
        assertEquals( 21, parser.parse(input4).calculate());
    }
}
