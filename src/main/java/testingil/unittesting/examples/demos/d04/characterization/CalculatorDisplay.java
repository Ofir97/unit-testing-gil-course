package testingil.unittesting.examples.demos.d04.characterization;

public class CalculatorDisplay {
    String display = "0";
    double lastArgument = 0;
    double firstArgument = 0;
    int result = 0;
    Boolean newArgument = false;
    Boolean shouldReset = true;

    OperationType lastOperation;
    OperationType firstOperation;

    public void press(String key) {
        switch (key) {
            case "+": {
                if (lastOperation != null) {
                    calculateResult();
                }
                lastOperation = OperationType.Plus;
                lastArgument = Double.parseDouble(display);
                newArgument = true;
                break;
            }
            case "-": {
                if (lastOperation != null) {
                    calculateResult();
                }
                lastOperation = OperationType.Sub;
                lastArgument = Double.parseDouble(display);
                newArgument = true;
                break;
            }
            case "*": {
                if (lastOperation == OperationType.Mul || lastOperation == OperationType.Div) {
                    calculateResult();
                    firstArgument = 0.0;
                    firstOperation = null;
                } else {
                    firstOperation = lastOperation;
                    firstArgument = lastArgument;
                }
                lastOperation = OperationType.Mul;
                lastArgument = Double.parseDouble(display);
                newArgument = true;
                break;
            }
            case "/": {
                if (lastOperation == OperationType.Mul || lastOperation == OperationType.Div) {
                    calculateResult();
                    firstArgument = 0.0;
                    firstOperation = null;
                } else {
                    firstOperation = lastOperation;
                    firstArgument = lastArgument;
                }
                lastOperation = OperationType.Div;
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
                    display = "0";
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

    private void calculateResult() {
        double currentArgument = Double.parseDouble(display);
        if (lastOperation == OperationType.Plus) {
            display = Double.toString(lastArgument + currentArgument);
        }
        if (lastOperation == OperationType.Sub) {
            display = Double.toString(lastArgument - currentArgument);
        }
        if (lastOperation == OperationType.Mul) {
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