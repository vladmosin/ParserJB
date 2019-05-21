package calculator;

import exceptions.*;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private Parser parser = new Parser();

    @Test
    public void testNumberCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(12, parser.parse("12").calculate());
        assertEquals(2, parser.parse("2").calculate());
    }

    @Test
    public void testConstantExpressionCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(-211, parser.parse("-211").calculate());
        assertEquals(-1, parser.parse("-1").calculate());
    }

    @Test
    public void testAddCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(10, parser.parse("(6+4)").calculate());
        assertEquals(29, parser.parse("(18+11)").calculate());
    }

    @Test
    public void testSubCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(2, parser.parse("(6-4)").calculate());
        assertEquals(-200, parser.parse("(11-211)").calculate());
    }

    @Test
    public void testMulCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(24, parser.parse("(6*4)").calculate());
        assertEquals(198, parser.parse("(18*11)").calculate());
    }

    @Test
    public void testDivCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(1, parser.parse("(6/4)").calculate());
        assertEquals(17, parser.parse("(187/11)").calculate());
    }

    @Test
    public void testAddModCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(2, parser.parse("(6%4)").calculate());
        assertEquals(11, parser.parse("(11%18)").calculate());
    }

    @Test
    public void testLessCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(1, parser.parse("(4<6)").calculate());
        assertEquals(0, parser.parse("(11<11)").calculate());
    }

    @Test
    public void testGreaterCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(1, parser.parse("(6>4)").calculate());
        assertEquals(0, parser.parse("(11>18)").calculate());
    }

    @Test
    public void testEqualCalculation() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException, FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(0, parser.parse("(6=4)").calculate());
        assertEquals(1, parser.parse("(181=181)").calculate());
    }

    @Test
    public void testComplexExpression() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(23, parser.parse("((6*4)+(12-13))").calculate());
        assertEquals(1, parser.parse("((181%2)=((4/3)+(187%17)))").calculate());
    }

    @Test
    public void testIfExpression() throws ParsingException, CalculationException,
            IllegalFunctionDeclarationException, FunctionRedefinitionException,
            FunctionNotFoundException, ArgumentNumberMismatchException, ParameterNotFoundException {
        assertEquals(23, parser.parse("[123]?(23):(1)").calculate());
        assertEquals(11, parser.parse("[(133>121)]?((1+10)):(15)").calculate());
        assertEquals(0, parser.parse("[((10+20)>(20+10))]?(1):(0)").calculate());
    }

    @Test
    public void testFunctionalExpressions() throws ParsingException, IllegalFunctionDeclarationException,
            FunctionRedefinitionException, FunctionNotFoundException,
            CalculationException, ArgumentNumberMismatchException, ParameterNotFoundException {
        var input1 = new String[] {"f(x)={x}", "g(x)={f(x)}", "g(15)"};
        var input2 = new String[] {"f(x)={(x+13)}", "g(x)={(f(x)-7)}", "g((-12+3))"};
        var input3 = new String[] {"g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?((f((x-1))+f((x-2)))):(x)}", "g(10)"};
        var input4 = new String[] {"g(x,y)={(x+y)}", "f(x)={[(x>1)]?((f((x-1))+f((x-2)))):(x)}", "g(10,11)"};

        assertEquals(15, parser.parse(input1).calculate());
        assertEquals(-3, parser.parse(input2).calculate());
        assertEquals( 60, parser.parse(input3).calculate());
        assertEquals( 21, parser.parse(input4).calculate());

        assertEquals(15, Calculator.calculate(input1));
        assertEquals(-3, Calculator.calculate(input2));
        assertEquals(60, Calculator.calculate(input3));
        assertEquals(21, Calculator.calculate(input4));
    }

    @Test
    public void testExceptions() {
        var input1 = new String[] {"1 + 2 + 3 + 4 + 5 + 6"};
        var input2 = new String[] {"f(x)={y}", "f(10)"};
        var input3 = new String[] {"f(x)={g(x)}", "g(10)"};
        var input4 = new String[] {"f(x,y)={(x+y)}", "f(2,7,8)"};
        var input5 = new String[] {"f(x,y)={(x/y)}", "f(2,0)"};

        assertThrows(ParsingException.class, () -> Calculator.calculate(input1));
        assertThrows(ParameterNotFoundException.class, () -> Calculator.calculate(input2));
        assertThrows(FunctionNotFoundException.class, () -> Calculator.calculate(input3));
        assertThrows(ArgumentNumberMismatchException.class, () -> Calculator.calculate(input4));
        assertThrows(CalculationException.class, () -> Calculator.calculate(input5));
    }

    @Test
    public void testFunctionWithSameNameAndDifferentArgs() throws FunctionNotFoundException, CalculationException,
            ParameterNotFoundException, FunctionRedefinitionException,
            ParsingException, IllegalFunctionDeclarationException, ArgumentNumberMismatchException {
        assertEquals(12, Calculator.calculate(new String[] {"f(x,y)={(x+y)}", "f(x)={x}", "f(12)"}));
        assertEquals(17, Calculator.calculate(new String[] {"f(x,y)={(x+y)}", "f(x)={x}", "f(12,5)"}));
    }
}
