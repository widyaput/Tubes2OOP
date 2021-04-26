public class DriverPlayer {
    public static void main(String args[]){
        Player p = Player.load();
        if (p != null){
            p.viewListEng();
            try {
                p.addEngimon(p.breeding(p.getListEng().getElement(0), p .getListEng().getElement(1)));
                p.viewListEng();
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: handle exception
            }
            // p.showMap();
            // p.viewListEng();
            p.viewListSkill();
        }
    }
}
