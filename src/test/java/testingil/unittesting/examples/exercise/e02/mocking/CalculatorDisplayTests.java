package testingil.unittesting.examples.exercise.e02.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import testingil.unittesting.examples.demos.d04.characterization.CalculatorDisplay;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorDisplayTests {

	private CalculatorDisplay cd;

	@BeforeEach
	void setUp() {
		cd = new CalculatorDisplay();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/arguments.csv")
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
}
