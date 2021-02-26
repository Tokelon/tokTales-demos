package com.tokelon.chess.core.logic.uci;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import javax.inject.Inject;

import java9.util.Optional;

public class UCI implements IUCI {


    private final IUCIConnector uciConnector;

    private final ILogger logger;

    @Inject
    public UCI(ILogging logging, IUCIConnector uciConnector) {
        this.logger = logging.getLogger(getClass());
        this.uciConnector = uciConnector;
    }


    @Override
    public void uci() {
        String output = uciConnector.send("uci", "uciok");
        // id name x
        // id author x
        // option name id
        // option type t
        // option default x
        // option min x
        // option max x
        // option var x
        //option name Style type combo default Normal var Solid var Normal var Risky\n
        logger.info(output);
    }

    @Override
    public void debug() {

    }

    @Override
    public void isReady() {
        uciConnector.send("isready", "readyok");
    }

    @Override
    public void setOption(String id, String value) {

    }

    @Override
    public void uciNewGame() {

    }

    @Override
    public void position(String fen, String... moves) {
        if(fen == null) {
            uciConnector.send("position startpos");
        }
        else {
            StringBuilder builder = new StringBuilder();
            builder.append("position fen ");
            builder.append(fen);
            builder.append(" moves");
            for (String move: moves) {
                builder.append(' ');
                builder.append(move);
            }

            uciConnector.send(builder.toString());
        }
    }

    @Override
    public void go(long movetime) {
        uciConnector.send("go movetime " + movetime);
    }

    @Override
    public void go(GoCommandParameter parameter) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void ponderhit() {

    }

    @Override
    public void quit() {

    }


    @Override
    public String getLastMove() {
        Optional<String> bestmove = uciConnector.getInputLines().filter(s -> s.contains("bestmove")).findFirst();
        if(bestmove.isEmpty()) {
            return null;
        }

        String move = bestmove.get().split(" ")[1];
        return move;
    }

}
