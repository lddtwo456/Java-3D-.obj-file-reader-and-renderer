class Main {
    public static void main(String[] args) {
        Object cube = new Object(0f, 0f, 0f, 1f, "models/cow.obj");
        Win WIN = new Win(800, 500, cube.vertex_buffer, cube.index_buffer);
    }
}