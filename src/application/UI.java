package application;

import chesslayer.ChessPiece;

public class UI {
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
            System.out.print(piece);
        }
        System.out.print("  ");
    }
}
