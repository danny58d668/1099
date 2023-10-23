package kalah.frontend;
import com.qualitascorpus.testsupport.IO;
import kalah.backend.Game;
public class DisplayManager implements Display {
    private final IO io;
    private final Game board;
    public DisplayManager(IO io , Game board){
        this.io = io;
        this.board = board;
    }
    @Override
    public void  PrintBoardState(){
        PrintEnclosure(1);
        PrintPlayerHouses(2);
        PrintEnclosure(0);
        PrintPlayerHouses(1);
        PrintEnclosure(1);
    }
    @Override
    public int PrintMove(int playerTurn){
        int[] value = this.board.getInit();
        return io.readInteger("Player P"+playerTurn+"'s turn - Specify house number or 'q' to quit: ",1,value[0],-1,"q");
    }
    @Override
    public void PrintTryAgain(){
        io.println("House is empty. Move again.");
    }
    @Override
    public void PrintQuit(){
        io.println("Game over");
    }
    @Override
    public void PrintWinner(){
        if(this.board.getWinner()>0){
            io.println("Player "+this.board.getWinner()+" wins!");
        }else{
            io.println("A tie!");
        }
    }
    @Override
    public void PrintScore(){
        io.println("\tplayer 1:"+ this.board.getSeedsInStoresAmount(1));
        io.println("\tplayer 2:"+ this.board.getSeedsInStoresAmount(2));
    }
    private void PrintEnclosure(int EnclosureType){
        int[] value = this.board.getInit();
        if (EnclosureType == 1){
            io.print("+----");
            for (int i = 0; i < value[0]; i++) {
                io.print("+-------");
            }
            io.println("+----+");
        }else{
            io.print("|    |");
            for (int i = 0; i < value[0]; i++) {
                io.print("-------");
                if (i < value[0] - 1) {
                    io.print("+");
                }
            }
            io.println("|    |");
        }
    }
    public void PrintPlayerHouses(int Player){
        int[] value = this.board.getInit();
        if(Player == 2){
            io.print("| P2 ");
            for(int i=value[0]; i>0;i--){
                io.print("| "+i);
                if(this.board.getSeedsInHousesAmount(i)>9){
                    io.print("[");
                }else{
                    io.print("[ ");
                }
                io.print(this.board.getSeedsInHousesAmount(i) + "] ");
            }
        }else if(Player==1){
            if(this.board.getSeedsInStoresAmount(2)>9){
                io.print("| ");
            }else{
                io.print("|  ");
            }
            io.print(this.board.getSeedsInStoresAmount(2)+" ");
            for(int i=1; i<=value[0];i++){
                io.print("| "+(i));
                if(this.board.getSeedsInHousesAmount(i+value[0])>9){
                    io.print("[");
                }else{
                    io.print("[ ");
                }
                io.print(this.board.getSeedsInHousesAmount(i+value[0]) + "] ");
            }
        }
        if(Player == 2){
            if(this.board.getSeedsInStoresAmount(1)>9){
                io.print("| ");
            }else{
                io.print("|  ");
            }
            io.println(this.board.getSeedsInStoresAmount(1)+" |");
        }else if(Player==1){
            io.println("| P1 |");
        }
    }
}