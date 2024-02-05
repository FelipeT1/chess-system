package application;

import chesslayer.ChessPiece;
import chesslayer.ChessPosition;
import chesslayer.enums.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    // códigos para cores
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // cores do fundo
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static ChessPosition readChessPosition(Scanner teclado){
        try{
            String s = teclado.nextLine();
            char chessCol = s.charAt(0);
            int  chessrow = Integer.parseInt(s.substring(1));
            return new ChessPosition(chessCol,chessrow);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error in UIChessPosition: Valores válidos são de a1 até h8");
        }

    }
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    // Só funcionará em terminais que suportem códigos ANSI
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printBoard(ChessPiece[][] pieces){
        for(int i = 0; i < pieces.length ; i++){
            System.out.printf("%d  ", pieces.length-i);
            for(int c = 0; c < pieces.length; c++){
                printPiece(pieces[i][c]);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }
    public static void printPiece(ChessPiece piece){
        if(piece == null){
            System.out.print("-");
        }
        else{
            if(piece.getColor() == Color.YELLOW){
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
            else{
                System.out.print(ANSI_GREEN + piece + ANSI_RESET);
            }
        }
        System.out.print("  ");
    }
}
