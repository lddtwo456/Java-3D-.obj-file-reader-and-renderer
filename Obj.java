import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Obj {
    float x;
    float y;
    float z;
    String filename;
    File file;
    //reading file
    ArrayList<String> lines = new ArrayList<String>();
    //reading lines
    //face attributes = group name and material
    //faces = v, vt, and vn addresses for each face
    ArrayList<ArrayList<String>> face_attr = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<ArrayList<Integer>>> faces = new ArrayList<ArrayList<ArrayList<Integer>>>();
    ArrayList<ArrayList<Float>> vertices = new ArrayList<ArrayList<Float>>();
    ArrayList<ArrayList<Float>> vert_norms = new ArrayList<ArrayList<Float>>();
    ArrayList<ArrayList<Float>> vert_texts = new ArrayList<ArrayList<Float>>();

    ArrayList<ArrayList<ArrayList<ArrayList<Float>>>> draw_instruction;

    /*
        base everything off first character of line
        if null:
            add null to all
        if v:
            add vals to vert list
            add null to others
        if vn:
            add vals to vn list
            add null to others
        if vt:
            add vals to vt list
            add null to others
        if f:
            yknow
        
    */

    public Obj(float x_pos, float y_pos, float z_pos, String filename) {
        x = x_pos;
        y = y_pos;
        z = z_pos;
        
        //read file
        this.filename = filename;
        file = new File(filename);

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        //process lines
        String group = "default";
        String material = "default";

        for (int line = 0; line < lines.size(); line++) {
            if (lines.get(line).length() < 1) {
                vertices.add(new ArrayList<Float>());
                vert_texts.add(new ArrayList<Float>());
                vert_norms.add(new ArrayList<Float>());

                continue;
            }
            
            switch (lines.get(line).charAt(0)) {
                case '#':
                    vertices.add(new ArrayList<Float>());
                    vert_texts.add(new ArrayList<Float>());
                    vert_norms.add(new ArrayList<Float>());

                    break;
                case 'v':
                    switch (lines.get(line).charAt(1)) {
                        case ' ':
                            ArrayList<Float> vert = new ArrayList<Float>();

                            int n = 0;
                            float scalar = 1;
                            boolean dec_found = false;
                            int negative = 1;

                            for (int c = 2; c < lines.get(line).length(); c++) {
                                char character = lines.get(line).charAt(c);
                                if (character == '.') {
                                    dec_found = true;
                                } 
                                else if (character == ' ') {
                                    vert.add(n*scalar*negative);
                                    n = 0;
                                    scalar = 1;
                                    dec_found = false;
                                    negative = 1;
                                }
                                else if (character == '-') {
                                    negative = -1;
                                }
                                else {
                                    n *= 10;
                                    n += Character.getNumericValue(character);

                                    if (dec_found) {
                                        scalar /= 10;
                                    } 
                                }
                            } 
                            vert.add(n*scalar*negative);

                            vertices.add(vert);
                            vert_texts.add(new ArrayList<Float>());
                            vert_norms.add(new ArrayList<Float>());

                            break;
                        case 't':
                            for (int c = 3; c < lines.get(line).length(); c++) {
                                ;
                            } 
                            break;
                        case 'n':
                            for (int c = 3; c < lines.get(line).length(); c++) {
                                ;
                            } 
                            break;
                    } 
                    break;
                case 'g':
                    ArrayList<Character> name = new ArrayList<Character>();
                    
                    for (int c = 2; c < lines.get(line).length(); c++) {
                        name.add(lines.get(line).charAt(c));
                    }

                    group = name.toString();
                    group = group.replace(",", "");
                    group = group.replace("[", "");
                    group = group.replace("]", "");
                    group = group.replace(" ", "");

                    break;
                case 'u':
                    ArrayList<Character> mat = new ArrayList<Character>();

                    for (int c = 7; c < lines.get(line).length(); c++) {
                        mat.add(lines.get(line).charAt(c));
                    }

                    material = mat.toString();
                    material = material.replace(",", "");
                    material = material.replace("[", "");
                    material = material.replace("]", "");
                    material = material.replace(" ", "");
                    
                    break;
                case 'f':
                    ArrayList<ArrayList<Integer>> face = new ArrayList<ArrayList<Integer>>();
                    ArrayList<Integer> vert = new ArrayList<Integer>();
                    int index = 0;

                    for (int c = 2; c < lines.get(line).length(); c++) {
                        char character = lines.get(line).charAt(c);

                        if (character == '/') {
                            vert.add(index);
                            index = 0;
                        }
                        else if (character == ' ') {
                            vert.add(index);
                            index = 0;
                            face.add(vert);
                            vert = new ArrayList<Integer>();
                        }
                        else {
                            index *= 10;
                            index += Character.getNumericValue(character);
                        }
                    }
                    vert.add(index);
                    face.add(vert);
                    faces.add(face);

                    break;
            }
        }

        draw_instruction = get_initial_draw_instruction();
    }

    static ArrayList<ArrayList<Float>> mat_mul(ArrayList<ArrayList<Float>> A, ArrayList<ArrayList<Float>> B) {
        int A_r = A.size();
        int A_c = A.get(0).size();
        int B_r = B.size();
        int B_c = B.get(0).size();
        int C_r = A_r;
        int C_c = B_c;
        float num = 0;
        ArrayList<ArrayList<Float>> C = new ArrayList<ArrayList<Float>>();

        if (A_c != B_r) {
            int x = 10/0;
            System.out.println(x);
        }

        for (int new_r = 0; new_r < C_r; new_r++) {
            ArrayList<Float> next_row = new ArrayList<Float>();

            for (int new_c = 0; new_c < C_c; new_c++) {
                for (int i = 0; i < A_r; i++) {
                    num += A.get(new_r).get(i)*B.get(i).get(new_c);
                }
                next_row.add(num);
                num = 0;
            }

            C.add(next_row);
        }

        return C;
    }

    static ArrayList<ArrayList<Float>> array_3x3(float a, float b, float c, float d, float e, float f, float g, float h, float i) {
        ArrayList<ArrayList<Float>> out = new ArrayList<ArrayList<Float>>();
        ArrayList<Float> row = new ArrayList<Float>();

        row.add(a);
        row.add(b);
        row.add(c);
        out.add(row);
        row = new ArrayList<Float>();
        row.add(d);
        row.add(e);
        row.add(f);
        out.add(row);
        row = new ArrayList<Float>();
        row.add(g);
        row.add(h);
        row.add(i);
        out.add(row);

        return out;
    }

    static ArrayList<ArrayList<Float>> array_3x1(float a, float b, float c) {
        ArrayList<ArrayList<Float>> out = new ArrayList<ArrayList<Float>>();
        ArrayList<Float> row = new ArrayList<Float>();

        row.add(a);
        out.add(row);
        row = new ArrayList<Float>();
        row.add(b);
        out.add(row);
        row = new ArrayList<Float>();
        row.add(c);
        out.add(row);

        return out;
    }

    static ArrayList<Float> rotate_point(float px, float py, float pz, double rx, double ry, double rz, float cx, float cy, float cz) {
        px -= cx;
        py -= cy;
        pz -= cz;

        rx = Math.toRadians(rx);
        ry = Math.toRadians(ry);
        rz = Math.toRadians(rz);

        ArrayList<ArrayList<Float>> P = new ArrayList<ArrayList<Float>>();

        P = array_3x1(px, py, pz);

        P = mat_mul(array_3x3(1f, 0f, 0f, 0f, (float) Math.cos(rx), -1f * (float) Math.sin(rx), 0f, (float) Math.sin(rx), (float) Math.cos(rx)), P);
        P = mat_mul(array_3x3((float) Math.cos(rz), -1f * (float) Math.sin(rz), 0f, (float) Math.sin(rz), (float) Math.cos(rz), 0f, 0f, 0f, 1f), P);
        P = mat_mul(array_3x3((float) Math.cos(ry), 0f, (float) Math.sin(ry), 0f, 1f, 0f, -1f * (float) Math.sin(ry), 0f, (float) Math.cos(ry)), P);

        ArrayList<Float> P_t = new ArrayList<Float>();
        P_t.add(P.get(0).get(0) + cx);
        P_t.add(P.get(1).get(0) + cy);
        P_t.add(P.get(2).get(0) + cz);
        return P_t;
    }

    public void move(float x_d, float y_d, float z_d) {
        x += x_d;
        y += y_d;
        z += z_d;
        
        for (ArrayList<ArrayList<ArrayList<Float>>> face : draw_instruction) {
            ArrayList<ArrayList<Float>> verts = face.get(0);

            for (ArrayList<Float> vert : verts) {
                vert.set(0, vert.get(0) + x_d);
                vert.set(1, vert.get(1) + y_d);
                vert.set(2, vert.get(2) + z_d);
            }
        }
    }

    public ArrayList<ArrayList<ArrayList<ArrayList<Float>>>> get_initial_draw_instruction() {
        ArrayList<ArrayList<ArrayList<ArrayList<Float>>>> draw_instruct= new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>();

        // loop through faces of opject
        for (ArrayList<ArrayList<Integer>> face : faces) {
            ArrayList<ArrayList<Float>> face_verts = new ArrayList<ArrayList<Float>>();
            ArrayList<ArrayList<Float>> face_vert_texts = new ArrayList<ArrayList<Float>>();
            ArrayList<ArrayList<Float>> face_vert_norms = new ArrayList<ArrayList<Float>>();
            ArrayList<ArrayList<ArrayList<Float>>> face_instructions = new ArrayList<ArrayList<ArrayList<Float>>>();

            // loop through vert data of face
            for (ArrayList<Integer> vert : face) {
                int v = vert.get(0);
                ArrayList<Float> vertex = vertices.get(v-1);
                ArrayList<Float> offset_vertex = new ArrayList<Float>();
                offset_vertex.add(vertex.get(0)+x);
                offset_vertex.add(vertex.get(1)+y);
                offset_vertex.add(vertex.get(2)+z);
                face_verts.add(offset_vertex);

                try {
                    int vt = vert.get(1);
                    face_vert_texts.add(vert_texts.get(vt-1));
                }
                catch (Exception e) {
                    ;
                }
                
                try {
                    int vn = vert.get(2);
                    face_vert_norms.add(vert_norms.get(vn-1));
                }
                catch (Exception e) {
                    ;
                }
            }

            face_instructions.add(face_verts);
            face_instructions.add(face_vert_texts);
            face_instructions.add(face_vert_norms);

            draw_instruct.add(face_instructions);
        }

        return draw_instruct;
    }

    public ArrayList<ArrayList<String>> get_face_attributes() {
        return new ArrayList<ArrayList<String>>();
    }
}