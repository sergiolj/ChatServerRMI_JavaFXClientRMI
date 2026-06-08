package br.edu.ucsal.sergiolj.containers.model;

import java.util.List;

public class KeyboardLayout {

    public static List<KeySet> getNumericKeypadLabelsExtended(){
        return List.of(
                new KeySet("Num\nLock", 0,0,1,1),
                new KeySet("/", 0,1,1,1),
                new KeySet("*", 0,2,1,1),
                new KeySet("-", 0,3,1,1),

                new KeySet("7", 1,0,1,1),
                new KeySet("8", 1,1,1,1),
                new KeySet("9", 1,2,1,1),
                new KeySet("+", 1,3,2,1),

                new KeySet("4", 2,0,1,1),
                new KeySet("5", 2,1,1,1),
                new KeySet("6", 2,2,1,1),

                new KeySet("1", 3,0,1,1),
                new KeySet("2", 3,1,1,1),
                new KeySet("3", 3,2,1,1),
                new KeySet("Enter", 3,3,2,1),

                new KeySet("0", 4,0,1,2),
                new KeySet(".", 4,2,1,1)
        );
    }

    public static List<String> getNumericKeypadLabelsSmall(){
        return List.of(
                "7", "8", "9", "/",
                "4","5","6","*",
                "1", "2", "3", "-",
                "C","0", ".", "+"
        );
    }
}
