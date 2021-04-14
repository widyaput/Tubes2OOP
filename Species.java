import java.util.*;

public abstract class Species{
    protected String name;
    protected Skill baseSkill;

    /**
     * Default konstruktor
     */
    public Species(){
        this.name = "";
        this.baseSkill = new Skill();
    }

    /**
     * User defined konstruktor
     */
    public Species(String _name, Skill _basesSkill){
        this.name = _name;
        this.baseSkill = _basesSkill;
    }     

    /**
     * Getter untuk nama species
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter untuk baseskill species
     * @return baseSkill atau skill bawaan species
     */
    public Skill getBaseSkill(){
        return this.baseSkill;
    }

    /**
     * Mengembalikan element yang dimiliki species
     * Untuk SingleElemental idx = 0, jika 1 element bernilai None
     * Untuk DoubleElemental idx = 0 dan idx = 1
     * @param idx nomor element (0 atau 1)
     * @return elemen yang dimiliki species
     */
    abstract Element getElement(int idx);
    /**
     * Fungsi mengecek apakah species memiliki elemen yang sesuai
     * @param elm element yang ingin dicek
     * @return true apabila species memiliki element
     */
    abstract boolean hasElement(Element elm);
    /**
     * Fungsi mengecek apakah singleelemental
     * @return true untuk SingleElemental dan false untuk DoubleElemental
     */
    abstract boolean isSingleElement();

    public static void main(String[] args) {
        Species s1;
        SingleElemental s2;
        DoubleElemental s3;

        KatalogSkill allSkills = new KatalogSkill();
    }
}
// javac Species.java DoubleElemental.java SingleElemental.java Skill.java KatalogSkill.java