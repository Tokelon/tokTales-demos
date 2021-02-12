package com.tokelon.chess.core.entities;

public interface IChessboard {


    /**
     * @return The number of fields on each of this chessboard's sides.
     */
    public int getSize();


    public IChesspiece getField(int x, int y);

    public void setField(int x, int y, IChesspiece piece);

    public boolean isFieldValid(int x, int y);

    public void movePiece(int fromX, int fromY, int toX, int toY);

}
