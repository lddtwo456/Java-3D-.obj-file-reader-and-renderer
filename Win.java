import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Win extends JFrame {
    int screen_width;
    int screen_height;

    float[][] vertex_buffer = null;
    int[] index_buffer = null;

    Camera cam;

    public Win(int screen_width, int screen_height, float[][] vertex_buffer, int[] index_buffer) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.vertex_buffer = vertex_buffer;
        this.index_buffer = index_buffer;

        cam = new Camera(0, 0, 0, 0, 0, 0, 90, this.screen_width, this.screen_height);

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
                int[] projected_point = cam.projectPoint(vertex_buffer[index_buffer[i]]);
                int px = projected_point[0];
                int py = projected_point[1]; 

                g.fillOval(px, py, 2, 2);
            }
        }   
    }
}
