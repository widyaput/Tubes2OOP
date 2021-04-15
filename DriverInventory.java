public class DriverInventory {
    public static void main(String arg[]){
        Inventory<Integer> coba = new InventorySkill();
        try {
            coba.addElement(1);
            coba.addElement(1);
            coba.addElement(2);
            coba.showStorage();
            coba.addElement(3);            
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
