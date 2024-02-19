package boardlayer;

public abstract class Piece {
    /*
    Essa classe é genérica e existe para propósitos de organização. Ela representa o estado mais abstrato de uma peça de xadrez.

    OOP: Associação (Piece tem um Board e tem um Position), Encapsulamento.
     */
    protected Position position;
    private Board board;
    public Piece(Board board) {
        this.board = board;
    }
    protected Board getBoard() {
        return board;
    }
    public abstract boolean[][] possibleMoves();
    // testa se uma peça pode se mover para uma dada posição
    // isso é um hook method, pois utiliza uma classe abstrata em sua implementação
    // só fará sentido para o programa  quando a abstrata for implementada em uma subclasse
    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getCol()];
    }

    // verifica se a peça está presa
    // Mais um exemplo de hook method,
    // Onde temos uma implementação concreta que UTILIZA
    // De um método abstrato.
    public boolean isMovingPossible(){
        for(boolean[] b : possibleMoves()){
            for(boolean move : b){
                if(move){
                    return true;
                }
            }
        }
        return false;
    }
}
