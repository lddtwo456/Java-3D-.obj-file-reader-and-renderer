class Main {
    public static void main(String[] args) {
        Object cube = new Object(-20f, 0f, -20f, 1f, "models/cow.obj");
        Win WIN = new Win(800, 500, cube.vertex_buffer, cube.index_buffer);

        while (true) {
            long start_time = System.nanoTime();
            
            cube.move(0.05f, 0f, 0f);

            System.out.println(System.nanoTime() - start_time);

            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                System.out.println("Thread.sleep interrupted");
            }

            WIN.repaint();
        }
    }
}