package core.java.settings.properties;

import java.awt.*;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/11 0011 <br>
 */
public class PropertiesTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PropertiesFrame frame = new PropertiesFrame();
                frame.setVisible(true);
            }
        });

    }
}