package testingil.unittesting.examples.exercise.e01.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testingil.unittesting.examples.demos.d04.characterization.CalculatorDisplay;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorDisplayTests {

    private CalculatorDisplay cd;

    @BeforeEach
    void setUp() {
        cd = new CalculatorDisplay();
    }

    @Test
    void whenPlusTwoDigits_thenDisplaySum() {
        cd.press("3");
        cd.press("+");
        cd.press("2");
        cd.press("=");

        assertEquals("5.0", cd.getDisplay());
    }

    @Test
    void whenMinusTwoDigits_thenDisplaySubtraction() {
        cd.press("7");
        cd.press("-");
        cd.press("4");
        cd.press("=");

        assertEquals("3.0", cd.getDisplay());
    }

    @Test
    void whenMulTwoDigits_thenDisplayMultiplication() {
        cd.press("6");
        cd.press("*");
        cd.press("4");
        cd.press("=");

        assertEquals("24.0", cd.getDisplay());
    }

    @Test
    void whenDivideTwoDigits_thenDisplayDivision() {
        cd.press("5");
        cd.press("/");
        cd.press("2");
        cd.press("=");

        assertEquals("2.5", cd.getDisplay());
    }

    @Test
    void whenPressDigit_thenDisplayDigit() {
        cd.press("7");
        cd.press("=");

        assertEquals("7", cd.getDisplay());
    }

    @Test
    void whenPressPlusDigit_thenDisplayDigit() {
        cd.press("+");
        cd.press("5");
        cd.press("=");

        assertEquals("5.0", cd.getDisplay());
    }

    @Test
    void whenPressTwicePlusAndDigit_thenDisplayDigit() {
        cd.press("+");
        cd.press("+");
        cd.press("4");
        cd.press("=");

        assertEquals("4.0", cd.getDisplay());
    }

    @Test
    void whenDivideByZero_thenDisplayError() {
        cd.press("5");
        cd.press("/");
        cd.press("0");
        cd.press("=");

        assertEquals("E", cd.getDisplay());
    }

    @Test
    void whenPressC_thenDisplayZero() {
        cd.press("C");
        cd.press("=");

        assertEquals("0", cd.getDisplay());
    }

    @Test
    void whenSumTwoNegatives_thenDisplayNegativeSum() {
        cd.press("-4");
        cd.press("+");
        cd.press("-2");
        cd.press("=");

        assertEquals("-6.0", cd.getDisplay());
    }

    @Test
    void whenPressNumberStartWithZero_thenIgnoreZero() {
        cd.press("0");
        cd.press("6");
        cd.press("2");
        cd.press("=");

        assertEquals("62", cd.getDisplay());
    }

    @Test
    void whenPressMultipleZeros_thenDisplayZero() {
        cd.press("0");
        cd.press("0");
        cd.press("0");
        cd.press("0");
        cd.press("0");
        cd.press("=");

        assertEquals("0", cd.getDisplay());
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void whenCalculateComplexExpression_thenDisplayResult(String firstOperation,
                                                          String lastOperation,
                                                          String expectedResult) {
        cd.press("4");
        cd.press(firstOperation);
        cd.press("2");
        cd.press(lastOperation);
        cd.press("2");
        cd.press("=");

        assertEquals(expectedResult, cd.getDisplay());
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("+", "*", "8.0"),
                Arguments.of("-", "*", "0.0"),
                Arguments.of("*", "*", "16.0"),
                Arguments.of("/", "*", "4.0"),
                Arguments.of("*", "/", "4.0"),
                Arguments.of("+", "+", "8.0"),
                Arguments.of("+", "/", "5.0"),
                Arguments.of("-", "/", "3.0"),
                Arguments.of("/", "/", "1.0"));
    }
}
