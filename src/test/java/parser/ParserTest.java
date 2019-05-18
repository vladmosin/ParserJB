package parser;

import calculator.*;
import exceptions.ParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser = new Parser();
    @Test
    public void testParseDigit() throws ParsingException {
        var number = new NumberExpression(1);

        assertTrue(number.isEqual(parser.parse("1")));
    }

    @Test
    public void testParseNumber() throws ParsingException {
        var number = new NumberExpression(1292);

        assertTrue(number.isEqual(parser.parse("1292")));
    }

    @Test
    public void testParseConstantExpression() throws ParsingException {
        var expression = new UnaryOperationExpression(new NumberExpression(123));

        assertTrue(expression.isEqual(parser.parse("-123")));
    }

    @Test
    public void testParseSub() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('-'),
                new NumberExpression(15), new NumberExpression(41));

        assertTrue(expression.isEqual(parser.parse("(15-41)")));
    }

    @Test
    public void testParseMul() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('*'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15*4)")));
    }

    @Test
    public void testParseDiv() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('/'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15/4)")));
    }

    @Test
    public void testParseMod() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('%'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15%4)")));
    }

    @Test
    public void testParseAdd() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('+'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15+4)")));
    }

    @Test
    public void testParseLess() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('<'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15<4)")));
    }

    @Test
    public void testParseGreater() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('>'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15>4)")));
    }

    @Test
    public void testParseEqual() throws ParsingException {
        var expression = new BinaryOperationExpression(new BinaryOperation('='),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse("(15=4)")));
    }
}