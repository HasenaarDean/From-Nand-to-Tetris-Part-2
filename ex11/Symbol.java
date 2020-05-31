public class Symbol {

    private final String name,
                         type,
                         kind; // arg, local, static, this
    private int kindIndex; // index in the type subTable

    public Symbol(String name, String type, String kind)
    {
        this.name = name;
        this.type = type;
        this.kind = kind;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public void setKindIndex(int newIndex)
    {
        kindIndex = newIndex;

    }

    public int getKindIndex()
    {
        return kindIndex;
    }
}
