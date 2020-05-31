import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class VmParser {
    private static final String POINTER_LOCATION = "3",
                                TEMP_LOCATION = "5",  STATIC_LOCATION = "16",
                                CONSTANT_LOCATION = "SP";

    private ArrayList<String> vmLines;
    private String vmFileName;
    private static int conditionCounter = 0, labaelCounter = 0;
    private String currentFunc = "";
    private static Stack<String> functionsStack = new Stack<>();
    private final String filePrefix = "";

    /**
     * Constructor
     * @param vmLines list of parsable vm code lines
     */
    public VmParser(ArrayList<String> vmLines, String fileName)
    {
        this.vmLines = new ArrayList<>(vmLines);
        this.vmFileName = fileName;
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
                if(lineParts.get(0).equals("function"))
                {
                    asmLines.addAll(translateFunctionDeclaration(lineParts)); // function declaration
                }
                else if (lineParts.get(0).equals("call"))
                {
                    asmLines.addAll(translateFunctionCalling(lineParts));
                }
                else {
                    asmLines.addAll(translateElementaryOperation(lineParts)); // push/pop operation
                }

            }
            else if(lineParts.size() == 1) // arithmetic operation
            {
                if(lineParts.get(0).equals("return"))
                {
                    asmLines.addAll(translateReturn());
                }
                else
                {
                    asmLines.addAll(translateArithmeticOperation(lineParts.get(0)));
                }
            }
            else if(lineParts.size() == 2) // branching operation
            {
                asmLines.addAll(translateBranchingOperation(lineParts));
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



        String asmFilePath = command+"Library/";
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
        String asmFilePath = "arithmeticLibrary/" + operation + ".asm";
        return getAsmFile(asmFilePath, "", ""); // stackArea and values are not needed
    }

    private ArrayList<String> translateBranchingOperation(ArrayList<String> vmLineParts)
    {
        String operation = vmLineParts.get(0), label = vmLineParts.get(1);
        String asmFilePsth = "branchingLibrary/"+operation + ".asm";

        return getAsmFile(asmFilePsth, "", label);
    }

    private ArrayList<String> translateFunctionDeclaration(ArrayList<String> vmLineParts)
    {
        ArrayList<String> translated = new ArrayList<>();
        String command = vmLineParts.get(0), funcName = vmLineParts.get(1);
        int pushAmount = Integer.parseInt(vmLineParts.get(2));

        currentFunc = funcName;

        translated.addAll(getAsmFile("functionOperationLibrary/declaration.asm", "", ""));

        String localize = "push constant 0";
        ArrayList<String> lineParts = new ArrayList<>(Arrays.asList(localize.split("\\s+")));

        for(int i = 1; i <= pushAmount; i++)
        {
            translated.addAll(translateElementaryOperation(lineParts));
        }


        return translated;
    }

    private ArrayList<String> translateReturn()
    {

        if (!functionsStack.empty()){
            functionsStack.pop();
            if(!functionsStack.empty()){
                currentFunc = functionsStack.peek();
            }
    }

        return getAsmFile("functionOperationLibrary/returnOperation.asm", "", "");
    }

    private ArrayList<String> translateFunctionCalling(ArrayList<String> vmLineParts)
    {
        ArrayList<String> translated = new ArrayList<>();
        translated.addAll(getAsmFile("functionOperationLibrary/saveReturnValue.asm", "", ""));
        functionsStack.push(vmLineParts.get(1));
        currentFunc = functionsStack.peek();

        translated.addAll(getAsmFile("functionOperationLibrary/callFunction.asm", "", vmLineParts.get(2)));
        labaelCounter++;

        return translated;
    }
    /*
    Returns an asm file by given name and updates its VALUE and STACKAREA fields if it's an elementary operation
     */
    private ArrayList<String> getAsmFile(String asmFilePath, String stackArea, String value)
    {
        asmFilePath = filePrefix + asmFilePath;
        ArrayList<String> asmLines = new ArrayList<>();
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(asmFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                vmFileName = vmFileName.substring(vmFileName.lastIndexOf("\\")+1);
                line = line.replace("FILE", vmFileName); // in lables
                line = line.replace("STATIC_ADDRESS", vmFileName + "." + value);
                line = line.replace("VALUE", value);
                line = line.replace("AREA", stackArea);
                line = line.replace("NUMBER_X", Integer.toString(conditionCounter)); // in eq, gt, lt
                line = line.replace("FUNCTION", currentFunc);
                line = line.replace("LABEL_X", Integer.toString(labaelCounter));
                asmLines.add(line);
            }
            return asmLines;
        } catch (Exception e) // not really necessary since we assume valid input
        {
            return asmLines;
        }
    }



}
