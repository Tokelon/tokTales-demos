package com.tokelon.chess.core.entities;

public class Chesspiece implements IChesspiece {


    private final ChesspieceColor color;
    private final ChesspieceType type;

    public Chesspiece(ChesspieceColor color, ChesspieceType type) {
        this.color = color;
        this.type = type;
    }


    @Override
    public ChesspieceColor getColor() {
        return color;
    }

    @Override
    public ChesspieceType getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Chesspiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }

    public static String keyOf(IChesspiece chesspiece) {
        return chesspiece.getColor().toString() + "_" + chesspiece.getType().toString();
    }

    public static String keyOf(ChesspieceColor color, ChesspieceType type) {
        return color.toString() + "_" + type.toString();
    }

}
