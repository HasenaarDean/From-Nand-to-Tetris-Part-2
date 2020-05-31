import java.io.File;

public class CompilationEngine {

    private Tokenizer tknzr;
    private XmlWriter xmlWriter;
    private VmWriter vmWriter;

    private final int INDENTATION_START = 0, INDENTATION_INC = 2;
    private int currIndentationAmount;

    private SymbolTable symbolTable;

    private String className, currentFunction;

    public CompilationEngine(File jackFile) {
        tknzr = new Tokenizer(jackFile);
        className = jackFile.getName().substring(0,jackFile.getName().lastIndexOf('.'));
        xmlWriter = new XmlWriter(jackFile.getPath().substring(0,jackFile.getPath().lastIndexOf('.')));
        vmWriter =  new VmWriter(jackFile.getPath().substring(0,jackFile.getPath().lastIndexOf('.')));
        currentFunction = "";

        currIndentationAmount = INDENTATION_START;
    }

    private void incIndentation() {
        currIndentationAmount += INDENTATION_INC;
    }

    private void decIndentation() {
        currIndentationAmount -= INDENTATION_INC;
    }


    public void compileClass()
     {
        // 'class' className '{' classVarDec& subroutineDec* '}'
        xmlWriter.write(currIndentationAmount, "<class>");
        incIndentation();

        symbolTable = new SymbolTable(className);

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<keyword> class </keyword>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        compileClassVarDec();
        compileSubroutineDec();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</class>");

        xmlWriter.close();
        vmWriter.close();

    }

    private void compileClassVarDec() // ('static'|'field') type varName (','varName)*';' //todo 11 done
    {
        tknzr.advance();
        if (!(tknzr.keyWord().equals("static") || tknzr.keyWord().equals("field"))) // no classVarDec
        {
            tknzr.reverse();
            return;
        }

        xmlWriter.write(currIndentationAmount, "<classVarDec>");
        incIndentation();

        String name, type, kind;

        xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>"); // static or field
        kind = tknzr.keyWord(); // static or field

        type = compileType();

        tknzr.advance(); // first var name
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
        name = tknzr.identifier(); // first var name

        symbolTable.define(name, type, kind);

        tknzr.advance();

        while (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(",")) // continue adding varName after ","
        {
            xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");

            // other vars names
            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
            name = tknzr.identifier();  // other vars names

            symbolTable.define(name, type, kind);

            tknzr.advance();
        }

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</classVarDec>");

        compileClassVarDec();
    }

    private void compileSubroutineDec() // (constructor|method|function) (void|type) name (parameter list) body //todo 11 done
    {
        tknzr.advance();
        if (!(tknzr.keyWord().equals("method") || tknzr.keyWord().equals("function") ||
                tknzr.keyWord().equals("constructor"))) // no subroutineDec
        {
            tknzr.reverse();
            return;
        }

        String funcType = tknzr.keyWord(); // method | constructor | function
        symbolTable.startSubroutine(funcType.equals("method"));

        xmlWriter.write(currIndentationAmount, "<subroutineDec>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>"); // constructor or method or function

        compileType();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>"); // subroutine name
        currentFunction = tknzr.identifier();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

        compileParameterList();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");


        compileSubroutineBody(funcType);

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</subroutineDec>");

        compileSubroutineDec();
    }

    private void compileSubroutineBody(String funcType) // { varDec*, statements } //todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<subroutineBody>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        compileVarDec();

        vmWriter.writeFunction(className + "."+currentFunction ,symbolTable.varCount("local"));

        if (funcType.equals("method"))
        {
            vmWriter.writePush("argument", 0);
            vmWriter.writePop("pointer",0);
        }
        else if(funcType.equals("constructor"))
        {
            vmWriter.writePush("constant",symbolTable.varCount("field"));
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop("pointer",0);
        }

        xmlWriter.write(currIndentationAmount, "<statements>");
        incIndentation();
        compileStatements();
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</statements>");


        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</subroutineBody>");
    }

    private void compileParameterList() // (parameter (, parameter)*)? //todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<parameterList>");
        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")")) // empty parameter list
        {
            xmlWriter.write(currIndentationAmount, "</parameterList>");
            return;
        }

        incIndentation();

        tknzr.reverse();

        while (!(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")"))) {
            compileParameter();
            tknzr.advance();
            if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(",")) {
                xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");
            }
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</parameterList>");

    }

    private void compileParameter() // type varname //todo 11 done
    {
        String type, name;

        type = compileType();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
        name = tknzr.identifier();

        symbolTable.define(name, type, "argument");

    }

    private String compileType() //todo 11 done
    {
        tknzr.advance();
        if (tknzr.tokenType().equals("keyword")) // int char boolean
        {
            xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>");
            return tknzr.keyWord();
        }
        else
        {
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
            return tknzr.identifier();
        }
    }

    private void compileVarDec() // type varname (, varname)* ; //todo 11 done
    {
        tknzr.advance();
        if (!(tknzr.tokenType().equals("keyword") && tknzr.keyWord().equals("var"))) // no var dec
        {
            tknzr.reverse();
            return;
        }

        xmlWriter.write(currIndentationAmount, "<varDec>");
        incIndentation();

        String name, type;

        xmlWriter.write(currIndentationAmount, "<keyword> var </keyword>");

        type = compileType();

        tknzr.advance();  // first var name
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
        name = tknzr.identifier();
        symbolTable.define(name, type, "local");

        tknzr.advance();
        while (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(",")) // continue adding varName after ","
        {

            xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");
            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
            name = tknzr.identifier();
            symbolTable.define(name, type, "local");
            tknzr.advance();
        }

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</varDec>");

        compileVarDec();
    }

    private void compileStatements() //todo 11 done
    {
        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("}")) // end of subroutine
        {
            tknzr.reverse();
            return;
        }

        switch (tknzr.keyWord()) {
            case "let":
                compileLet();
                break;
            case "if":
                compileIf();
                break;
            case "while":
                compileWhile();
                break;
            case "do":
                compileDo();
                break;
            case "return":
                compileReturn();
                break;
        }

        compileStatements(); // next statement
    }

    private void compileDo() //todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<doStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> do </keyword>");

        compileSubroutineCall();

        vmWriter.writePop("temp", 0);

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</doStatement>");


    }

    private void compileSubroutineCall() // todo 11 done
    {
        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        String subName = tknzr.identifier();
        int nArgs = 0;

        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("(")) // do meow(...)
        {

            vmWriter.writePush("pointer", 0);

            xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");
            nArgs = compileExpressionList() + 1;
            xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

            vmWriter.writeCall(className + "." +subName, nArgs);

        }
        else if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(".")) // do Cat.meow(...) //
        {
            String objectName = subName;
            xmlWriter.write(currIndentationAmount, "<symbol> . </symbol>");

            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
            subName = tknzr.identifier();

            String type = symbolTable.typeOf(objectName);

            if (type.equals(""))
            {
                subName = objectName + "." + subName;
            }
            else
            {
                nArgs = 1;
                vmWriter.writePush(symbolTable.kindOf(objectName), symbolTable.indexOf(objectName));
                subName = type + "." + subName;
            }

            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

            nArgs += compileExpressionList();

            xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

            vmWriter.writeCall(subName, nArgs);
        }


    }

    private void compileLet() // todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<letStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> let </keyword>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
        String varName = tknzr.identifier();

        boolean onHeap = false; //dealing with arrays

        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("[")) // varname[expression]
        {
            onHeap = true;
            
            xmlWriter.write(currIndentationAmount, "<symbol> [ </symbol>");
            
            vmWriter.writePush(symbolTable.kindOf(varName), symbolTable.indexOf(varName));

            compileExpression();

            xmlWriter.write(currIndentationAmount, "<symbol> ] </symbol>");
            tknzr.advance();

            vmWriter.writeArithmetic("+");
        }

        xmlWriter.write(currIndentationAmount, "<symbol> = </symbol>");

        compileExpression();

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</letStatement>");

        if (onHeap)
        {
            vmWriter.writePop("temp",0);
            vmWriter.writePop("pointer",1);
            vmWriter.writePush("temp",0);
            vmWriter.writePop("that",0);
        }
        else
        {
            vmWriter.writePop(symbolTable.kindOf(varName), symbolTable.indexOf(varName));
        }
    }

    private void compileWhile() // todo 11 done
    {

        String whileLabel = vmWriter.getNewLabel();
        String endLabel = vmWriter.getNewLabel();

        xmlWriter.write(currIndentationAmount, "<whileStatement>");
        incIndentation();

        vmWriter.writeLabel(whileLabel);

        xmlWriter.write(currIndentationAmount, "<keyword> while </keyword>");

        tknzr.advance();

        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");
        compileExpression();
        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

        // if the condition doesn't hold, end the loop
        vmWriter.writeArithmetic("!");
        vmWriter.writeIf(endLabel);

        /////////////////////////////////////////////////////////////////////

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        xmlWriter.write(currIndentationAmount, "<statements>");
        incIndentation();
        compileStatements();
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</statements>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");



        vmWriter.writeGoTo(whileLabel);
        
        vmWriter.writeLabel(endLabel);

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</whileStatement>");
    }

    private void compileReturn() // todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<returnStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> return </keyword>");
        tknzr.advance();


        if (!(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(";")))
        {
            tknzr.reverse();
            compileExpression();
        }
        else // void function
        {
            vmWriter.writePush("constant", 0);
        }
        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</returnStatement>");

        vmWriter.writeReturn();
    }

    private void compileIf() // todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<ifStatement>");
        incIndentation();

        /////////////////////////////////////////////////////////////////////

        xmlWriter.write(currIndentationAmount, "<keyword> if </keyword>");

        String elseLabel = vmWriter.getNewLabel();
        String endLabel = vmWriter.getNewLabel();


        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

        compileExpression();

        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

        // if the condition doesn't hold, jump to the else_label
        vmWriter.writeArithmetic("!");
        vmWriter.writeIf(elseLabel);

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        xmlWriter.write(currIndentationAmount, "<statements>");
        incIndentation();
        compileStatements();
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</statements>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");

        vmWriter.writeGoTo(endLabel); // if the  condition held, skip the else part

        vmWriter.writeLabel(elseLabel);
        tknzr.advance();
        if ((tknzr.tokenType().equals("keyword") && tknzr.keyWord().equals("else")))
        {
            xmlWriter.write(currIndentationAmount, "<keyword> else </keyword>");

            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

            xmlWriter.write(currIndentationAmount, "<statements>");
            incIndentation();
            compileStatements();
            decIndentation();
            xmlWriter.write(currIndentationAmount, "</statements>");

            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");
        }
        else
        {
            tknzr.reverse();
        }

        vmWriter.writeLabel(endLabel);

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</ifStatement>");
    }

    private void compileExpression() //todo 11 done
    {
        String op = "[\\-\\~\\+\\*\\/\\&\\|\\<\\>\\=]";
        xmlWriter.write(currIndentationAmount, "<expression>");
        incIndentation();

        compileTerm();

        tknzr.advance();

        while (((tknzr.symbol().matches(op) || tknzr.symbol().equals("&gt;")
                || tknzr.symbol().equals("&lt;")
                || tknzr.symbol().equals("&amp;")))) // temp op term op term op term...
        {
            xmlWriter.write(currIndentationAmount, "<symbol> " + tknzr.symbol() + " </symbol>");

            String operation = tknzr.symbol();

            compileTerm();

            vmWriter.writeArithmetic(operation);

            tknzr.advance();
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</expression>");


    }

    private void compileTerm()
    {
        xmlWriter.write(currIndentationAmount, "<term>");
        incIndentation();

        tknzr.advance();

        switch (tknzr.tokenType())
        {
            case "intConst": // todo done 11
                xmlWriter.write(currIndentationAmount, "<integerConstant> " + tknzr.intVal() +
                                                              " </integerConstant>");

                vmWriter.writePush("constant", Integer.parseInt(tknzr.intVal()));
                break;

            case "stringConst": // todo done 11
                String toWrite = removeQuotations(tknzr.stringVal());
                xmlWriter.write(currIndentationAmount, "<stringConstant> " + toWrite +
                                                              " </stringConstant>");

                vmWriter.writePush("constant", toWrite.length());
                vmWriter.writeCall("String.new",1);
                for(char c : toWrite.toCharArray())
                {
                    vmWriter.writePush("constant", (int)(c));
                    vmWriter.writeCall("String.appendChar",2);
                }

                break;

            case "keyword": // keywordConstant: {true false null this} // todo done 11
                switch (tknzr.keyWord())
                {
                    case "true":
                        vmWriter.writePush("constant",0);
                        vmWriter.writeArithmetic("!");
                        break;

                    case "false":
                        vmWriter.writePush("constant",0);
                        break;

                    case "null":
                        vmWriter.writePush("constant",0);
                        break;

                    case "this":
                        vmWriter.writePush("pointer", 0);
                        break;
                }

                xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>");
                break;

            case "symbol": // todo done 11
                if (tknzr.symbol().equals("(")) // '('expression')'
                {
                    xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");
                    compileExpression();
                    xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");
                }
                else // unaryOp term
                {
                    xmlWriter.write(currIndentationAmount, "<symbol> " + tknzr.symbol() + " </symbol>");

                    String unaryOp = tknzr.symbol();

                    compileTerm();

                    if(unaryOp.equals("-"))
                    {
                        vmWriter.writeArithmetic("~");
                    }
                    else
                    {
                        vmWriter.writeArithmetic("!");
                    }
                }
                break;
            default: // varname | varname[expression] | subroutineCall //todo done 11
            {
                String identifier = tknzr.identifier();

                tknzr.advance();
                if (tknzr.tokenType().equals("symbol") && (tknzr.symbol().equals("(") || tknzr.symbol().equals(".")))  //subroutineCall
                {
                    tknzr.reverse();
                    tknzr.reverse();
                    compileSubroutineCall();

                }
                else if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("[")) //varname[expression]
                {
                    xmlWriter.write(currIndentationAmount, "<identifier> " + identifier + " </identifier>");
                    vmWriter.writePush(symbolTable.kindOf(identifier), symbolTable.indexOf(identifier));

                    xmlWriter.write(currIndentationAmount, "<symbol> [ </symbol>");
                    compileExpression();
                    xmlWriter.write(currIndentationAmount, "<symbol> ] </symbol>");

                    vmWriter.writeArithmetic("+");
                    vmWriter.writePop("pointer", 1);
                    vmWriter.writePush("that", 0 );
                }
                else // varname
                {
                    vmWriter.writePush(symbolTable.kindOf(identifier), symbolTable.indexOf(identifier));
                    xmlWriter.write(currIndentationAmount, "<identifier> " + identifier + " </identifier>");

                    tknzr.reverse();
                }
            }
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</term>");
    }

    private int compileExpressionList() //todo 11 done
    {
        xmlWriter.write(currIndentationAmount, "<expressionList>");

        int nArgs = 0;

        incIndentation();
        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")")) // empty expression list
        {
            decIndentation();
            xmlWriter.write(currIndentationAmount, "</expressionList>");
            return nArgs;
        }

        while (!(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")")))
        {
            tknzr.reverse();
            compileExpression();
            nArgs++;
            if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(","))
            {
                xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");
                tknzr.advance();
            }
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</expressionList>");

        return nArgs;
    }

    private String removeQuotations(String val)
    {
        return val.substring(val.indexOf('"')+1, val.lastIndexOf('"'));
    }


}

