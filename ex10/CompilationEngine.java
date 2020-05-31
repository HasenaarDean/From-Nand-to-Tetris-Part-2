import java.io.File;

public class CompilationEngine {

    private Tokenizer tknzr;
    private XmlWriter xmlWriter;

    private final int INDENTATION_START = 0, INDENTATION_INC = 2;
    private int currIndentationAmount;

    public CompilationEngine(File jackFile) {
        tknzr = new Tokenizer(jackFile);

        xmlWriter = new XmlWriter(jackFile.getPath().substring(0,jackFile.getPath().lastIndexOf('.')));
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

    }

    private void compileClassVarDec() // ('static'|'field') type varName (','varName)*';'
    {
        tknzr.advance();
        if (!(tknzr.keyWord().equals("static") || tknzr.keyWord().equals("field"))) // no classVarDec
        {
            tknzr.reverse();
            return;
        }

        xmlWriter.write(currIndentationAmount, "<classVarDec>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>"); // static or field

        compileType();

        tknzr.advance(); // first var name
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        tknzr.advance();

        while (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(",")) // continue adding varName after ","
        {
            xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");

            // other vars names
            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

            tknzr.advance();
        }

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</classVarDec>");

        compileClassVarDec();
    }

    private void compileSubroutineDec() // (constructor|method|function) (void|type) name (parameter list) body
    {
        tknzr.advance();
        if (!(tknzr.keyWord().equals("method") || tknzr.keyWord().equals("function") ||
                tknzr.keyWord().equals("constructor"))) // no subroutineDec
        {
            tknzr.reverse();
            return;
        }


        xmlWriter.write(currIndentationAmount, "<subroutineDec>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>"); // constructor or method or function

        compileType();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>"); // subroutine name

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

        compileParameterList();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");


        compileSubroutineBody();

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</subroutineDec>");

        compileSubroutineDec();
    }

    private void compileSubroutineBody() // { varDec*, statements }
    {
        xmlWriter.write(currIndentationAmount, "<subroutineBody>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        compileVarDec();

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

    private void compileParameterList() // (parameter (, parameter)*)?
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

    private void compileParameter() // type varname
    {
        compileType();

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>"); // varname

    }

    private void compileType()
    {
        tknzr.advance();
        if(tknzr.tokenType().equals("keyword")) // int char boolean
        {
            xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>");
        }
        else
        {
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
        }
    }

    private void compileVarDec() // type varname (, varname)* ;
    {
        tknzr.advance();
        if(!(tknzr.tokenType().equals("keyword") && tknzr.keyWord().equals("var"))) // no var dec
        {
            tknzr.reverse();
            return;
        }

        xmlWriter.write(currIndentationAmount, "<varDec>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> var </keyword>");

        compileType();

        tknzr.advance();  // first var name
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        tknzr.advance();
        while (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(",")) // continue adding varName after ","
        {
            xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");
            tknzr.advance();
            xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");
            tknzr.advance();
        }

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</varDec>");

        compileVarDec();
    }

    private void compileStatements()
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

    private void compileDo()
    {
        xmlWriter.write(currIndentationAmount, "<doStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> do </keyword>");

        compileSubroutineCall();

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</doStatement>");


    }

    private void compileSubroutineCall()
    {
        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        tknzr.advance();
        if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("(")) // do meow(...)
        {
            xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");
            compileExpressionList();
            xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

        } else if (tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(".")) // do Cat.meow(...)
        {
            xmlWriter.write(currIndentationAmount, "<symbol> . </symbol>");
            compileSubroutineCall();
        }

    }

    private void compileLet()
    {
        xmlWriter.write(currIndentationAmount, "<letStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> let </keyword>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() + " </identifier>");

        tknzr.advance();
        if(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("[")) // varname[expression]
        {
            xmlWriter.write(currIndentationAmount, "<symbol> [ </symbol>");

            compileExpression();

            xmlWriter.write(currIndentationAmount, "<symbol> ] </symbol>");
            tknzr.advance();
        }

        xmlWriter.write(currIndentationAmount, "<symbol> = </symbol>");

        compileExpression();

        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</letStatement>");




    }

    private void compileWhile()
    {
        xmlWriter.write(currIndentationAmount, "<whileStatement>");
        incIndentation();

        /////////////////////////////////////////////////////////////////////

        xmlWriter.write(currIndentationAmount, "<keyword> while </keyword>");

        tknzr.advance();

        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

        compileExpression();
        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

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

        /////////////////////////////////////////////////////////////////////

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</whileStatement>");
    }

    private void compileReturn()
    {
        xmlWriter.write(currIndentationAmount, "<returnStatement>");
        incIndentation();

        xmlWriter.write(currIndentationAmount, "<keyword> return </keyword>");
        tknzr.advance();

        if(!(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(";")))
        {
            tknzr.reverse();
            compileExpression();
        }
        xmlWriter.write(currIndentationAmount, "<symbol> ; </symbol>");

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</returnStatement>");
    }

    private void compileIf()
    {
        xmlWriter.write(currIndentationAmount, "<ifStatement>");
        incIndentation();

        /////////////////////////////////////////////////////////////////////

        xmlWriter.write(currIndentationAmount, "<keyword> if </keyword>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");

        compileExpression();

        xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");

        /////////////////////////////////////////////////////////////////

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> { </symbol>");

        xmlWriter.write(currIndentationAmount, "<statements>");
        incIndentation();
        compileStatements();
        decIndentation();
        xmlWriter.write(currIndentationAmount, "</statements>");

        tknzr.advance();
        xmlWriter.write(currIndentationAmount, "<symbol> } </symbol>");

        /////////////////////////////////////////////////////////////////////

        tknzr.advance();
        if((tknzr.tokenType().equals("keyword") && tknzr.keyWord().equals("else")))
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

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</ifStatement>");
    }

    private void compileExpression()
    {
        String op = "[\\-\\~\\+\\*\\/\\&\\|\\<\\>\\=]";
        xmlWriter.write(currIndentationAmount, "<expression>");
        incIndentation();

        compileTerm();

        tknzr.advance();

        while(((tknzr.symbol().matches(op) || tknzr.symbol().equals("&gt;")
                || tknzr.symbol().equals("&lt;")
                || tknzr.symbol().equals("&amp;")))) // temp op term op term op term...
        {
            xmlWriter.write(currIndentationAmount, "<symbol> " + tknzr.symbol() + " </symbol>"); //todo change to symbol
            compileTerm();

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
            case "intConst":
                xmlWriter.write(currIndentationAmount, "<integerConstant> " + tknzr.intVal() +
                                                              " </integerConstant>");
                break;
            case "stringConst":
                String toWrite = removeQuotations(tknzr.stringVal());
                xmlWriter.write(currIndentationAmount, "<stringConstant> " + toWrite +
                                                              " </stringConstant>");
                break;
            case "keyword": // keywordConstant: {true false null this}
                xmlWriter.write(currIndentationAmount, "<keyword> " + tknzr.keyWord() + " </keyword>");
                break;
            case "symbol":
                if(tknzr.symbol().equals("(")) // '('expression')'
                {
                    xmlWriter.write(currIndentationAmount, "<symbol> ( </symbol>");
                    compileExpression();
                    xmlWriter.write(currIndentationAmount, "<symbol> ) </symbol>");
                }
                else // unaryOp term
                {
                    xmlWriter.write(currIndentationAmount, "<symbol> " + tknzr.symbol() + " </symbol>");

                    compileTerm();
                }
                break;
            default: // varname | varname[expression] | subroutineCall
            {

                tknzr.advance();
                boolean isPublicSubroutineCall = tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("."); // cat.meow()
                boolean isSubroutineCall = tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("("); // meow()
                tknzr.reverse();

                if(isPublicSubroutineCall)
                {
                    xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() +
                            " </identifier>"); // varname
                    tknzr.advance();
                    xmlWriter.write(currIndentationAmount, "<symbol> . </symbol>");
                    tknzr.advance();
                    isSubroutineCall = true;
                }

                if(!isSubroutineCall)
                {
                    xmlWriter.write(currIndentationAmount, "<identifier> " + tknzr.identifier() +
                                                                  " </identifier>"); // varname
                    tknzr.advance();
                    if(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals("[")) // [expression]
                    {
                        xmlWriter.write(currIndentationAmount, "<symbol> [ </symbol>");
                        compileExpression();

                        xmlWriter.write(currIndentationAmount, "<symbol> ] </symbol>");
                    }
                    else
                    {
                        tknzr.reverse();
                    }
                }
                else // subroutine call
                {
                    tknzr.reverse();
                    compileSubroutineCall();
                }
                break;
            }
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</term>");
    }

    private void compileExpressionList()
    {
        xmlWriter.write(currIndentationAmount, "<expressionList>");
        incIndentation();
        tknzr.advance();
        if(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")")) // empty expression list
        {
            decIndentation();
            xmlWriter.write(currIndentationAmount, "</expressionList>");
            return;
        }

        while(!(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(")")))
        {
            tknzr.reverse();
            compileExpression();
            if(tknzr.tokenType().equals("symbol") && tknzr.symbol().equals(","))
            {
                xmlWriter.write(currIndentationAmount, "<symbol> , </symbol>");
                tknzr.advance();
            }
        }

        decIndentation();
        xmlWriter.write(currIndentationAmount, "</expressionList>");
    }

    private String removeQuotations(String val)
    {
        return val.substring(val.indexOf('"')+1, val.lastIndexOf('"'));
    }
}

