import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");

        String error_message = "Invalid coordinates. Choose different coordinates.";

        Scanner input = new Scanner(System.in);

        int[] players = {1,2};

        char[][] player1_ship_board = {{'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'}};
        
        char[][] player1_target_board = {{'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'}};

        char[][] player2_ship_board = {{'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'}};
        
        char[][] player2_target_board = {{'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'},
                                    {'-','-','-','-','-'}};

        // Allow each player to add 5 ships
        for (int player: players) {
            // Players will enter 5 ships
            if (player == 1) {
                player1_ship_board = addShips(player, input, player1_ship_board, error_message);
            }
            else {
                player2_ship_board = addShips(player, input, player2_ship_board, error_message);
            }
        }

        //Allow players to fire upon other player's board
        int player1_hits = 0;
        int player2_hits = 0;
        int player_turn;
        int turn_count = 0;
        int[] turn_result = new int[2];
        do {
            if (turn_count % 2 == 0) {
                player_turn = 1;
                turn_result = playerTurns(player_turn, turn_count, input, error_message, player2_ship_board, player1_target_board);
                player1_hits += turn_result[0];
                turn_count = turn_result[1];
            }
            else if (turn_count % 2 == 1) {
                player_turn = 2;
                turn_result = playerTurns(player_turn, turn_count, input, error_message, player1_ship_board, player2_target_board);
                player2_hits += turn_result[0];
                turn_count = turn_result[1];
            }   
        } while (player1_hits < 5 && player2_hits < 5);

        // Delcare winner
        if (player1_hits >= 5) {
            System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
            System.out.println("Final boards:");
            printBattleShip(player1_ship_board);
            System.out.println("");
            printBattleShip(player2_ship_board);

        }
        else {
            System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
            System.out.println("Final boards:");
            printBattleShip(player1_ship_board);
            System.out.println("");
            printBattleShip(player2_ship_board);
        }
    }
    
    private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}

    private static char[][] addShips(int player, Scanner input, char[][] ship_board, String error_message) {
        System.out.println("PLAYER " + player + ", ENTER YOUR SHIPS' COORDINATES.");
            // Players will enter 5 ships
            int location = 1;
            while (location <= 5) {
                System.out.println("Enter ship " + location + " location:");
                int error = 0;
                try {
                    int column = input.nextInt();
                    int row = input.nextInt();
                    if (ship_board[column][row] == '@') {
                            System.out.println("You already have a ship there. Choose different coordinates.");
                            error = 1;
                    }
                    else {
                            ship_board[column][row] = '@';
                    }

                } 
                catch (Exception e) {
                    System.out.println(error_message);
                    input.nextLine();
                    error = 1;
                    continue;
                }
                if (error != 1) {
                    location += 1;
                }
            }   
                printBattleShip(ship_board);

                int num_of_lines = 1;
                while (num_of_lines < 100) {
                    System.out.println();
                    num_of_lines += 1;
                }

        return ship_board;
    }

    private static int[] playerTurns(int player_turn, int turn_count, Scanner input, String error_message, char[][] ship_board, char[][] target_board) {
        System.out.println("Player " + player_turn + ", enter hit row/column: ");
        int[] return_array = {0, 0};
        int hits = 0;
            try {
                int column = input.nextInt();
                int row = input.nextInt();
                if (ship_board[column][row] == '@') {
                    System.out.println("PLAYER " + player_turn + " HIT PLAYER " + ((player_turn % 2)+1) + "'s SHIP!");
                    ship_board[column][row] = 'X';
                    target_board[column][row] = 'X';
                    printBattleShip(target_board);
                    return_array[0] = hits+1;
                    return_array[1] = turn_count+1;
                    return return_array;
                }
                else if (ship_board[column][row] == '-') {
                    System.out.println("PLAYER " + player_turn + " MISSED!");
                    ship_board[column][row] = 'O';
                    target_board[column][row] = 'O';
                    printBattleShip(target_board);
                    return_array[0] = hits;
                    return_array[1] = turn_count+1;
                    return return_array;
                }
                else {
                    System.out.println("You already fired on this spot. Choose different coordinates.");
                    printBattleShip(target_board);
                    return_array[0] = hits;
                    return_array[1] = turn_count;
                    return return_array;
                }

                } 
                catch (Exception e) {
                    System.out.println(error_message);
                    input.nextLine();
                    return_array[0] = hits;
                    return_array[1] = turn_count;
                    return return_array;
                }
                
    }
}
