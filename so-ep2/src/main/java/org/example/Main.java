package org.example;

import org.example.helpers.IOHelper;
import org.example.helpers.RandomnessHelper;

class Main {

    public static void main(String[] args) {
        RandomnessHelper.getShuffledReadersAndWriters(10);
        System.out.println(IOHelper.readDatabase().size());

    }

}
