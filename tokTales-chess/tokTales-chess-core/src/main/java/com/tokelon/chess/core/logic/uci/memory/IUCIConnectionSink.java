package com.tokelon.chess.core.logic.uci.memory;

import java.io.OutputStream;
import java.util.List;

public interface IUCIConnectionSink {


    /** Blocks until an input line is found that contains the given terminator.
     *
     * @param terminator The value that will cause this method to return.
     *
     * @return A list of the input lines that were read up until the terminator (inclusive).
     */
    public List<String> receiveUntilLineContains(String terminator); // Add timeout parameter


    /** Retrieves and removes all available input lines from the sink.
     *
     * @return A list containing all input lines from the sink.
     */
    public List<String> readInputLines(); // Use default maxLines here
    //public List<String> readInputLines(int maxLines);



    /**
     * @return The output stream for this sink.
     */
    public OutputStream getOutputStream();

    //public InputStream getInputStream(); // Add this?
}
