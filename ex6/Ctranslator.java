import java.util.HashMap;

public class Ctranslator {

    private HashMap<String, HashMap<String, String>> cTable;

    public Ctranslator()
    {
        init_tables();
    }

    public String getCpuLine(String instruction)
    {
        String dest, comp, jmp;

        if(instruction.contains("=")){dest=instruction.substring(0,  instruction.indexOf("=")); }
        else { dest = ""; }


        if(instruction.contains(";")) { jmp=instruction.substring(instruction.indexOf(";")+1,  instruction.length()); }
        else { jmp = ""; }

        comp =  instruction.substring(dest.length(), instruction.length() - jmp.length())
                .replace("=", "")
                .replace(";", "");

        String destCPU = translateDest(dest),
               compCPU = translateComp(comp),
               jmpCPU = translateJmp(jmp);

        return  compCPU + destCPU + jmpCPU;
    }

    private String translateDest(String dest)
    {
        return cTable.get("dest").get(dest);
    }

    private String translateComp(String comp) {


        if(comp.contains("*") || comp.contains(">") || comp.contains("<"))
        {
            return "1" + cTable.get("comp").get(comp) + "0000";
        }
        else
        {
            String a="0", msbits = "111"; // 15,14,13 bits
            if(comp.contains("M")) a="1"; // M OR A register
            comp = comp.replaceAll("[MA]", "X");
            return msbits + a + cTable.get("comp").get(comp);
        }

    }

    private String translateJmp(String jmp) {
        return cTable.get("jmp").get(jmp);
    }

    private HashMap<String, HashMap<String, String>> init_tables()
    {
        cTable = new HashMap<String, HashMap<String, String>>();

        HashMap<String, String> destTable = new HashMap<String, String>();
        destTable.put("",    "000");
        destTable.put("M",   "001");
        destTable.put("D",   "010");
        destTable.put("MD",  "011");
        destTable.put("A",   "100");
        destTable.put("AM",  "101");
        destTable.put("AD",  "110");
        destTable.put("AMD", "111");
        cTable.put("dest", destTable);


        HashMap<String, String> compTable = new HashMap<String, String>();
        compTable.put("0",    "101010");
        compTable.put("1",    "111111");
        compTable.put("-1",   "111010");
        compTable.put("D",    "001100");
        compTable.put("X",    "110000");
        compTable.put("!D",   "001101");
        compTable.put("!X",   "110001");
        compTable.put("-D",   "001111");
        compTable.put("-X",   "110011");
        compTable.put("D+1",  "011111");
        compTable.put("X+1",  "110111");
        compTable.put("D-1",  "001110");
        compTable.put("X-1",  "110010");
        compTable.put("D+X",  "000010");
        compTable.put("D-X",  "010011");
        compTable.put("X-D",  "000111");
        compTable.put("D&X",  "000000");
        compTable.put("D|X",  "010101");

        //TODO check shift and mul
        compTable.put("D*A",  "10000");
        compTable.put("D*M",  "10100");
        compTable.put("D<<",  "01011");
        compTable.put("A<<",  "01010");
        compTable.put("M<<",  "01110");
        compTable.put("D>>",  "01001");
        compTable.put("A>>",  "01000");
        compTable.put("M>>",  "01100");

        cTable.put("comp", compTable);

        HashMap<String, String> jmpTable = new HashMap<String, String>();
        jmpTable.put("",    "000");
        jmpTable.put("JGT", "001");
        jmpTable.put("JEQ", "010");
        jmpTable.put("JGE", "011");
        jmpTable.put("JLT", "100");
        jmpTable.put("JNE", "101");
        jmpTable.put("JLE", "110");
        jmpTable.put("JMP", "111");
        cTable.put("jmp", jmpTable);


        return cTable;
    }
}
