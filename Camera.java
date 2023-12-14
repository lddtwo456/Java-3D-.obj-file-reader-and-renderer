import java.util.ArrayList;

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

    public Camera(float x_pos, float y_pos, float z_pos, float x_rotation, float y_rotation, float z_rotation, int default_fov, int screen_width, int screen_height) {
        x = x_pos;
        y = y_pos;
        z = z_pos;

        x_rot = x_rotation;
        y_rot = y_rotation;
        z_rot = z_rotation;

        fov = default_fov;
        sc_width = screen_width;
        sc_height = screen_height;

        near_plane = (sc_width/2) / (float) Math.sin((double) Math.toRadians(Double.valueOf(fov)/2));
        aspect_ratio = sc_width / sc_height;
    }

    public ArrayList<Integer> projectPoint(float x_pos, float y_pos, float z_pos) {
        ArrayList<Integer> projected_point = new ArrayList<Integer>();

        try {
            if (z_pos+z > 0) {
                int new_x = (int) ((((x_pos+x)/(-1f*(z_pos+z)))*near_plane) + .5f + (sc_width/2));
                int new_y = (int) ((((y_pos+y)/(-1f*(z_pos+z)))*near_plane) + .5f + (sc_height/2));

                projected_point.add(new_x);
                projected_point.add(new_y);
            }
        }
        catch (Exception e) {
            projected_point.add((int) (x_pos + .5f + (sc_width/2)));
            projected_point.add((int) (y_pos + .5f + (sc_height/2)));
        }

        return projected_point;
    }

    public void move(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
}