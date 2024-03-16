package net.k3nder.iopr;

/**
 * @author krist
 */
public class Chunk {
    /**
     * constrains a data of the chunk
     */
    private byte[] data;
    public Chunk(byte[] data){
        this.data = data;
    }
    public byte[] getData() {
        return data;
    }
}
