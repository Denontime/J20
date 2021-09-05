package edu.game.checkout;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ms extends GameObject {

    Image deadImg = GameUtil.getImage("images/dead.png");

    boolean left, up, right, down;
    boolean live = true;

    // 按下上下左右键，则改变方向值。
    // 比如：按下上键，则e.getKeyCode()的值就是VK_UP，那么置：up=true;
    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_DOWN -> down = true;
            default -> {
            }
        }
    }

    // 松开上下左右键，则改变方向值。
    // 比如：松开上键，则e.getKeyCode()的值就是VK_UP，那么置：up=false;
    public void minusDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_DOWN -> down = false;
            default -> {
            }
        }
    }

    @Override
    public void drawMySelf(Graphics g) {
        if (live) {
            super.drawMySelf(g);

            //如下代码，用来实现边界检测
            if (y > Constant.GAME_HEIGHT - height || y < 30) {
                if (left) x -= speed;
                if (right) x += speed;
            } else if (x < 0 || x > Constant.GAME_WIDTH - width) {
                if (up) y -= speed;
                if (down) y += speed;
            } else {
                // 根据方向，计算飞机新的坐标
                if (left) x -= speed;
                if (right) x += speed;
                if (up) y -= speed;
                if (down) y += speed;
            }
        } else {
            super.drawMySelf(g);
            death(g);
        }
    }

    public void death(Graphics g) {
        g.drawImage(deadImg, (int) x, (int) y, null);
    }

    public Rectangle getRect() {
        return new Rectangle((int) x + 8, (int) y + 8, width - 10, height - 10);
    }

    public ms(Image img, double x, double y, int speed) {
        super(img, x, y);
        this.speed = speed;
    }
}