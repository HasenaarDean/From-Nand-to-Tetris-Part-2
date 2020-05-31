import java.io.FileWriter;

public class XmlWriter {

    FileWriter xmlFileWriter;

    public XmlWriter(String fileName) {
        try {
            xmlFileWriter = new FileWriter(fileName + ".xml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void write(int indentationAmount, String string)
    {
        StringBuilder sb = new StringBuilder();
        String indentation = "";
        for(int i = 1; i <= indentationAmount; i++)
        {
            indentation = sb.append(" ").toString();
        }

        try
        {
            //System.out.println(indentation + string);
            xmlFileWriter.write(indentation +  string + "\n");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void close()
    {
        try
        {
            xmlFileWriter.close();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

