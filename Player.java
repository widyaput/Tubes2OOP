public class Player {
    private Engimon activeEngimon;
    private boolean isThereActiveEngimon;
    private Pair<Integer,Integer> coorP;
    private Pair<Integer,Integer> coorEA;
    private InventoryEngimon listEngimon;
    private InventorySkill listSkill;
    //Todo
    //PETA, MOVE, SPAWN DLL

    Player(Pair<Integer,Integer> Koordinat){
        this.coorP = Koordinat;
        this.listSkill = new InventorySkill();
        this.listEngimon = new InventoryEngimon();
        this.isThereActiveEngimon = false;
        //activeengimon akan diassign ketika activate
        //begitu pula coordinatnya
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        KatalogSkill katalogSkill = new KatalogSkill();
        Engimon E1 = new Engimon(katalogSpecies.getSpeciesFromIndex(8), "Alam", "", "", "", "", 1, 0, 0);
        Engimon E2 = new Engimon(katalogSpecies.getSpeciesFromIndex(9), "Chelsie", "", "", "", "", 1, 0, 0);
        Engimon E3 = new Engimon(katalogSpecies.getSpeciesFromIndex(10), "Monica", "", "", "", "", 1, 0, 0);
        try {
            this.listEngimon.addElement(E1);
            this.listEngimon.addElement(E2);
            this.listEngimon.addElement(E3);
            this.listSkill.addElement(katalogSkill.getSkillFromIndex(3));
            this.listSkill.addElement(katalogSkill.getSkillFromIndex(8));
            this.listSkill.addElement(katalogSkill.getSkillFromIndex(16));
        } catch (Exception e) {
            //Do nothing
        }


    }

    public void addEngimon(Engimon element) throws Exception{
        try {
            this.listEngimon.addElement(element);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addSkillItem(Skill other) throws Exception{
        try {
            this.listSkill.addElement(other);
        } catch (Exception e) {
            throw e;
        }
    }

    public void activateEngimon(int index) throws Exception{
        
        try {
            this.listEngimon.getElement(index);
        } catch (Exception e) {
            throw new CustomException("Engimon index is not valid");
        }
        Engimon dummy = this.listEngimon.getElement(index);
        if (this.isThereActiveEngimon){
            this.listEngimon.setElement(this.activeEngimon, index);
            this.activeEngimon = dummy;
        }else{
            //TODO
            //RELOCATE AE
            this.activeEngimon = dummy;
            this.listEngimon.removeElement(index);
        }
        this.isThereActiveEngimon = true;
    }

    public void removeXSkill(int index, int count) throws Exception{
        try {
            this.listSkill.removeXElement(index, count);
        } catch (Exception e) {
            throw e;
            
        }
    }

    public void removeEngimon(int index) throws Exception{
        try {
            this.listEngimon.removeElement(index);
        } catch (Exception e) {
            throw e;
            
        }
    }

    public void renameEngimonFromInv(int index, String newName) throws Exception{
        try {
            this.listEngimon.getElement(index).rename(newName);
        } catch (Exception e) {
            throw new CustomException("Invalid Engimon");
        }
    }

    public void learn(int index) throws Exception{
        if (!isThereActiveEngimon){
            throw new CustomException("There is no active engimon yet!");
        }
        try {
            this.listSkill.getElement(index);
        } catch (Exception e) {
            throw new CustomException("Invalid skill item");
        }
        //need update using exception, request to thomas
        try {
            int berhasil = this.activeEngimon.addSkill(this.listSkill.getElement(index));
        } catch (Exception e) {
            throw e;
        }
    }

}
