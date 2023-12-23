public class Object {
    float x;
    float y;
    float z;
    float scale;

    String file_name;
    Obj buffers;

    float[][] vertex_buffer = null;
    int[] index_buffer = null;

    public Object(float x, float y, float z, float scale, String file_name) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.scale = scale;
        this.file_name = file_name;

        this.buffers = new Obj(this.scale, this.file_name);
    }
}