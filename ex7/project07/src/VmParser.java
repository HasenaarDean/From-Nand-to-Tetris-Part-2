

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class VmParser {

    /*
    private static HashMap<String, String> pushLibrary, popLibrary;
    static {initLibraries();}

    private static void initLibraries()
    {
        pushLibrary = new HashMap<>();
        popLibrary = new HashMap<>();

        pushLibrary.put("constant", "src/pushLibrary/constant.asm"); // constant
        pushLibrary.put("latt", "src/pushLibrary/latt.asm"); // local, argument, this, and that
        pushLibrary.put("pt", "src/pushLibrary/pt.asm"); // pointer and temp
        pushLibrary.put("static", "stc/pushLibrary/static.asm");

        popLibrary.put("constant", "src/popLibrary/constant.asm"); // constant
        popLibrary.put("latt", "src/popLibrary/latt.asm"); // local, argument, this, and that
        popLibrary.put("pt", "src/popLibrary/pt.asm"); // pointer and temp
        popLibrary.put("static", "stc/popLibrary/static.asm");

    }
    */

    private static final String POINTER_LOCATION = "3",
                                TEMP_LOCATION = "5",  STATIC_LOCATION = "16",
                                CONSTANT_LOCATION = "SP";

    private ArrayList<String> vmLines;
    private String vmFileName;
    private int conditionCounter;

    /**
     * Constructor
     * @param vmLines list of parsable vm code lines
     */
    public VmParser(ArrayList<String> vmLines, String fileName)
    {
        this.vmLines = new ArrayList<>(vmLines);
        this.vmFileName = fileName;
        this.conditionCounter = 0;
    }

    /**
     * Parses vm File to an asm File
     * @return list of asm code lines
     */
    public ArrayList<String> Parse()
    {
        ArrayList<String> asmLines = new ArrayList<>();
        for (String vmLine : vmLines)
        {

            ArrayList<String> lineParts = new ArrayList<>(Arrays.asList(vmLine.split("\\s+")));
            if(lineParts.size() == 3) // elementary operation
            {
                asmLines.addAll(translateElementaryOperation(lineParts));
            }
            else // arithmetic operation
            {
                asmLines.addAll(translateArithmeticOperation(lineParts.get(0)));
            }
        }

        return asmLines;
    }

    /*
    Generates a path to an asm line from a single vm line
     */
    private ArrayList<String> translateElementaryOperation(ArrayList<String> vmLineParts)
    {
        String command = vmLineParts.get(0), stackArea = vmLineParts.get(1), value = vmLineParts.get(2),
                stackAreaFile = "";

        String asmFilePath = "src/"+command+"Library/";
        switch (stackArea)
        {
            case "constant":
                stackArea = CONSTANT_LOCATION;
                stackAreaFile = "constant";
                break;
            case "local":
                stackArea = "LCL";
                stackAreaFile = "latt";
                break;
            case "argument":
                stackArea = "ARG";
                stackAreaFile = "latt";
                break;
            case "this":
                stackArea = "THIS";
                stackAreaFile = "latt";
                break;
            case "that":
                stackArea = "THAT";
                stackAreaFile = "latt";
                break;
            case "pointer":
                stackArea = POINTER_LOCATION;
                stackAreaFile = "pt";
                break;
            case "temp":
                stackArea = TEMP_LOCATION;
                stackAreaFile = "pt";
                break;
            case "static":
                stackArea = STATIC_LOCATION;
                stackAreaFile = "static";
                break;
        }
        asmFilePath += stackAreaFile + ".asm";

        return getAsmFile(asmFilePath, stackArea, value);
    }

    private ArrayList<String> translateArithmeticOperation(String operation)
    {
        if(operation.equals("gt") || operation.equals("lt") || operation.equals("eq"))
        {
            conditionCounter++;
        }
        String asmFilePath = "src/arithmeticLibrary/" + operation + ".asm";
        return getAsmFile(asmFilePath, "", ""); // stackArea and values are not needed
    }

    /*
    Returns an asm file by given name and updates its VALUE and STACKAREA fields if it's an elementary operation
     */
    private ArrayList<String> getAsmFile(String asmFilePath, String stackArea, String value)
    {
        ArrayList<String> asmLines = new ArrayList<>();
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(asmFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("STATIC_ADDRESS", vmFileName + "." + value);
                line = line.replace("VALUE", value);
                line = line.replace("AREA", stackArea);
                line = line.replace("NUMBER_X", Integer.toString(conditionCounter)); // in eq, gt, lt
                asmLines.add(line);
            }
            return asmLines;
        } catch (Exception e) // not really necessary since we assume valid input
        {
            return null;
        }
    }



}
