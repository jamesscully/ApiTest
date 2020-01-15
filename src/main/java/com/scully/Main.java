package com.scully;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        switch (args[0]) {
            case "--taskA":
                Part1A.main(Arrays.copyOfRange(args, 1, args.length));
                break;

            case "--taskB":
                Part1B.main(Arrays.copyOfRange(args, 1, args.length));
                break;

            case "--taskC":
                Part1C.main(Arrays.copyOfRange(args, 1, args.length));
                break;

            case "--server":
                Task2.main(args);
                break;

            default:
                System.err.println("Please specify an option from: --taskA --taskB --taskC --server");
        }
    }
}
