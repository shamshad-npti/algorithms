/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cg;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author shamshad
 */
public class LineDrB extends LineDr {

    public LineDrB(Point p1, Point p2) {
        super(p1, p2);
    }

    @Override
    public void draw(Graphics g) {
        swap(p1.x > p2.x);
        int dx = abs(p2.x - p1.x), d, x = p1.x, y = p1.y;
        int dy = abs(p2.y - p1.y);
        d = 2 * dy - dx;
        g.fillRect(x, y, 1, 1);
        for (int i = x; i <= p2.x; i++) {
            x++;
            if(d < 0) {
                d = d + 2 * dy;
            } else {
                y++;
                d = d + 2 * (dy - dx);
            }
            g.fillRect(x, y, 1, 1);
        }
    } 
}
