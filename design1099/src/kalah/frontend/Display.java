package kalah.frontend;
public interface Display {
    void PrintBoardState();
    int PrintMove(int playerTurn);
    void PrintTryAgain();
    void PrintQuit();
    void PrintScore();
    void PrintWinner();
}