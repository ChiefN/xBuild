package com.example.xbulild.util;

import java.util.UUID;

public class Util {

    public static String createId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
