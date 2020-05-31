import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assembler {

    static final String SPACES = "\\s*";
    static final String BEGINNING_OF_REMARK_LINE = "//";
    static final String COMMAND_LINE_PATTERN = "(\\S+)" + "(//)" + "(\\S)";
    static final String INNER_REMARK = "(//)+" + "(\\S)*";
    static final String OPEN_BRACKETS = "(";
    static final String CLOSE_BRACKETS = "\\)";
    static final String AT_SIGN = "@";
    static final Integer REGISTER16 = 16;
    static final String NUMBER = "\\d+";


    /*
    checked and works
     */
    private static ArrayList<String> spaceCommentRemoval(ArrayList<String> lst) {
        ArrayList<String> filtered_lines_list = new ArrayList<>();
        Pattern p = Pattern.compile(COMMAND_LINE_PATTERN);
        for (String tmp : lst) {
            tmp = tmp.replaceAll(SPACES, "");
            if (tmp.equals("")) {
                continue;
            }


            if (!tmp.startsWith(BEGINNING_OF_REMARK_LINE)) {
                tmp = tmp.replaceAll(INNER_REMARK, "");
                filtered_lines_list.add(tmp);
            }
        }
        return filtered_lines_list;
    }

    /*
    Saves lables to the symbols table and removes the label lines from the array
    checked worked
     */
    private static ArrayList<String> labelsLoop(HashMap<String, Integer> symbols, ArrayList<String> lst) {
        ArrayList<String> no_labels_lines_list = new ArrayList<>();
        Integer i = 0;
        for (String tmp : lst) {
            if (tmp.startsWith("(")) {
                String tempName = tmp.replaceAll("[(]", "");
                tempName = tempName.replaceAll("[)]", "");
                symbols.put(tempName, i);
            } else {
                no_labels_lines_list.add(tmp);
                i++;
            }

        }
        return no_labels_lines_list;
    }

    /*
    Stores vairable in the table and replaces them with their index in the file
     */
    private static ArrayList<String> variablesLoop(HashMap<String, Integer> symbols,
                                                   ArrayList<String> lst) {

        ArrayList<String> final_list = new ArrayList<>();
        Integer i = REGISTER16;
        for (String tmp : lst) {
            if (tmp.startsWith(AT_SIGN) && (!Character.isDigit(tmp.charAt(1)))) {
                String name;
                name = tmp.replaceAll(AT_SIGN, "");
                if (!symbols.containsKey(name)) {
                    symbols.put(name, i);
                    String updated_line_tmp;
                    updated_line_tmp = AT_SIGN + i.toString();
                    final_list.add(updated_line_tmp);
                    i++;
                } else {
                    Integer tmpValue = symbols.get(name);
                    String updated_line_tmp;
                    updated_line_tmp = AT_SIGN + tmpValue.toString();
                    final_list.add(updated_line_tmp);
                }
            } else {
                final_list.add(tmp);
            }
        }

        return final_list;

    }

    /*
    Creates the symbol map
     */
    private static void initializeSymbolMap(HashMap<String, Integer> symbols) {
        symbols.put("R0", 0);
        symbols.put("R1", 1);
        symbols.put("R2", 2);
        symbols.put("R3", 3);
        symbols.put("R4", 4);
        symbols.put("R5", 5);
        symbols.put("R6", 6);
        symbols.put("R7", 7);
        symbols.put("R8", 8);
        symbols.put("R9", 9);
        symbols.put("R10", 10);
        symbols.put("R11", 11);
        symbols.put("R12", 12);
        symbols.put("R13", 13);
        symbols.put("R14", 14);
        symbols.put("R15", 15);
        symbols.put("SCREEN", 16384);
        symbols.put("KBD", 24576);
        symbols.put("SP", 0);
        symbols.put("LCL", 1);
        symbols.put("ARG", 2);
        symbols.put("THIS", 3);
        symbols.put("THAT", 4);
    }

    /*
    Creates a list ready for the assembler to parse
     */
    private static ArrayList<String> getLinesForParser(ArrayList<String> codeLines) {
        HashMap<String, Integer> symbols = new HashMap<>();
        initializeSymbolMap(symbols);

        codeLines = spaceCommentRemoval(codeLines);
        codeLines = labelsLoop(symbols, codeLines);
        codeLines = variablesLoop(symbols, codeLines);

        return codeLines;
    }

    private static BufferedReader getReader(String sjavaFile) throws FileNotFoundException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(sjavaFile));
        return reader;
    }
    private static ArrayList<String> getCodeLines(String fileName)
    {
        ArrayList<String> codeLines = new ArrayList<>();
        try {
            BufferedReader reader = getReader(fileName);
            String line;
            while ((line = reader.readLine()) != null) {
                codeLines.add(line);
            }

            return codeLines;
        }
        catch (Exception e) // not really necessary since we assume valid input
        {
            return null;
        }
    }

    private static HashMap<String, ArrayList<String>> getAsmFiles(String input) {
        HashMap<String, ArrayList<String>> asmFiles = new HashMap<>();
        if (input.endsWith(".asm")) // got only one file
        {
            String fileName = input.substring(0, input.indexOf('.'));
            asmFiles.put(fileName, getCodeLines(input));
        }
        else // got a directory
        {
            File dir = new File(input);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    input = child.getPath();
                    if (input.endsWith(".asm")) {
                        String fileName = input.substring(0, input.indexOf('.'));
                        asmFiles.put(fileName, getCodeLines(input));
                    }
                }
            }
        }

        return asmFiles;
    }


    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> asmFiles = getAsmFiles(args[0]);

        for(String fileName : asmFiles.keySet())
        {
            ArrayList<String> asmLines = getLinesForParser(asmFiles.get(fileName));

            AsmParser parser = new AsmParser(asmLines);

            List<String> result = parser.Parse();

            try {
                FileWriter writer = new FileWriter(fileName + ".hack");
                for(String cpuLine: result) {
                    writer.write(cpuLine +"\n");
                }
                writer.close();
            }
            catch (Exception e) {
            }
        }
    }
}
