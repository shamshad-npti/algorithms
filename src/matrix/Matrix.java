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
package matrix;

/**
 *
 * @author shamshad
 * @param <T>
 */
public class Matrix {
    private int n;
    private int m;
    private double[][] elem;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.elem = new double[n][m];
    }

    public Matrix(Matrix mat) {
        this.m = mat.m;
        this.n = mat.n;
        this.elem = new double[n][m];
        for(int i = 0; i < n; i++) {
            System.arraycopy(mat.elem[i], 0, elem[i], 0, m);
        }
    }
    public double get(int i, int j) {
        return this.elem[i][j];
    }
    
    public void set(int i, int j, double v) {
        this.elem[i][j] = v;
    }
    
    public void add(Matrix mat) {
        if(mat.m != this.m || this.n != mat.n) {
            
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                this.elem[i][j] += mat.elem[i][j];
            }
        }
    }
    
    public void substract(Matrix mat) {
        if(mat.m != this.m || this.n != mat.n) {
            
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                this.elem[i][j] -= mat.elem[i][j];
            }
        }
    }
    
    public void negate() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                this.elem[i][j] = -this.elem[i][j];
            }
        }
    }
    
    public Matrix[] luDecomposition() {
        if(this.m != this.n) {
            
        }
        Matrix l = new Matrix(n, m);
        Matrix u = new Matrix(n, m);
        Matrix t = new Matrix(this);
        for(int i = 0; i < n; i++) {
            l.elem[i][i] = 1.0;
            for(int j = i + 1; j < n; j++) {
                l.elem[j][i] = t.elem[j][i] / t.elem[i][i];
            }
            System.arraycopy(t.elem[i], i, u.elem[i], i, n - i);
            for(int j = i + 1; j < n; j++) {
                for(int k = i + 1; k < m; k++) {
                    t.elem[j][k] -= l.elem[j][i] * u.elem[i][k]; 
                }
            }
        }
        return new Matrix[] {l, u};
    }
    
    public Matrix[] qrDecomposition() {
        Matrix q = new Matrix(this.n, this.n), r = new Matrix(this.n, this.m);
        Matrix t = new Matrix(this);
        for(int i = 0; i < m; i++) {
            r.elem[i][i] = t.colNorm(i);
            for(int j = 0; j < n; j++) {
                q.elem[j][i] = t.elem[j][i] / r.elem[i][i];
            }
            for(int j = i + 1; j < m; j++) {
                for(int k = 0; k < n; k++) {
                    r.elem[i][j] += q.elem[k][i] * t.elem[k][j];
                }
            }
            for(int j = i + 1; j < m; j++) {
                for(int k = 0; k < n; k++) {
                    t.elem[k][j] = t.elem[k][j] - q.elem[k][i] * r.elem[i][j];
                }
            }
        }
        for(int i = m; i < n; i++) {
            double norm = 0.0, sum;
            for(int j = 0; j < i; j++) {
                norm += q.elem[0][j] * q.elem[0][j];
            }
            norm = Math.sqrt(1.0 - norm);
            for(int j = 1; j < n; j++) {
                sum = 0.0;
                for(int k = 0; k < i; k++) {
                    sum += q.elem[0][k] * q.elem[j][k];
                }
                q.elem[j][i] = -sum / norm;
            }
            q.elem[0][i] = norm;
        }
        return new Matrix[] {q, r};
    }
    
    public static Matrix eye(int n) {
        Matrix mat = new Matrix(n, n);
        for(int i = 0; i < n; i++) {
            mat.elem[i][i] = 1.0;
        }
        return mat;
    }
    
    public static Matrix transpose(Matrix mat) {
        Matrix temp = new Matrix(mat.m, mat.n);
        for(int i = 0; i < mat.n; i++) {
            for(int j = 0; j < mat.n; j++) {
                temp.elem[j][i] = temp.elem[i][j];
            }
        }
        return temp;
    }
    
    public double rowNorm(int r) {
        return Math.sqrt(rowNormSqr(r));
    }
    
    public double rowNormSqr(int r) {
        double norm = 0.0;
        for(int i = 0; i < m; i++) {
            norm += this.elem[r][i] * this.elem[r][i];
        }
        return norm;
    }
    
    public double colNorm(int c) {
        double norm = 0.0;
        for(int i = 0; i < n; i++) {
            norm = norm + this.elem[i][c] * this.elem[i][c];
        }
        return Math.sqrt(norm);
    }
    
    public double det() {
        if(this.n != this.m) {
            return 0.0;
        }
        double det = 1.0;
        Matrix u = luDecomposition()[1];
        for(int i = 0; i < u.n; i++) {
            det *= u.elem[i][i];
        }
        return det;
    }

    public void transpose() {
        if(this.m == this.n) {
            for(int i = 0; i < n; i++) {
                for(int j = i + 1; j < m; j++) {
                    double t = this.elem[i][j];
                    this.elem[i][j] = this.elem[j][i];
                    this.elem[j][i] = t;
                }
            }
        }
    }
    
    public static Matrix multiply(Matrix m1, Matrix m2) {
        Matrix m = new Matrix(m1.n, m2.m);
        for(int i = 0; i < m1.n; i++) {
            for(int j = 0; j < m2.m; j++) {
                for(int k = 0; k < m1.m; k++) {
                    m.elem[i][j] += m1.elem[i][k] * m2.elem[k][j];
                }
            }
        }
        return m;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                sb.append(String.format("%.2f", elem[i][j])).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
