package edu.game.checkout;

import java.awt.*;

public class Score extends Shell {
    double degree;

    public Score() {
        this.degree = Math.random() * Math.PI;
        this.x = 330;
        this.y = 100;
        width = 10;
        height = 10;
        speed = 4;
    }

    public void draw(Graphics g) {
        //将外部传入对象g的状态保存好
        Color c = g.getColor();
        g.setColor(Color.blue);
        g.fillOval((int) x, (int) y, width, height);

        move();

        //返回给外部，变回以前的颜色
        g.setColor(c);
    }

}
