package kalah.backend;
public class GameManager implements Game {
    private final Pit[] board;
    private final Pit[] storeBoard;
    private int PlayerStart = 1;
    private int PlayerTurn = PlayerStart;
    private final int InitialHouses =6;
    private final int InitialStoresPerPlayer =1;
    private final int InitialAmountInHouses  =4;
    private final int InitialAmountInStores = 0;
    private final int NumberOfPlayers =2;
    private final int PlaceAmount =1;
    private boolean LastSeedSown =false;
    public GameManager(){
        board = new Pit[InitialHouses *NumberOfPlayers + 1];
        storeBoard = new Pit[NumberOfPlayers + 1];
        InitSeedsandHouses();
    }
    @Override
    public boolean move(int player, int move){
        boolean FirstInstance = false;
        int CaptureHouse = 0;
        if(player ==1){
            move =move + InitialHouses;
        }
        int  currentLocation = move;
        if (verifyValidInput(move)){
            int Steps = getSeedsInHousesAmount(move);
            this.board[move].removeHouseValue();
            for(int i = Steps; i>0;i--){
                currentLocation++;
                if((currentLocation>InitialHouses * NumberOfPlayers)&&(player==1)){
                    storeBoard[player].addHouseValue(PlaceAmount);
                    currentLocation =0;
                    LastSeedSown=true;
                }else if((currentLocation == InitialHouses+1)&&(!FirstInstance)&&(player ==2)){
                    storeBoard[player].addHouseValue(PlaceAmount);
                    FirstInstance = true;
                    currentLocation--;
                    LastSeedSown=true;
                }else if(currentLocation > InitialHouses * NumberOfPlayers){
                    currentLocation =0;
                }else{
                    if(getSeedsInHousesAmount(currentLocation)==0){
                        CaptureHouse = currentLocation;
                    }
                    board[currentLocation].addHouseValue(PlaceAmount);
                    LastSeedSown=false;
                    if((player ==2)&&(currentLocation >= InitialHouses * NumberOfPlayers)){
                        currentLocation =0;
                    }
                }
            }
            if(!LastSeedSown){
                if((CaptureHouse == currentLocation)){
                    if((getPlayerTurn()==1)&& (CaptureHouse >6)){
                        int HouseCaptured = InitialHouses-(CaptureHouse-InitialHouses)+1;
                        CaptureHouse(HouseCaptured,currentLocation,player);
                    }else if((getPlayerTurn()==2)&& (CaptureHouse <= 6)){
                        int HouseCaptured = ((InitialHouses-CaptureHouse)+1)+InitialHouses;
                        CaptureHouse(HouseCaptured,currentLocation,player);
                    }
                }
            }
        }else {
            return false;
        }
        return true;
    }
    @Override
    public int[] getInit(){
        int[] InitArray = new int[4];
        InitArray[0] = InitialHouses;
        InitArray[1] = InitialStoresPerPlayer;
        InitArray[2] = InitialAmountInHouses;
        InitArray[3] = InitialAmountInStores;
        return InitArray;
    }
    @Override
    public int getSeedsInHousesAmount(int house){
        return board[house].getHouseValue();
    }
    @Override
    public int getSeedsInStoresAmount(int store){
        return storeBoard[store].getHouseValue();
    }
    @Override
    public void playerTurn(){
        for(int i =1; i<=NumberOfPlayers;i++){
            if(PlayerTurn == i){
                PlayerTurn++;
                break;
            }else if(PlayerTurn==NumberOfPlayers){
                PlayerTurn=1;
                break;
            }
        }
    }
    @Override
    public int getPlayerTurn(){
        return PlayerTurn;
    }
    @Override
    public int getPlayerValidMove(int player){
        int max = InitialHouses;
        int i=0;
        int HousesEmpty=0;
        if(player ==2){
            i=1;
        }else if (player ==1){
            max = InitialHouses * NumberOfPlayers;
            i=InitialHouses+1;
        }
        for(int j = i; j<= max; j++){
            int checkEmpty=getSeedsInHousesAmount(j);
            if(checkEmpty == 0){
                HousesEmpty++;
            }
        }
        return HousesEmpty;
    }
    @Override
    public boolean getLastSeedStatus(){
        return LastSeedSown;
    }
    @Override
    public void CleanBoard(){
        for(int player = 1; player<=NumberOfPlayers ; player++){
            for(int j =1; j<InitialHouses * NumberOfPlayers + 1;j++){
                if((player ==2)&&(j<InitialHouses+1)){
                    this.storeBoard[player].addHouseValue(this.board[j].getHouseValue());
                }else if((player==1)&&(j>InitialHouses)){
                    this.storeBoard[player].addHouseValue(this.board[j].getHouseValue());
                }
            }
        }
    }
    @Override
    public int getWinner(){
        int max = this.storeBoard[1].getHouseValue();
        int defaultWinner = 1;
        for(int player = 2; player<=NumberOfPlayers ; player++){
            if(max<this.storeBoard[player].getHouseValue()){
                max = this.storeBoard[player].getHouseValue();
                defaultWinner = player;
            }else if(max ==this.storeBoard[player].getHouseValue()){
                defaultWinner = 0;
            }
        }
        return defaultWinner;
    }
    private void  InitSeedsandHouses(){
        for(int i = 1; i<InitialHouses * NumberOfPlayers + 1 ; i++){
            this.board[i]=(new House(i , InitialAmountInHouses));
        }
        for(int i = 1; i<=NumberOfPlayers * InitialStoresPerPlayer; i++){
            this.storeBoard[i]=(new Store(i , InitialAmountInStores));
        }
    }
    private boolean verifyValidInput(int House){
        return board[House].getHouseValue() != 0;
    }
    private void CaptureHouse(int HouseCaptured, int currentLocation, int player){
        if(getSeedsInHousesAmount(HouseCaptured)>0){
            int Steps = getSeedsInHousesAmount(HouseCaptured)+getSeedsInHousesAmount(currentLocation);
            storeBoard[player].addHouseValue(Steps);
            this.board[HouseCaptured].removeHouseValue();
            this.board[currentLocation].removeHouseValue();
        }
    }
}
