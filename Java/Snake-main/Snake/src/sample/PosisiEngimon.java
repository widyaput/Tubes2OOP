package sample;

public class PosisiEngimon {
    private int barisEngimon;
    private int kolomEngimon;
    private Engimon jenisEngimon;

    public PosisiEngimon(int b, int k, Engimon e){
        this.barisEngimon = b;
        this.kolomEngimon = k;
        this.jenisEngimon = e;
    }

    public int getBarisPosisi(){
        return this.barisEngimon;
    }

    public int getKolomPosisi(){
        return this.kolomEngimon;
    }

    public Engimon getEngimon(){
        return this.jenisEngimon;
    }

    public void setBarisPosisi(int b){
        this.barisEngimon = b;
    }

    public void setKolomPosisi(int k){
        this.kolomEngimon = k;
    }

    public void setEngimon(Engimon e){
        this.jenisEngimon = e;
    }
}
