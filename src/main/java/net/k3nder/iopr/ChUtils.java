package net.k3nder.iopr;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krist
 */

public class ChUtils {
    /**
     * maximum long of one chunk
     */
    public static Integer chunksSize = 128;
    public ChUtils() {
        throw new IllegalCallerException("ChUtils is a utils class");
    }

    /**
     * @param dir directory of the chunk .ch files
     * @param output output file
     * @throws IOException for create a  file and write it
     */
    public static void reMake(File dir, File output) throws IOException {
        byte[] result = new byte[]{};
        if(!dir.isDirectory()) throw new IllegalArgumentException("is a not directory: " + dir);
        for(int i = 0; true; i++){
            File file = new File(dir, i + ".ch");
            if(!file.exists()) break;
            System.out.println(file);
            byte[] ch = FileUtils.readFileToByteArray(file);
            result = plusArray(result,ch);
        }
        if(output.exists()) throw new IllegalArgumentException("output: " + output + " ready exist");
        FileUtils.writeByteArrayToFile(output, result);
    }

    /**
     *
     * @param file file to fragment on chunks
     * @return chunks
     */
    public static Chunk[] separateOnChunks(File file){
        try{
            List<Chunk> result = new ArrayList<>();
            FileInputStream inputStream = FileUtils.openInputStream(file);
            final int FILE_SIZE = inputStream.available();
            int readBytes = 0;
            System.out.println("file = " + file);
            System.out.println("FILE_SIZE = " + FILE_SIZE);

            while (readBytes<FILE_SIZE){
                System.out.println(Math.min((FILE_SIZE - readBytes), chunksSize));
                int available_size = Math.min((FILE_SIZE - readBytes), chunksSize);
                byte[] data = readData(readBytes, available_size, file);
                result.add(new Chunk(data));
                readBytes += available_size;
            }
            return result.toArray(new Chunk[]{});
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readData(int of, int to, File input) throws IOException {
        System.out.println("size_max: " + chunksSize + " of: " + of + " to: " + to);
        RandomAccessFile file = new RandomAccessFile(input, "r");
        file.seek(of);
        byte[] buffer = new byte[to]; // Buffer del tamaño deseado
        file.read(buffer);
        return buffer;
    }

    /**
     * generate .ch files of the chunks
     * @param chunks chunks to write in .ch files
     * @param dirName output directory for generate the .ch files
     * @throws IOException
     */
    public static void generateChunksFiles(Chunk[] chunks, String dirName) throws IOException {
        for(int i = 0; i<chunks.length; i++){
            Chunk ch = chunks[i];
            File chFile = new File(dirName, i + ".ch");
            FileUtils.writeByteArrayToFile(chFile,ch.getData());
        }
    }
    private static byte[] plusArray(byte[] array1, byte[] array2){
        byte[] sumArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, sumArray, 0, array1.length);
        System.arraycopy(array2, 0, sumArray, array1.length, array2.length);
        return sumArray;
    }
}
