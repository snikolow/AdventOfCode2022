package challenge.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    enum Outcome {
        WIN(6), DRAW(3), LOSS(0);

        final int value;

        Outcome(int value) {
            this.value = value;
        }
    }

    enum Move {
        ROCK(1), PAPER(2), SCISSORS(3);

        final int value;

        Move(int value) {
            this.value = value;
        }
    }

    private record Game(String bot, String me) {
        Game(String[] currentLine) {
            this(currentLine[0], currentLine[1]);
        }

        Move botMove() {
            return switch (bot) {
                case "A" -> Move.ROCK;
                case "B" -> Move.PAPER;
                case "C" -> Move.SCISSORS;
                default -> throw new IllegalArgumentException("Invalid move provided.");
            };
        }

        Move myMove() {
            return switch(me) {
                case "X" -> Move.ROCK;
                case "Y" -> Move.PAPER;
                case "Z" -> Move.SCISSORS;
                default -> throw new IllegalArgumentException("Invalid move provided.");
            };
        }

        Move selectMyMoveForSecondRound(Move botMove, String currentLineLetter) {
            switch (currentLineLetter) {
                case "X" -> {
                    if (Move.ROCK == botMove) {
                        return Move.SCISSORS;
                    } else if (Move.PAPER == botMove) {
                        return Move.ROCK;
                    } else {
                        return Move.PAPER;
                    }
                }
                case "Y" -> {
                    return botMove;
                }
                case "Z" -> {
                    if (Move.ROCK == botMove) {
                        return Move.PAPER;
                    } else if (Move.PAPER == botMove) {
                        return Move.SCISSORS;
                    } else {
                        return Move.ROCK;
                    }
                }
                default -> throw new IllegalArgumentException("Invalid move provided.");
            }
        }

        int gamePoints(Move botMove, Move myMove) {
            if (botMove == myMove) {
                return Outcome.DRAW.value + myMove.value;
            }

            if (isOutcomeWin(botMove, myMove)) {
                return Outcome.WIN.value + myMove.value;
            } else {
                return Outcome.LOSS.value + myMove.value;
            }
        }

        boolean isOutcomeWin(Move botMove, Move myMove) {
            if (Move.ROCK == myMove) {
                return botMove == Move.SCISSORS;
            } else if (Move.PAPER == myMove) {
                return botMove == Move.ROCK;
            } else {
                return botMove == Move.PAPER;
            }
        }
    }

    public static void main(String[] args) {
        try {
            URL path = Main.class.getResource("Resources/input.txt");
            Scanner scanner = new Scanner(new File(Objects.requireNonNull(path).getFile()));

            int score = 0;
            int finalScore = 0;

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                Game currentGame = new Game(currentLine.split(" "));

                if (!currentLine.isBlank()) {
                    // First Round.
                    score += currentGame.gamePoints(currentGame.botMove(), currentGame.myMove());

                    // Second Round.
                    Move myMoveForSecondRound = currentGame.selectMyMoveForSecondRound(currentGame.botMove(), currentLine.split(" ")[1]);

                    finalScore += currentGame.gamePoints(currentGame.botMove(), myMoveForSecondRound);
                }
            }

            System.out.printf("Rock/Paper/Scissors score for first round: %d and final score for second round is: %d", score, finalScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
