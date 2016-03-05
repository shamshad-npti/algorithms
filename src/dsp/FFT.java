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
public class FFT {
    
    public static Complex[] FFT(double[] coef, int base) {
        if((base & (base - 1)) != 0) return null;
        Complex[] cf = new Complex[coef.length];
        for (int i = 0; i < coef.length; i++) {
            cf[i] = new Complex(coef[i], 0.0);
        }
        return fft(cf, base, false);
    }

    public static Complex[] IFFT(Complex[] coef, int base) {
        return fft(coef, base, true);
    }
    
    private static Complex[] fft(Complex[] complex, int base, boolean inv) {
        Complex[] cf = new Complex[base];
        for (int i = 0; i < base; i++) {
            cf[i] = new Complex();
        }
        bitReversePermute(cf, complex, base);
        int n = Integer.numberOfTrailingZeros(base);
        int q = inv ? -1 : 1;
        for(int s = 1; s <= n; s++) {
            int m = 1 << s;
            Complex root = Complex.rootOfUnity(m, q);
            for(int k = 0; k < base; k += m) {
                Complex w = Complex.eye();
                int h = m >> 1;
                for(int j = 0; j < h; j++) {
                    Complex t = w.mult(cf[k + j + h]);
                    Complex u = cf[k + j];
                    cf[k + j] = u.add(t);
                    cf[k + j + h] = u.sub(t);
                    w = w.mult(root);
                }
            }
        }
        if(inv) {
            Complex r = new Complex(1.0 / base, 0.0);
            for(int i = 0; i < base; i++) {
                cf[i] = cf[i].mult(r);
            }
        }
        
        return cf;
    }
    
    private static void bitReversePermute(Complex[] a, Complex[] A, int base) {
        int h = Integer.numberOfLeadingZeros(base) + 1;
        for(int i = 0; i < A.length; i++) {
            int rev = Integer.reverse(i) >>> h;
            a[rev].real = A[i].real;
            a[rev].imag = A[i].imag;
        }
    }
}
