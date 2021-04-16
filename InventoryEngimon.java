public class InventoryEngimon extends Inventory<Engimon> {
    
    
    public void addElement(Engimon other) throws CustomException{
        if (InventoryBase.banyakItem != InventoryBase.maksItem ){
            this.storage.add(other);
            InventoryBase.banyakItem++;
            this.storage.sort((Engimon e1, Engimon e2) -> e1.compareTo(e2));
        }
        
        else throw (new CustomException("Inventory penuh"));
    }
}
