import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Camera cam = new Camera(0, 0, 0, 0, 0, 0, 90, 100, 100);
        
        ArrayList<Obj> objects = new ArrayList<Obj>();
        objects.add(new Obj(0, 0, 0, "cube.obj"));
        objects.add(new Obj(0, 0, 0, "cube2copy.obj"));

        System.out.println(Obj.rotate_point(1, 0, 0, 0, 90, 0, 0, 0, 0));

        for (Obj obj : objects) {
            obj.draw();
        }
    }
}