import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Win extends JFrame {
    int screen_width;
    int screen_height;

    float[][] vertex_buffer = null;
    int[] index_buffer = null;

    public Win(int screen_width, int screen_height, float[][] vertex_buffer, int[] index_buffer) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.vertex_buffer = vertex_buffer;
        this.index_buffer = index_buffer;

        setContentPane(new DrawWin());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(screen_width, screen_height));

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    class DrawWin extends JPanel {
        public void paintComponent(Graphics g) {
            for (int i = 0; i < index_buffer.length; i += 3) {    
                g.fillOval((int) (40f*vertex_buffer[index_buffer[i]][0]) + screen_width/2, (int) (40f*vertex_buffer[index_buffer[i]][1]) + screen_height/2, 2, 2);;
            }
        }   
    }
}
