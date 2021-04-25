public class DriverPlayer {
    public static void main(String args[]){
        Player p = Player.load();
        if (p != null){
            p.showMap();
            p.viewListEng();
            p.viewListSkill();
        }
    }
}
