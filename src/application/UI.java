package application;

import chesslayer.ChessMatch;
import chesslayer.ChessPiece;
import chesslayer.ChessPosition;
import chesslayer.enums.Color;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    public static void printCapturedPieces(List<ChessPiece> capturedPieces){
        System.out.println("Captured Pieces: ");
        List<ChessPiece> yellow = capturedPieces.stream().filter(x->x.getColor().equals(Color.YELLOW)).collect(Collectors.toList());
        List<ChessPiece> green = capturedPieces.stream().filter(x->x.getColor().equals(Color.GREEN)).collect(Collectors.toList());
        System.out.print(ANSI_YELLOW );
        System.out.printf(yellow.toString());
        System.out.println(ANSI_RESET);

        System.out.print(ANSI_GREEN );
        System.out.printf(green.toString());
        System.out.println(ANSI_RESET);

    }
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces){
        printBoard(chessMatch.getPieces());
        printCapturedPieces(capturedPieces);
        System.out.printf("Turno: %d\n", chessMatch.getTurn());
        if(chessMatch.isCheck()){
            System.out.println("JOGADOR " + chessMatch.getCurrentPlayer() +  " CHECK!");
        }
        if(chessMatch.getCurrentPlayer().equals(Color.YELLOW)){
            System.out.printf(ANSI_YELLOW + "Player : %s\n", chessMatch.getCurrentPlayer() + ANSI_RESET);
        }
        else{
            System.out.printf(ANSI_GREEN + "Player : %s\n", chessMatch.getCurrentPlayer() + ANSI_RESET);
        }
    }
    public static void printBoard(ChessPiece[][] pieces){
        for(int i = 0; i < pieces.length ; i++){
            System.out.printf("%d  ", pieces.length-i);
            for(int c = 0; c < pieces.length; c++){
                printPiece(pieces[i][c],false);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for(int i = 0; i < pieces.length ; i++){
            System.out.printf("%d  ", pieces.length-i);
            for(int c = 0; c < pieces.length; c++){
                printPiece(pieces[i][c], possibleMoves[i][c]);
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }
    public static void printPiece(ChessPiece piece, boolean background){
        if(background){
            System.out.print(ANSI_PURPLE_BACKGROUND);
        }
        if(piece == null){
            System.out.print("-" + ANSI_RESET);
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
