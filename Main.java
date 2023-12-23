class Main {
    public static void main(String[] args) {
        Object cube = new Object(0f, 0f, 0f, 1f, "models/teapot.obj");
        Win WIN = new Win(800, 500, cube.buffers.vertex_buffer, cube.buffers.index_buffer);
    }
}