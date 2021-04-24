import java.util.*;
import java.io.*;

public class Peta{
    private final int max_spawn = 4;
    private final int def = 16;
    private int baris;
    private int kolom;
    private int nElmt;
    private Vector<Character> isiPeta;
    private Vector<Character> PetaTetap;
    private Vector<PosisiEngimon> DaftarEngimon;
    

    public Peta(){
        this.baris = def;
        this.kolom = def;
        this.nElmt = this.baris * this.kolom;
        this.isiPeta = new Vector<Character>();
        this.PetaTetap = new Vector<Character>();
        this.DaftarEngimon = new Vector<PosisiEngimon>();
    }

    public Peta(int b, int k){
        this.baris = b;
        this.kolom = k;
        this.nElmt = this.baris * this.kolom;
        this.isiPeta = new Vector<Character>();
        this.PetaTetap = new Vector<Character>();
        this.DaftarEngimon = new Vector<PosisiEngimon>();
    }

    //Getter&Setter
    public int GetBaris(){
        return this.baris;
    }
    public int GetKolom(){
        return this.kolom;
    }
    public int GetBarisObjek(int posisi){
        return (posisi / this.kolom);
    }
    public int GetKolomObjek(int posisi){
        return (posisi % this.kolom);
    }
    //pair<int, Engimon> GetDaftarEngimon(int index); //index dari vector daftar engimon
    public char GetElementPeta(int b, int k) throws CustomException {
        if(b > this.baris || b < 0 || k > this.kolom || k < 0){
            throw (new CustomException("Invalid Index Peta"));
            //return 'o'; //masih salah
        } else {
            return this.isiPeta.get((b*this.kolom)+k);
        }
    }
    public char GetElementPeta(int posisi) throws CustomException {
        if(posisi < 0 || posisi >= this.nElmt){
            throw (new CustomException("Invalid Index Peta"));
        } else {
            return this.isiPeta.get(posisi);
        }
    }
    public char GetElementPetaTetap(int b, int k) throws CustomException {
        if(b > this.baris || b < 0 || k > this.kolom || k < 0){
            throw (new CustomException("Invalid Index Peta"));
        } else {
            return this.PetaTetap.get((b*this.kolom)+k);
        }
    }
    public char GetElementPetaTetap(int posisi) throws CustomException {
        if(posisi < 0 || posisi >= this.nElmt){
            throw (new CustomException("Invalid Index Peta"));
        } else {
            return this.PetaTetap.get(posisi);
        }
    }
    public void SetElementPeta(int b, int k, char element) throws CustomException {
        if(b > this.baris || b < 0 || k > this.kolom || k < 0){
            throw (new CustomException("Invalid Index Peta"));
        } else {
            this.isiPeta.set(((b*this.kolom)+k), element);
        }
    }
    public void SetElementPeta(int index, char element) throws CustomException {
        if(index < 0 || index >= this.nElmt){
            throw (new CustomException("Invalid Index Peta"));
        } else {
            this.isiPeta.set(index, element);
        }
    }
    //untuk dapetin engimon pada posisi (b,k)
    public Engimon GetEngimonfromDaftar(int b, int k){
        Engimon e;
        for (int i = 0; i != this.DaftarEngimon.size(); i++){
            if(DaftarEngimon.get(i).getBarisPosisi() == b && DaftarEngimon.get(i).getKolomPosisi() == k){
                e = DaftarEngimon.get(i).getEngimon();
                return e;
            }
        }
        return null;
    }
    //untuk dapetin engimon dan posisinya pada posisi (b,k)
    public PosisiEngimon GetEngimonforDelete(int b, int k){
        PosisiEngimon e;
        for (int i = 0; i != this.DaftarEngimon.size(); i++){
            if(DaftarEngimon.get(i).getBarisPosisi() == b && DaftarEngimon.get(i).getKolomPosisi() == k){
                e = DaftarEngimon.get(i);
                return e;
            }
        }
        return null;
    }

    // membaca file txt peta
    public boolean BacaFile(String filename){
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(filename));
            int i = 0;
            while (inFile.hasNext()) {
                char isi = inFile.next().charAt(0);
                this.isiPeta.add(i, isi);
                this.PetaTetap.add(i, isi);
                i++;
            }
            inFile.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } 
        return true;
    }
    
    // print element atribut isi peta
    public void PrintPeta(){
        try{
            for(int i = 0; i < this.baris; i++){
                for(int j =0; j < this.kolom; j++){
                    System.out.print(GetElementPeta(i,j) + " ");
                }
                System.out.println("");
            }
    
            System.out.println("Keterangan Simbol Pada Peta : ");
            System.out.println("- : GrassLand");
            System.out.println("o : Sea");
            System.out.println("^ : Mountains");
            System.out.println("# : Tundra");
            System.out.println("P : Player");
            System.out.println("X : Active Engimon");
        } catch(Exception exc){
            //
        }
        
    }

    //ini untuk print peta yang polosan
    public void PrintPetaTetap(){
        try{
            for(int i = 0; i < this.baris; i++){
                for(int j =0; j < this.kolom; j++){
                    System.out.print(GetElementPetaTetap(i,j) + " ");
                }
                System.out.println("");
            }
    
            System.out.println("Keterangan Simbol Pada Peta : ");
            System.out.println("- : GrassLand");
            System.out.println("o : Sea");
            System.out.println("^ : Mountains");
            System.out.println("# : Tundra");
        } catch(Exception exc){
            //
        }
        

    }

    //menambahkan element ada daftar engimon dan ubah element peta
    public void AddEngimon(PosisiEngimon e, char c){ 
        try{
            this.DaftarEngimon.add(e);
            SetElementPeta(e.getBarisPosisi(), e.getKolomPosisi(), c);
        } catch(Exception exc){
            //
        }

    }

    //kalo engimonnya sudah berhasil dijinakkan hapus dari DaftarEngimon dan ubah element peta
    public void DeleteEngimon(int b, int k){
        try{
            PosisiEngimon e = GetEngimonforDelete(b,k);
            SetElementPeta(e.getBarisPosisi(), e.getKolomPosisi(), GetElementPetaTetap(e.getBarisPosisi(), e.getKolomPosisi()));
            this.DaftarEngimon.remove(e);
        } catch(Exception exc){
            //
            System.out.println(exc.getMessage()); 
        }
    }

    // membuat engimon liar yang akan di spawn
    public Engimon CreateEngimon(char e, int level){
        KatalogSpecies katalogSpecies = new KatalogSpecies();
        Random rand = new Random();
        int random = rand.nextInt(2);
        Species spesies;

        if(e == 'f' || e == 'F'){
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(0);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(8);
            }
        } else if(e == 'e' || e == 'E'){
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(2);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(10);
            }
        } else if(e == 'g' || e == 'G'){
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(3);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(11);
            }
        } else if(e == 'w' || e == 'W') {
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(1);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(9);
            }
        } else if(e == 'i' || e == 'I') {
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(4);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(12);
            }
        } else  if(e == 'l' || e == 'L') {
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(5);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(13);
            }
        } else  if(e == 's' || e == 'S') {
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(6);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(14);
            }
        } else  { //if(e == 'n' || e == 'N')
            if(random == 0){
                spesies = katalogSpecies.getSpeciesFromIndex(7);
            } else {
                spesies = katalogSpecies.getSpeciesFromIndex(15);
            }
        }

        Engimon engimon = new Engimon(spesies, level, 0, 0);
        return engimon;
    }

    // memilih level dari engimon liar yang akan di spawn, 
    // activeLevel adalah level tertinggi engimon pada inventory player saat sekarang
    public int selectlevel(int activeLevel){
        int level = 0;
        Random rand = new Random();
        if(activeLevel < 10) {
            while(level < activeLevel){
                level = rand.nextInt(13);
            }
        } else if(activeLevel < 20 && activeLevel >= 10){
            while(level < activeLevel){
                level = rand.nextInt(25);
            }
        } else {
            while(level < activeLevel){
                level = rand.nextInt(35);
            }
        }
        
        return level;
    }
    
    
    //0 : untuk yg  Grassland; 1 : untuk yg sea; 2 : untuk yg mountains, 3 : untuk tundra, 4 : untuk yg dual element
    public int CekElementEngimonRandom(char engimonTerpilih){
        if( engimonTerpilih == 'g' || engimonTerpilih == 'e' || engimonTerpilih == 'G' || engimonTerpilih == 'E'){
            return 0;
        } else if(engimonTerpilih == 'w' || engimonTerpilih == 'W'){
            return 1;
        } else if(engimonTerpilih == 'f' || engimonTerpilih == 'F'){
            return 2;
        } else if(engimonTerpilih == 'i' || engimonTerpilih == 'I'){
            return 3;
        }
        //untuk yg double element
        return 4;
    }
    
    // merandom posisi engimon liar akan muncul
    public int RandomPosisi(char engimonTerpilih, int cek){
        int posisi;
        Random random_posisi = new Random();
        if(cek == 0){ //untuk yg Grassland
            posisi = random_posisi.nextInt(this.nElmt);
            while(this.isiPeta.get(posisi) != '-'){
                posisi = random_posisi.nextInt(this.nElmt);
            }
        } else if(cek == 1){ //untuk yg Sea
            posisi = random_posisi.nextInt(this.nElmt);
            while(this.isiPeta.get(posisi) != 'o'){
                posisi = random_posisi.nextInt(this.nElmt);
            }
        } else if(cek == 2){ //untuk yg Mountains
            posisi = random_posisi.nextInt(this.nElmt);
            while(this.isiPeta.get(posisi) != '^'){
                posisi = random_posisi.nextInt(this.nElmt);
            }
        } else if(cek == 3){ //untuk yg Tundra
            posisi = random_posisi.nextInt(this.nElmt);
            while(this.isiPeta.get(posisi) != '#'){
                posisi = random_posisi.nextInt(this.nElmt);
            }
        } else { //cek == 4, untuk yang dual element
            posisi = random_posisi.nextInt(this.nElmt - 1);
            while(this.isiPeta.get(posisi) != '-' && this.isiPeta.get(posisi) != 'o' && this.isiPeta.get(posisi) != '^' && this.isiPeta.get(posisi) != '#'){
                posisi = random_posisi.nextInt(this.nElmt);
            }
        }
        return posisi;
    }

    // untuk spawn engimon liar
    // fungsi untuk mendapat level dari active engimon belum
    public void SpawnEngimon(){
        int posisi, level;
        int BanyakSpawn = max_spawn - this.DaftarEngimon.size();
        String draft = "fFgGeEwiWIlLsSnN";
        char engimonTerpilih;
        Random rand = new Random();
        int activeLevel = 3; //level dari active engimon
        for(int i = 0; i < BanyakSpawn; i++){
            engimonTerpilih = draft.charAt(rand.nextInt(16));
            posisi = RandomPosisi(engimonTerpilih, CekElementEngimonRandom(engimonTerpilih));
            //random level
            level = selectlevel(activeLevel);
            //create obj engimon
            Engimon e = CreateEngimon(engimonTerpilih, level);
            PosisiEngimon engimon = new PosisiEngimon(GetBarisObjek(posisi), GetKolomObjek(posisi), e); 
    
            //masukin ke daftar engimon dan set element di peta
            AddEngimon(engimon, engimonTerpilih);
            // cout << engimonTerpilih << endl;
            // cout << posisi << endl;
        }
    }

    // print element yang terdapat pada daftar engimon
    public void PrintDaftarEngimon(){
        for(PosisiEngimon a : this.DaftarEngimon){
            System.out.println("Baris : " + a.getBarisPosisi());
            System.out.println("Kolom : " + a.getKolomPosisi());
            System.out.println(a.getEngimon());
        }
    }

    // mengubah posisi engimon liar pada peta, ketika dijalankan
    public void UbahPosisi(int barisE, int kolomE, int pilih, int index){
        try{
            char simbol = GetElementPeta(barisE, kolomE);
            char simbolPeta = GetElementPetaTetap(barisE, kolomE);
            if(pilih == 0){ //gerak ke atas
                int newb = barisE - 1;
                SetElementPeta(newb, kolomE, simbol);
                this.DaftarEngimon.get(index).setBarisPosisi(newb); //ubah nilai baris
            } else if(pilih == 1){//gerak ke bawah
                int newb2 = barisE + 1;
                SetElementPeta(newb2, kolomE, simbol);
                this.DaftarEngimon.get(index).setBarisPosisi(newb2); //ubah nilai baris
            } else if(pilih == 2){//gerak ke kiri
                int newk = kolomE - 1;
                SetElementPeta(barisE, newk, simbol);
                this.DaftarEngimon.get(index).setKolomPosisi(newk); //ubah nilai kolom
            } else { //pilih == 3 //gerak ke kanan
                int newk2 = kolomE + 1;
                SetElementPeta(barisE, newk2, simbol);
                this.DaftarEngimon.get(index).setKolomPosisi(newk2); //ubah nilai kolom
            }
            //ubah posisi engimon yg lama jadi element petanya
            SetElementPeta(barisE, kolomE, simbolPeta);
        }  catch(Exception exc){
            System.out.println(exc.getMessage());
        }
        
    }

    // mengecek apakah tempat berpindah engimon sudah sesuai dengan jenis nya
    public int CekValid(int b, int k, char charEngimon){
        try{
            int tmp = CekElementEngimonRandom(charEngimon);
            if(tmp == 2 && GetElementPeta(b, k) == '^'){
                return 1;
            } else if(tmp == 3 && GetElementPeta(b, k) == '#'){
                return 1;
            } else if(tmp == 1 && GetElementPeta(b, k) == 'o'){
                return 1;
            } else if(tmp == 0 && GetElementPeta(b, k) == '-'){
                return 1;
            } else if(tmp == 4){
                if(GetElementPeta(b, k) != '^' && GetElementPeta(b, k) != 'o' && GetElementPeta(b, k) != '-' && GetElementPeta(b, k) != '#'){
                    return 0;
                } else {
                    return 1;
                }
            }
        } catch(Exception exc){
            System.out.println(exc.getMessage());
        }
        return 0;
    }

    //untuk ngecek apakah petak yg dituju kosong atau tidak
    public int CekValidGerak(int barisE, int kolomE, int pilih) throws CustomException {
        int valid = 0;
        try{
            char simbol = GetElementPeta(barisE, kolomE);
            if(pilih == 0){ //gerak ke atas
                int newb = barisE - 1;
                if(GetElementPeta(newb, kolomE) == 'P'){
                    throw (new CustomException("Ada Player"));
                } else {
                    valid = CekValid(newb, kolomE, simbol);
                }
                
            } else if(pilih == 1){//gerak ke bawah
                int newb2 = barisE + 1;
                if(GetElementPeta(newb2, kolomE) == 'P'){
                    throw (new CustomException("Ada Player"));
                } else {
                    valid = CekValid(newb2, kolomE, simbol);
                }
               
            } else if(pilih == 2){//gerak ke kiri
                int newk = kolomE - 1;
                if(GetElementPeta(newk, kolomE) == 'P'){
                    throw (new CustomException("Ada Player"));
                } else {
                    valid = CekValid(newk, kolomE, simbol);
                }
                
            } else { //pilih == 3 //gerak ke kanan
                int newk2 = kolomE + 1;
                if(GetElementPeta(newk2, kolomE) == 'P'){
                    throw (new CustomException("Ada Player"));
                } else {
                    valid = CekValid(newk2, kolomE, simbol);
                }
            }
           
        }  catch(Exception exc){
            System.out.println(exc.getMessage());
        }
        return valid;
    }

    // memilih gerakan engimon
    // engimon liar hanya mungkin bergerak dalam 4 arah : atas, bawah, kiri, dan kanan
    public void PilihGerak(int barisE, int kolomE, int index){
        Random rand = new Random();
        int coba = 0; 
        //random pilih gerakan antara atas(0), bawah(1), kiri(2), kanan(3)
        int pilih = rand.nextInt(4);
        try{
            int cek = CekValidGerak(barisE, kolomE, pilih); //untuk ngecek apakah dipetak yg akan dituju kosong atau tidak
            coba++;
            while(cek != 1 && coba < 4){ //coba dibatasi menjadi 4 kali, kalo coba sudah 4 artinya engimon liar tidak dapat bergerak
                pilih = (pilih + 1) % 4; //ditambahin
                cek = CekValidGerak(barisE, kolomE, pilih);
                coba++;
            }

            if(cek == 1){ //terpenuhi
                UbahPosisi(barisE, kolomE, pilih, index); //ubah nilai posisi dari engimon dan ubah element di peta
            }
        } catch(Exception exc){
            // System.out.println(exc.getMessage());
        }
        
    }

    // prosedur untuk menggerakkan semua engimon liar yang terdapat pada peta dan daftar engimon liar
    public void GerakinEngimonLiar(){
        int i = 0;
        for(PosisiEngimon e : this.DaftarEngimon){
            PilihGerak(e.getBarisPosisi(), e.getKolomPosisi(), i);
            i++;
        }
    }

    // untuk mengecek apakah masih ada engimon liar pada daftar engimon liar
    // yang memiliki status false
    public boolean Notclear(){
        for(int i = 0; i < this.DaftarEngimon.size(); i++){
            if(this.DaftarEngimon.get(i).getEngimon().getStatus() == false){
                return true;
            }
        }
        return false;
    }

    //delete engimon liar yang mati
    // hapus dari daftar engimon liar dan hapus dari peta
    public void deleteDeadEngimon(){
        //System.out.println(this.DaftarEngimon.size());
        while(Notclear()){
            for(int i = 0; i < this.DaftarEngimon.size(); i++){
                // hapus engimon yang udah mati dari peta dan daftar engimon
                if(this.DaftarEngimon.get(i).getEngimon().getStatus() == false){
                    int baris = this.DaftarEngimon.get(i).getBarisPosisi();
                    int kolom = this.DaftarEngimon.get(i).getKolomPosisi();
                    DeleteEngimon(baris, kolom);
                }
            }
        } 
    }

    // prosedur untuk menambahankan Exp dari engimon liar
    public void addExpEngimonLiar(){
        for(PosisiEngimon e : this.DaftarEngimon){
            e.getEngimon().addEXP(100); // ditambahkan exp 100 (bisa langsung naik level)
        }
        // untuk menghapus engimon yang telah mati
        deleteDeadEngimon();
    }

    

    public static void main(String[] args) {
        Peta p = new Peta(16,16);
        p.BacaFile("map.txt");
        //p.PrintPeta();
        // System.out.println(p.GetElementPeta(0, 7));
        // System.out.println(p.GetElementPeta(7, 0));
        // try{
        //     System.out.println(p.GetElementPeta(7, 0));
        //     System.out.println(p.GetElementPeta(20, 1));
        //     System.out.println(p.GetElementPeta(0, 7));
        // } catch(Exception exc){
        //     //
        //     System.out.println(exc.getMessage());
        // }
        
        p.SpawnEngimon();
        p.PrintPeta();
        //p.PrintDaftarEngimon();

        //test delete
        // KatalogSpecies katalogSpecies = new KatalogSpecies();
        // Engimon engimon = new Engimon(katalogSpecies.getSpeciesFromIndex(15), 10, 0, 0);
        // PosisiEngimon e = new PosisiEngimon(1,1,engimon);
        // p.AddEngimon(e, 'T');
        // p.PrintDaftarEngimon();
        // p.PrintPeta();
        // p.DeleteEngimon(1, 1);
        // p.PrintDaftarEngimon();
        // p.PrintPeta();

        //test add exp
        p.addExpEngimonLiar();
        //p.PrintDaftarEngimon();
        //test add exp jika engimon mati
        for(int i = 0; i<100; i++){
            p.addExpEngimonLiar();
        }
        p.PrintPeta();
        p.PrintDaftarEngimon();

        // p.GerakinEngimonLiar();
        // p.PrintPeta();
        // p.PrintDaftarEngimon();
    }
}