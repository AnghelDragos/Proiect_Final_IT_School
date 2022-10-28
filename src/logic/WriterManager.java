package logic;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriterManager {
    private final BufferedWriter bufferedWriter;

    //TODO de implementat logica ca la reader
    public WriterManager(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void write(String str){
        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush(){
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
