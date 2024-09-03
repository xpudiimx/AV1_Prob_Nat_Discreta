import java.util.Set;
import java.util.Scanner;
import java.util.Stack;

public class PropositionalFormulaTester {


    private static final Set<Character> VALID_Connectives = Set.of('~','^','v','>','-','_');

    private static Scanner entrada = new Scanner(System.in);

    public static String recebeFormula(){
        System.out.println("Informe a fórmula para ser analisada");
        String formula = entrada.nextLine();
        return formula;
    }

    String formula = recebeFormula();

    public void validaConectivos() {
        for (char c : formula.toCharArray()) {
            if (!Character.isLetter(c) && !VALID_Connectives.contains(c) && c != '(' && c != ')') {
                System.err.println("Conectivos não válidos.");
                return;
            }

        }
        System.out.println("Conectivos validos.");
    }

    public boolean validaFormato() {
        Stack<Character> stack = new Stack<>();
        boolean lastWasOperand = false;
        boolean lastWasConnective = false;

        for (char c : formula.toCharArray()) {
            if (c == '(') {
                stack.push(c);
                lastWasOperand = false;
                lastWasConnective = false;
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    System.err.println("Fórmula mal formulada: Parênteses desequilibrados.");
                    return false;
                }
                lastWasOperand = true;
            } else if (Character.isLetter(c)) {
                if (lastWasOperand) {
                    System.err.println("Fórmula mal formulada: Dois operandos seguidos.");
                    return false;
                }
                lastWasOperand = true;
                lastWasConnective = false;
            } else if (VALID_Connectives.contains(c)) {
                if (lastWasConnective && c != '~') {
                    System.err.println("Fórmula mal formulada: Dois conectivos seguidos.");
                    return false;
                }
                lastWasOperand = false;
                lastWasConnective = true;
            } else {
                System.err.println("Fórmula mal formulada: Caractere inválido.");
                return false;
            }
        }

        if (!stack.isEmpty()) {
            System.err.println("Fórmula mal formulada: Parênteses desequilibrados.");
            return false;
        }

        if (lastWasConnective) {
            System.err.println("Fórmula mal formulada: Fórmula termina com um conectivo.");
            return false;
        }

        System.out.println("Fórmula bem formulada.");
        return true;
    }
}


