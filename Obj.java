import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Obj {
    String file_name;
    File file;
    Object parent;

    // BUFFER GENERATION
    ArrayList<String> lines = new ArrayList<String>();
    ArrayList<ArrayList<Integer>> grouped_indices = new ArrayList<ArrayList<Integer>>();

    // [[x, y, z], [vert], ...]
    ArrayList<ArrayList<Float>> vertices = new ArrayList<ArrayList<Float>>();
    // [index, ...]
    ArrayList<Integer> subobj_indices = new ArrayList<Integer>();

    public Obj(String file_name, Object parent) {
        this.file_name = file_name;

        this.parent = parent;

        this.buildBuffers();
    }

    private void buildBuffers() {
        this.getData();

        // get rid of empty vertices and adjust indices
        int greatest_index = 0;
        int i = 0; 
        while (i < this.vertices.size()) {
            if (this.vertices.get(i).size() > 0) {
                greatest_index = i;
                i++;
            } else {
                this.vertices.remove(i);

                for (int j = 0; j < this.grouped_indices.size(); j++) {
                    for (int k = 0; k < this.grouped_indices.get(j).size(); k++) {
                        if (this.grouped_indices.get(j).get(k) > greatest_index) {
                            this.grouped_indices.get(j).set(k, this.grouped_indices.get(j).get(k) - 1);
                        }
                    }
                }
            }
        }

        parent.vertex_buffer = new float[vertices.size()][3];
        for (int j = 0; j < vertices.size(); j++) {
            for (int k = 0; k < vertices.get(0).size(); k++) {
                parent.vertex_buffer[j][k] = vertices.get(j).get(k);
            }
        }

        ArrayList<Integer> ungrouped_indices = new ArrayList<Integer>();
        for (ArrayList<Integer> subobj_i : this.grouped_indices) {
            for (int index : subobj_i) {
                ungrouped_indices.add(index);
            }
        }

        parent.index_buffer = new int[ungrouped_indices.size()];
        for (int j = 0; j < ungrouped_indices.size(); j++) {
            parent.index_buffer[j] = ungrouped_indices.get(j);
        }
    }

    public void getData() {
        this.file = new File(this.file_name);

        try (Scanner reader = new Scanner(this.file)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found error: "+file_name);
            e.getStackTrace();
        }

        for (String line : lines) {
            this.processLine(line);
        } this.grouped_indices.add(this.subobj_indices);
    }

    private void processLine(String line) {
        if (line.length() == 0) {
            this.vertices.add(new ArrayList<Float>());
            return;
        }

        switch (line.charAt(0)) {
            case '#':
                this.vertices.add(new ArrayList<Float>());
                break;
            case 'v':
                switch (line.charAt(1)) {
                    case ' ':
                        // vertex
                        this.vertices.add(this.processVertex(line));
                        break;
                    case 'n':
                        // vertex normal
                        this.vertices.add(new ArrayList<Float>());
                        break;
                    case 't':
                        // vertex texture
                        this.vertices.add(new ArrayList<Float>());
                        break;
                }
                break;
            case 'g':
                this.vertices.add(new ArrayList<Float>());

                this.grouped_indices.add(this.subobj_indices);
                this.subobj_indices = new ArrayList<Integer>();

                break;
            case 'f':
                this.vertices.add(new ArrayList<Float>());
                this.processFace(line);

                break;
        }
    }

    private ArrayList<Float> processVertex(String line) {
        ArrayList<Float> vert = new ArrayList<Float>();

        boolean dec_found = false;
        boolean is_negative = false;
        int divisor = 1;
        float n = 0f;

        for (int c_index = 2; c_index < line.length(); c_index++) {
            char c = line.charAt(c_index);
            
            if (Character.isDigit(c)) {
                if (!dec_found) {
                    n *= 10;
                    n += Character.getNumericValue(c);
                } else {
                    divisor *= 10;
                    n += (float) Character.getNumericValue(c) / divisor;
                }
            } else if (c == '.') {
                dec_found = true;
            } else if (c == ' ') {
                if (is_negative) {vert.add(n*-1);} else {vert.add(n);}
                dec_found = false;
                is_negative = false;
                n = 0f;
                divisor = 1;
            } else if (c == '-') {
                is_negative = true;
            }
        } if (is_negative) {vert.add(n*-1);} else {vert.add(n);}
        
        return vert;
    }

    private void processFace(String line) {
        ArrayList<Integer> face = new ArrayList<Integer>();
        
        int n = 0;
        int v_data_type = 0;
        for (int c_index = 2; c_index < line.length(); c_index++) {
            char c = line.charAt(c_index);

            if (Character.isDigit(c)) {
                switch (v_data_type) {
                    case 0:
                        // v
                        n *= 10;
                        n += Character.getNumericValue(c);
                        break;
                    case 1:
                        // vt
                        break;
                    case 2:
                        // vn
                        break;
                }
            } else {
                switch (v_data_type) {
                    case 0:
                        // v
                        face.add(n);
                        break;
                    case 1:
                        // vt
                        break;
                    case 2:
                        // vn
                        break;
                }

                if (c == '/') {
                    v_data_type += 1;
                } else {
                    v_data_type = 0;
                }

                n = 0;
            }
        }
        switch (v_data_type) {
            case 0:
                // v
                face.add(n);
                break;
            case 1:
                // vt
                break;
            case 2:
                // vn
                break;
        }

        // turn face data into groups of 3 indices for buffer format
        switch(face.size()) {
            case 3:
                for (int i : face) {
                    this.subobj_indices.add(i-1);
                }

                break;
            case 4:
                for (int i = 0; i < 3; i++) {
                    this.subobj_indices.add(face.get(i)-1);
                }
                this.subobj_indices.add(face.get(2)-1);
                this.subobj_indices.add(face.get(3)-1);
                this.subobj_indices.add(face.get(0)-1);

                break;
        }
    }
}
