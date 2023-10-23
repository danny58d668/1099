package kalah.backend;
public abstract class Pit {
    protected int houseValue;
    protected int player;
    public Pit(int player, int houseValue){
        this.player = player;
        this.houseValue = houseValue;
    }
    public int getHouseValue(){
        return this.houseValue;
    }
    public void addHouseValue(int amount){
        this.houseValue = this.houseValue+ amount;
    }
    public void removeHouseValue(){
        this.houseValue =0;
    }
}
