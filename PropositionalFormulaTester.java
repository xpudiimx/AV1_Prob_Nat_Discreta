import java.util.Set;
import java.util.Scanner;
import java.util.Stack;

public class PropositionalFormulaTester {


    private static final Set<Character> VALID_Connectives = Set.of('~','^','v','>','-','_');

    private static Scanner entrada = new Scanner(System.in);

    public static String recebeFormula(){
        System.out.println("Informe a formula para ser analisada");
        String formula = entrada.nextLine();
        return formula;
    }

    String formula = recebeFormula();

    public void validaFormula() {
        for (char c : formula.toCharArray()) {
            if (!Character.isLetter(c) && !VALID_Connectives.contains(c) && c != '(' && c != ')') {
                System.err.println("A formula não é válida.");
                return;
            }

        }
        System.out.println("Formula valida.");
    }

    public String analisaFormato() {
        if (isFBF(formula)) {
            return "A fórmula é bem formulada (FBF).";
        } else {
            return "A fórmula não é bem formulada.";
        }
    }

    public static boolean isFBF(String formula) {
        Stack<Character> stack = new Stack<>();
        boolean lastWasOperator = true;
        boolean expectingOperand = true;

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            if (Character.isLetter(c)) {
                if (expectingOperand) {
                    expectingOperand = false;
                    lastWasOperator = false;
                } else {
                    return false;
                }
            } else if (VALID_Connectives.contains(c)) {
                if (c == '~') {
                    lastWasOperator = true;
                } else if (!lastWasOperator) {
                    lastWasOperator = true;
                    expectingOperand = true;
                } else {
                    return false;
                }
            } else if (c == '(') {
                stack.push(c);
                expectingOperand = true;
            } else if (c == ')') {
                if (stack.isEmpty() || lastWasOperator) {
                    return false;
                }
                stack.pop();
                lastWasOperator = false;
            } else {
                return false;
            }
        }

        return stack.isEmpty() && !lastWasOperator;
    }
}


