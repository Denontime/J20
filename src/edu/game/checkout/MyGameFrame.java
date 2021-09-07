package edu.game.checkout;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class MyGameFrame extends JFrame {

    Date startTime = new Date();        //游戏起始时刻
    Date endTime;                       //游戏结束时刻


    //将背景图片与飞机图片定义为成员变量
    Image bgImg = GameUtil.getImage("images/bg.jpeg");
    Image msImg = GameUtil.getImage("images/mushroom.png");

    ms msa = new ms(msImg, 320, 250, 10);

    ArrayList<Shell> shellList = new ArrayList<>();
    ArrayList<Score> scoreList = new ArrayList<>();

    //paint方法作用是：画出整个窗口及内部内容。被系统自动调用。
    @Override
    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(856, 500, BufferedImage.TYPE_INT_RGB); //缓冲图片
        Graphics bg = bf.createGraphics();  //缓冲图片的graphics对象

        bg.drawImage(bgImg, 0, 0, null);
        msa.drawMySelf(bg);

        for (Shell b : shellList) {
            b.draw(bg);
            //飞机和所有炮弹对象进行矩形检测
            boolean touch = b.getRect().intersects(msa.getRect());
            if (touch) {
                if (msa.live){
                    msa.live = false;   //飞机死掉,红叉出现
                    endTime = new Date();
                }
            }
        }

        for (Score s : scoreList) {
            s.draw(bg);
            //飞机和所有炮弹对象进行矩形检测
//            boolean touch = b.getRect().intersects(msa.getRect());
//            if (touch) {
//                if (msa.live){
//                    msa.live = false;   //飞机死掉,红叉出现
//                    endTime = new Date();
//                }
//            }
        }

        if (!msa.live) {
            if (endTime == null) {
                endTime = new Date();
            }
            int period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
            printInfo(bg, "GameOver", 50, 240, 160, Color.red);
            printInfo(bg, "Time:" + period + "second", 50, 220, 220, Color.ORANGE);
        }

        g.drawImage(bf, 0, 0, null); //只需绘制bf一张即可，不会有闪烁现象
    }

    /**
     * 在窗口上打印信息
     */
    public void printInfo(Graphics g, String str, int size, int x, int y, Color color) {
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("Forte", Font.PLAIN, size);
        g.setFont(f);
        g.drawString(str, x, y);
        g.setColor(c);
    }

    //定义为内部类，可以方便的使用外部类的普通属性
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            msa.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            msa.minusDirection(e);
        }
    }

    class PaintThread extends Thread {
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true) {
                repaint();
                try {
                    //noinspection BusyWait
                    Thread.sleep(20); //1s = 1000ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void launchFrame() {

        setTitle("Mushroom");                                   //在游戏窗口打印标题
        setVisible(true);                                       //窗口默认不可见，设为可见
        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);     //窗口大小：宽度500，高度500
        setLocation(300, 300);                            //窗口左上角顶点的坐标位置
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //窗口关闭结束程序


        addWindowListener(new WindowAdapter() {                 //增加窗口监听

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub
                System.out.println("windowIconified");

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub
                System.out.println("windowDeiconified");

            }
        });

        addKeyListener(new KeyMonitor());   //增加键盘的监听
        new PaintThread().start();          //启动重画线程

        //初始化，生成一堆炮弹
        for (int i = 0; i < 1; i++) {
            Shell boom = new Shell();
            shellList.add(boom);
        }
        for (int i = 0; i < 1; i++) {
            Score fruit = new Score();
            scoreList.add(fruit);
        }
    }


    public static void main(String[] args) {
        MyGameFrame gameFrame = new MyGameFrame();
        gameFrame.launchFrame();
    }
}