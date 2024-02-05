package chesslayer.exceptions;

import boardlayer.exceptions.BoardException;


// Uma excessão de xadrez é uma de tabuleiro
public class ChessException extends BoardException {
    public ChessException(String message) {
        super(message);
    }
}
