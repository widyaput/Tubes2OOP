public class Player {
    private Engimon activeEngimon;
    private boolean isThereActiveEngimon;
    private Pair<Integer,Integer> coorP;
    private Pair<Integer,Integer> coorEA;
    private InventoryEngimon listEngimon;
    private InventorySkill listSkill;
    private Peta map;
    //Todo
    //Deactivate, breeding, battle DLL

    Player(Peta map){
        this.map = map;
        this.coorP = Pair.makePair(this.map.GetKolom()-1,this.map.GetBaris()-1);
        this.listSkill = new InventorySkill();
        this.listEngimon = new InventoryEngimon();
        this.isThereActiveEngimon = false;
        this.coorEA = Pair.makePair(-1,-1);
        this.activeEngimon = null;
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
            this.map.SetElementPeta(this.coorP.getFirst(), this.coorP.getFirst(),'P');
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
            this.relocateAE();
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


    public Integer getXCoor(){
        return this.coorP.getSecond();
    }

    public Integer getYCoor(){
        return this.coorP.getFirst();
    }

    public Integer getXEA(){
        //asumsi ea terpasang
        return this.coorEA.getSecond();
    }

    public Integer getYEA(){
        return this.coorEA.getFirst();
    }

    public InventoryEngimon getListEng(){
        return this.listEngimon;
    }

    public InventorySkill getListSkill(){
        return this.listSkill;
    }

    public void viewListEng(){
        this.listEngimon.showStorage();
    }

    public void viewListSkill(){
        this.listSkill.showStorage();
    }

    public boolean getStatusEA(){
        return this.isThereActiveEngimon;
    }

    public void moveW() throws CustomException{
        int y = this.coorP.getFirst();
        int x = this.coorP.getSecond();
        if (y-1 >= 0 && (this.map.GetElementPeta(y-1,x) == '-' ||
                        this.map.GetElementPeta(y-1,x) == 'o' ||
                        this.map.GetElementPeta(y-1,x) == '^' ||
                        this.map.GetElementPeta(y-1,x) == '#' ||
                        this.map.GetElementPeta(y-1,x) == 'X')){
            this.map.SetElementPeta(y,x,this.map.GetElementPetaTetap(y,x));
            this.coorP.setFirst(Integer.valueOf(y-1));
            this.map.SetElementPeta(y-1,x,'P');
        }else throw new CustomException("Invalid move");
    }

    public void moveA() throws CustomException{
        int y = this.coorP.getFirst();
        int x = this.coorP.getSecond();
        if (x-1 >= 0 && (this.map.GetElementPeta(y,x-1) == '-' ||
                        this.map.GetElementPeta(y,x-1) == 'o' ||
                        this.map.GetElementPeta(y,x-1) == '^' ||
                        this.map.GetElementPeta(y,x-1) == '#' ||
                        this.map.GetElementPeta(y,x-1) == 'X')){
            this.map.SetElementPeta(y,x,this.map.GetElementPetaTetap(y,x));
            this.coorP.setSecond(Integer.valueOf(x-1));
            this.map.SetElementPeta(y,x-1,'P');
        }else throw new CustomException("Invalid move");
    }
    public void moveS() throws CustomException{
        int y = this.coorP.getFirst();
        int x = this.coorP.getSecond();
        if (y+1 < this.map.GetBaris() && (this.map.GetElementPeta(y+1,x) == '-' ||
                        this.map.GetElementPeta(y+1,x) == 'o' ||
                        this.map.GetElementPeta(y+1,x) == '^' ||
                        this.map.GetElementPeta(y+1,x) == '#' ||
                        this.map.GetElementPeta(y+1,x) == 'X')){
            this.map.SetElementPeta(y,x,this.map.GetElementPetaTetap(y,x));
            this.coorP.setFirst(Integer.valueOf(y+1));
            this.map.SetElementPeta(y+1,x,'P');
        }else throw new CustomException("Invalid move");
    }
    public void moveD() throws CustomException{
        int y = this.coorP.getFirst();
        int x = this.coorP.getSecond();
        if (x+1 < this.map.GetKolom() && (this.map.GetElementPeta(y,x+1) == '-' ||
                        this.map.GetElementPeta(y,x+1) == 'o' ||
                        this.map.GetElementPeta(y,x+1) == '^' ||
                        this.map.GetElementPeta(y,x+1) == '#' ||
                        this.map.GetElementPeta(y,x+1) == 'X')){
            this.map.SetElementPeta(y,x,this.map.GetElementPetaTetap(y,x));
            this.coorP.setSecond(Integer.valueOf(x+1));
            this.map.SetElementPeta(y,x+1,'P');
        }else throw new CustomException("Invalid move");
    }

    public void showMap(){
        this.map.PrintPeta();
    }

    public void moveAE(String move) throws CustomException{
        boolean exc = false;
        int y = this.coorP.getFirst();
        int x = this.coorP.getSecond();

        if (move.equals("d")){
            char element = this.map.GetElementPeta(y,x-1);
            if (element != '-' && element != 'o' && element != '^' && element != '#'){
                exc = true;
            }else{
                if (this.coorEA.getFirst() != y || this.coorEA.getSecond() != x){
                    this.map.SetElementPeta(this.coorEA.getFirst(),this.coorEA.getSecond(), this.map.GetElementPetaTetap(this.coorEA.getFirst(),this.coorEA.getSecond()));
                }
                this.coorEA.setFirst(Integer.valueOf(y));
                this.coorEA.setSecond(Integer.valueOf(x-1));
                this.map.SetElementPeta(y,x-1,'X');
            }
        }else if (move.equals("s")){
            char element = this.map.GetElementPeta(y-1,x);
            if (element != '-' && element != 'o' && element != '^' && element != '#'){
                exc = true;
            }else{
                if (this.coorEA.getFirst() != y || this.coorEA.getSecond() != x){
                    this.map.SetElementPeta(this.coorEA.getFirst(),this.coorEA.getSecond(), this.map.GetElementPetaTetap(this.coorEA.getFirst(),this.coorEA.getSecond()));
                }
                this.coorEA.setFirst(Integer.valueOf(y-1));
                this.coorEA.setSecond(Integer.valueOf(x));
                this.map.SetElementPeta(y-1,x,'X');
            }
        }else if (move.equals("a")){
            char element = this.map.GetElementPeta(y,x+1);
            if (element != '-' && element != 'o' && element != '^' && element != '#'){
                exc = true;
            }else{
                if (this.coorEA.getFirst() != y || this.coorEA.getSecond() != x){
                    this.map.SetElementPeta(this.coorEA.getFirst(),this.coorEA.getSecond(), this.map.GetElementPetaTetap(this.coorEA.getFirst(),this.coorEA.getSecond()));
                }
                this.coorEA.setFirst(Integer.valueOf(y));
                this.coorEA.setSecond(Integer.valueOf(x+1));
                this.map.SetElementPeta(y,x+1,'X');
            }
        }else if (move.equals("w")){
            char element = this.map.GetElementPeta(y+1,x);
            if (element != '-' && element != 'o' && element != '^' && element != '#'){
                exc = true;
            }else{
                if (this.coorEA.getFirst() != y || this.coorEA.getSecond() != x){
                    this.map.SetElementPeta(this.coorEA.getFirst(),this.coorEA.getSecond(), this.map.GetElementPetaTetap(this.coorEA.getFirst(),this.coorEA.getSecond()));
                }
                this.coorEA.setFirst(Integer.valueOf(y+1));
                this.coorEA.setSecond(Integer.valueOf(x));
                this.map.SetElementPeta(y+1,x,'X');
            }
        }

        if (exc) throw new CustomException("Invalid move active engimon");
    }

    public void relocateAE(){
        int y = this.coorP.getFirst();
        int x =this.coorP.getSecond();
        boolean found = true;
        int a = 0;
        int b = 0;
        try {
            if (y+1 < this.map.GetBaris() && (this.map.GetElementPeta(y+1,x) == '-' ||
                                            this.map.GetElementPeta(y+1,x) == 'o' ||
                                            this.map.GetElementPeta(y+1,x) == '^' ||
                                            this.map.GetElementPeta(y+1,x) == '#' )){
            a = y+1;
            b = x;
            }else if (x+1 < this.map.GetKolom() && (this.map.GetElementPeta(y,x+1) == '-' ||
                                                    this.map.GetElementPeta(y,x+1) == 'o' ||
                                                    this.map.GetElementPeta(y,x+1) == '^' ||
                                                    this.map.GetElementPeta(y,x+1) == '#')){
                a = y;
                b = x+1;
            }else if (x-1 >= 0 && (this.map.GetElementPeta(y,x-1) == '-' ||
                                    this.map.GetElementPeta(y,x-1) == 'o' ||
                                    this.map.GetElementPeta(y,x-1) == '^' ||
                                    this.map.GetElementPeta(y,x-1) == '#')){
                a = y;
                b = x-1;
            }else if (y-1 >= 0 && (this.map.GetElementPeta(y-1,x) == '-' ||
                                    this.map.GetElementPeta(y-1,x) == 'o' ||
                                    this.map.GetElementPeta(y-1,x) == '^' ||
                                    this.map.GetElementPeta(y-1,x) == '#' )){
                a = y-1;
                b = x;
            }else if (y+1 < this.map.GetBaris() && x+1 < this.map.GetKolom() &&
            (this.map.GetElementPeta(y+1,x+1) == '-' ||
            this.map.GetElementPeta(y+1,x+1) == 'o' ||
            this.map.GetElementPeta(y+1,x+1) == '^' ||
            this.map.GetElementPeta(y+1,x+1) == '#')){
                a = y+1;
                b = x+1;
            }else if (y+1 < this.map.GetBaris() && x-1 >= 0 &&
            (this.map.GetElementPeta(y+1,x-1) == '-' ||
            this.map.GetElementPeta(y+1,x-1) == 'o' ||
            this.map.GetElementPeta(y+1,x-1) == '^' ||
            this.map.GetElementPeta(y+1,x-1) == '#')){
                a = y+1;
                b = x-1;
            }else if (y-1 >= 0 && x+1 < this.map.GetKolom() &&
            (this.map.GetElementPeta(y-1,x+1) == '-' ||
            this.map.GetElementPeta(y-1,x+1) == 'o' ||
            this.map.GetElementPeta(y-1,x+1) == '^' ||
            this.map.GetElementPeta(y-1,x+1) == '#')){
                a = y-1;
                b = x+1;
            }else if (y-1 >= 0 && x-1 >= 0 &&
            (this.map.GetElementPeta(y-1,x-1) == '-' ||
            this.map.GetElementPeta(y-1,x-1) == 'o' ||
            this.map.GetElementPeta(y-1,x-1) == '^' ||
            this.map.GetElementPeta(y-1,x-1) == '#')){
                a = y-1;
                b = x-1;
            }else{
                found = false;
                a = this.coorP.getFirst();
                b = this.coorP.getSecond();
            }
            this.coorEA.setFirst(Integer.valueOf(a));
            this.coorEA.setSecond(Integer.valueOf(b));
            if (found) this.map.SetElementPeta(a,b,'X');
        } catch (Exception e) {
            // donothing
        }
    }

    public void spawn(){
        this.map.SpawnEngimon();
    }
}
