package data;

import java.io.*;

public class FileInfo {

    public static BufferedReader createReader() {
        File input = new File("resources/input.txt");
        try {
            return new BufferedReader(new FileReader(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public static BufferedWriter createWriter(){
        File output = new File("resources/output.txt");
        if(output.exists()){
            output.delete();
            try {
                return new BufferedWriter(new FileWriter(output));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }else{
            try {
                return new BufferedWriter(new FileWriter(output));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
