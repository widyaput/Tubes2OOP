import java.util.ArrayList;

enum Element{
    NONE,
    FIRE,
    WATER,
    ELECTRIC,
    GROUND,
    ICE
}

public class Skill{
    private String name;
    private int basePower;
    private int masteryLevel;
    private final int maxLevel = 3;
    private ArrayList<Element> elements;

    /**
     * Konstruktor untuk skill
     */
    public Skill(){
        this.name = "";
        this.basePower = 0;
        this.masteryLevel = 0;
        elements = new ArrayList<Element>();
    }
    /**
     * Konstruktor untuk skill (user-defined)
     * @param _name nama skill
     * @param _basePower basis power
     * @param _mL mastery level
     * @param _elements elemen yang kompatibel
     */
    public Skill(String _name, int _basePower, int _mL, ArrayList<Element> _elements){
        this.name = _name;
        this.basePower = _basePower;
        this.masteryLevel = _mL;
        this.elements = _elements;
    }

    /**
     * Getter untuk nama skill
     * @return nama skill
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter untuk basepower skill
     * @return basis power skill
     */
    public int getBasePower(){
        return this.basePower;
    }
    /**
     * Getter untuk mastery level
     * @return mastery level skill range 1-3
     */
    public int getMasteryLevel(){
        return this.masteryLevel;
    }

    /**
     * Getter elemen kompatibel dengan skill
     * @return
     */
    public ArrayList<Element> getElements(){
        return this.elements;
    }

    /**
     * Setter mastery level skill
     * @param _masterLevel
     */
    public void setMasteryLevel(int _masterLevel){
        this.masteryLevel = _masterLevel;
    }

    /**
     * Fungsi mengecek apakah skill kompatibel dengan element
     * @param element elemen yang ingin dicek
     * @return
     */
    public boolean hasElement(Element element){
        for (Element e: elements){
            if (e.equals(element)){ return true; }
        }
        return false;
    }

    /**
     * Prosedur menambahkan mastery level skill dengan pengecekan supaya <= 3
     */
    public void incrMasteryLevel(){
        if (this.masteryLevel < maxLevel){
            masteryLevel++;
        }
    }

    @Override
    /**
     * Prosedur override toString untuk keperluan
     */
    public String toString(){
        String elm = "";

        for (Element element:elements){
            elm+=element.toString();
            elm+=" ";
        }
        return String.format("[ %s | %s | %d | %d]",
         this.name, elm, this.basePower, this.masteryLevel);
    }

    public int compareTo(Skill other){
        if (this.getBasePower() > other.getBasePower()){
            return -1;
        }else return 1;
    }

}