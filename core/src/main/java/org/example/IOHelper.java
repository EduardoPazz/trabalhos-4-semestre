package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public enum IOHelper {
    ;

    public static BufferedReader getBufferedReader(final InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }

    public static BufferedWriter getBufferedWriter(final OutputStream is) {
        return new BufferedWriter(new OutputStreamWriter(is));
    }

    public static String getHelp() {
        return "help";
    }
}
