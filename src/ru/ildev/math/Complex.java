/*
 *
 */
package ru.ildev.math;

import ru.ildev.math.MoreMath;

/**
 * Класс комплексного числа. Комплексным числом называется пара чисел
 * <i>(a, b)</i>, где <i>a</i> называется действительной частью, а <i>b</i>
 * мнимой частью.
 * @version 0.0.1
 * @author Ilyas74
 */
public class Complex implements Cloneable {

    /** Действительная часть. */
    public float r = 0.0f;// real
    /** Мнимая часть. */
    public float i = 0.0f;// imaginary

    /**
     * Стандартный контруктор.
     */
    public Complex() {}

    /**
     *
     * @param r
     * @param i
     */
    public Complex(float r, float i) {
        this.r = r;
        this.i = i;
    }

    /**
     *
     * @param complex
     */
    public Complex(Complex complex) {
        this.r = complex.r;
        this.i = complex.i;
    }

    /**
     *
     * @return
     */
    public float getReal() {
        return this.r;
    }

    /**
     *
     * @param r
     * @return
     */
    public Complex setReal(float r) {
        this.r = r;
        return this;
    }

    /**
     *
     * @return
     */
    public float getImaginary() {
        return this.i;
    }

    /**
     *
     * @param i
     * @return
     */
    public Complex setImaginary(float i) {
        this.i = i;
        return this;
    }

    /**
     *
     * @param r
     * @param i
     * @return
     */
    public Complex set(float r, float i) {
        this.r = r;
        this.i = i;
        return this;
    }

    /**
     *
     * @param complex
     * @return
     */
    public Complex set(Complex complex) {
        this.r = complex.r;
        this.i = complex.i;
        return this;
    }

    /**
     *
     * @param complex
     * @return
     */
    public Complex copy(Complex complex) {
        this.r = complex.r;
        this.i = complex.i;
        return this;
    }

    @Override
    public Complex clone() {
        return new Complex(this.r, this.i);
    }

    /**
     *
     * @return
     */
    public Complex zero() {
        this.r = 0.0f;
        this.i = 0.0f;
        return this;
    }

    /**
     * a + b = (ar + br, ai + bi)
     * @param br
     * @param bi
     * @return
     */
    public Complex add(float br, float bi) {
        this.r += br;
        this.i += bi;
        return this;
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex add(Complex b) {
        this.r += b.r;
        this.i += b.i;
        return this;
    }

    /**
     *
     * @param br
     * @param bi
     * @return
     */
    public Complex sum(float br, float bi) {
        return new Complex(this.r + br, this.i + bi);
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex sum(Complex b) {
        return new Complex(this.r + b.r, this.i + b.i);
    }

    /**
     * a - b = (ar - br, ai - bi)
     * @param br
     * @param bi
     * @return
     */
    public Complex subtract(float br, float bi) {
        this.r -= br;
        this.i -= bi;
        return this;
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex subtract(Complex b) {
        this.r -= b.r;
        this.i -= b.i;
        return this;
    }

    /**
     *
     * @param br
     * @param bi
     * @return
     */
    public Complex difference(float br, float bi) {
        return new Complex(this.r - br, this.i - bi);
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex difference(Complex b) {
        return new Complex(this.r - b.r, this.i - b.i);
    }

    /**
     * a * b = (ar * br - ai * bi, ar * bi + ai * br)
     * @param br
     * @param bi
     * @return
     */
    public Complex multiply(float br, float bi) {
        float ar = this.r;
        float ai = this.i;

        this.r = ar * br - ai * bi;
        this.i = ar * bi + br * ai;

        return this;
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex multiply(Complex b) {
        float ar = this.r;
        float ai = this.i;

        this.r = ar * b.r - ai * b.i;
        this.i = ar * b.i + b.r * ai;

        return this;
    }

    /**
     *
     * @param br
     * @param bi
     * @return
     */
    public Complex product(float br, float bi) {
        return new Complex(this.r * br - this.i * bi, this.r * bi + br * this.i);
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex product(Complex b) {
        return new Complex(this.r * b.r - this.i * b.i, this.r * b.i + b.r * this.i);
    }

    /**
     *
     * @return
     */
    public Complex squared() {
        float r = this.r;
        float i = this.i;

        this.r = r * r - i * i;
        this.i = 2.0f * r * i;

        return this;
    }

    /**
     *
     * @return
     */
    public Complex getSquared() {
        return new Complex(this.r * this.r - this.i * this.i, 2.0f * this.r * this.i);
    }

    /**
     * a / b = ((ar * br + ai * bi) / (br * br + bi * bi),
     *         (br * ai - ar * bi) / (br * br + bi * bi))
     * @param br
     * @param bi
     * @return
     */
    public Complex divide(float br, float bi) {
        float ar = this.r;
        float ai = this.i;
        float x = 1.0f / (br * br + bi * bi);

        this.r = (ar * br + ai * bi) * x;
        this.i = (br * ai - ar * bi) * x;

        return this;
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex divide(Complex b) {
        float ar = this.r;
        float ai = this.i;
        float x = 1.0f / (b.r * b.r + b.i * b.i);

        this.r = (ar * b.r + ai * b.i) * x;
        this.i = (b.r * ai - ar * b.i) * x;

        return this;
    }

    /**
     *
     * @param br
     * @param bi
     * @return
     */
    public Complex quotient(float br, float bi) {
        float x = 1.0f / (br * br + bi * bi);
        return new Complex(
            (this.r * br + this.i * bi) * x,
            (br * this.i - this.r * bi) * x
        );
    }

    /**
     *
     * @param b
     * @return
     */
    public Complex quotient(Complex b) {
        float x = 1.0f / (b.r * b.r + b.i * b.i);
        return new Complex(
            (this.r * b.r + this.i * b.i) * x,
            (b.r * this.i - this.r * b.i) * x
        );
    }

    /**
     * |a| = sqrt(ar * ar + ai * ai)
     * @return
     */
    public float abs() {
        return MoreMath.sqrt(this.r * this.r + this.i * this.i);
    }

    /**
     * norm(a) = ar * ar + ai * ai
     * @return
     */
    public float norm() {
        return this.r * this.r + this.i * this.i;
    }

    /**
     *
     * @return
     */
    public float phase() {
        return MoreMath.atan2(this.i, this.r);
    }

    /**
     *
     * @param r
     * @param i
     * @return
     */
    public boolean equals(float r, float i) {
        return this.r == r && this.i == i;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        } else if(this == other) {
            return true;
        } else if(other instanceof Complex) {
            Complex complex = (Complex) other;
            return this.r == complex.r && this.i == complex.i;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.r) ^ (Double.doubleToLongBits(this.r) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.i) ^ (Double.doubleToLongBits(this.i) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[real=" + this.r + ",imaginary=" + this.i + "]";
    }

}
