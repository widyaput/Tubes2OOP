import java.util.*;

public class KatalogSpecies implements Iterable{
    private ArrayList<Species> species;

    /**
     * Konstruktor KatalogSpecies dan inisiasi jenis species yang ada
     */
    public KatalogSpecies(){
        KatalogSkill skills = new KatalogSkill();
        ArrayList<Skill> fireSkill = skills.getSkillWithElement(Element.FIRE);
        ArrayList<Skill> waterSkill = skills.getSkillWithElement(Element.WATER);
        ArrayList<Skill> electricSkill = skills.getSkillWithElement(Element.ELECTRIC);
        ArrayList<Skill> groundSkill = skills.getSkillWithElement(Element.GROUND);
        ArrayList<Skill> iceSkill = skills.getSkillWithElement(Element.ICE);

        Species fire1 = new SingleElemental("Snorlax", Element.FIRE, fireSkill.get(2));
        Species water1 = new SingleElemental("Dewgong", Element.WATER, waterSkill.get(2));
        Species electric1 = new SingleElemental("Zapdos", Element.ELECTRIC, electricSkill.get(2));
        Species ground1 = new SingleElemental("Tangela", Element.GROUND, groundSkill.get(2));
        Species ice1 = new SingleElemental("Articuno", Element.ICE, iceSkill.get(2));
        Species fire_elec1 = new DoubleElemental("Exeguttor", Element.FIRE, Element.ELECTRIC, fireSkill.get(3));
        Species water_ice1 = new DoubleElemental("Cloyster", Element.WATER, Element.ICE, iceSkill.get(3));
        Species water_ground1 = new DoubleElemental("Venusaur", Element.WATER, Element.GROUND, groundSkill.get(3));
        
        Species fire2 = new SingleElemental("Kangaskhan", Element.FIRE, fireSkill.get(1));
        Species water2 = new SingleElemental("Jynx", Element.WATER, waterSkill.get(1));
        Species electric2 = new SingleElemental("Magnemite", Element.ELECTRIC, electricSkill.get(1));
        Species ground2 = new SingleElemental("Victreebel", Element.GROUND, groundSkill.get(1));
        Species ice2 = new SingleElemental("Lapras", Element.ICE, iceSkill.get(1));
        Species fire_elec2 = new DoubleElemental("Voltorb", Element.FIRE, Element.ELECTRIC, fireSkill.get(1));
        Species water_ice2 = new DoubleElemental("Vaporeon", Element.WATER, Element.ICE, iceSkill.get(1));
        Species water_ground2 = new DoubleElemental("Meowth", Element.WATER, Element.GROUND, groundSkill.get(1));

        species = new ArrayList<Species>();

        species.add(fire1);
        species.add(water1);
        species.add(electric1);
        species.add(ground1);
        species.add(ice1);
        species.add(fire_elec1);
        species.add(water_ice1);
        species.add(water_ground1);
        species.add(fire2);
        species.add(water2);
        species.add(electric2);
        species.add(ground2);
        species.add(ice2);
        species.add(fire_elec2);
        species.add(water_ice2);
        species.add(water_ground2);
    }

    /**
     * Method mengembalikan species pada indeks ke idx
     * @param idx indeks yang ingin dicari speciesnya
     * @return species pada indeks ke idx
     */
    public Species getSpeciesFromIndex(int idx){
        return this.species.get(idx);
    }

    /**
     * Method mengembalikan indeks dari suatu species
     * @param namaSpecies nama species yang ingin dicari indeksnya
     * @return indeks suatu species pada KatalogSpecies
     */
    public int getIndexOfSpecies(String namaSpecies){
        int i = 0;
        for (Species species2 : species) {
            if (species2.getName().equals(namaSpecies)){
                return i;
            }
            i++;
        }
        return 0;
    }

    /**
     * Method mengembalikan seluruh species yang tersedia
     * @return seluruh species
     */
    public ArrayList<Species> getAllSpecies(){
        return this.species;
    }

    @Override
    public Iterator iterator() {
        return this.species.iterator();
    }

    public static void main(String[] args) {
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        KatalogSkill katalogSkill = new KatalogSkill();

        Species s0 = katalogSpecies.getSpeciesFromIndex(0);
        Species s1 = katalogSpecies.getSpeciesFromIndex(6);
        
        System.out.println(s0);
        System.out.println(s1);

        System.out.println(s0.hasElement(Element.FIRE));
        s1.baseSkill.incrMasteryLevel();
        s1.baseSkill.incrMasteryLevel();
        s1.baseSkill.incrMasteryLevel();
        s1.baseSkill.incrMasteryLevel();
        System.out.println(s1);
    }
}

// javac KatalogSpecies.java Species.java Skill.java KatalogSkill.java