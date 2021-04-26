import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.*;

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
    }

    public void starterPack(){
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        KatalogSkill katalogSkill = new KatalogSkill();
        Engimon E1 = new Engimon(katalogSpecies.getSpeciesFromIndex(8), "Alam", "", "", "", "", 13, 0, 0);
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

    public Engimon breeding(Engimon A, Engimon B) throws CustomException{
        if (A.getLevel() < 4 || B.getLevel() < 4) throw new CustomException("Not enough level to breed");
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        Species spec = null;
        System.out.println("Masukkan nama anak : ");
        Scanner scanner = new Scanner(System.in);
        String nama = scanner.nextLine();
        
        if (A.getElement().get(0) == B.getElement().get(0)) {
            spec = A.getSpecies();
        } else {
            if (A.getAdvElement(B.getElement().get(0)) > B.getAdvElement(A.getElement().get(0))) {
                  spec = A.getSpecies();
            } else if (A.getAdvElement(B.getElement().get(0)) == B.getAdvElement(A.getElement().get(0))) {
                if ((A.getSpecies().hasElement(Element.FIRE) && B.getSpecies().hasElement(Element.ELECTRIC)) ||
                    (B.getSpecies().hasElement(Element.FIRE) && A.getSpecies().hasElement(Element.ELECTRIC))) {
                    spec = katalogSpecies.getSpeciesFromIndex(5);
                } else if ((A.getSpecies().hasElement(Element.WATER) && B.getSpecies().hasElement(Element.ICE)) ||
                           (B.getSpecies().hasElement(Element.WATER) && A.getSpecies().hasElement(Element.ICE))) {
                    spec = katalogSpecies.getSpeciesFromIndex(6);
                } else if ((A.getSpecies().hasElement(Element.WATER) && B.getSpecies().hasElement(Element.GROUND)) ||
                           (B.getSpecies().hasElement(Element.WATER) && A.getSpecies().hasElement(Element.GROUND))) {
                    spec = katalogSpecies.getSpeciesFromIndex(7);
                }
            } else {
                spec = B.getSpecies();
            }
        }
        A.downLevel(3);
        B.downLevel(3);
        Engimon C = new Engimon(spec, nama, A.getSpecies().getName(), A.getName(), B.getSpecies().getName(), B.getName(), this.skillanak(A, B, spec), 1, 0, 100);
        
        return C;
    }
    
    public ArrayList<Skill> skillanak(Engimon A, Engimon B, Species spec) {
        int masteryA, masteryB;
        ArrayList<Skill> skillsA = A.getSkills();
        ArrayList<Skill> skillsB = B.getSkills();
        int n = skillsA.size() + skillsB.size();
        ArrayList<Skill> allSkill = new ArrayList<Skill>();
        ArrayList<Skill> getSkill = new ArrayList<Skill>();
        Skill temp;
        
        // Make a new array with all of the skills
        for (Skill s : skillsA) {
            allSkill.add(s);
        }
        for (Skill s : skillsB) {
            allSkill.add(s);
        }
        
        // Sort all skills according to mastery level
        // New bubble algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                masteryA = allSkill.get(j).getMasteryLevel();
                masteryB = allSkill.get(j+1).getMasteryLevel();
                if (masteryA < masteryB) {
                    temp = allSkill.get(j);
                    allSkill.set(j, allSkill.get(j+1));
                    allSkill.set(j+1, temp);
                }
            }
        }

        List<Skill> dummy = allSkill.stream().sorted((a,b) -> (a.getMasteryLevel() > b.getMasteryLevel() ? -1 : 1)).collect(Collectors.toList());
        allSkill = new ArrayList<Skill>(dummy);
        /*
        Old sorting algorithm
        int max;
        for (int i = 0; i < n - 1; i++) {
            max = i;
            for (int j = i + 1; j < n; j++) {
                masteryA = allSkill[max].getMasteryLevel();
                masteryB = allSkill[j].getMasteryLevel();
                if (masteryA < masteryB)
                    max = j;
            }
            temp = allSkill[i];
            allSkill[i] = allSkill[max];
            allSkill[max] = temp;
        }
        */
        
        // Skill unik untuk species dari anak
        Skill unique = spec.getBaseSkill();
        boolean inheritUniquePar = false;
        
        // Remove duplicate skills, and check if there is unique skill of child's species
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (allSkill.get(i) == unique) {
                    inheritUniquePar = true;
                }
                if (allSkill.get(i) == allSkill.get(j)) {   
                    int mastery = allSkill.get(i).getMasteryLevel() + 1;
                    if (mastery > 3) {
                        mastery = 3;
                    }
                    allSkill.get(i).setMasteryLevel(mastery);
                    n = n - 1;
                    for (int k = j; k < n; k++) {
                        allSkill.set(k, allSkill.get(k + 1));
                    }
                }
            }
        }
        
        // If parents don't have the unique skill of child's species,
        // inherit that skill
        if (!inheritUniquePar) {
            allSkill.add(unique);
            for (int i = n; i > 0; i--) {
                allSkill.set(i, allSkill.get(i - 1));
            }
            allSkill.set(0, unique);
        }

        getSkill.clear();
        for (int i = 0; i < 4 && i < n; i++) {
            getSkill.add(allSkill.get(i));
        }
        return getSkill;
    }

    public void setCoorP(Pair<Integer,Integer> coor){
        this.coorP = coor;
    }

    public void setCoorEA(Pair<Integer,Integer> coor){
        this.coorEA = coor;
    }

    public Peta getMap(){
        return this.map;
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
        int highestLevel = this.listEngimon.getStorage().stream().map(a -> a.getLevel()).reduce((a,b) -> a > b ? a : b).get();
        this.map.SpawnEngimon(highestLevel);
    }

    public boolean battle(Engimon enemy) {
        Scanner sc = new Scanner(System.in);  
        int ongoing = 1;
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
            sc.nextLine();
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
                hasil = false;
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
                map.DeleteEngimon(coorEnemy.getFirst(),coorEnemy.getSecond());
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
            if ( y-1 >= 0 && (map.GetElementPeta(y-1,x) != '-' && map.GetElementPeta(y-1,x) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y-1,x) != 'X')){
                return true;
            }else if (x+1 < map.GetKolom() && (map.GetElementPeta(y,x+1) != '-' && map.GetElementPeta(y,x+1) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y,x+1) != 'X')){
                return true;
            }else if (y+1 < map.GetBaris() && (map.GetElementPeta(y+1,x) != '-' && map.GetElementPeta(y+1,x) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y+1,x) != 'X')){
                return true;
            }else if (x-1 >= 0 && (map.GetElementPeta(y,x-1) != '-' && map.GetElementPeta(y,x-1) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y,x-1) != 'X')){
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
            if ( y-1 >= 0 && (map.GetElementPeta(y-1,x) != '-' && map.GetElementPeta(y-1,x) != 'o'&& map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y-1,x) != 'X')){
                a= y-1;
                b=x;
            }else if (x+1 < map.GetKolom() && (map.GetElementPeta(y,x+1) != '-' && map.GetElementPeta(y,x+1) != 'o'&& map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y,x+1) != 'X')){
                a = y;
                b=x+1;
            }else if (y+1 < map.GetBaris() && (map.GetElementPeta(y+1,x) != '-' && map.GetElementPeta(y+1,x) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y+1,x) != 'X')){
                a=y+1;
                b=x;
            }else if (x-1 >= 0 && (map.GetElementPeta(y,x-1) != '-' && map.GetElementPeta(y,x-1) != 'o' && map.GetElementPeta(y-1,x) != '#' && map.GetElementPeta(y-1,x) != '^' && map.GetElementPeta(y,x-1) != 'X')){
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

    public void save() throws IOException{
        FileWriter write = new FileWriter("load.txt", false);
        PrintWriter print = new PrintWriter(write);
        print.printf("%d %d\n", this.map.GetBaris(), this.map.GetKolom());
        try {
            for (int i = 0; i < this.map.GetBaris(); i++){
                for (int j = 0; j < this.map.GetKolom(); j++){
                    print.printf("%c", this.map.GetElementPetaTetap(i,j));
                    if (j == this.map.GetKolom()-1){
                        print.printf("\n");
                    }
                }
            }
            for (int i = 0; i < this.map.GetBaris(); i++){
                for (int j = 0; j < this.map.GetKolom(); j++){
                    print.printf("%c", this.map.GetElementPeta(i,j));
                    if (j == this.map.GetKolom()-1){
                        print.printf("\n");
                    }
                }
            }
            Vector<PosisiEngimon> dummy = this.map.getDaftarEngimons();
            print.printf("%d\n", dummy.size());
            // KatalogSpecies speciesfac = new KatalogSpecies();
            // KatalogSkill skillfac = new KatalogSkill();
            for (int i = 0; i < dummy.size(); i++){
                PosisiEngimon dam = dummy.get(i);
                //baris, kolom, spesies, level, exp, cumulativeexp
                print.printf("%d %d %s %d %d %d\n", dam.getBarisPosisi(), dam.getKolomPosisi(), dam.getEngimon().getSpeciesName(), dam.getEngimon().getLevel(), dam.getEngimon().getEXP(), dam.getEngimon().getCumulativeEXP());
                // ArrayList<Skill> dum = dam.getEngimon().getSkills();
                // print.printf("%d\n", dum.size());
                // for (int j = 0 ; j < dum.size(); j++){
                //     //nama skill, mastery level
                //     print.printf("%s %d\n", dum.get(j).getName(), dum.get(j).getMasteryLevel());
                // }
            }
            //sampai sini udah save map
            //giliran save playernya
            // save coorP, status active engimon (bool, jika iya berarti akan ada coor EA dan atribut EA)
            // save list engimon lengkap
            // save list skill item lengkap
            print.printf("%d %d\n", this.coorP.getFirst(), this.coorP.getSecond());
            print.printf("%d\n", (this.isThereActiveEngimon ? 1 : 0));
            if (this.isThereActiveEngimon){
                print.printf("%d %d\n", this.coorEA.getFirst(), this.coorEA.getSecond());
                //species, nama, nama ortu1, spesies ortu 1, nama ortu 2, spesies ortu2, level, exp, cumulativeexp
                print.printf("%s %s %s %s %s %s %d %d %d\n", activeEngimon.getSpeciesName(), activeEngimon.getName().equals("") ? "null" : activeEngimon.getName(), activeEngimon.getParentName().equals("") ? "null" : activeEngimon.getParentName(), activeEngimon.getParentSpecies().equals("") ? "null" : activeEngimon.getParentSpecies(), activeEngimon.getParent2Name().equals("") ? "null" : activeEngimon.getParent2Species(), activeEngimon.getParent2Species().equals("") ? "null" : activeEngimon.getParent2Species(), activeEngimon.getLevel(), activeEngimon.getEXP(), activeEngimon.getCumulativeEXP());
                ArrayList<Skill> dum = activeEngimon.getSkills();
                print.printf("%d\n", dum.size());
                for (int j = 0 ; j < dum.size(); j++){
                    //nama skill, mastery level
                    print.printf("%s %d\n", dum.get(j).getName(), dum.get(j).getMasteryLevel());
                }
            }
            Vector<Engimon> dumeng = this.listEngimon.getStorage();
            print.printf("%d\n", dumeng.size());
            for (int i = 0 ; i < dumeng.size(); i++){
                //species, nama, nama ortu1, spesies ortu 1, nama ortu 2, spesies ortu2, level, exp, cumulativeexp
                print.printf("%s %s %s %s %s %s %d %d %d\n", dumeng.get(i).getSpeciesName(), dumeng.get(i).getName().equals("") ? "null" : dumeng.get(i).getName(), dumeng.get(i).getParentName().equals("") ? "null" : dumeng.get(i).getParentName(), dumeng.get(i).getParentSpecies().equals("") ? "null" : dumeng.get(i).getParent2Species(), dumeng.get(i).getParent2Name().equals("") ? "null" : dumeng.get(i).getParent2Name(), dumeng.get(i).getParent2Species().equals("") ? "null" : dumeng.get(i).getParent2Species(), dumeng.get(i).getLevel(), dumeng.get(i).getEXP(), dumeng.get(i).getCumulativeEXP());
                ArrayList<Skill> dum = dumeng.get(i).getSkills();
                print.printf("%d\n", dum.size());
                for (int j = 0 ; j < dum.size(); j++){
                    //nama skill, mastery level
                    print.printf("%s %d\n", dum.get(j).getName(), dum.get(j).getMasteryLevel());
                }
            }

            Vector<Skill> dumskill = this.listSkill.getStorage();
            print.printf("%d\n", dumskill.size());
            for (int i = 0; i < dumskill.size(); i++){
                print.printf("%s %d", dumskill.get(i).getName(), dumskill.get(i).getMasteryLevel());
                if (i != dumskill.size()-1) print.printf("\n");
            }
        } catch (Exception e) {
            System.out.println("Error"); //developing testing
            //TODO: handle exception
        }finally{
            print.close();
        }

        
    }

    public static Player load(){
        try {
            Scanner reader = new Scanner(new File("load.txt"));
            String dummy = reader.nextLine();
            int baris, kolom;
            Pattern digit = Pattern.compile("\\d+");
            Matcher m;
            Pattern str = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
            m = digit.matcher(dummy);
            m.find();
            baris = Integer.parseInt(dummy.substring(m.start(),m.end()));
            m.find();
            kolom = Integer.parseInt(dummy.substring(m.start(), m.end()));
            
            Peta map = new Peta(baris,kolom);
            Vector<Character> petaisi = new Vector<Character>();
            Vector<Character> petatetap = new Vector<Character>();
            for(int i = 0; i < baris; i++){
                dummy = reader.nextLine();
                for (int j = 0; j < kolom;j++){
                    petatetap.add(dummy.charAt(j));
                }
            }
            for(int i = 0; i < baris; i++){
                dummy = reader.nextLine();
                for (int j = 0; j < kolom;j++){
                    petaisi.add(dummy.charAt(j));
                }
            }
            map.LoadPeta(petaisi, petatetap);
            KatalogSkill skillfac = new KatalogSkill();
            KatalogSpecies specfac = new KatalogSpecies();
            int wild;
            wild = Integer.parseInt(reader.nextLine());
            for (int i = 0 ; i < wild; i++){
                dummy = reader.nextLine();
                m = digit.matcher(dummy);
                Matcher n = str.matcher(dummy);
                n.find();
                String spcname = dummy.substring(n.start(), n.end());
                Species spcdum = specfac.getSpeciesFromIndex(specfac.getIndexOfSpecies(spcname));
                int posisi1, posisi2,level, exp, cexp;
                m.find();
                posisi1 =Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                posisi2 = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                level = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                exp = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                cexp = Integer.parseInt(dummy.substring(m.start(), m.end()));
                Engimon wilde = new Engimon(spcdum,level,exp,cexp);
                PosisiEngimon p = new PosisiEngimon(posisi1,posisi2,wilde);
                map.AddEngimon(p, map.GetElementPeta(posisi1, posisi2));
            }
            Player p1 = new Player(map);
            int coor1, coor2;
            dummy = reader.nextLine();
            m = digit.matcher(dummy);
            m.find();
            coor1 = Integer.parseInt(dummy.substring(m.start(), m.end()));
            m.find();
            coor2 = Integer.parseInt(dummy.substring(m.start(), m.end()));
            p1.setCoorP(Pair.makePair(coor1, coor2));
            boolean isEA = reader.nextLine().equals("0") ? false : true;
            if (isEA){
                dummy = reader.nextLine();
                m = digit.matcher(dummy);
                m.find();
                coor1 = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                coor2 = Integer.parseInt(dummy.substring(m.start(), m.end()));
                dummy = reader.nextLine();
                m = digit.matcher(dummy);
                Matcher n = str.matcher(dummy);
                n.find();
                String spcname = dummy.substring(n.start(), n.end());
                n.find();
                String name = dummy.substring(n.start(), n.end());
                n.find();
                String namaortu1 = dummy.substring(n.start(), n.end());
                n.find();
                String namaspecies1 = dummy.substring(n.start(), n.end());
                n.find();
                String namaortu2 = dummy.substring(n.start(), n.end());
                n.find();
                String namaspecies2 = dummy.substring(n.start(), n.end());
                m.find();
                int lvl = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                int exp = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                int ce = Integer.parseInt(dummy.substring(m.start(), m.end()));
                ArrayList<Skill> dumSkills = new ArrayList<Skill>();
                int size = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < size; i++){
                    dummy = reader.nextLine();
                    m = digit.matcher(dummy);
                    m.find();
                    int idx = m.start();
                    int mlvl = Integer.parseInt(dummy.substring(m.start(), m.end()));
                    Skill s = skillfac.getSkillFromIndex(skillfac.getIndexOfSkill(dummy.substring(0, idx-1)));
                    s.setMasteryLevel(mlvl);
                    dumSkills.add(s);
                }
                Engimon ea = new Engimon(specfac.getSpeciesFromIndex(specfac.getIndexOfSpecies(spcname)), name.equals("null") ? "" : name, namaortu1.equals("null") ? "" : namaortu1, namaspecies1.equals("null") ? "" : namaspecies1, namaortu2.equals("null") ? "" : namaortu2, namaspecies2.equals("null") ? "" : namaspecies2, dumSkills, lvl,exp,ce);
                p1.activeEngimon = ea;
                p1.isThereActiveEngimon = true;
                p1.setCoorEA(Pair.makePair(coor1, coor2));
                p1.map.SetElementPeta(coor1, coor2, 'X');
            }
            int eng = Integer.parseInt(reader.nextLine());
            for (int j = 0; j < eng; j++){
                dummy = reader.nextLine();
                m = digit.matcher(dummy);
                Matcher n = str.matcher(dummy);
                n.find();
                String spcname = dummy.substring(n.start(), n.end());
                n.find();
                String name = dummy.substring(n.start(), n.end());
                n.find();
                String namaortu1 = dummy.substring(n.start(), n.end());
                n.find();
                String namaspecies1 = dummy.substring(n.start(), n.end());
                n.find();
                String namaortu2 = dummy.substring(n.start(), n.end());
                n.find();
                String namaspecies2 = dummy.substring(n.start(), n.end());
                m.find();
                int lvl = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                int exp = Integer.parseInt(dummy.substring(m.start(), m.end()));
                m.find();
                int ce = Integer.parseInt(dummy.substring(m.start(), m.end()));
                ArrayList<Skill> dumSkills = new ArrayList<Skill>();
                int size = Integer.parseInt(reader.nextLine());
                for (int i = 0; i < size; i++){
                    dummy = reader.nextLine();
                    m = digit.matcher(dummy);
                    m.find();
                    int idx = m.start();
                    int mlvl = Integer.parseInt(dummy.substring(m.start(), m.end()));
                    Skill s = skillfac.getSkillFromIndex(skillfac.getIndexOfSkill(dummy.substring(0, idx-1)));
                    s.setMasteryLevel(mlvl);
                    dumSkills.add(s);
                }
                Engimon e = new Engimon(specfac.getSpeciesFromIndex(specfac.getIndexOfSpecies(spcname)), name.equals("null") ? "" : name, namaortu1.equals("null") ? "" : namaortu1, namaspecies1.equals("null") ? "" : namaspecies1, namaortu2.equals("null") ? "" : namaortu2, namaspecies2.equals("null") ? "" : namaspecies2, dumSkills, lvl,exp,ce);
                p1.addEngimon(e); 
            }

            int skill = Integer.parseInt(reader.nextLine());
            for (int i = 0; i < skill; i++){
                dummy = reader.nextLine();
                m = digit.matcher(dummy);
                m.find();
                int idx = m.start();
                int mlvl = Integer.parseInt(dummy.substring(m.start(), m.end()));
                Skill s = skillfac.getSkillFromIndex(skillfac.getIndexOfSkill(dummy.substring(0, idx-1)));
                s.setMasteryLevel(mlvl);
                p1.addSkillItem(s);
            }

            return p1;
        } catch (Exception e) {
            System.out.println("Failed to load");
            return null;
            //TODO: handle exception
        }
    }
}
