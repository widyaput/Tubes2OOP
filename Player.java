import java.util.ArrayList;
import java.util.Scanner;

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

    public boolean battle(Engimon enemy) {
        Scanner sc = new Scanner(System.in);  
        int ongoing = 1;
        int attempt = 1;
        boolean hasil = true;
        while(ongoing == 1) {
            int answer;
            //tampilkan stats lengkap musuh
            // cout << "------------------------" << endl;
            // cout << enemy;
            // cout << "Level : " << enemy.getLevel() << endl;
            // cout << "Element : " << endl;
            // vector<Element> element = enemy.getElement();
            // for (auto i = element.begin(); i != element.end(); ++i) {
            //     cout << " - " << *i << endl;
            // }
            // cout << "------------------------" << endl; 
            System.out.println(enemy.toString());
            
            double adv1 = Engimon.advantage(activeEngimon,enemy,1);
            double adv2 = Engimon.advantage(activeEngimon,enemy,2);
            double power1 = Engimon.countPower(activeEngimon,adv1);
            double power2 = Engimon.countPower(enemy,adv2);
            System.out.println("Your Engimon has a total power of " + power1);
            System.out.println("Enemy Engimon has a total power of " + power2);

            System.out.println("Input the number of the command you want to do");
            System.out.println("1. attack");
            System.out.println("2. change active engimon");
            System.out.println("3. run");
            System.out.println("Choose a command : ");
            answer = sc.nextInt();
            if(answer == 1) {
                //this->drawing(18+4);
                //ongoing = attack(P,P.getActiveEngimon(),enemy,attempt); // error soalnya harusnya pake reference
                if(power1 >= power2) { // kalo player menang
                   // this->drawing(20+4);
                    System.out.println("Your Engimon wins");
                    activeEngimon.addEXP(enemy.getLevel()*5); // tambah exp
                    System.out.println("Your active engimon gained " + enemy.getLevel()*5 + " experience points");
                    if(activeEngimon.getStatus() == false) {
                        activeEngimon = new Engimon(new SingleElemental(),0,0,0);
                        isThereActiveEngimon = false;
                        try {
                            map.SetElementPeta(coorEA.getFirst(),coorEA.getSecond(), map.GetElementPetaTetap(coorEA.getFirst(),coorEA.getSecond()));
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
    
                        System.out.println("Your active Engimon has reached its limit. He will ascend to Engimon heaven");
                    }
                    enemy.setWild(false);
                    enemy.setLives(3);
                    try {
                        addEngimon(enemy); // nambah engimon ke inventory
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    //dapet skill musuh pertama

                    ArrayList<Skill> s = enemy.getSkills();
                    try {
                        addSkillItem(s.get(0));
                        System.out.println("You obtained skill item : " + s.get(0).getName());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    ongoing = 0;
                    hasil = true;
                }
                else {
                    //this->drawing(19+4);
                    if(activeEngimon.getLives() > 1) {
                        System.out.println("Your Engimon's lives decreased");
                        activeEngimon.setLives(activeEngimon.getLives() - 1);
                        ongoing = 1;
                    }
                    else {
                        activeEngimon = new Engimon(new SingleElemental(),0,0,0); // Engimon player ditimpa default Engimon
                        isThereActiveEngimon = false;
                        ongoing = 0;
                        try {
                            map.SetElementPeta(coorEA.getFirst(),coorEA.getSecond(), map.GetElementPetaTetap(coorEA.getFirst(),coorEA.getSecond()));
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                        hasil = false;
                    }
                    
    
                    // if(getListEng().getSize() >= 1) { // kalo masih ada Engimon di inventory
                    //     int found = 0;
                    //     while(found == 0) {
                    //         int x,y;
                    //         System.out.println("Please choose another Engimon to be active");
                    //         viewListEngimon(); 
                    //         System.out.println("Input the index of the Engimon : ");
                    //         x = sc.nextInt();
                    //         y = x - 1;
                    //         try {
                    //             activateEngimon(y); // error kalo input index di luar batas
                    //             found = 1;
                    //         }
                    //         catch (exception e)
                    //         {
                    //             System.out.println("Wrong input. Please try again.");
    
                    //             //ini apa gak suruh minta inputan lagi aja sampai dapet engimon yang pas?
                    //             //maksudnya biar dia cuma keluar dari battle kalau kalah atau run
    
                    //             //ABAIKAN KOMEN INI, BACA YANG BAWAH YANG TANYA GAK BATTLE LAGI KALAU PLAYER KALAH
                    //         }
                    //         if (found) {
                    //             System.out.println("You have successfully changed your active Engimon");
                    //         }
        
                    //     }
                    //}
                    //changeEngimon(p,attempt); // kalo Engimon mati wajib ganti
                    //ongoing = 0;
                    //oh ini harusnya kalau sampai sini gak battle lagi ya?
                    //kalau iya hasilnya false;
                    //hasil = false;
                    
                    //kalau dia kalah dia wajib activate engimon waktu mash mode battle? kalau di luar mode battle gimna?
                }
                        
            }
            else if(answer == 2) {
                if(getListEng().getSize() >= 1) { // kalo masih ada Engimon di inventory
                    int found = 0;
                    while(found == 0) {
                        int x,y;
                        System.out.println("Please choose another Engimon to activate");
                        //belom ada viewListEngimon
                        viewListEng(); 
                        System.out.println("Input the index of the Engimon : ");
                        x = sc.nextInt();
                        y = x - 1;
                        if(x <= getListEng().getSize()) {
                            try {
                                activateEngimon(y); // error kalo input index di luar batas
                                System.out.println("You have successfully changed your active Engimon");
                                found = 1;
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                                
                            }
                        }
                        else {
                            System.out.println("Invalid index");
                            System.out.println("");
                        }
                        
                    }
                }
                else {
                    if(isThereActiveEngimon == false) { // otomatis kalah kalo Active Engimon mati dan nggak ada lagi di inventory
                        System.out.println("All of your Engimon are defeated.");
                        System.out.println("GAME OVER");
                        hasil = false;
                        //flow program gak bakal bisa sampai kesini, karena init battle mewajibkan active engimon ada sebelumnya
                    }
                    else {
                        System.out.println("You don't have any other available Engimon");
                    }
                }
    
            }
            else if(answer == 3) {
                System.out.println("You successfully fled");
                ongoing = 0;
                //ongoing = run(attempt);
                //int x = rand() % 2 + 1;
                // if(attempt > 0) {
                //     attempt = attempt - 1;
                //     if(x == 1) {
                //         cout << "You failed to run" << endl;
                //         cout << "" << endl;
                //         ongoing = 1;
                //     }
                //     else {
                //         cout << "You successfully fled" << endl;
                //         ongoing = 0;
                //         hasil = false;
                //     }
                // }
                // else {
                //     cout << "You may only attempt to run once." << endl;
                //     cout << "" << endl;
                //     ongoing = 1; //ini attempt runnya gak tiap engimon aja? kalau 1 tiap player bisa langsung kalah kalau enemynya OP
                // }
            }
    
        }
        sc.close();
        return hasil;
    }
    
    //peta belom beres
    public void initBattle() throws CustomException {
        if (isThereActiveEngimon && isEnemyAround()){
            Pair<Integer,Integer> coorEnemy = getEnemyAround();
            Engimon enemy = map.GetEngimonfromDaftar(coorEnemy.getFirst(),coorEnemy.getSecond());
            //this->drawing(21+4);
            //this->drawing(22+4);
            boolean win = battle(enemy);
            if (win) {
                map.DeleteEngimon2(coorEnemy.getFirst(),coorEnemy.getSecond());
            }
        }else{
            if (! isThereActiveEngimon){
                throw(new CustomException("You don't any active Engimon"));
            }
            else {
                throw(new CustomException("There are no enemy eround"));
            }
        }
    }
    //peta belom beres
    boolean isEnemyAround(){
        int y = coorP.getFirst();
        int x = coorP.getSecond();
        try {
            if ( y-1 >= 0 && (map.GetElementPeta(y-1,x) != '-' && map.GetElementPeta(y-1,x) != 'o' && map.GetElementPeta(y-1,x) != 'X')){
                return true;
            }else if (x+1 < map.GetKolom() && (map.GetElementPeta(y,x+1) != '-' && map.GetElementPeta(y,x+1) != 'o' && map.GetElementPeta(y,x+1) != 'X')){
                return true;
            }else if (y+1 < map.GetBaris() && (map.GetElementPeta(y+1,x) != '-' && map.GetElementPeta(y+1,x) != 'o' && map.GetElementPeta(y+1,x) != 'X')){
                return true;
            }else if (x-1 >= 0 && (map.GetElementPeta(y,x-1) != '-' && map.GetElementPeta(y,x-1) != 'o' && map.GetElementPeta(y,x-1) != 'X')){
                return true;
            }
            return false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
        
    
        
    }
    
    //peta belom beres
    Pair<Integer,Integer> getEnemyAround(){
        int y = coorP.getFirst();
        int x = coorP.getSecond();
        int a;
        int b;
        try {
            if ( y-1 >= 0 && (map.GetElementPeta(y-1,x) != '-' && map.GetElementPeta(y-1,x) != 'o' && map.GetElementPeta(y-1,x) != 'X')){
                a= y-1;
                b=x;
            }else if (x+1 < map.GetKolom() && (map.GetElementPeta(y,x+1) != '-' && map.GetElementPeta(y,x+1) != 'o' && map.GetElementPeta(y,x+1) != 'X')){
                a = y;
                b=x+1;
            }else if (y+1 < map.GetBaris() && (map.GetElementPeta(y+1,x) != '-' && map.GetElementPeta(y+1,x) != 'o' && map.GetElementPeta(y+1,x) != 'X')){
                a=y+1;
                b=x;
            }else if (x-1 >= 0 && (map.GetElementPeta(y,x-1) != '-' && map.GetElementPeta(y,x-1) != 'o' && map.GetElementPeta(y,x-1) != 'X')){
                a = y;
                b = x-1;
            }else {a = -1; b=-1;}
        
            return Pair.makePair(a,b);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Pair.makePair(-1,-1);
        
    }
    
    public boolean isGameOver(){
        return (!(isThereActiveEngimon) && (getListEng().getSize()==0));
    }
}
