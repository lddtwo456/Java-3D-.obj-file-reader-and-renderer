import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Camera cam = new Camera(0, 0, 0, 0, 0, 0, 90, 100, 100);
        
        ArrayList<Obj> objects = new ArrayList<Obj>();
        objects.add(new Obj(0, 0, 0, "cube.obj"));
        objects.add(new Obj(0, 0, 0, "cube2copy.obj"));

        for (Obj obj : objects) {
            obj.draw();
        }
    }
}