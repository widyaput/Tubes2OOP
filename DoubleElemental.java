import javax.print.DocFlavor.STRING;

public class DoubleElemental extends Species {
    private Element elm1;
    private Element elm2;

    public DoubleElemental(){
        super();
        this.elm1 = Element.NONE;
        this.elm2 = Element.NONE;
    }

    public DoubleElemental(String _name, Element elm1, Element elm2, Skill _basesSkill){
        super(_name, _basesSkill);
        this.elm1 = elm1;
        this.elm2 = elm2;
    }

    public Element getElement(int idx){
        if (idx == 0) { return elm1; }
        else { return elm2; }
    }

    public boolean hasElement(Element elm) { return elm1.equals(elm) || elm2.equals(elm); }

    public boolean isSingleElement(){
        return false;
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nType: Double Elemental\nElement 1: %s\nElement 2: %s\nSkill bawaan: %s", this.name, this.elm1, this.elm2, this.baseSkill);
    }
}