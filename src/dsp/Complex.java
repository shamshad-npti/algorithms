/*
 * Copyright (C) 2016 shamshad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dsp;

/**
 *
 * @author shamshad
 */
public class Complex {
    public double real;
    public double imag;

    public Complex() {
    }

    public static Complex rootOfUnity(int n) {
        return rootOfUnity(n, 1);
    }

    public static Complex rootOfUnity(int n, int m) {
        double angle = 2 * m * Math.PI / n;
        return new Complex(Math.cos(angle), Math.sin(angle));
    }

    public static Complex eye() {
        return new Complex(1.0, 0.0);
    }

    public Complex mult(Complex c) {
        return new Complex(real * c.real - imag * c.imag, 
                imag * c.real + c.imag * real);
    }

    public Complex add(Complex c) {
        return new Complex(real + c.real, imag + c.imag);
    }

    public Complex sub(Complex c) {
        return new Complex(real - c.real, imag - c.imag);
    }

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImag() {
        return imag;
    }

    public void setImag(double imag) {
        this.imag = imag;
    }

    public double len() {
        return Math.sqrt(real * real + imag * imag);
    }

    @Override
    public String toString() {
        return String.format("[ %.2f, %.2fi]", real, imag);
    }

}
