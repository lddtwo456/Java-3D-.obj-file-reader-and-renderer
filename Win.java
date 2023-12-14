import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Win extends JFrame {
    int w;
    int h;

    int test_val = 0;

    // placeholder camera and draw queue
    ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> draw_instruction = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>();
    Camera cam = new Camera(0f, 0f, 0f, 0f, 0f, 0f, 90, 50, 50);

    public Win(int screen_width, int screen_height) {
        w = screen_width;
        h = screen_height;

        setContentPane(new DrawWin());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(w, h));

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    class DrawWin extends JPanel {
        public void paintComponent(Graphics g) {
            // loop through draw queue drawing wireframe
            for (ArrayList<ArrayList<ArrayList<ArrayList<Float>>>> obj : draw_instruction) {
                for (ArrayList<ArrayList<ArrayList<Float>>> face : obj) {
                    ArrayList<ArrayList<Float>> verts = face.get(0);
                    ArrayList<ArrayList<Integer>> projected_verts = new ArrayList<ArrayList<Integer>>();

                    switch (verts.size()) {
                        case 3:
                            for (ArrayList<Float> vert : verts) {
                                if (vert.size() == 3) {
                                    projected_verts.add(cam.projectPoint(vert.get(0), vert.get(1), vert.get(2)));
                                } else {
                                    projected_verts.add(new ArrayList<Integer>());
                                }
                            }
                            
                            int lowest_size = 2;
                            for (ArrayList<Integer> vert : projected_verts) {
                                if (vert.size() < lowest_size) {
                                    lowest_size = vert.size();
                                }
                            }
                            if (lowest_size == 2) {
                                g.drawPolygon(new int[] {projected_verts.get(0).get(0), projected_verts.get(1).get(0), projected_verts.get(2).get(0)}, new int[] {projected_verts.get(0).get(1), projected_verts.get(1).get(1), projected_verts.get(2).get(1)}, 3);
                            }
                            break;
                        case 4:
                            for (ArrayList<Float> vert : verts) {
                                projected_verts.add(cam.projectPoint(vert.get(0), vert.get(1), vert.get(2)));
                            }

                            g.drawPolygon(new int[] {projected_verts.get(0).get(0), projected_verts.get(1).get(0), projected_verts.get(2).get(0), projected_verts.get(3).get(0)}, new int[] {projected_verts.get(0).get(1), projected_verts.get(1).get(1), projected_verts.get(2).get(1), projected_verts.get(3).get(1)}, 4);
                            break;
                    }
                }
            }
        }
    }

    public void draw_3D(ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> draw_instruction, Camera cam) {
        // update camera position/rotation and draw queue
        this.draw_instruction = draw_instruction;
        this.cam = cam;
        this.test_val += 1;
        repaint();
    }

    public void set_title(String text) {
        setTitle(text);;
    }
}
