import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    private String className;
    private ArrayList<Symbol> classSymbols;
    private ArrayList<Symbol> subroutineSymbols;
    private HashMap<String, Integer> indeces;

    public SymbolTable(String className) {
        this.className = className;
        this.classSymbols = new ArrayList<>();
        this.subroutineSymbols = new ArrayList<>();

        indeces = new HashMap<>();
        indeces.put("field", 0);
        indeces.put("static", 0);
        indeces.put("local", 0);
        indeces.put("argument", 0);
    }

    public void define(String name, String type, String kind)
    {
        Symbol toAdd = new Symbol(name, type, kind);
        ArrayList<Symbol> symbols;

        if (kind.equals("static") || kind.equals("field")) {
            symbols = classSymbols;
        } else // ARG || LOCAL
        {
            symbols = subroutineSymbols;
        }

        toAdd.setKindIndex(indeces.get(kind));
        indeces.put(kind, indeces.get(kind) + 1);

        symbols.add(toAdd);
    }

    public void startSubroutine(boolean isMethod)
    {
        subroutineSymbols.clear();
        indeces.put("local", 0);
        indeces.put("argument", 0);
        if(isMethod)
        {
            this.define("this", className, "argument");
        }
    }

    public int varCount(String kind)
    {
        int varCounter = 0;
        ArrayList<Symbol> symbols;
        if (kind.equals("static") || kind.equals("field")) {
            symbols = classSymbols;
        } else // ARG || LOCAL
        {
            symbols = subroutineSymbols;
        }

        for (Symbol symbol : symbols)
        {
            varCounter += symbol.getKind().equals(kind) ? 1 : 0;
        }

        return varCounter;
    }

    public String kindOf(String name)
    {
        String kind = "";
        for(Symbol symbol : subroutineSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                kind = symbol.getKind();
            }
        }

        if(!kind.equals("")) return kind;

        for(Symbol symbol : classSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                kind = symbol.getKind();
                return kind;
            }
        }

        return kind;
    }

    public String typeOf(String name)
    {
        String type = "";
        for(Symbol symbol : subroutineSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                type = symbol.getType();
            }
        }

        if(!type.equals("")) return type;

        for(Symbol symbol : classSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                type = symbol.getType();
                return type;
            }
        }

        return type;
    }

    public int indexOf(String name)
    {

        for(Symbol symbol : subroutineSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                return symbol.getKindIndex();
            }
        }


        for(Symbol symbol : classSymbols)
        {
            if(name.equals(symbol.getName()))
            {
                return symbol.getKindIndex();
            }
        }

        return -1;
    }

}

