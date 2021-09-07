package edu.game.checkout;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ms extends GameObject {

    Image deadImg = GameUtil.getImage("images/dead.png");

    boolean left, up, right, down, left_state, up_state, right_state, down_state;
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
            down_state = y <= Constant.GAME_HEIGHT - height;
            up_state = y >= 30;
            left_state = x >= 0;
            right_state = x <= Constant.GAME_WIDTH - width;
            
            // 根据方向，计算飞机新的坐标
            if (left && left_state) x -= speed;
            if (right && right_state) x += speed;
            if (up && up_state) y -= speed;
            if (down && down_state) y += speed;

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