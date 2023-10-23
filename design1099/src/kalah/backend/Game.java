package kalah.backend;
public interface Game {
    boolean move(int player, int move);
    int[] getInit();
    void playerTurn();
    int getPlayerTurn();
    int getPlayerValidMove(int player);
    int getSeedsInHousesAmount(int house);
    int getSeedsInStoresAmount(int store);
    boolean getLastSeedStatus();
    void CleanBoard();
    int getWinner();
}
