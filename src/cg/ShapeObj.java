/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cg;

import java.awt.Graphics;

/**
 *
 * @author shamshad
 */
public interface ShapeObj {
    public void draw(Graphics g);
    default public int abs(int x) {
        return x < 0 ? -x : x;
    }
    default public float abs(float x) {
        return x < 0 ? -x : x;
    }
    default public double abs(double x) {
        return x < 0 ? -x : x;
    }
}
