package com.tokelon.chess.core.entities;

public interface IChessboard {


    public void movePiece(int fromX, int fromY, int toX, int toY);


    /**
     * @return The number of fields on each of this chessboard's sides.
     */
    public int getSize();


    public IChesspiece getField(int x, int y);

    public void setField(int x, int y, IChesspiece piece);

    public boolean isFieldValid(int x, int y);


    public default char fieldToNotationX(int fieldX) {
        return (char)('a' + fieldX);
    }

    public default byte fieldToNotationY(int fieldY) {
        return (byte) (getSize() - fieldY);
    }

    public default int notationToFieldX(char notationX) {
        return notationX == '0' ? getSize() : (notationX - 'a');
    }

    public default int notationToFieldY(byte notationY) {
        return getSize() - notationY;
    }

}
