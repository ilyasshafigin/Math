/*
 *
 */
package ru.ildev.math;

import java.util.Random;

/**
 * Класс генератора шума Перлина.
 *
 * @author Ilyas74
 * @version 1.4.5
 */
public class Noise {

    private static final int[][] G1 = {{-1}, {1}};
    private static final int[][] G2 = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static final int[][] G3 = {{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {-1, 1, 0}, {0, -1, 1}, {0, -1, -1}};
    private static final int[][] G4 = {{-1, -1, -1, 0}, {-1, -1, 1, 0}, {-1, 1, -1, 0}, {-1, 1, 1, 0}, {1, -1, -1, 0}, {1, -1, 1, 0}, {1, 1, -1, 0}, {1, 1, 1, 0}, {-1, -1, 0, -1}, {-1, 1, 0, -1}, {1, -1, 0, -1}, {1, 1, 0, -1}, {-1, -1, 0, 1}, {-1, 1, 0, 1}, {1, -1, 0, 1}, {1, 1, 0, 1}, {-1, 0, -1, -1}, {1, 0, -1, -1}, {-1, 0, -1, 1}, {1, 0, -1, 1}, {-1, 0, 1, -1}, {1, 0, 1, -1}, {-1, 0, 1, 1}, {1, 0, 1, 1}, {0, -1, -1, -1}, {0, -1, -1, 1}, {0, -1, 1, -1}, {0, -1, 1, 1}, {0, 1, -1, -1}, {0, 1, -1, 1}, {0, 1, 1, -1}, {0, 1, 1, 1}};

    private int[] p = new int[512];

    /**
     * Стандартная стойкость.
     */
    public static final float DEFAULT_PERSISTENCE = 0.5f;
    /**
     * Стандартная частота.
     */
    public static final float DEFAULT_FREQUENCY = 0.25f;
    /**
     * Стандартная амплитуда.
     */
    public static final float DEFAULT_AMPLITUDE = 1.0f;
    /**
     * Стандартное количесто октав.
     */
    public static final int DEFAULT_OCTAVES = 4;

    /**
     * Стойкость.
     */
    private float persistence;
    /**
     * Частота.
     */
    private float frequency;
    /**
     * Амплитуда.
     */
    private float amplitude;
    /**
     * Количество октав.
     */
    private int octaves;

    /**
     * Стандартный конструктор.
     */
    public Noise() {
        this.persistence = DEFAULT_PERSISTENCE;
        this.frequency = DEFAULT_FREQUENCY;
        this.amplitude = DEFAULT_AMPLITUDE;
        this.octaves = DEFAULT_OCTAVES;
        this.setSeed(System.nanoTime());
    }

    /**
     * Конструктор, устанавливающий сид генератору.
     *
     * @param seed сид.
     */
    public Noise(long seed) {
        this.persistence = DEFAULT_PERSISTENCE;
        this.frequency = DEFAULT_FREQUENCY;
        this.amplitude = DEFAULT_AMPLITUDE;
        this.octaves = DEFAULT_OCTAVES;
        this.setSeed(seed);
    }

    /**
     * Конструктор, устанавливающий параметры генератору.
     *
     * @param persistence стойкость.
     * @param frequency   частота.
     * @param amplitude   амплитупа.
     * @param octaves     октавы.
     */
    public Noise(float persistence, float frequency, float amplitude, int octaves) {
        this.persistence = persistence;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.octaves = octaves;
        this.setSeed(System.nanoTime());
    }

    /**
     * Конструктор, устанавливающий параметры генератору.
     *
     * @param seed        сид.
     * @param persistence стойкость.
     * @param frequency   частота.
     * @param amplitude   амплитупа.
     * @param octaves     октавы.
     */
    public Noise(long seed, float persistence, float frequency, float amplitude, int octaves) {
        this.persistence = persistence;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.octaves = octaves;
        this.setSeed(seed);
    }

    /**
     * Получает стойкость шума.
     *
     * @return стойкость.
     */
    public float getPersistence() {
        return this.persistence;
    }

    /**
     * Устанавливает стойкость шума.
     *
     * @param persistence стойкость.
     */
    public void setPersistence(float persistence) {
        this.persistence = persistence;
    }

    /**
     * Получает частоту шума.
     *
     * @return частоту.
     */
    public float getFrequency() {
        return this.frequency;
    }

    /**
     * Устанавливает частоту шума.
     *
     * @param frequency частота.
     */
    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    /**
     * Получает амплитуду шума.
     *
     * @return амплитуду.
     */
    public float getAmplitude() {
        return this.amplitude;
    }

    /**
     * Устанавливает амплитуду шума.
     *
     * @param amplitude амплитуда.
     */
    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    /**
     * Получает количество октав.
     *
     * @return количество октав.
     */
    public int getOctaves() {
        return this.octaves;
    }

    /**
     * Устанавливает количество октав.
     *
     * @param octaves количество октав.
     */
    public void setOctaves(int octaves) {
        this.octaves = octaves;
    }

    /**
     * Устанавливает сид.
     *
     * @param seed сид.
     */
    public void setSeed(long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < 256; i++) this.p[i] = i;
        for (int i = 0; i < 256; i++) {
            int j = random.nextInt() & 255;
            int t = this.p[j];
            this.p[j] = this.p[i];
            this.p[i] = t;
        }
        for (int i = 0; i < 256; i++) this.p[i + 256] = this.p[i];
    }

    /**
     * Генерирует одномерный шум.
     *
     * @param x число.
     * @return одномерный шум.
     */
    public float noise(float x) {
        float total = 0.0f;
        float frequency = this.frequency;
        float amplitude = this.amplitude;

        for (int i = 0; i < this.octaves; i++) {
            total += amplitude * (1.0f + this.noise1D(x * frequency)) / 2.0f;
            amplitude *= this.persistence;
            frequency *= 2.0f;
        }

        return total;
    }

    /**
     * Генерирует двухмерный шум.
     *
     * @param x первое число.
     * @param y второе число.
     * @return двухмерный шум
     */
    public float noise(float x, float y) {
        if (y == 0.0f) return this.noise(x);

        float total = 0.0f;
        float frequency = this.frequency;
        float amplitude = this.amplitude;

        for (int i = 0; i < this.octaves; i++) {
            total += amplitude * (1.0f + this.noise2D(x * frequency, y * frequency)) / 2.0f;
            amplitude *= this.persistence;
            frequency *= 2.0f;
        }

        return total;
    }

    /**
     * Генерирует трехмерный шум.
     *
     * @param x первое число.
     * @param y второе число.
     * @param z третье число.
     * @return двухмерный шум
     */
    public float noise(float x, float y, float z) {
        if (z == 0.0f) return this.noise(x, y);

        float total = 0.0f;
        float frequency = this.frequency;
        float amplitude = this.amplitude;

        for (int i = 0; i < this.octaves; i++) {
            total += amplitude * (1.0f + this.noise3D(x * frequency, y * frequency, z * frequency)) / 2.0f;
            amplitude *= this.persistence;
            frequency *= 2.0f;
        }

        return total;
    }

    /**
     * Генерирует четырехмерный шум.
     *
     * @param x первое число.
     * @param y второе число.
     * @param z третье число.
     * @param w четвертое число.
     * @return двухмерный шум
     */
    public float noise(float x, float y, float z, float w) {
        if (w == 0.0f) return this.noise(x, y, z);

        float total = 0.0f;
        float frequency = this.frequency;
        float amplitude = this.amplitude;

        for (int i = 0; i < this.octaves; i++) {
            total += amplitude * (1.0f + this.noise4D(x * frequency, y * frequency, z * frequency, w * frequency)) / 2.0f;
            amplitude *= this.persistence;
            frequency *= 2.0f;
        }

        return total;
    }

    /**
     * Получает одномерный шум.
     *
     * @param x число.
     * @return одномерный шум.
     */
    public float noise1D(float x) {
        int xf = MoreMath.floor(x);
        x -= xf;
        int X = xf & 255;

        float fx = fade(x);
        int A = this.p[X], B = this.p[X + 1];

        return lerp(fx, grad(this.p[A], x), grad(this.p[B], x - 1.0f));
    }

    /**
     * Получает двухмерный шум.
     *
     * @param x число.
     * @param y число.
     * @return двухмерный шум.
     */
    public float noise2D(float x, float y) {
        int xf = MoreMath.floor(x);
        x -= xf;
        int X = xf & 255;
        int yf = MoreMath.floor(y);
        y -= yf;
        int Y = yf & 255;

        float fx = fade(x);
        float fy = fade(y);
        int A = this.p[X] + Y, B = this.p[X + 1] + Y;

        return lerp(fy, lerp(fx, grad(this.p[A], x, y), grad(this.p[B], x - 1.0f, y)), lerp(fx, grad(this.p[A + 1], x, y - 1.0f), grad(this.p[B + 1], x - 1.0f, y - 1.0f)));
    }

    /**
     * Получает трехмерный шум.
     *
     * @param x число.
     * @param y число.
     * @param z число.
     * @return трехмерный шум.
     */
    public float noise3D(float x, float y, float z) {
        int xf = MoreMath.floor(x);
        x -= xf;
        int X = xf & 255;
        int yf = MoreMath.floor(y);
        y -= yf;
        int Y = yf & 255;
        int zf = MoreMath.floor(z);
        z -= zf;
        int Z = zf & 255;

        float fx = fade(x);
        float fy = fade(y);
        float fz = fade(z);
        int A = this.p[X] + Y, AA = this.p[A] + Z, AB = this.p[A + 1] + Z,
                B = this.p[X + 1] + Y, BA = this.p[B] + Z, BB = this.p[B + 1] + Z;

        return lerp(fz, lerp(fy, lerp(fx, grad(this.p[AA], x, y, z), grad(this.p[BA], x - 1.0f, y, z)), lerp(fx, grad(this.p[AB], x, y - 1.0f, z), grad(this.p[BB], x - 1.0f, y - 1.0f, z))), lerp(fy, lerp(fx, grad(this.p[AA + 1], x, y, z - 1.0f), grad(this.p[BA + 1], x - 1.0f, y, z - 1.0f)), lerp(fx, grad(this.p[AB + 1], x, y - 1.0f, z - 1.0f), grad(this.p[BB + 1], x - 1.0f, y - 1.0f, z - 1.0f))));
    }

    /**
     * Получает четырехмерный шум.
     *
     * @param x число.
     * @param y число.
     * @param z число.
     * @param w число.
     * @return четырехмерный шум.
     */
    public float noise4D(float x, float y, float z, float w) {
        int xf = MoreMath.floor(x);
        x -= xf;
        int X = xf & 255;
        int yf = MoreMath.floor(y);
        y -= yf;
        int Y = yf & 255;
        int zf = MoreMath.floor(z);
        z -= zf;
        int Z = zf & 255;
        int wf = MoreMath.floor(w);
        w -= wf;
        int W = wf & 255;

        float fx = fade(x);
        float fy = fade(y);
        float fz = fade(z);
        float fw = fade(w);
        int A = this.p[X] + Y, AA = this.p[A] + Z, AB = this.p[A + 1] + Z,
                B = this.p[X + 1] + Y, BA = this.p[B] + Z, BB = this.p[B + 1] + Z,
                AAA = this.p[AA] + W, AAB = this.p[AA + 1] + W, ABA = this.p[AB] + W,
                ABB = this.p[AB + 1] + W, BAA = this.p[BA] + W, BAB = this.p[BA + 1] + W,
                BBA = this.p[BB] + W, BBB = this.p[BB + 1] + W;

        return lerp(fw, lerp(fz, lerp(fy, lerp(fx, grad(this.p[AAA], x, y, z, w), grad(this.p[BAA], x - 1.0f, y, z, w)), lerp(fx, grad(this.p[ABA], x, y - 1.0f, z, w), grad(this.p[BBA], x - 1.0f, y - 1.0f, z, w))), lerp(fy, lerp(fx, grad(this.p[AAB], x, y, z - 1.0f, w), grad(this.p[BAB], x - 1.0f, y, z - 1.0f, w)), lerp(fx, grad(this.p[ABB], x, y - 1.0f, z - 1.0f, w), grad(this.p[BBB], x - 1.0f, y - 1.0f, z - 1.0f, w)))), lerp(fz, lerp(fy, lerp(fx, grad(this.p[AAA + 1], x, y, z, w - 1.0f), grad(this.p[BAA + 1], x - 1.0f, y, z, w - 1.0f)), lerp(fx, grad(this.p[ABA + 1], x, y - 1.0f, z, w - 1.0f), grad(this.p[BBA + 1], x - 1.0f, y - 1.0f, z, w - 1.0f))), lerp(fy, lerp(fx, grad(this.p[AAB + 1], x, y, z - 1.0f, w - 1.0f), grad(this.p[BAB + 1], x - 1.0f, y, z - 1.0f, w - 1.0f)), lerp(fx, grad(this.p[ABB + 1], x, y - 1.0f, z - 1.0f, w - 1.0f), grad(this.p[BBB + 1], x - 1.0f, y - 1.0f, z - 1.0f, w - 1.0f)))));
    }

    private static float fade(float t) {
        return t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f);
    }

    private static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    private static float grad(int hash, float x) {
        int h = hash & 1;
        return x * G1[h][0];
    }

    private static float grad(int hash, float x, float y) {
        int h = hash & 3;
        return x * G2[h][0] + y * G2[h][1];
    }

    private static float grad(int hash, float x, float y, float z) {
        int h = hash & 15;
        return x * G3[h][0] + y * G3[h][1] + z * G3[h][2];
    }

    private static float grad(int hash, float x, float y, float z, float w) {
        int h = hash & 31;
        return x * G4[h][0] + y * G4[h][1] + z * G4[h][2] + w * G4[h][3];
    }

}
