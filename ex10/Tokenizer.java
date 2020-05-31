import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

public class Tokenizer {

    private static String keywords = "class|constructor|function|method|field|static|var|int|" +
            "char|boolean|void|true|false|null|this|let|do|if|else|while|return";
    private static String symbols = "[\\{\\}\\(\\)\\[\\]\\.\\,\\;\\+\\-\\*\\/\\&\\|\\<\\>\\=\\~]";
    private static String identifierRegexString = "[a-zA-Z_]+[a-zA-Z_0-9]*";
    private static String intRegexString = "\\d+";
    private static String stringRegexString = "\"[^\"\n]*\"";
    private static Pattern allTokenPatterns = Pattern.compile(keywords + "|" + symbols + "|" + stringRegexString
            + "|" + identifierRegexString + "|" + intRegexString);
    private static String currentToken = "";
    private static String currentTokenType = "";
    private static Pattern stringPattern = Pattern.compile(stringRegexString);
    private static Pattern restOfCommentBlockBeginningOfLine = Pattern.compile("\\s*" + "\\*");
    private static Pattern endOfCommentBlockBeginningOfLine = Pattern.compile("\\s*" + "\\*/");
    private static Pattern emptyLine = Pattern.compile("\\s*");
    private static Pattern startCommentLine = Pattern.compile("\\s*//");
    private ArrayList<String> tokensList = new ArrayList<>();
    private static Integer counter = -1;

//        public static void main(String[] args){
//
//        File file = new File("C:\\Users\\deanh\\Desktop\\nand2tetris\\projects\\10\\Square\\SquareGame.jack");
//        Tokenizer tokenizer;
//        tokenizer = new Tokenizer(file);
//
//        printList(tokenizer.tokensList);
//
//    }

    @Override
    public String toString() {
        return "in " + counter + " : " + currentToken;
    }

    /**
     * Opens the input file/stream and gets ready
     to tokenize it.
     * @param jackFile the input file.
     */
    public Tokenizer(File jackFile)
    {
        ArrayList<String> jackCodeLines = new ArrayList<>();
        jackCodeLines = getCodeLines(jackFile.getAbsolutePath());
        jackCodeLines = getParsableLines(jackCodeLines);
        String joinedCode;
        joinedCode = String.join("\n", jackCodeLines);
        Matcher matcher = allTokenPatterns.matcher(joinedCode);
        tokensList = new ArrayList<>();
        counter = -1;

        while (matcher.find()){
            if(matcher.group().equals(">")){
                tokensList.add("&gt;");
            } else if(matcher.group().equals("<")){
                tokensList.add("&lt;");
            } else if (matcher.group().equals("&")){
                tokensList.add("&amp;");
            } else {
                tokensList.add(matcher.group());
            }
        }

        for(int i = 0; i < this.tokensList.size(); i++){
            Matcher matcherString = stringPattern.matcher(this.tokensList.get(i));
            if(matcherString.matches()){
                this.tokensList.set(i, this.tokensList.get(i).replaceAll("&", "&amp;"));
                this.tokensList.set(i, this.tokensList.get(i).replaceAll(">", "&gt;"));
                this.tokensList.set(i, this.tokensList.get(i).replaceAll("<", "&lt;"));
            }
        }


        currentToken = "";
        currentTokenType = "";

    }

    /**
     * Do we have more tokens in the input?
     * @return boolean.
     */
    public boolean hasMoreTokens() {

        return counter + 1 < tokensList.size();
    }

    /**
     * updates token's type.
     */
    private void updateTokenType(){
        if(currentToken.matches(keywords)){
            currentTokenType = "keyword";
        } else if(currentToken.matches(symbols)){
            currentTokenType = "symbol";
        } else if(currentToken.matches(identifierRegexString)){
            currentTokenType = "identifier";
        } else if(currentToken.matches(intRegexString)){
            currentTokenType = "intConst";
        } else if(currentToken.matches(stringRegexString)){
            currentTokenType = "stringConst";
        } else if(currentToken.equals("&gt;") || currentToken.equals("&lt;") || currentToken.equals("&amp;")){
            currentTokenType = "symbol";
        }
    }

    /**
     * Gets the next token from the input and
     makes it the current token. This method
     should only be called if hasMoreTokens()
     is true. Initially there is no current token.
     */
    public void advance() {
        if(hasMoreTokens()){
            counter++;
            currentToken = tokensList.get(counter);
            updateTokenType();
        } else {
            System.err.println("Cannot advance - last token");
        }

    }

    /**
     * Gets the previous token from the input and
     makes it the current token. This method
     should only be called if we are not in
     first token. else: do nothing.
     */
    public void reverse() {
        if(counter > 0){
            counter--;
            currentToken = tokensList.get(counter);
            updateTokenType();
        } else {
            System.err.println("Cannot reverse - first token");
        }
    }

    /**
     * Returns the type of the current token.
     */
    public String tokenType()
    {

        return currentTokenType;
    }

    /**
     * Returns the keyword which is the current
     token. Should be called only when
     tokenType() is KEYWORD.
     */
    public String keyWord()
    {
        if(currentTokenType.equals("keyword")){
            return currentToken;
        } else {
            return "";
        }
    }

    /**
     * Returns the string which is the current
     token. Should be called only when
     tokenType() is SYMBOL.
     */
    public String symbol()
    {
        if(currentTokenType.equals("symbol")){
            return currentToken;
        } else {
            return "";
        }
    }

    /**
     * Returns the identifier which is the current
     token. Should be called only when
     tokenType() is IDENTIFIER.
     */
    public String identifier()
    {
        if(currentTokenType.equals("identifier")){
            return currentToken;
        } else {
            return "";
        }
    }

    /**
     * Returns the integer value of the current
     token. Should be called only when
     tokenType() is INT_CONST.
     */
    public String intVal()
    {
        if(currentTokenType.equals("intConst")){
            return currentToken;
        } else {
            return "";
        }
    }

    /**
     * Returns the string value of the current
     token, without the double quotes. Should
     be called only when tokenType() is
     STRING_CONST.
     */
    public String stringVal()
    {
        if(currentTokenType.equals("stringConst")){
            return currentToken;
        } else {
            return "";
        }
    }

    /*
Removes comments and spaces for the actual parsing
*/
    private static ArrayList<String> getParsableLines(ArrayList<String> rawLines){
        ArrayList<String> codeLines = new ArrayList<>();
        for(String line : rawLines){

            Matcher matcherEndOfCommentLine = endOfCommentBlockBeginningOfLine.matcher(line);

            if(matcherEndOfCommentLine.lookingAt()){
                line = line.substring(line.indexOf("*/") + 2, line.length());
            }

            Matcher matcherEmptyLine = emptyLine.matcher(line);
            Matcher matcherStartSimpleCommentLine = startCommentLine.matcher(line);
            Matcher matcherMiddleCommentBlockLine = restOfCommentBlockBeginningOfLine.matcher(line);
            Integer countLoop = 0;

            if(!matcherEmptyLine.matches() && !matcherMiddleCommentBlockLine.lookingAt()
                    && !matcherStartSimpleCommentLine.lookingAt()){
                while ((line.contains("//") || line.contains("/*")) && countLoop < line.length()) // check mid-line comment
                {
                    line = handleComment(line);
                    countLoop++;
                }
                line = line.trim();
                codeLines.add(line);

            }
        }
        return codeLines;
    }

    private static String handleComment(String line){

        Boolean weAreInString = false;
        Boolean weAreInComment = false;
        Integer strCounter = 0;
        Integer indexCounter = 0;
        for (int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            if(i != 0){
                char d = line.charAt(i-1);
                if((Character.toString(c).equals("/") ||
                        Character.toString(c).equals("*")) && !weAreInString && Character.toString(d).equals("/")){
                    weAreInComment = true;
                    indexCounter--;
                }
            } else {
                if(Character.toString(c).equals("/")){
                    weAreInComment = true;
                }
            }
            if(Character.toString(c).equals("\"") && !weAreInComment){
                strCounter++;
                weAreInString = (strCounter < 2);
            }
            if(!weAreInComment){
                indexCounter++;
            } else {
                break;
            }
        }
        String templine1 = line.substring(0 ,indexCounter);
        String templine2 = line.substring(indexCounter, line.length());

        if(templine2.contains("*/")){
            Integer tempint1 = templine2.indexOf("*/");
            templine2 = templine2.substring(tempint1 + 2, templine2.length());
            line = templine1 + templine2;
        } else {
            line = templine1;
        }
        return line;
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
}

