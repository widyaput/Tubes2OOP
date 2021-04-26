import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class KatalogSkill implements Iterable<Skill>{
    private ArrayList<Skill> skill_collection;

    /**
     * Konstruktor katalogSkill untuk inisiasi jenis-jenis skill
     */
    public KatalogSkill (){
        ArrayList<Element> fire = new ArrayList<Element> (Arrays.asList(Element.FIRE));
        ArrayList<Element> water = new ArrayList<Element> (Arrays.asList(Element.WATER));
        ArrayList<Element> ice = new ArrayList<Element> (Arrays.asList(Element.ICE));
        ArrayList<Element> electric = new ArrayList<Element> (Arrays.asList(Element.ELECTRIC));
        ArrayList<Element> ground = new ArrayList<Element> (Arrays.asList(Element.GROUND));
        ArrayList<Element> fire_electric = new ArrayList<Element> (Arrays.asList(Element.FIRE, Element.ELECTRIC));
        ArrayList<Element> water_ice = new ArrayList<Element> (Arrays.asList(Element.WATER, Element.ICE));
        ArrayList<Element> water_ground = new ArrayList<Element> (Arrays.asList(Element.WATER, Element.GROUND));
        ArrayList<Element> ground_ice = new ArrayList<Element> (Arrays.asList(Element.GROUND, Element.ICE));
        ArrayList<Element> electric_ice_water = new ArrayList<Element> (Arrays.asList(Element.ELECTRIC, Element.ICE, Element.WATER));
        ArrayList<Element> allElement = new ArrayList<Element> (Arrays.asList(Element.FIRE, Element.ELECTRIC, Element.WATER, Element.GROUND, Element.ICE));;
    
        Skill s0 = new Skill("Regular Punch", 500, 1, allElement);
        Skill s1 = new Skill("Inferno", 900, 1, fire);
        Skill s2 = new Skill("Ragnarok Trisagon", 950, 1, fire);
        Skill s3 = new Skill("Maralagidyne", 970, 1, fire);
        Skill s4 = new Skill("Titanomachia", 1112, 1, fire_electric);
        Skill s5 = new Skill("Aquary Tide", 895, 1, water);
        Skill s6 = new Skill("Water Blast", 980, 1, water);
        Skill s7 = new Skill("Blackwater Taint", 1010, 1, water);
        Skill s8 = new Skill("Depthsurge", 1100, 1, water);
        Skill s9 = new Skill("Earthbolt", 910, 1, ground);
        Skill s10 = new Skill("Clearstone", 960, 1, ground);
        Skill s11 = new Skill("Phantom Plow", 1000, 1, ground);
        Skill s12 = new Skill("Rain of Spines", 1120, 1, ground_ice);
        Skill s13 = new Skill("Thunderswarm", 920, 1, electric);
        Skill s14 = new Skill("Stormrage", 980, 1, electric);
        Skill s15 = new Skill("Presper Moonbow", 999, 1, electric);
        Skill s16 = new Skill("Mystic Lash", 1200, 1, electric_ice_water);
        Skill s17 = new Skill("Arctic Haze", 920, 1, ice);
        Skill s18 = new Skill("Glaze Lock", 980, 1, ice);
        Skill s19 = new Skill("Heat Leech", 1100, 1, ice);
        Skill s20 = new Skill("Blizzard", 1150, 1, water_ice);
        Skill s21 = new Skill("Shatterstone", 1190, 1, water_ground);

        skill_collection = new ArrayList<Skill>(Arrays.asList(s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20, s21));
    }

    /**
     * Getter untuk seluruh skill
     * @return koleksi katalog skill dalam ArrayList
     */
    public ArrayList<Skill> getAllSkill(){
        return skill_collection;
    }

    /**
     * Getter untuk seluruh skill yang kompatibel dengan element
     * @param _element element yang ingin dicari skillnya
     * @return kumpulan skill yang kompatibel dalam ArrayList
     */
    public ArrayList<Skill> getSkillWithElement(Element _element){
        ArrayList<Skill> skills = new ArrayList<Skill>();
        
        skill_collection.forEach(skill -> {
            if (skill.hasElement(_element)){ 
                skills.add(skill);
            }
        });
        
        return skills;
    }

    /**
     * Fungsi mengembalikan skill pada indeks ke idx (method untuk permudah save)
     * @param idx
     * @return
     */
    public Skill getSkillFromIndex(int idx){
        return this.skill_collection.get(idx);
    }
        /**
     * Method mengembalikan indeks dari suatu skill
     * @param namaSkill nama skill yang ingin dicari indeksnya
     * @return indeks suatu skill pada KatalogSkill
     */
    public int getIndexOfSkill(String namaSkill){
        int i = 0;
        for (Skill skill : skill_collection) {
            if (skill.getName().equals(namaSkill)){
                return i;
            }
            i++;
        }
        return 0;
    }
    @Override
    public Iterator<Skill> iterator() {
        return this.skill_collection.iterator();
    }
    /**
     * Testing
     */
    public static void main(String[] args) {
        KatalogSkill katalogSkill = new KatalogSkill();

        ArrayList<Skill> skills = katalogSkill.getAllSkill();

        System.out.println(skills.get(0));
    }
}

// javac Skill.java KatalogSkill.java