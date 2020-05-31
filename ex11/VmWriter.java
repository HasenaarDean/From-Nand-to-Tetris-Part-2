import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class VmWriter {
    HashMap<String, String> arithmeticTable;

    private FileWriter vmFileWriter;
    private int labelIndex;
    private int lineCount;

    public VmWriter(String fileName) {
        try {
            vmFileWriter = new FileWriter(fileName + ".vm");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        lineCount = 1;
        labelIndex = 0;
        setArithmeticTable();
    }

    private void write(String command, String arg, String arg2) {
        try {
            vmFileWriter.write(command + " " + arg + " " + arg2 + "\n");
            //System.out.println(lineCount++ +"    " + command + " " + arg + " " + arg2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void writePush(String segment, int index) {
        segment = segment.replace("field", "this");
        write("push", segment, Integer.toString(index));
    }

    public void writePop(String segment, int index) {
        segment = segment.replace("field", "this");
        write("pop", segment, Integer.toString(index));
    }

    public void writeArithmetic(String command) {
        String operation = arithmeticTable.get(command) == null ? command : arithmeticTable.get(command);
        write(operation, "", "");
    }

    public void writeLabel(String label) {
        write("label", label, "");
    }

    public void writeGoTo(String label) {
        write("goto", label, "");
    }

    public void writeIf(String label) {
        write("if-goto", label, "");
    }

    public void writeCall(String name, int nArgs) {
        write("call", name, Integer.toString(nArgs));
    }

    public void writeFunction(String name, int nLocals) {
        write("function", name, Integer.toString(nLocals));
    }

    public void writeReturn() {
        write("return", "", "");
    }

    public void close() {
        try {
            vmFileWriter.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getNewLabel(){
        String label = "LABEL_" + labelIndex;
        labelIndex++;

        return label;

    }

    private void setArithmeticTable()
    {
        arithmeticTable = new HashMap<>();

        arithmeticTable.put("+", "add");
        arithmeticTable.put("-", "sub");
        arithmeticTable.put("&amp;", "and");
        arithmeticTable.put("|", "or");
        arithmeticTable.put("&gt;", "gt");
        arithmeticTable.put("&lt;", "lt");
        arithmeticTable.put("=", "eq");
        arithmeticTable.put("*", "call Math.multiply 2");
        arithmeticTable.put("/", "call Math.divide 2");
        arithmeticTable.put("~", "neg");
        arithmeticTable.put("!", "not");


    }
}
