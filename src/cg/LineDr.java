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
public class LineDr implements ShapeObj {
    protected Point p1, p2;
    public LineDr(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }
    
    @Override
    public void draw(Graphics g) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        double m = 1.0 * dy / dx;
        if(Math.abs(m) <= 1) {
            swap(p1.x > p2.x);
            for(int i = p1.x; i <= p2.x; i++)
                g.fillRect(i, (int)(m * (i - p2.x) + p2.y), 1, 1);
        } else {
            swap(p1.y > p2.y);  
            m = 1.0 / m;
            for(int i = p1.y; i <= p2.y; i++)
                g.fillRect((int)(m * (i - p2.y) + p2.x), i, 1, 1);
        }
    }
    
    protected void swap(boolean b) {
        if(b) {
            Point p = p1;
            p1 = p2;
            p2 = p;            
        }
    }
}
