package application;

import chesslayer.ChessMatch;
import chesslayer.ChessPiece;
import chesslayer.ChessPosition;
import chesslayer.exceptions.ChessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        List<ChessPiece> capturedPieces = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        UI.clearScreen();
        while (true) {
            try{
                UI.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if(capturedPiece!=null){
                    capturedPieces.add(capturedPiece);
                }
                UI.clearScreen();
            }
            catch (ChessException e){
                e.printStackTrace();
            }

        }
    }
}

