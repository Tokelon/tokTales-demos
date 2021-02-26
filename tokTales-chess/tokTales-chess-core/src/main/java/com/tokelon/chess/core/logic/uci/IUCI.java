package com.tokelon.chess.core.logic.uci;

public interface IUCI {


    public void uci();

    public void debug();

    public void isReady();

    public void setOption(String id, String value);

    //public void register();

    public void uciNewGame();

    //position [fen <fenstring> | startpos ]  moves <move1> .... <movei>
    public void position(String fen, String... moves);

    //public void goInfinite();
    public void go(long movetime);
    public void go(GoCommandParameter parameter);

    public void stop();

    public void ponderhit();

    public void quit();


    public String getLastMove();

}
