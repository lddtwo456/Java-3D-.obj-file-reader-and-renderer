import java.util.ArrayList;

public class Object {
    float x;
    float y;
    float z;
    float scale;

    String file_name;

    float[][] vertex_buffer = null;
    int[] index_buffer = null; 

    public Object(float x, float y, float z, float scale, String file_name) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.scale = scale;
        this.file_name = file_name;

        new Obj(this.scale, this.file_name, this);
    }
}