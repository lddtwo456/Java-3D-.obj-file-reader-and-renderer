import java.util.ArrayList;

public class Camera {
    double x;
    double y;
    double z;
    float x_rot;
    float y_rot;
    float z_rot;
    int fov;
    int sc_width;
    int sc_height;
    double near_plane;
    float aspect_ratio;

    public Camera(double x_pos, double y_pos, double z_pos, float x_rotation, float y_rotation, float z_rotation, int default_fov, int screen_width, int screen_height) {
        x = x_pos;
        y = y_pos;
        z = z_pos;

        x_rot = x_rotation;
        y_rot = y_rotation;
        z_rot = z_rotation;

        fov = default_fov;
        sc_width = screen_width;
        sc_height = screen_height;

        near_plane = (sc_width/2) / Math.sin(Math.toRadians(Double.valueOf(fov)/2));
        aspect_ratio = sc_width / sc_height;
    }

    public ArrayList<Double> projectPoint(double x_pos, double y_pos, double z_pos) {
        ArrayList<Double> projected_point = new ArrayList<Double>();

        try {
            double new_x = (x_pos/(-1*z_pos))*near_plane;
            double new_y = (y_pos/(-1*z_pos))*near_plane;

            projected_point.add(new_x);
            projected_point.add(new_y);
        }
        catch (Exception e) {
            projected_point.add(x_pos);
            projected_point.add(y_pos);
        }

        return projected_point;
    }
}