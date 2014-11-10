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
public class LineDrDDA extends LineDr {

    public LineDrDDA(Point p1, Point p2) {
        super(p1, p2);
    }

    @Override
    public void draw(Graphics g) {
        float dx = p2.x - p1.x;
        float dy = p2.y - p1.y;
        float step = abs(abs(dx) > abs(dy) ? dx : dy);
        dx /= step;
        dy /= step;
        float x = p1.x, y = p1.y;
        g.fillRect((int)x, (int)y, 1, 1);
        for(int k = 0; k < step; k++) {
            x += dx;
            y += dy;
            g.fillRect((int)x, (int)y, 1, 1);
        }
    }
    
}
