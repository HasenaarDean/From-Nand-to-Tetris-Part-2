import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class VMtranslator {

    private static String asmFileName;

    private static final Pattern
            elementaryOperation = Pattern.compile("\\s*(push|pop)" +
            "\\s+(local|constant|argument|this|that|temp|pointer|static)\\s+\\d+\\s*"),
            arithmeticOperation = Pattern.compile("\\s*(add|sub|neg|eq|gt|lt|and|or|not)\\s*"),
            branchingOperation = Pattern.compile("\\s*(label|if-goto|goto)\\s+.+\\s*"),
            functionCalling = Pattern.compile("\\s*call\\s+.+\\s+\\d+\\s*"),
            functionDeclaration = Pattern.compile("\\s*function\\s+.+\\s+\\d+\\s*"),
            returnStatement = Pattern.compile("\\s*return\\s*");

    /*
    Parses given file to a list of code
     */
    private static ArrayList<String> getCodeLines(String fileName)
    {
        ArrayList<String> codeLines = new ArrayList<>();
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                codeLines.add(line);
            }
            return codeLines;
        } catch (Exception e) // not really necessary since we assume valid input
        {
            return null;
        }
    }

    /*
    Gets all vm files from given directory. returns map<fileName, list of its content>
     */
    private static HashMap<String, ArrayList<String>> getVmFiles(String input)
    {
        HashMap<String, ArrayList<String>> vmFiles = new HashMap<>();
        if (input.endsWith(".vm")) // got only one file
        {
            String fileName = input.substring(0, input.indexOf('.'));
            asmFileName = fileName;
            vmFiles.put(fileName, getCodeLines(input));

        }
        else // got a directory
        {
            File dir = new File(input);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                asmFileName = input + "/" + dir.getName();
                for (File child : directoryListing) {
                    input = child.getPath();
                    if (input.endsWith(".vm")) {
                        String fileName = input.substring(0, input.indexOf('.'));
                        vmFiles.put(fileName, getCodeLines(input));
                    }
                }
            }
        }
        return vmFiles;
    }

    /*
    Removes comments and spaces for the actual parsing
     */
    private static ArrayList<String> getParsableLines(ArrayList<String> rawLines)
    {
        ArrayList<String> codeLines = new ArrayList<>();
        for(String line : rawLines)
        {
            if(line.contains("/")) // check mid-line comment
            {
                line = line.substring(0,line.indexOf('/'));
            }
            if(line.matches(elementaryOperation.pattern()) || line.matches(arithmeticOperation.pattern())
                    || line.matches(branchingOperation.pattern()) || line.matches(functionDeclaration.pattern())
                    || line.matches(returnStatement.pattern()) || line.matches(functionCalling.pattern()))
            {

                codeLines.add(line);
            }
        }
        return codeLines;
    }


    private static void printList(ArrayList<String> list)
    {
        System.out.println("------------------------");
        for(String line : list)
        {
            System.out.println(line);
        }
        System.out.println("------------------------");
    }

    public static void main(String[] args)
    {
        HashMap<String, ArrayList<String>> vmFiles = getVmFiles(args[0]);
        ArrayList<String> parsedAsmLines = new ArrayList<>();

        // BOOTSTRAP CODE
        parsedAsmLines.add("@256\nD=A\n@SP\nM=D\n");
        ArrayList<String> bootStrapVm = new ArrayList<>();
        bootStrapVm.add("call Sys.init 0");
        vmFiles.put("", bootStrapVm);

        for(String fileName : vmFiles.keySet())
        {
            ArrayList<String> vmLines = getParsableLines(vmFiles.get(fileName));

            VmParser vmParser = new VmParser(vmLines, fileName.substring(fileName.lastIndexOf("/")+1));
            ArrayList<String> asmLines = vmParser.Parse();

            parsedAsmLines.addAll(asmLines);
        }

        try {
            FileWriter writer = new FileWriter(asmFileName + ".asm");
            for(String asmLine: parsedAsmLines) {
                writer.write(asmLine +"\n");
            }
            writer.close();
        }
        catch (Exception e) {
            System.err.println("");
        }

    }
}
