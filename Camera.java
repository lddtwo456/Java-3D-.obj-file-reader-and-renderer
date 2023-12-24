public class Camera {
    float x;
    float y;
    float z;

    float x_rot;
    float y_rot;
    float z_rot;

    int fov;
    int sc_width;
    int sc_height;

    float near_plane;
    float aspect_ratio;

    public Camera(float x, float y, float z, float x_rot, float y_rot, float z_rot, int fov, int sc_width, int sc_height) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.x_rot = x_rot;
        this.y_rot = y_rot;
        this.z_rot = z_rot;

        this.fov = fov;
        this.sc_width = sc_width;
        this.sc_height = sc_height;

        this.near_plane = (this.sc_width/2) / (float) Math.sin((double) Math.toRadians(Double.valueOf(this.fov)/2));
        this.aspect_ratio = this.sc_width / this.sc_height;
    }

    public int[] projectPoint(float[] point) {
        float px = point[0] + this.x;
        float py = point[1] + this.y;
        float pz = point[2] + this.z;

        int new_x = (int) ((((px)/(-1f*(pz)))*near_plane) + .5f + (sc_width/2));
        int new_y = (int) ((((py)/(-1f*(pz)))*near_plane) + .5f + (sc_height/2));
        
        return new int[]{new_x, new_y};
    }

    public void move(float x, float y, float z) {
        this.y += y;
    }

    public void rotate(float xr, float yr, float zr) {
        this.x_rot += xr;
        this.y_rot += yr;
        this.z_rot += zr;
    }
}
