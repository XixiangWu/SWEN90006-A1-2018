package swen90006.machine;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.util.List;

public class SimpleDriver {

    private static final int MEMORY_SIZE = Machine.MAX_ADDR+1;
    
    public static void main(String[] args) throws Exception
    {
        if (args.length != 1){
            System.err.println("Usage: java SimpleDriver <inputfile>");
	    System.err.println("\nMust have 1 argument instead of " + args.length);
            System.exit(1);
        }

        Charset charset = Charset.forName("UTF-8");

        List<String> lines = null;
        try {
            lines = Files.readAllLines(FileSystems.getDefault().getPath(args[0]), charset);
        }catch (Exception e){
            System.err.println("Invalid input file! (stacktrace follows)");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        Machine m = null;
        try {
            System.out.println("There are this many instructions: " + lines.size());

            m = new Machine();
            
            Integer res = null;
            res = m.execute(lines);
            System.out.println("Program result: " + res + "\n");

        } catch (Exception e) {
            System.err.println("Exception while executing program. (stacktrace follows)");
            e.printStackTrace(System.err);
            if (m != null){
                System.err.println("Number of instructions executed before exception: " + m.getCount());
            }
            System.exit(1);
        }
    }
}
