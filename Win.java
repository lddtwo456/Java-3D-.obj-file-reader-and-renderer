import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Win extends JFrame {
    int w;
    int h;

    public Win(int screen_width, int screen_height) {
        w = screen_width;
        h = screen_height;

        setContentPane(new DrawWin());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(w, h));
    }

    class DrawWin extends JPanel {
        public void paintComponent(Graphics g) {
            g.fillRect(20, 20, 100, 200);
        }
    }

    public void Update() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
