package org.example.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializerToByteArray {
    public static byte[] serialize(Object obj) {
        try {
        ByteArrayOutputStream bf = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(bf);
        o.writeObject(obj);
            return bf.toByteArray();
        } catch (IOException e) {
            return null;
        }

    }
}

