package com.crossbowffs.javautils.homework;

public class JosephsRing {
    public static void main(String[] args) {
        final int num = 7;
        final int step = 3;

        boolean[] people = new boolean[num];
        int index = 0;
        for (int killed = 0; killed < num - 1; ++killed) {
            for (int j = 0; j <= step; ++j) {
                index = (index + 1) % num;
                if (people[index]) --j;
            }
            people[index] = true;
            System.out.println("Killed: " + index);
        }
        for (int i = 0; i < num; ++i) {
            if (!people[i]) {
                System.out.println("Survivor: " + i);
                break;
            }
        }
    }
}
