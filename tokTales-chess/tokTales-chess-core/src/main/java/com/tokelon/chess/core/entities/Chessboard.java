package com.tokelon.chess.core.entities;

public class Chessboard implements IChessboard {


    private final IChesspiece[][] board;

    public Chessboard() {
        this.board = new IChesspiece[8][8];
    }


    @Override
    public int getSize() {
        return board.length;
    }


    @Override
    public IChesspiece getField(int x, int y) {
        if(!isFieldValid(x, y)) {
            throw new IllegalArgumentException("x and y must be >= 0 and <= chessboard size");
        }

        return board[x][y];
    }

    @Override
    public void setField(int x, int y, IChesspiece piece) {
        if(!isFieldValid(x, y)) {
            throw new IllegalArgumentException("x and y must be >= 0 and <= chessboard size");
        }

        board[x][y] = piece;
    }

    @Override
    public boolean isFieldValid(int x, int y) {
        return 0 <= x && x < getSize() && 0 <= y && y < getSize();
    }


    @Override
    public void movePiece(int fromX, int fromY, int toX, int toY) {
        if(!isFieldValid(fromX, fromY) || !isFieldValid(toX, toY)) {
            throw new IllegalArgumentException("Coordinates must be >= 0 and <= chessboard size");
        }

        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
    }



    public static Chessboard createInitial() {
        Chessboard chessboard = new Chessboard();

        chessboard.setField(0, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.ROOK));
        chessboard.setField(1, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.KNIGHT));
        chessboard.setField(2, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.BISHOP));
        chessboard.setField(3, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.QUEEN));
        chessboard.setField(4, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.KING));
        chessboard.setField(5, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.BISHOP));
        chessboard.setField(6, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.KNIGHT));
        chessboard.setField(7, 0, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.ROOK));

        chessboard.setField(0, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(1, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(2, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(3, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(4, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(5, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(6, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));
        chessboard.setField(7, 1, new Chesspiece(ChesspieceColor.BLACK, ChesspieceType.PAWN));

        chessboard.setField(0, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(1, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(2, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(3, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(4, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(5, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(6, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));
        chessboard.setField(7, 6, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.PAWN));

        chessboard.setField(0, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.ROOK));
        chessboard.setField(1, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.KNIGHT));
        chessboard.setField(2, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.BISHOP));
        chessboard.setField(3, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.QUEEN));
        chessboard.setField(4, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.KING));
        chessboard.setField(5, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.BISHOP));
        chessboard.setField(6, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.KNIGHT));
        chessboard.setField(7, 7, new Chesspiece(ChesspieceColor.WHITE, ChesspieceType.ROOK));

        return chessboard;
    }

}
