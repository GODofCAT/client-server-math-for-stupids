package Server;

import java.util.Random;

public class Controller {
    Random random = new Random();

    char[] signs = new char[]{'+', '-', '*', '/'};

    private int points;
    private int rightAnswer;
    private int answer;

    public Controller() {
        points = 0;
        rightAnswer = 0;
    }

    void ResetGame() {
        points = 0;
        rightAnswer = 0;
    }

    public String StartRound() {
        int firstNum = random.nextInt(10);
        int secondNum;

        do {
            secondNum = random.nextInt(10);
        } while (secondNum == 0);

        char sign = signs[random.nextInt(signs.length-1)];

        CalculateRightAnswer(firstNum, secondNum, sign);

        return Integer.toString(firstNum) + Character.toString(sign) + Integer.toString(secondNum) + " = ";
    }

    private void CalculateRightAnswer(int fn, int sn, char s) {
        switch (s) {
            case '+':
                rightAnswer = fn + sn;
                break;
            case '-':
                rightAnswer = fn - sn;
                break;
            case '*':
                rightAnswer = fn * sn;
                break;
            case '/':
                rightAnswer = fn / sn;
                break;
        }
    }

    public String CheckAnswer(int answer){
        if (answer == rightAnswer){
            points++;
            return "yes";
        }
        if (answer != rightAnswer){
            return "try again(";
        }
        return null;
    }

    public int GetPoints(){
        return points;
    }

}
