package testingil.unittesting.examples.exercise.e03.spring;

import testingil.unittesting.examples.demos.d04.characterization.OperationType;

public class Calculator {
	String display = "0";
	double lastArgument = 0;
	double firstArgument = 0;
	int result = 0;
	Boolean newArgument = false;
	Boolean shouldReset = true;

	testingil.unittesting.examples.demos.d04.characterization.OperationType lastOperation;
	testingil.unittesting.examples.demos.d04.characterization.OperationType firstOperation;

	public void press(String key) {
		switch (key) {
			case "+": {
				if (lastOperation != null) {
					calculateResult();
				}
				lastOperation = testingil.unittesting.examples.demos.d04.characterization.OperationType.Plus;
				lastArgument = Double.parseDouble(display);
				newArgument = true;
				break;
			}
			case "-": {
				if (lastOperation != null) {
					calculateResult();
				}
				lastOperation = testingil.unittesting.examples.demos.d04.characterization.OperationType.Sub;
				lastArgument = Double.parseDouble(display);
				newArgument = true;
				break;
			}
			case "*": {
				if (lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Mul || lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Div) {
					calculateResult();
					firstArgument = 0.0;
					firstOperation = null;
				} else {
					firstOperation = lastOperation;
					firstArgument = lastArgument;
				}
				lastOperation = testingil.unittesting.examples.demos.d04.characterization.OperationType.Mul;
				lastArgument = Double.parseDouble(display);
				newArgument = true;
				break;
			}
			case "/": {
				if (lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Mul || lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Div) {
					calculateResult();
					firstArgument = 0.0;
					firstOperation = null;
				} else {
					firstOperation = lastOperation;
					firstArgument = lastArgument;
				}
				lastOperation = testingil.unittesting.examples.demos.d04.characterization.OperationType.Div;
				lastArgument = Double.parseDouble(display);
				newArgument = true;
				break;
			}
			case "=": {
				calculateResult();
				break;
			}
			default: {
				if (shouldReset) {
					display = "";
					shouldReset = false;
				}
				if (newArgument) {
					display = "";
					newArgument = false;
				}
				if (key.equals("C")) {
					resetCalculator();
					break;
				}

				display += key;
			}
		}
	}

	public String getDisplay() {
		if (display.equals("") || isInputContainsOnlyZeros(display))
			return "0";
		if (display.length() > 5)
			return "E";
		if (display.startsWith("0"))
			return display.substring(1);
		if (firstArgument != 0 && firstOperation != null) {
			lastArgument = firstArgument;
			lastOperation = firstOperation;
			calculateResult();
		}
		return display;
	}

	public void resetCalculator() {
		display = "0";
		lastArgument = 0;
		firstArgument = 0;
		result = 0;
		newArgument = false;
		shouldReset = true;
		lastOperation = null;
		firstOperation = null;
	}

	private void calculateResult() {
		double currentArgument = Double.parseDouble(display);
		if (lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Plus) {
			display = Double.toString(lastArgument + currentArgument);
		}
		if (lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Sub) {
			display = Double.toString(lastArgument - currentArgument);
		}
		if (lastOperation == testingil.unittesting.examples.demos.d04.characterization.OperationType.Mul) {
			display = Double.toString(lastArgument * currentArgument);
		}
		if (lastOperation == OperationType.Div) {
			if (currentArgument != 0) {
				display = Double.toString(lastArgument / currentArgument);
			} else {
				display = "Division By Zero Error";
			}
			shouldReset = true;
		}
	}

	private boolean isInputContainsOnlyZeros(String input) {
		for (char character : input.toCharArray()) {
			if (character != '0') {
				return false;
			}
		}
		return true;
	}
}