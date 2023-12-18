package org.dp.view;
import org.dp.scene.Scene;
import org.dp.utils.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Playground extends Component{

    private static Playground thePlayground = null;
    private JFrame canvasFrame;
    private boolean isClosed = false;

    private int currentCursor = 0;
    private org.dp.view.events.MouseEvent lastMouseEvent = null;

    // 单例模式！只允许一个playground存在
    private Playground(){
        super(new Vector2i(0,0), new Vector2i(1600,900));
        canvasFrame = new JFrame("大富翁");
        canvasFrame.setSize(1600, 900);
        canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Playground p =this;
        canvasFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing!");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                isClosed = true;
            }
        });
        canvasFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lastMouseEvent = new org.dp.view.events.ClickEvent(e.getX(), e.getY());
            }
        });
        canvasFrame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                lastMouseEvent = new org.dp.view.events.HoverEvent(e.getX(), e.getY());
            }
        });
    }

    public static Playground get(){
        if(thePlayground == null){
            thePlayground = new Playground();
        }
        return thePlayground;
    }

    public void showWindow() {
        canvasFrame.setVisible(true);        // 设置窗体可视
        Graphics g = canvasFrame.getGraphics();
        Playground playground = this;

        Runnable renderRunner = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Image offScreenImage = canvasFrame.createImage(1600, 900);     //新建一个图像缓存空间,这里图像大小为800*600
                    if(lastMouseEvent != null){
                        Component.emitRootMouseEvent(playground, lastMouseEvent);
                        lastMouseEvent = null;
                    }

                    Graphics gImage = offScreenImage.getGraphics();  //把它的画笔拿过来,给gImage保存着
                    Component.drawRoot(playground, gImage);

                    if(canvasFrame.getCursor().getType() != currentCursor)
                        canvasFrame.setCursor(currentCursor);

                    g.drawImage(offScreenImage, 0, 0, null);         //然后一次性显示出来

                    if(isClosed())
                        break;
                }
            }
        };
        Thread renderThread = new Thread(renderRunner);
        renderThread.start();


    }

    public JFrame getFrame(){
        return canvasFrame;
    }

    @Override
    public void drawMe(Graphics graphics) {

    }

    @Override
    public boolean onMouseEventMe(org.dp.view.events.MouseEvent e) {
        return true;
    }

    public void clear() {
        canvasFrame.getGraphics().clearRect(0,0,500,350);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setCursor(int cursorId){
        currentCursor = cursorId;
    }

    public void switchScene(Scene newScene){
        this.getChildren().clear();
        this.addComponent(newScene);
    }
}
