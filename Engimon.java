
import java.util.*;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public class Engimon {
    private Species species;
    private String name;
    private ArrayList<Skill> skills;
    private String p1Name;
    private String p1Species;
    private String p2Name;
    private String p2Species;
    private final int maxSkill = 4;
    private int level;
    private int EXP;
    private int cumulativeEXP;
    private final int maxEXP = 10000;
    private boolean status;
    private boolean wild;
    private int lives;
    public static double table[][] = {
        {1,0,1,0.5,2},
        {2,1,0,1,1},
        {1,2,1,0,1.5},
        {1.5,1,2,1,0},
        {0,1,0.5,2,1}
    };

    /**
     * Ctor untuk inisiasi Engimon melalui breeding 
     * @param _species
     * @param _name
     * @param parentName
     * @param parentSpecies
     * @param parentName2
     * @param parentSpecies2
     * @param _skills
     * @param _level
     * @param _EXP
     * @param _cumulativeEXP
     */
    public Engimon(Species _species, String _name, String parentName, String parentSpecies, String parentName2, String parentSpecies2, ArrayList<Skill> _skills, int _level, int _EXP, int _cumulativeEXP){
        this.species = _species;
        this.name = _name;
        this.p1Name = parentName;
        this.p1Species = parentSpecies;
        this.p2Name = parentName2;
        this.p2Species = parentSpecies2;
        this.level = _level;
        this.EXP = _EXP;
        this.cumulativeEXP = _cumulativeEXP;
        this.lives = 3;
        this.status = true;
        this.wild = false;
        skills = new ArrayList<Skill>();
        this.skills = _skills;
    }
    /**
     * Ctor untuk inisiasi engimon melalui spawn Engimon
     * @param _species
     * @param _name
     * @param _level
     * @param _EXP
     * @param _cumulativeEXP
     */
    public Engimon(Species _species, int _level, int _EXP, int _cumulativeEXP){
        this.species = _species;
        this.name = "Wild " + species.getName();
        this.level = _level;
        this.EXP = _EXP;
        this.cumulativeEXP = _cumulativeEXP;
        this.lives = 1;
        this.p1Name = "";
        this.p1Species = "";
        this.p2Name = "";
        this.p2Species = "";
        this.status = true;
        this.wild = true;
        skills = new ArrayList<Skill>();
        skills.add(_species.getBaseSkill());
    }

    /**
     * Ctor skill engimon hanya dari skill bawaan engimon (starter pack)
     * @param _species
     * @param _name
     * @param parentName
     * @param parentSpecies
     * @param parentName2
     * @param parentSpecies2
     * @param _level
     * @param _EXP
     * @param _cumulativeEXP
     */
    public Engimon(Species _species, String _name, String parentName, String parentSpecies, String parentName2, String parentSpecies2, int _level, int _EXP, int _cumulativeEXP){
        this.species = _species;
        this.name = _name;
        this.p1Name = parentName;
        this.p1Species = parentSpecies;
        this.p2Name = parentName2;
        this.p2Species = parentSpecies2;
        this.level = _level;
        this.EXP = _EXP;
        this.cumulativeEXP = _cumulativeEXP;
        this.lives = 3;
        this.status = true;
        this.wild = false;
        skills = new ArrayList<Skill>();
        skills.add(_species.getBaseSkill());
    }
    /**
     * Getter nama engimon
     * @return nama engimon
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter level engimon
     * @return level engimon
     */

    public int getLevel(){
        return this.level;
    }
    public int getLives(){
        return this.lives;
    }

    /**
     * Getter exp engimon
     * @return exp engimon
     */
    public int getEXP(){
        return this.EXP;
    }

    public int getCumulativeEXP(){
        return this.cumulativeEXP;
    }

    public Species getSpecies(){
        return this.species;
    }

    /**
     * Method mengembalikan advantage element Engimon dengan element other
     * @return element advantage
     */
    public double getAdvElement(Element other){
        return Engimon.table[Engimon.getElementIndex(species.getElement(0))][Engimon.getElementIndex(other)];
    }
    public boolean isWild(){
        return this.wild == true;
    }
    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public ArrayList<Element> getElement(){
        Element elm1 = this.species.getElement(0);
        Element elm2 = this.species.getElement(1);
        ArrayList<Element> elms;

        if (elm2.equals(Element.NONE)) {
            elms = new ArrayList<Element>(Arrays.asList(elm1));
        }
        else{
            elms = new ArrayList<Element>(Arrays.asList(elm1, elm2));
        }

        return elms;
    }

    public boolean getStatus(){
        return this.status;
    }

    public String getSpeciesName(){
        return species.getName();
    }

    public double[][] getAdvElementTable(){
        return Engimon.table;
    }
    
    /**
     * Setter
     */

    public void setLives(int n){
        this.lives = n;
    }

    public void setParent(String speciesA, String parentNameA, String speciesB, String parentNameB){
        p1Species = speciesA;
        p1Name = parentNameA;
        p2Species = speciesB;
        p2Name = parentNameB;
    }

    public void setStatus(boolean _status){
        this.status = _status;
    }

    public void setWild(boolean _wild){
        this.wild = _wild;
    }

    public void rename(String _name){
        this.name = _name;
    }
    private void upLevel(int incr){
        this.level++;
    }
    public void downLevel(int decr){
        this.level-=decr;
        this.cumulativeEXP-=(decr*100);
    }

    /**
     * Method menambahkan EXP disertai pengecekan apakah sudah mencapai maksimal EXP disertai kenaikan level
     * @param exp
     */
    public void addEXP(int exp){
        this.EXP += exp;
        this.cumulativeEXP += exp;

        if (cumulativeEXP > maxEXP){ status = false; }
        else{
            if (EXP/100 >= 1){
                upLevel(EXP/100);
                EXP = EXP%100;
            }
        }
    }

    /**
     * Method menambahkan skill pada engimon
     * @return 1 apabila berhasil
     * 2 apabila sudah punya skill
     * 3 apabila elemen tidak kompatibel
     * 0 apabila slot penuh
     */
    public int addSkill(Skill skill){
        if (skills.size() >= maxSkill){
            return 0;
        }
        else{
            for(Skill s:skills){
                if (s.equals(skill)){
                    return 2;
                }
            }

            if (species.isSingleElement()){
                if (skill.hasElement(species.getElement(0))) {
                    skills.add(skill);
                }
                else{
                    return 3;
                }
            }

            else{
                if (skill.hasElement(species.getElement(0)) ||
                skill.hasElement(species.getElement(1))){
                    skills.add(skill);
                }
                else{
                    return 3;
                }
            }

        }
        return 1;
    }

    public void dropSkill(Skill skill){
        if(skills.contains(skill)){
            skills.remove(skill);
        }
        else{
            // throw anda tidak memiliki skill
        }
    }

    public boolean checkNumOfElement(int countElemen){
        if (species.getElement(1).equals(Element.NONE) && countElemen == 1) return true;
        return false;
    }

    public static double countPower(Engimon e, double adv){
        double total = e.getLevel() * adv;
        ArrayList<Skill> skills = e.getSkills();

        for (Skill skill : skills) {
            total = total + (skill.getBasePower() * skill.getMasteryLevel());
        }

        return total;
    }

    public static int getElementIndex(Element elm){
        if (elm.equals(Element.FIRE)){
            return 0;
        }

        if (elm.equals(Element.WATER)){
            return 1;
        }

        if (elm.equals(Element.ELECTRIC)){
            return 2;
        }

        if (elm.equals(Element.GROUND)){
            return 3;
        }

        if (elm.equals(Element.ICE)){
            return 4;
        }
        return -1;
    }
    
    public static double advantage(Engimon e1, Engimon e2, int n){
        double x = 0;
        double y = 0;

        ArrayList<Element> elm1 = e1.getElement();
        ArrayList<Element> elm2 = e2.getElement();

        for (Element elment1: elm1){
            for (Element elemnt2: elm2){
                int i = Engimon.getElementIndex(elment1);
                int j = Engimon.getElementIndex(elemnt2);

                if (Engimon.table[i][j] > x){
                    x = Engimon.table[i][j];
                }

                if (Engimon.table[j][i] > y){
                    y = Engimon.table[j][i];
                }
            }
        }

        if (n == 1){
            return x;
        }

        if (n == 2){
            return y;
        }

        return -1;
    }

    public int compareTo(Engimon other){
        if (getElementIndex(this.getSpecies().getElement(0)) > getElementIndex(other.getSpecies().getElement(0)) ){
            return 1;
        }
        if (getElementIndex(this.getSpecies().getElement(0)) < getElementIndex(other.getSpecies().getElement(0))){
            return -1;
        }
        else{
            if (this.getLevel() > other.getLevel()) return -1;
            else return 1;
        }
    }

    @Override
    public String toString(){
        String daftarSkill = "";
        for (Skill skill : skills) {
            daftarSkill+=skill.toString();
            daftarSkill+="\n";
        }

        return String.format("Species: \n%s\nNama Engimon: %s\nNama parent: %s %s | %s %s\nDaftar skill: \n%s\nLevel: %d\nEXP: %d\nTotalEXP: %d\nStatus: %s\nWild: %s\nLives: %d\n", species, name, p1Name, p1Species, p2Name, p2Species, daftarSkill, level, EXP, cumulativeEXP, status, wild, lives);
    }

    public static void main(String[] args) {
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        KatalogSkill katalogSkill = new KatalogSkill();

        // Ctor engimon dengan skill dari skill bawaan
        Engimon e1 = new Engimon(katalogSpecies.getSpeciesFromIndex(0), "Engi1", "Bapak", "Snorlax", "Ibu", "Dewgong", 20, 0,0);
        Engimon e2 = new Engimon(katalogSpecies.getSpeciesFromIndex(1), 5, 0, 0);

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e2.addSkill(katalogSkill.getSkillFromIndex(7)));


        e2.addEXP(250);
        System.out.println(e2);
    }
}

// javac Engimon.java Species.java DoubleElemental.java SingleElemental.java Skill.java KatalogSkill.java