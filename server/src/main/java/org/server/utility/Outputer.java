package org.server.utility;

import org.example.utility.Printable;

public class Outputer implements Printable {
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void print_error(Object obj) {
        System.out.println(obj);
    }
}
