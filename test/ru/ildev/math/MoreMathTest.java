package ru.ildev.math;

import org.junit.Test;

/**
 * @author Ilyas Shafigin
 * @since 03.08.15
 */
public class MoreMathTest {

    @Test
    public void testAbsI() throws Exception {
        System.out.println("test abs(int):");
        long time;

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            MoreMath.abs(-i);
        }
        System.out.println("\tfast:" + (System.nanoTime() - time));

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            Math.abs(-i);
        }
        System.out.println("\tslow:" + (System.nanoTime() - time));
    }

    @Test
    public void testAbsF() throws Exception {
        System.out.println("test abs(float):");
        long time;

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            MoreMath.abs((float) (-i));
        }
        System.out.println("\tfast:" + (System.nanoTime() - time));

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            Math.abs((float) (-i));
        }
        System.out.println("\tslow:" + (System.nanoTime() - time));
    }

    @Test
    public void testFloor() throws Exception {
        System.out.println("test floor(int):");
        long time;

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            MoreMath.floor(i * 0.001f);
        }
        System.out.println("\tfast:" + (System.nanoTime() - time));

        time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            Math.floor(i * 0.001f);
        }
        System.out.println("\tslow:" + (System.nanoTime() - time));
    }

    @Test
    public void testCeil() throws Exception {

    }

    @Test
    public void testRound() throws Exception {

    }

    @Test
    public void testPow() throws Exception {

    }

    @Test
    public void testSqrtI() throws Exception {

    }

    @Test
    public void testSqrtF() throws Exception {

    }

    @Test
    public void testSin() throws Exception {

    }

    @Test
    public void testCos() throws Exception {

    }

    @Test
    public void testTan() throws Exception {

    }

    @Test
    public void testAsin() throws Exception {

    }

    @Test
    public void testAcos() throws Exception {

    }

    @Test
    public void testAtan() throws Exception {

    }

    @Test
    public void testAtan2() throws Exception {

    }
}