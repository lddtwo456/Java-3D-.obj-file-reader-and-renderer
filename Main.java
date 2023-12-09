import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Win win = new Win(800, 600);
        Camera cam = new Camera(0, 0, 0, 0, 0, 0, 90, win.w, win.h);

        ArrayList<Obj> objects = new ArrayList<Obj>();
        objects.add(new Obj(0, 0, 0, "cube.obj"));

        ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> draw_queue = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>();
        ArrayList<ArrayList<ArrayList<String>>> face_attributes = new ArrayList<ArrayList<ArrayList<String>>>();

        for (Obj obj : objects) {
            draw_queue.add(obj.get_draw_instruction());
        }
        System.out.println(draw_queue);

        win.Update();
    }
}