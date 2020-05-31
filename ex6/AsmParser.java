import java.util.*;

public class AsmParser {

    private ArrayList<String> asmLines, cpuLines;
    private final int LINE_LENGHT = 16;


    public AsmParser(List<String> codeLines) // notice map and list
    {
        this.asmLines = new ArrayList<String>(codeLines);
        this.cpuLines = new ArrayList<>();
    }

    public ArrayList<String> Parse() {
        Ctranslator ctranslator = new Ctranslator();
        for (String asmLine : asmLines) {

            String cpuLine;

            if (asmLine.startsWith("@")) // A instruction
            {
                cpuLine = translateA(asmLine);
            }
            else // C instruction
            {
                cpuLine = ctranslator.getCpuLine(asmLine);
            }

            cpuLines.add(cpuLine);
        }

        return cpuLines;
    }

    private  String translateA(String line) {
        Integer address = Integer.parseInt(line.substring(1)); // ignore @ and go to int
        String cpuLine = Integer.toBinaryString(address);
        String zeroPad = String.join("",
                Collections.nCopies(LINE_LENGHT - cpuLine.length(), "0"));

        cpuLine = zeroPad + cpuLine;

        return cpuLine;
    }
}
