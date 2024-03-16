package net.k3nder.iopr;

import picocli.CommandLine;

import java.io.*;

/**
 * @author krist
 */
@CommandLine.Command(name = "MiApp", mixinStandardHelpOptions = true, version = "1.0",
        description = "Una aplicación de ejemplo con Picocli")
public class Main {
    // archivo de ejemplo
    /*
    El archivo se va apartir en partes de 10 bytes(se puede cambiar con la entrada), la entrada sera mediante terminal para selecionar el archivo,
    este creara una carpeta con los CHUNCKS (partes del archivo) con el nombre del chunk como 0.chk...
    los CHUNCKs tambien tendran un byte para indicar su tamaño,

    para juntarlo, se iteratra una carpta con sus chuncks i se juntaran en uno escribiendo SOLO el tamaño del archivo

    FUNC:
    se tiene que tener una veriable de tipo int que almacene los bites que se han leido asta el momento

    se leeran bytes desde ese index de bytes asta, si es mayor que 10 leer 10 si es menor leer lo que se pueda leer
    */
    private static final File EXAMPLE_FILE = new File("C:\\Users\\krist\\Downloads\\Screenshot_20240229-195926.png");
    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }
    @CommandLine.Command(name = "fragment")
    public void Fragment(
            @CommandLine.Option(names = {"-f","--file"}, required = true, description = "file to fragment") String input,
            @CommandLine.Option(names = {"-o","--output"}, required = true, description = "output path") String output,
            @CommandLine.Option(names = {"-b","--bytes"}, required = false, description = "bytes size of one chunk", defaultValue = "8") int byt
            ) throws IOException {
        File file = new File(input);
        ChUtils.chunksSize = byt;
        if(!file.exists()) throw new IllegalArgumentException("error, file: " + input + " don't exist");
        Chunk[] chs = ChUtils.separateOnChunks(file);
        ChUtils.generateChunksFiles(chs, output);
    }
    @CommandLine.Command(name = "remake")
    public void remake(
            @CommandLine.Option(names = {"-p","--path"}, required = true, description = "path of chunks") String input,
            @CommandLine.Option(names = {"-o","--output"}, required = true, description = "file output") String output
    ) throws IOException {
        File inputFile = new File(input);
        File outputFile = new File(output);
        if(!inputFile.exists()) throw new IllegalArgumentException("input file don't exist");
        if(outputFile.exists()) throw new IllegalArgumentException("output file ready exist");
        ChUtils.reMake(inputFile, outputFile);
    }
}