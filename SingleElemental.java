public class SingleElemental extends Species{
    private Element elm;

    public SingleElemental(){
        super();
        this.elm = Element.NONE;
    }

    public SingleElemental(String _name, Element elm, Skill _basesSkill){
        super(_name, _basesSkill);
        this.elm = elm;
    }

    public boolean hasElement(Element elm){
        return true;
    }

    public Element getElement(int idx){
        if (idx == 0){ return elm; }
        return Element.NONE;
    }

    public boolean isSingleElement(){
        return true;
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nType: Single Elemental\nElement : %s\nSkill bawaan: %s", this.name, this.elm, this.baseSkill);
    }
}