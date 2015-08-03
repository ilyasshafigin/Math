/*
 *
 */
package ru.ildev.math;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс, содержащий огромное количество математических функций. В них входят более быстрые стандартные функции,
 * а также дополнительные.
 * @version 2.10.16
 * @author Ilyas74
 */
public final class MoreMath {

    /** Генератор случайных чисел. */
    public static final Random random = new Random();
    /** Генератор шума Перлина. */
    public static final Noise noise = new Noise();

    /** Число <i>e</i>, основание натуральных логарифмов. <i>e</i> = {@value #E}. */
    public static final float E = 2.71828182845904523536f;
    /** Натуральный логарифм числа 2. <i>log 2</i> = {@value #LN_2}. */
    public static final float LN_2 = 0.69314718055994530941f;
    /** Натуральный логарифм числа 10. <i>log 10</i> = {@value #LN_10}. */
    public static final float LN_10 = 2.30258509299404568402f;

    /**
     * Число PI. Оно представляет собой отношение длины окружности к ее
     * диаметру. <i>pi</i> = {@value #PI}.
     */
    public static final float PI = 3.14159265358979323846f;
    /** Удвоенное PI. <i>two pi</i> = <i>2 * pi</i> = {@value #TWO_PI}. */
    public static final float TWO_PI = PI * 2.0f;// PI*2
    /** Половина PI. <i>half pi</i> = <i>pi / 2</i> = {@value #HALF_PI}. */
    public static final float HALF_PI = PI / 2.0f;
    /** Треть PI. <i>third pi</i> = <i>pi / 3</i> = {@value #THIRD_PI}. */
    public static final float THIRD_PI = PI / 3.0f;
    /** Четверть PI. <i>quarter pi</i> = <i>pi / 4</i> = {@value #QUARTER_PI}. */
    public static final float QUARTER_PI = PI / 4.0f;
    /** Три четвертых PI. <i>three halves pi</i> = <i>3/4 * pi</i> = {@value #THREE_HALVES_PI}. */
    public static final float THREE_HALVES_PI = TWO_PI - HALF_PI;// 3/4 * PI

    /** Корень квадратный из 2. <i>sqrt 2</i> = {@value #SQRT_2}. */
    public static final float SQRT_2 = 1.4142135623730950488f;
    /** Единица, деленная на корень квадратный из 2. <i>1/(sqrt 2)</i> = {@value #INV_SQRT_2}. */
    public static final float INV_SQRT_2 = 1.0f / SQRT_2;

    /** *  <i>1/3</i> = {@value #THRID}. */
    public static final float THRID = 1.0f / 3.0f;
    /** Корень квадратный 1/3. <i>sqrt(1/3)</i> = {@value #SQRT_INV_3}. */
    public static final float SQRT_INV_3 = 0.5773502691896257645f;

    /** <i>1 <<<< 23</i> = {@value #SHIFT_23}. */
    private static final float SHIFT_23 = 1 << 23;
    /** <i>1 / (1 <<<< 23)</i> = {@value #INV_SHIFT_23}. */
    private static final float INV_SHIFT_23 = 1.0f / SHIFT_23;

    /**
     * Минимальная величина или предел. Задает точность вычислений.
     * Он равен {@value #EPSILON}.
     */
    public static final float EPSILON = 1.1102230246251565E-16f;

    /** Коэффициент конвертации угла в радианах в угол в градусах. */
    public static final float RAD_DEG = 180.0f / PI;
    /** Коэффициент конвертации угла в градусах в угол в радианах. */
    public static final float DEG_RAD = PI / 180.0f;

    /**
     * Флаг, определяющий, будут ли включены таблицы синусов и косинусов, т.е.
     * значения синусов и косинусов будут браться из таблицы, при этом значения
     * могут быть не точными, но может увеличиться произодительность .
     */
    public static boolean LUT_ENABLED = true;
    /**
     * Флаг линейной интерполяции значений таблиц синусов и косинусов. Если он
     * включен, то значения синусов и косинусов буден возвращаться не строго
     * из таблиц, а будут расчитываться между этими значениями.
     */
    public static boolean LUT_LERP = true;

    private static final int TABLE_SIZE_BITS = 12;
    private static final int TABLE_SIZE = 1 << TABLE_SIZE_BITS;
    private static final int TABLE_SIZE_MASK = TABLE_SIZE - 1;
    private static final float CONVERSION_FACTOR = TABLE_SIZE / TWO_PI;

    /** Таблица значений синусов и косинусов от 0 до 2*PI. */
    private static final float[] sinTable = new float[TABLE_SIZE];
    private static final float[] cosTable = new float[TABLE_SIZE];
    static {
        for(int i = 0; i < TABLE_SIZE; i++) {
            sinTable[i] = (float) StrictMath.sin(i / CONVERSION_FACTOR);
            cosTable[i] = (float) StrictMath.cos(i / CONVERSION_FACTOR);
        }
    }

    // -- Математика --------

    /**
     * Расчитывает абсолютное значение целого числа.
     * @param a целое число.
     * @return абсолютное значение целого числа.
     */
    public static int abs(int a) {
        // 1
        return (a > 0) ? a : -a;

        // 2 - slow
        //int b = a >> 31;
        //return (a ^ b) - b;
    }

    /**
     * Расчитывает абсолютное значение действительного числа.
     * @param a действительное число.
     * @return абсолютное значение действительного числа.
     */
    public static float abs(float a) {
        return a > 0.0f ? a : 0.0f - a;
    }

    /**
     * Расчитывает остаток от деления числа {@code a} на число {@code b}.
     * В отличии от оператора {@code %}, он может возвращать отрицательные числа.
     * @param a целое число.
     * @param b целое число.
     * @return a mod b.
     */
    public static int mod(int a, int b) {
        int n = a / b;

        a -= n * b;
        if(a < 0) {
            return a + b;
        } else {
            return a;
        }
    }

    /**
     * Расчитывает остаток от деления числа {@code a} на число {@code b}.
     * В отличии от оператора {@code %}, он может возвращать отрицательные числа.
     * @param a действительное число.
     * @param b действительное число.
     * @return a mod b.
     */
    public static float mod(float a, float b) {
        int n = (int) (a / b);

        a -= n * b;
        if(a < 0.0f) {
            return a + b;
        } else {
            return a;
        }
    }

    /**
     *
     * @param a целое число.
     * @param b целое число.
     * @return
     */
    public static int rem(int a, int b) {
        int n = fix(a / b);

        a -= n * b;
        if(a < 0) {
            return a + b;
        } else {
            return a;
        }
    }

    /**
     *
     * @param a действительное число.
     * @param b действительное число.
     * @return
     */
    public static float rem(float a, float b) {
        float n = fix(a / b);

        a -= n * b;
        if(a < 0.0f) {
            return a + b;
        } else {
            return a;
        }
    }

    /**
     *
     * @param a целое число.
     * @return
     */
    public static int fix(int a) {
        int sign = sign(a);
        if(sign == -1) {
            return ceil(a);
        } else if(sign == 1) {
            return floor(a);
        } else {
            return 0;
        }
    }

    /**
     *
     * @param a действительное число.
     * @return
     */
    public static float fix(float a) {
        int sign = sign(a);
        if(sign == -1) {
            return ceil(a);
        } else if(sign == 1) {
            return floor(a);
        } else {
            return 0.0f;
        }
    }

    /**
     * Получает {@code true}, если число является четным.
     * @param a целое число.
     * @return {@code true}, если число является четным.
     */
    public static boolean isEven(int a) {
        int i = rem(a, 2);
        return i == 0;
    }

    /**
     * Получает {@code true}, если число является четным.
     * @param a действительное число.
     * @return {@code true}, если число является четным.
     */
    public static boolean isEven(float a) {
        float i = rem(a, 2.0f);
        return i == 0.0f;
    }

    /**
     * Получает знак целого числа. Если число больше нуля, то 1, если меньше,
     * то -1, иначе 0.
     * @param a целое число.
     * @return знак числа.
     */
    public static int sign(int a) {
        return a < 0 ? -1 : a > 0 ? 1 : 0;
    }

    /**
     * Получает знак действительного числа. Если число больше нуля, то 1, если
     * меньше, то -1, иначе 0.
     * @param a действительное число.
     * @return знак числа.
     */
    public static int sign(float a) {
        return a < 0.0f ? -1 : a > 0.0f ? 1 : 0;
    }

    /**
     * Ступенчатая функция. Возвращает 0, если число ниже порога, 1, если выше.
     * @param a целое число.
     * @param high порог.
     * @return число от 0 до 1.
     */
    public static int step(int a, int high) {
        return a < high ? 0 : 1;
    }

    /**
     * Ступенчатая функция. Возвращает 0, если число ниже порога, 1, если выше.
     * @param a действительное число.
     * @param high порог.
     * @return число от 0 до 1.
     */
    public static float step(float a, float high) {
        return a < high ? 0.0f : 1.0f;
    }

    /**
     * Импульсная функция. Возвращает 1, если число принадлежит промежутку, 0,
     * если нет.
     * @param a целое число.
     * @param low верхний предел промежутка.
     * @param high нижний предел промежутка.
     * @return число от 0 до 1.
     */
    public static int pulse(int a, int low, int high) {
        return a < low || a >= high ? 0 : 1;
    }

    /**
     * Импульсная функция. Возвращает 1, если число принадлежит промежутку, 0,
     * если нет.
     * @param a действительное число.
     * @param low верхний предел промежутка.
     * @param high нижний предел промежутка.
     * @return число от 0 до 1.
     */
    public static float pulse(float a, float low, float high) {
        return a < low || a >= high ? 0.0f : 1.0f;
    }

    /**
     * Округляет число в меньшую сторону.
     * @param a действительное число.
     * @return ближайшее целое число аргумента.
     */
    public static int floor(float a) {
        int b = (int) a;
        if(a < 0.0f && a != b) b--;
        return b;
    }

    /**
     * Округляет число в большую сторону.
     * @param a действительное число.
     * @return ближайшее целое число аргумента.
     */
    public static int ceil(float a) {
        int b = (int) a;
        if(a > 0.0f && a != b) b++;
        return b;
    }

    /**
     * Округляет действительное число до целого числа.
     * @param a действительное число.
     * @return округленное число.
     */
    public static int round(float a) {
        return floor(a + 0.5f);
    }

    /**
     * Округляет действительное число до {@code n} знаков после точки.
     * <pre>
     * Например:
     * round(1234.567, 1)  == 1234.6
     * round(1234.567, 2)  == 1234.58
     * round(1234.567, 0)  == 1235.0
     * round(1234.567, -1) == 1230.0
     * round(1234.567, -2) == 1200.0
     * </pre>
     * @param a действительное число.
     * @param n количество знаков после точки, число больше нуля.
     * @return округленное число.
     */
    public static float round(float a, int n) {
        if(n == 0) {
            return floor(a + 0.5f);
        } else {
            // TODO pow(10, n) с отрицательным n возвращает NaN. Решено, но не проверено
            float pow = (float) Math.pow(10, abs(n));
            if(n < 0) pow = 1.0f / pow;
            return floor(a * pow + 0.5f) / pow;
        }
    }

    /**
     * Получает меньшее из двух целых чисел.
     * @param a первое целое число.
     * @param b второе целое число.
     * @return меньшее из целых чисел.
     */
    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    /**
     * Получает меньшее из двух действительных чисел.
     * @param a первое действительное число.
     * @param b второе действительное число.
     * @return меньшее из действительных чисел.
     */
    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    /**
     * Получает меньшее из трех целых чисел.
     * @param a первое целое число.
     * @param b второе целое число.
     * @param c третье целое число.
     * @return меньшее из целых чисел.
     */
    public static int min(int a, int b, int c) {
        return a < b ? (a < c ? a : c) : (b < c ? b : c);
    }

    /**
     * Получает меньшее из трех действительных чисел.
     * @param a первое действительное число.
     * @param b второе действительное число.
     * @param c третье действительное число.
     * @return меньшее из действительных чисел.
     */
    public static float min(float a, float b, float c) {
        return a < b ? (a < c ? a : c) : (b < c ? b : c);
    }

    /**
     * Получает меньшее из множества целых чисел.
     * @param n множество целых чисел.
     * @return меньшее из целых чисел.
     */
    public static int min(int[] n) {
        //if(n == null) throw new NullPointerException("n == null");
        int min = n[0];
        for(int i = 1; i < n.length; i++) {
            if(n[i] < min) {
                min = n[i];
            }
        }
        return min;
    }

    /**
     * Получает меньшее из множества действительных чисел.
     * @param n множество действительных чисел.
     * @return меньшее из действительных чисел.
     */
    public static float min(float[] n) {
        //if(n == null) throw new NullPointerException("n == null");
        float min = n[0];
        for(int i = 1; i < n.length; i++) {
            if(n[i] < min) {
                min = n[i];
            }
        }
        return min;
    }

    /**
     * Получает большее из двух целых чисел.
     * @param a первое целое число.
     * @param b второе целое число.
     * @return большее из целых чисел.
     */
    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Получает большее из двух действительных чисел.
     * @param a первое действительное число.
     * @param b второе действительное число.
     * @return большее из действительных чисел.
     */
    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    /**
     * Получает большее из трех целых чисел.
     * @param a первое целое число.
     * @param b второе целое число.
     * @param c третье целое число.
     * @return большее из целых чисел.
     */
    public static int max(int a, int b, int c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    /**
     * Получает большее из трех действительных чисел.
     * @param a первое действительное число.
     * @param b второе действительное число.
     * @param c третье действительное число.
     * @return большее из действительных чисел.
     */
    public static float max(float a, float b, float c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    /**
     * Получает большее из множества целых чисел.
     * @param n множество целых чисел.
     * @return большее из целых чисел.
     */
    public static int max(int[] n) {
        //if(n == null) throw new NullPointerException("n == null");
        int max = n[0];
        for(int i = 1; i < n.length; i++) {
            if(n[i] > max) {
                max = n[i];
            }
        }
        return max;
    }

    /**
     * Получает большее из множества действительных чисел.
     * @param n множество действительных чисел.
     * @return большее из действительных чисел.
     */
    public static float max(float[] n) {
        //if(n == null) throw new NullPointerException("n == null");
        float max = n[0];
        for(int i = 1; i < n.length; i++) {
            if(n[i] > max) {
                max = n[i];
            }
        }
        return max;
    }

    /**
     * Фиксирует целое число {@code a} на интервале {@code [low; high]}.
     * @param a целое число.
     * @param low верхний предел.
     * @param high нижний предел.
     * @return целое число, лежащее на интервале {@code [low; high]}.
     */
    public static int clamp(int a, int low, int high) {
        return a < low ? low : a > high ? high : a;
    }

    /**
     * Фиксирует действительное число {@code a} на интервале {@code [low; high]}.
     * @param a действительное число.
     * @param low верхний предел.
     * @param high нижний предел.
     * @return действительное число, лежащее на интервале {@code [low; high]}.
     */
    public static float clamp(float a, float low, float high) {
        return a < low ? low : a > high ? high : a;
    }

    /**
     * Расчитывает расстояние между двумя двухмерными точками.
     * @param x1 x координата первой точки.
     * @param y1 y координата первой точки.
     * @param x2 x координата второй точки.
     * @param y2 y координата второй точки.
     * @return расстояние между двумя двухмерными точками.
     */
    public static float dist(float x1, float y1, float x2, float y2) {
        float x = x1 - x2;
        float y = y1 - y2;
        return sqrt(x * x + y * y);
    }

    /**
     * Расчитывает расстояние между двумя трехмерными точками.
     * @param x1 x координата первой точки.
     * @param y1 y координата первой точки.
     * @param z1 z координата первой точки.
     * @param x2 x координата второй точки.
     * @param y2 y координата второй точки.
     * @param z2 z координата второй точки.
     * @return расстояние между двумя трехмерными точками.
     */
    public static float dist(float x1, float y1, float z1, float x2, float y2, float z2) {
        float x = x1 - x2;
        float y = y1 - y2;
        float z = z1 - z2;
        return sqrt(x * x + y * y + z * z);
    }

    /**
     * Расчитывает длину двухмерного вектора, считается, что начало вектора
     * есть начало координат.
     * @param x x координата вектора.
     * @param y y координата вектора.
     * @return длину двухмерного вектора.
     */
    public static float mag(float x, float y) {
        return sqrt(x * x + y * y);
    }

    /**
     * Расчитывает длину трехмерного вектора, считается, что начало вектора
     * есть начало координат.
     * @param x x координата первой точки.
     * @param y y координата первой точки.
     * @param z z координата первой точки.
     * @return длину трехмерного вектора.
     */
    public static float mag(float x, float y, float z) {
        return sqrt(x * x + y * y + z * z);
    }

    /**
     * Получает экспоненту числа.
     * @param a число.
     * @return экспоненту числа {@code a}.
     */
    public static float exp(float a) {
        return (float) StrictMath.exp(a);
    }

    /**
     * Функция линейной интерполяции целых чисел.
     * @param a первой целое число.
     * @param b второе целое число.
     * @param alpha коэффициент.
     * @return целое число, принадлежащее прямой ab.
     */
    public static int lerp(int a, int b, float alpha) {
        //return (1.0 - alpha) * a + alpha * b;
        return round(a + alpha * (b - a));
    }

    /**
     * Функция линейной интерполяции действительных чисел.
     * @param a первой действительное число.
     * @param b второе действительное число.
     * @param alpha коэффициент.
     * @return действительное число, принадлежащее прямой ab.
     */
    public static float lerp(float a, float b, float alpha) {
        //return (1.0 - alpha) * a + alpha * b;
        return a + alpha * (b - a);
    }

    /**
     * Переводит целое число из одного диапазона в другой диапазон.
     * @param a целое число.
     * @param low1 нижняя граница текущего диапазона.
     * @param high1 верхняя граница текущего диапазона.
     * @param low2 нижняя граница конечного диапазона.
     * @param high2 верхняя граница конечного диапазона.
     * @return целое число в новом диапазоне.
     */
    public static int map(int a, int low1, int high1, int low2, int high2) {
        return round(low2 + (float) (a - low1) / (high1 - low1) * (high2 - low2));
    }

    /**
     * Переводит действительное число из одного диапазона в другой диапазон.
     * @param a действительное число.
     * @param low1 нижняя граница текущего диапазона.
     * @param high1 верхняя граница текущего диапазона.
     * @param low2 нижняя граница конечного диапазона.
     * @param high2 верхняя граница конечного диапазона.
     * @return действительное число в новом диапазоне.
     */
    public static float map(float a, float low1, float high1, float low2, float high2) {
        return low2 + (a - low1) / (high1 - low1) * (high2 - low2);
    }

    /**
     * Нормализует число из другого диапазона в диапазоне от 0 до 1.
     * @param a число.
     * @param low нижняя граница текущего диапазона.
     * @param high верхняя граница текущего диапазона.
     * @return нормализованное число.
     */
    public static float norm(float a, float low, float high) {
        return (a - low) / (high - low); // == map(a, low, high, 0.0, 1.0);
    }

    /**
     * Расчитывает логарифм числа {@code a} по основанию {@code base}.
     * @param base основание логарифма.
     * @param a число.
     * @return логарифм числа {@code a} по основанию {@code base}.
     */
    public static float log(float base, float a) {
        return (float) (StrictMath.log(a) / StrictMath.log(base));
    }

    /**
     * Расчитывает натуральный логарифм числа {@code a}.
     * @param a число.
     * @return натуральный логарифм числа {@code a}.
     */
    public static float log(float a) {
        return (float) StrictMath.log(a);
    }

    /**
     * Расчитывает десятичный логарифм числа {@code a}.
     * @param a число.
     * @return десятичный логарифм числа {@code a}.
     */
    public static float lg(float a) {
        return (float) StrictMath.log10(a);
    }

    /**
     * Возводит число {@code a} в степень {@code b}.
     * @param a число.
     * @param b степень.
     * @return число, возведенное в степень {@code b}.
     */
    public static float pow(float a, float b) {
        //1 - adapted from: http://www.dctsystems.co.uk/Software/power.html
        float x = Float.floatToRawIntBits(a);
        x *= INV_SHIFT_23;
        x -= 127.0f;

        float y = x - floor(x);
        b *= x + (y - y * y) * 0.346607f;
        y = b - floor(b);
        y = (y - y * y) * 0.33971f;

        return Float.intBitsToFloat((int) ((b + 127.0f - y) * SHIFT_23));

        //2 - возвращаемое число полностью совпадает с возвращаемым числом
        // стандартного метода, но этот метод очень долго считает.
        /*
        float r = 1;
        while(b != 0.0) {
            if(b % 2 == 0) {
                b /= 2;
                a *= a;
            } else {
                b--;
                r *= a;
            }
        }
        return r;
        */
    }

    /**
     * Расчитывает квадрат целого числа.
     * @param a целое число.
     * @return квадрат целого числа.
     */
    public static int sq(int a) {
        return a * a;
    }

    /**
     * Расчитывает квадрат действительного числа.
     * @param a действительное число.
     * @return квадрат действительного числа.
     */
    public static float sq(float a) {
        return a * a;
    }

    /**
     * Расчитывает куб целого числа.
     * @param a целое число.
     * @return куб целого числа.
     */
    public static int cub(int a) {
        return a * a * a;
    }

    /**
     * Расчитывает куб действительного числа.
     * @param a действительное число.
     * @return куб действительного числа.
     */
    public static float cub(float a) {
        return a * a * a;
    }

    /**
     * Расчитывает корень квадратный целого числа.
     * @param a целое число.
     * @return корень квадратный целого числа.
     */
    public static int sqrt(int a) {
        // По методу Ньютона
        int temp, div, result = a;

        if(a <= 0) {
            return 0;
        } else if((a & 0xFFFF0000) > 0) {
            if((a & 0xFF000000) > 0) {
                div = 0x3FFF;
            } else {
                div = 0x3FF;
            }
        } else if((a & 0x0FF00) > 0) {
            div = 0x3F;
        } else {
            div = a > 4 ? 0x7 : a;
        }

        while(a > 0) {
            temp = a / div + div;
            div = (temp >> 1) + (temp & 1);

            if(result > div) {
                result = div;
            } else {
                if(a / result == result - 1 && a % result == 0) {
                    result--;
                }
                return result;
            }
        }
        return result;
    }

    /**
     * Расчитывает корень квадратный действительного числа.
     * @param a действительное число.
     * @return корень квадратный действительного числа.
     */
    public static float sqrt(float a) {
        // Алгоритм Ньютона
        int sp = 0;
        boolean inv = false;
        float result, b;

        if(a <= 0.0f) {
            return 0.0f;
        } else if(a < 1.0f) {
            a = 1.0f / a;
            inv = true;
        }

        while(a > 16.0f) {
            sp++;
            a /= 16.0f;
        }

        result = 2.0f;
        int ITERATIONS = 4;
        for(int i = ITERATIONS; i > 0; i--) {
            b = a / result;
            result += b;
            result *= 0.5f;
        }

        while(sp > 0) {
            sp--;
            result *= 4.0f;
        }

        return inv ? 1.0f / result : result;
    }

    /**
     * Расчитывает корень кубический целого числа.
     * @param a цулого число.
     * @return корень кубический цулого числа.
     */
    public static int cbrt(int a) {
        return round((float) StrictMath.cbrt(a));
    }

    /**
     * Расчитывает корень кубический действительного числа.
     * @param a действительное число.
     * @return корень кубический действительного числа.
     */
    public static float cbrt(float a) {
        return (float) StrictMath.cbrt(a);
    }

    /**
     * Получает ближайшее число, которое является степенью двойки.
     * @param a целое число.
     * @return ближайшее число, являющееся степенью двойки.
     */
    public static int nextPowerOfTwo(int a) {
        a |= a >> 1;
        a |= a >> 2;
        a |= a >> 4;
        a |= a >> 8;
        a |= a >> 16;
        return a + 1;
    }

    /**
     * Получает {@code true}, если число является степенью 2.
     * @param a целое число.
     * @return {@code true}, если число является степенью 2.
     */
    public static boolean isPowerOfTwo(int a) {
        return a > 0 && (a & a - 1) == 0;
    }

    /**
     * Получает число в битах полученное из целого числа.
     * @param a целое число.
     * @return число в битах.
     */
    public static int getBitCount(int a) {
        int count = 0;
        while(a > 0) {
            count += a & 1;
            a >>= 1;
        }
        return count;
    }

    /**
     * Определяет, одинаковы ли числа со стандартной ошибкой.
     * @param a первое число.
     * @param b втрое число.
     * @return {@code true}, если числа одинаковы.
     */
    public static boolean equals(float a, float b) {
        return equals(a, b, EPSILON);
    }

    /**
     * Определяет, одинаковы ли числа.
     * @param a первое число.
     * @param b втрое число.
     * @param error значение ошибки.
     * @return {@code true}, если числа одинаковы.
     */
    public static boolean equals(float a, float b, float error) {
        return abs(a - b) < error;
    }

    // -- Тригонометрия ----------

    /**
     * Вычисляет синус угла.
     * @param a угол в радианах.
     * @return синус угла от -1 до 1.
     */
    public static float sin(float a) {
        if(LUT_ENABLED) {
            int index = (int) (a * CONVERSION_FACTOR) & TABLE_SIZE_MASK;
            if(LUT_LERP) {
                return (1.0f - a) * sinTable[index] + a * sinTable[index + 1 & TABLE_SIZE_MASK];
            } else {
                return sinTable[index];
            }
        } else {
            return (float) StrictMath.sin(a);
        }
    }

    /**
     * Вычисляет косинус угла.
     * @param a угол в радианах.
     * @return косинус угла от -1 до 1.
     */
    public static float cos(float a) {
        if(LUT_ENABLED) {
            int index = (int) (a * CONVERSION_FACTOR) & TABLE_SIZE_MASK;
            if(LUT_LERP) {
                return (1.0f - a) * cosTable[index] + a * cosTable[index + 1 & TABLE_SIZE_MASK];
            } else {
                return cosTable[index];
            }
        } else {
            return (float) StrictMath.cos(a);
        }
    }

    /**
     * Вычисляет тангенс угла.
     * @param a угол в радианах.
     * @return тангенс угла.
     */
    public static float tan(float a) {
        return (float) StrictMath.tan(a);
    }

    /**
     * Вычисляет котангенс угла.
     * @param a угол в радианах.
     * @return котангенс угла.
     */
    public static float cot(float a) {
        return (float) (1.0 / StrictMath.tan(a));
    }

    /**
     * Вычисляет арксинус числа.
     * @param a число от -1 до 1.
     * @return арксинус числа, угол от <i>-pi/2</i> до <i>pi/2</i>.
     */
    public static float asin(float a) {
        return (float) StrictMath.asin(a);
    }

    /**
     * Вычисляет арккосинус числа.
     * @param a число от -1 до 1.
     * @return арккосинус числа, угол от 0 до <i>pi</i>.
     */
    public static float acos(float a) {
        return (float) StrictMath.acos(a);
    }

    /**
     * Вычисляет арктангенс числа.
     * @param a число.
     * @return арктангенс числа, угол от <i>-pi/2</i> до <i>pi/2</i>.
     */
    public static float atan(float a) {
        return (float) StrictMath.atan(a);
    }

    /**
     * Расчитывает угол поворота точки с координатами {@code (x;y)} вокруг оси OX
     * между (<i>-pi</i>; <i>pi</i>).
     * @param y x-координата точки.
     * @param x y-координата точки.
     * @return угол поворота точки от <i>-pi</i> до <i>pi</i>.
     */
    public static float atan2(float y, float x) {
        if(x == 0.0f) {
            if(y > 0.0f) {
                return HALF_PI;
            } else if(y == 0.0f) {
                return 0.0f;
            } else {
                return -HALF_PI;
            }
        }

        float atan;
        float z = y / x;
        if(abs(z) < 1.0f) {
            atan = z / (1.0f + 0.28f * z * z);
            if(x < 0.0f) {
                if(y < 0.0f) {
                    return atan - PI;
                } else {
                    return atan + PI;
                }
            }
        } else {
            atan = HALF_PI - z / (z * z + 0.28f);
            if(y < 0.0f) {
                return atan - PI;
            }
        }

        return atan;
    }

    /**
     * Преобразует угол в радианах в соответствующий угол в градусах.
     * @param a число, представляет собой угол в радианах.
     * @return угол в градусах.
     */
    public static float degrees(float a) {
        return a * RAD_DEG;
    }

    /**
     * Преобразует угол в градусах в соответствующий угол в радианах.
     * @param a число, представляет собой угол в градусах.
     * @return угол в радианах.
     */
    public static float radians(float a) {
        return a * DEG_RAD;
    }

    /**
     * Сокращает угол. Возвращает угол в пределах (<i>-pi</i>; <i>pi</i>).
     * @param theta угол в радианах.
     * @return угол между (<i>-pi</i>; <i>pi</i>).
     */
    public static float reduceAngle(float theta) {
        theta %= TWO_PI;

        if(abs(theta) > PI) {
            theta = theta - TWO_PI;
        }

        if(abs(theta) > HALF_PI) {
            theta = PI - theta;
        }

        return theta;
    }

    // -- Генераторы случайных чисел

    /**
     * Расчитывает случайное действительное число, лежащее между 0 и 1.
     * @return случайное действительное число, лежащее между 0 и 1.
     */
    public static float random() {
        return random.nextFloat();
    }

    /**
     * Расчитывает случайное целое число, лежащее между 0 и {@code n}.
     * @param max максимальная величина.
     * @return случайное целое число, лежащее между 0 и {@code n}.
     */
    public static int random(int max) {
        return random.nextInt(max);
    }

    /**
     * Расчитывает случайное целое число между {@code min} и {@code max}.
     * @param min минимальная величина.
     * @param max максимальная величина.
     * @return случайное целое число от {@code min} до {@code max}.
     */
    public static int random(int min, int max) {
        return floor(random.nextFloat() * (max - min + 1)) + min;
    }

    /**
     * Расчитывает случайное действительное число, лежащее между 0 и {@code n}.
     * @param max максимальная величина.
     * @return случайное действительное число от 0 до {@code n}.
     */
    public static float random(float max) {
        return random.nextFloat() * max;
    }

    /**
     * Расчитывает случайное действительное число между {@code min} и {@code max}.
     * @param min минимальная величина.
     * @param max максимальная величина.
     * @return случайное действительное число от {@code min} до {@code max}.
     */
    public static float random(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    /**
     * Получает действительное число, расчитанное по гауссовому распределению.
     * @return случайное действительное число, от -1 до 1.
     */
    public static float gaussian() {
        return (float) random.nextGaussian();
    }

    /**
     * Получает целое число, расчитанное по гауссовому распределению.
     * @param max максимальная величина.
     * @return целое число, от 0 до {@code max}.
     */
    public static int gaussian(int max) {
        //return round(map(random.nextGaussian(), -1.0, 1.0, 0.0, n));
        return round(((float) random.nextGaussian() + 1.0f) / 2.0f * max);
    }

    /**
     * Получает действительное число, расчитанное по гауссовому распределению.
     * @param max максимальная величина.
     * @return действительное число, от 0 до {@code max}.
     */
    public static float gaussian(float max) {
        //return map(gaussian(), -1.0, 1.0, 0.0, n);
        return ((float) random.nextGaussian() + 1.0f) / 2.0f * max;
    }

    /**
     * Получает целое число, расчитанное по гауссовому распределению.
     * @param min минимальная величина.
     * @param max максимальная величина.
     * @return целое число, от 0 до {@code n}.
     */
    public static int gaussian(int min, int max) {
        //return round(map(gaussian(), -1.0, 1.0, min, max));
        return round(min + ((float) random.nextGaussian() + 1.0f) / 2.0f * (max - min));
    }

    /**
     * Получает действительное число, расчитанное по гауссовому распределению.
     * @param min минимальная величина.
     * @param max максимальная величина.
     * @return действительное число, от 0 до {@code n}.
     */
    public static float gaussian(float min, float max) {
        //return map(gaussian(), -1.0, 1.0, min, max);
        return min + ((float) random.nextGaussian() + 1.0f) / 2.0f * (max - min);
    }

    /**
     * Генерирует одномерный шум по стандартным параметрам.
     * @param x действительное число.
     * @return случайное действительное число от 0 до 1.
     */
    public static float noise(float x) {
        return noise.noise(x);
    }

    /**
     * Генерирует двухмерный шум по стандартным параметрам.
     * @param x действительное число.
     * @param y действительное число.
     * @return случайное действительное число от 0 до 1.
     */
    public static float noise(float x, float y) {
        return noise.noise(x, y);
    }

    /**
     * Генерирует трехмерный шум по стандартным параметрам.
     * @param x действительное число.
     * @param y действительное число.
     * @param z действительное число.
     * @return случайное действительное число от 0 до 1.
     */
    public static float noise(float x, float y, float z) {
        return noise.noise(x, y, z);
    }

    /**
     * Генерирует четырехмерный шум по стандартным параметрам.
     * @param x действительное число.
     * @param y действительное число.
     * @param z действительное число.
     * @param w действительное число.
     * @return случайное действительное число от 0 до 1.
     */
    public static float noise(float x, float y, float z, float w) {
        return noise.noise(x, y, z, w);
    }

    /**
     * Определяет, имеет ли шанс случайное событие с указанной вероятность.
     * @param probability вероятность события от 0 до 1.
     * @return {@code true}, если событие имеет шанс.
     */
    public static boolean chance(float probability) {
        return random.nextFloat() <= probability;
    }

    // -- Другое ----------

    /**
     * Расчитывает количество цифр в целом числе.
     * <pre><code>
     * Например:
     * numberCount(10) == 2
     * numberCount(0) == 1
     * numberCount(999) == 3
     * </code></pre>
     * @param a целое число.
     * @return количество цифр в целомчисле.
     */
    public static int numberCount(int a) {
        if(a == 0) return 1;

        int value = a < 0 ? -a : a;
        int count = 0;
        while(value > 0) {
            count++;
            value /= 10;
        }
        return count;
    }

    /**
     * Расчитывает количество цифр в действительном числе. Дробная часть не
     * считается.
     * <pre><code>
     * numberCount(10.0) == 2
     * numberCount(0.0) == 1
     * numberCount(999.9) == 3
     * numberCount(0.1) == 0
     * numberCount(333.156) == 3
     * </code></pre>
     * @param a действителное число.
     * @return количество цифр в действительном числе.
     */
    public static int numberCount(float a) {
        if(a == 0.0) return 1;

        int value = (int) (a < 0 ? -a : a);
        int count = 0;
        while(value > 0.0) {
            count++;
            value /= 10;
        }
        return count;
    }

    /**
     * Сигмоидная функция.
     * <pre>
     *           1
     * f(x) = -------
     *        1 + <i>e</i><sup>-x</sup>
     * </pre>
     * @param a число.
     * @return новое число.
     */
    public static float sigmoid(float a) {
        return 1.0f / (1.0f + exp(-a));
    }

    /**
     * Функция нормального (Гаусовского) распределения.
     * @param x параметр.
     * @param mu математическое ожидание, медиана и мода распределения.
     * @param sigma стандартное отклонение распределения.
     * @return значение функции.
     */
    public static float gaussian(float x, float mu, float sigma) {
        return exp(-(0.5f*(x-mu)*(x-mu))/(sigma*sigma)) / (sigma*sqrt(TWO_PI));
    }

    /**
     * Определяет, валидно ли число, т.е. не является ли число {@code Infinity}
     * или {@code NaN}.
     * @param a число.
     * @return {@code true} если число валидно.
     */
    public static boolean isValid(float a) {
        return !Double.isInfinite(a) && !Double.isNaN(a);
    }

    /**
     * Меняет местами элементы массива.
     * @param array массив класса {@code Integer}.
     * @param a индекс первого элемента.
     * @param b индекс второго элемента.
     */
    public static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    /**
     * Меняет местами элементы массива.
     * @param array массив класса {@code Double}.
     * @param a индекс первого элемента.
     * @param b индекс второго элемента.
     */
    public static void swap(double[] array, int a, int b) {
        double tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    /**
     * Меняет местами элементы массива.
     * @param array массив класса {@code Float}.
     * @param a индекс первого элемента.
     * @param b индекс второго элемента.
     */
    public static void swap(float[] array, int a, int b) {
        float tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    /**
     * Получает медиану множества целых чисел.
     * @param values множество целых чисел.
     * @return медиану множества целых чисел.
     */
    public static int median(int... values) {
        if(values == null) throw new NullPointerException("values == null");
        assert values.length != 0 : "values.length == 0";

        if(values.length == 1) {
            return values[0];
        } else {
            int[] v = new int[values.length];
            System.arraycopy(values, 0, v, 0, values.length);
            Arrays.sort(v);

            if(isEven(v.length)) {
                int i = round(v.length / 2.0f);
                return v[i];
            } else {
                return v[v.length / 2];
            }
        }
    }

    /**
     * Получает медиану множества действительных чисел.
     * @param values множество действительных чисел.
     * @return множества действительных чисел.
     */
    public static float median(float... values) {
        if(values == null) throw new NullPointerException("values == null");
        assert values.length != 0 : "values.length == 0";

        if(values.length == 1) {
            return values[0];
        } else {
            float[] v = new float[values.length];
            System.arraycopy(values, 0, v, 0, values.length);
            Arrays.sort(v);

            if(isEven(v.length)) {
                int i = ceil(v.length / 2.0f);
                float n1 = v[i];
                float n0 = v[i - 1];
                return (n0 + n1) / 2.0f;
            } else {
                return v[v.length / 2];
            }
        }
    }

    /**
     * Расчитывает сумму множества целых чисел.
     * @param values множество целых чисел.
     * @return сумму множества целых чисел.
     */
    public static int sum(int... values) {
        if(values == null) throw new NullPointerException("values == null");

        int sum = 0;
        for(int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum;
    }

    /**
     * Расчитывает сумму множества действительных чисел.
     * @param values множество действительных чисел.
     * @return сумму множества действительных чисел.
     */
    public static float sum(float... values) {
        if(values == null) throw new NullPointerException("values == null");

        float sum = 0.0f;
        for(int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum;
    }

    /**
     * Получает кумулятивную сумму множества целых чисел.
     * @param values множемтво целых чисел.
     * @return кумулятивную сумму множества целых чисел.
     */
    public static int[] cumSum(int... values) {
        if(values == null) throw new NullPointerException("values == null");
        int[] sum = new int[values.length];

        for(int i = 0; i < values.length; i++) {
            if(i == 0) {
                sum[i] = values[0];
            } else {
                sum[i] = sum[i - 1] + values[i];
            }
        }

        return sum;
    }

    /**
     * Получает кумулятивную сумму множества действительных чисел.
     * @param values множество действительных чисел.
     * @return кумулятивную сумму множества действительных чисел.
     */
    public static float[] cumSum(float... values) {
        if(values == null) throw new NullPointerException("values == null");
        float[] sum = new float[values.length];

        for(int i = 0; i < values.length; i++) {
            if(i == 0) {
                sum[i] = values[0];
            } else {
                sum[i] = sum[i - 1] + values[i];
            }
        }

        return sum;
    }

}
