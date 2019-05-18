package parser;

import calculator.*;
import exceptions.ParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    public void testParseDigit() throws ParsingException {
        var parser = new Parser("1");
        var number = new NumberExpression(1);

        assertTrue(number.isEqual(parser.parse()));
    }

    @Test
    public void testParseNumber() throws ParsingException {
        var parser = new Parser("1292");
        var number = new NumberExpression(1292);

        assertTrue(number.isEqual(parser.parse()));
    }

    @Test
    public void testParseConstantExpression() throws ParsingException {
        var parser = new Parser("-123");
        var expression = new UnaryOperationExpression(new NumberExpression(123));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseSub() throws ParsingException {
        var parser = new Parser("(15-41)");
        var expression = new BinaryOperationExpression(new BinaryOperation('-'),
                new NumberExpression(15), new NumberExpression(41));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseMul() throws ParsingException {
        var parser = new Parser("(15*4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('*'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseDiv() throws ParsingException {
        var parser = new Parser("(15/4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('/'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseMod() throws ParsingException {
        var parser = new Parser("(15%4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('%'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseAdd() throws ParsingException {
        var parser = new Parser("(15+4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('+'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseLess() throws ParsingException {
        var parser = new Parser("(15<4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('<'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseGreater() throws ParsingException {
        var parser = new Parser("(15>4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('>'),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }

    @Test
    public void testParseEqual() throws ParsingException {
        var parser = new Parser("(15=4)");
        var expression = new BinaryOperationExpression(new BinaryOperation('='),
                new NumberExpression(15), new NumberExpression(4));

        assertTrue(expression.isEqual(parser.parse()));
    }
}