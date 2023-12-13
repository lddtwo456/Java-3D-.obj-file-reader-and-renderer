import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Win win = new Win(800, 600);
        Camera cam = new Camera(0f, -3f, 10f, 0, 0, 0, 90, win.w, win.h);


        ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> draw_queue = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>();        ArrayList<Obj> objects = new ArrayList<Obj>();
        objects.add(new Obj(3, 0, 7, "teapot.obj"));
        objects.add(new Obj(-3, 2, 10, "cow.obj"));
        objects.add(new Obj(0, 4, 40, "teddy.obj"));

        for (Obj obj : objects) {
            draw_queue.add(obj.draw_instruction);
        }
        win.draw_3D(draw_queue, cam);

        // run loop
        while (true) {
            win.draw_3D(draw_queue, cam);

            cam.move(0f, 0f, -.05f);

            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                System.out.println("Thread.sleep interrupted!");
            }
        }
    }
}