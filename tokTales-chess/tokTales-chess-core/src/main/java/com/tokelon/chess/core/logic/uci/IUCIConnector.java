package com.tokelon.chess.core.logic.uci;

import java.io.Closeable;

import java9.util.stream.Stream;

public interface IUCIConnector extends Closeable {


    public void send(String input);

    public Stream<String> send(String input, String until);

    public Stream<String> receive(String until);


    public Stream<String> readInput();

}
