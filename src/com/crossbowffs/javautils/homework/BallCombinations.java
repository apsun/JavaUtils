package com.crossbowffs.javautils.homework;

public class BallCombinations {
    public static void main(String[] args) {
        final int people = 100;
        final int balls = 100;
        final int an = 3;
        final int bn = 2;
        final double cn = 0.5;

        for (int ax = 0; ax <= balls / an; ++ax) {
            int ballsLeftAfterA = balls - ax * an;

            for (int bx = 0; bx <= ballsLeftAfterA / bn; ++bx) {
                int ballsLeftAfterB = ballsLeftAfterA - bx * bn;

                int cx = (int)(ballsLeftAfterB / cn);
                if (ax + bx + cx != people) continue;

                System.out.println(String.format("A:%d B:%d C:%d", ax, bx, cx));
            }
        }
    }
}
