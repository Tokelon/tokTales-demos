package com.tokelon.chess.core.logic.uci.memory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueUCIConnectionSink implements IUCIConnectionSink {


    private final ConcurrentLinkedQueue<String> lineQueue;
    private final OutputStream output;
    private final QueueUCIOutputReader outputReader;

    public QueueUCIConnectionSink() {
        this.lineQueue = new ConcurrentLinkedQueue<>();
        this.output = new BufferOutputStream(lineQueue);
        this.outputReader = new QueueUCIOutputReader(lineQueue);
    }


    @Override
    public List<String> receiveUntilLineContains(String terminator) {
        List<String> lines;
        do {
            lines = outputReader.getLinesUntilContains(terminator);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (lines == null);
        return lines;
    }

    @Override
    public List<String> readInputLines() {
        return outputReader.getLines();
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }



    protected class QueueUCIOutputReader {


        private final ConcurrentLinkedQueue<String> lineQueue;

        public QueueUCIOutputReader(ConcurrentLinkedQueue<String> lineQueue) {
            this.lineQueue = lineQueue;
        }


        protected List<String> getLines() {
            ArrayList<String> lines = new ArrayList<>(lineQueue.size());
            String line;
            do {
                line = lineQueue.poll();
                if(line != null) {
                    lines.add(line);
                }
            }
            while(line != null);

            return lines;
        }

        protected List<String> getLinesUntilContains(String terminator) {
            for (String line: lineQueue) {
                if(line.contains(terminator)) {
                    return getLines();
                }
            }

            return null;
        }
    }


    protected class BufferOutputStream extends OutputStream {


        private final ByteArrayOutputStream buffer;

        private final ConcurrentLinkedQueue<String> queue;

        public BufferOutputStream(ConcurrentLinkedQueue<String> lineQueue) {
            this.queue = lineQueue;

            this.buffer = new ByteArrayOutputStream(256);
        }


        @Override
        public void write(int i) {
            if(i == '\n') {
                byte[] bytes = buffer.toByteArray();
                buffer.reset();

                String line = new String(bytes, StandardCharsets.UTF_8);
                queue.add(line);
            }
            else {
                buffer.write(i);
            }
        }
    }

}
