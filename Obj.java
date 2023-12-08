import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Obj {
    double x;
    double y;
    double z;
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

    public Obj(double x_pos, double y_pos, double z_pos, String filename) {
        x = x_pos;
        y = y_pos;
        z = z_pos;
        
        //read file
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
                System.out.println("empty");
                continue;
            }
            
            switch (lines.get(line).charAt(0)) {
                case '#':
                    vertices.add(new ArrayList<Float>());
                    vert_texts.add(new ArrayList<Float>());
                    vert_norms.add(new ArrayList<Float>());

                    continue;
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

                            System.out.println(vertices);

                            break;
                        case 't':
                            for (int c = 3; c < lines.get(line).length(); c++) {
                                System.out.println(lines.get(line).charAt(c));
                            } 
                            break;
                        case 'n':
                            for (int c = 3; c < lines.get(line).length(); c++) {
                                System.out.println(lines.get(line).charAt(c));
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

            System.out.println("----------------------------");
        }
    }

    public void draw() {
        System.out.println(faces);
    }
}
