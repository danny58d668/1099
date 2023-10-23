package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.backend.Game;
import kalah.backend.GameManager;
import kalah.frontend.Display;
import kalah.frontend.DisplayManager;
public class Kalah {
	private final Game board;
	public Kalah(){
		this.board = new GameManager();
	}
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		Display display = new DisplayManager(io, board);
		for(;;){
			display.PrintBoardState();
			if(this.board.getPlayerValidMove(this.board.getPlayerTurn())==this.board.getInit()[0]){
				display.PrintQuit();
				display.PrintBoardState();
				this.board.CleanBoard();
				display.PrintScore();
				display.PrintWinner();
				break;
			}
			int userInput = display.PrintMove(this.board.getPlayerTurn());
			if(userInput == -1){
				display.PrintQuit();
				display.PrintBoardState();
				break;
			}
			if((this.board.move(this.board.getPlayerTurn(), userInput))){
				if(!this.board.getLastSeedStatus()){
					this.board.playerTurn();
				}
			}else{
				display.PrintTryAgain();
			}
		}
	}
}
