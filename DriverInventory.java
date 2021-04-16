public class DriverInventory {
    public static void main(String arg[]){

        KatalogSpecies katalogSpecies = new KatalogSpecies();
        KatalogSkill katalogSkill = new KatalogSkill();
        // Engimon e1 = new Engimon(katalogSpecies.getSpeciesFromIndex(1), 5, 0, 0);
        // Engimon e2 = new Engimon(katalogSpecies.getSpeciesFromIndex(0), 5, 0, 0);
        // Engimon e3 = new Engimon(katalogSpecies.getSpeciesFromIndex(1), 10, 0, 0);

        InventoryEngimon coba = new InventoryEngimon();
        InventorySkill coba2 = new InventorySkill();

        try {
            
            coba2.addElement(katalogSkill.getSkillFromIndex(1));
            coba2.addElement(katalogSkill.getSkillFromIndex(5));
            coba2.addElement(katalogSkill.getSkillFromIndex(3));
            coba2.addElement(katalogSkill.getSkillFromIndex(2));
            coba2.addElement(katalogSkill.getSkillFromIndex(2));
            coba2.addElement(katalogSkill.getSkillFromIndex(4));
            
            coba2.showStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            coba.removeElement(3);    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
