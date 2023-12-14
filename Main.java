import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    final static long target_FPS = 60;

    static Win win = new Win(1200, 700);
    static Camera cam = new Camera(0f, -3f, 10f, 0, 0, 0, 90, win.w, win.h);
    
    static ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> draw_queue = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>();
    static ArrayList<Obj> objects = new ArrayList<Obj>();

    // directions for camera movement
    static int x_dir = 0;
    static int y_dir = 0;
    static int z_dir = 0;

    // used to prevent key held problems
    static boolean w_pressed = false;
    static boolean a_pressed = false;
    static boolean s_pressed = false;
    static boolean d_pressed = false;

    public static void main(String[] args) {
        objects.add(new Obj(3, 0, 7, "teapot.obj"));
        objects.add(new Obj(-3, 2, 10, "cow.obj"));
        objects.add(new Obj(0, 4, 40, "teddy.obj"));

        // input
        win.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'w':
                        if (w_pressed) {
                            break;
                        } else {
                            z_dir = -1;
                            w_pressed = true;
                            break;
                        }
                    case 'a':
                        if (a_pressed) {
                            break;
                        } else {
                            x_dir = -1;
                            a_pressed = true;
                            break;
                        }
                    case 's':
                        if (s_pressed) {
                            break;
                        } else {
                            z_dir = 1;
                            s_pressed = true;
                            break;
                        }
                    case 'd':
                        if (d_pressed) {
                            break;
                        } else {
                            x_dir = 1;
                            d_pressed = true;
                            break;
                        }
                }
            }
            public void keyPressed(KeyEvent e) {
                
            }
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'w':
                        if (s_pressed) {
                            z_dir = 1;
                        } else {
                            z_dir = 0;
                        }
                        w_pressed = false;
                        break;
                    case 'a':
                        if (d_pressed) {
                            x_dir = 1;
                        } else {
                            x_dir = 0;
                        }
                        a_pressed = false;
                        break;
                    case 's':
                        if (w_pressed) {
                            z_dir = -1;
                        } else {
                            z_dir = 0;
                        }
                        s_pressed = false;
                        break;
                    case 'd':
                        if (a_pressed) {
                            x_dir = -1;
                        } else {
                            x_dir = 0;
                        }
                        d_pressed = false;
                        break;
                }
            }
        });

        long strt_time = System.nanoTime();
        float delta_time_millis = 0f;
        float target_time_millis = (float) (1000f / target_FPS);
        float wait_time;
        float current_fps = 0f;

        // run loop
        while (true) {
            strt_time = System.nanoTime();

            // reset queue
            draw_queue = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>();
            cam.move(0.05f*x_dir, 0.05f*y_dir, .05f*z_dir);

            for (Obj obj : objects) {
                if (obj.filename == "cow.obj") {
                    obj.move(0.01f, 0f, 0f);
                }
                if (obj.filename == "teapot.obj") {
                    obj.move(-0.01f, -0.01f, 0.005f);
                }
                if (obj.filename == "teddy.obj") {
                    obj.move(0f, 0.02f, -0.02f);
                }

                // update queue
                draw_queue.add(obj.draw_instruction);
            }

            win.draw_3D(draw_queue, cam);
            win.set_title(String.valueOf(current_fps));

            delta_time_millis = (float) (System.nanoTime() - strt_time) / 1000000f;
            wait_time = target_time_millis - delta_time_millis + 0.5f;
            current_fps = 1000f / delta_time_millis;
            try {
                if (wait_time > 0) {
                    Thread.sleep((int) (wait_time));
                }
            } catch(InterruptedException e) {
                System.out.println("Thread.sleep interrupted!");
            }
        }
    }
}