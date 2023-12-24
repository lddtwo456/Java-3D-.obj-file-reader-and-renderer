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

        new Obj(this.file_name, this);
        this.setup();
    }

    public void setup() {
        for (float[] vert : vertex_buffer) {
            // scale
            vert[0] *= this.scale;
            vert[1] *= this.scale;
            vert[2] *= this.scale;

            // move
            vert[0] += this.x;
            vert[1] += this.y;
            vert[2] += this.z;
        }
    }

    public void move(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        for (float[] vert : vertex_buffer) {
            vert[0] += x;
            vert[1] += y;
            vert[2] += z;
        }
    }
}