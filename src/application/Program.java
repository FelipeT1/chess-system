package application;

import chesslayer.ChessMatch;
import chesslayer.ChessPosition;
import chesslayer.exceptions.ChessException;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            try{
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                chessMatch.performChessMove(source, target);
                UI.clearScreen();
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (RuntimeException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}

