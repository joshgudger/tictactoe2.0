import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

public class ExtendedTicTacToe extends Application {

    private static final int BOARD_SIZE = 5; // 5x5 board
    private Button[][] board = new Button[BOARD_SIZE][BOARD_SIZE];
    private boolean isXTurn = true; // Track whose turn it is (X starts first)

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Add padding and spacing between buttons
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        // Set the background color of the grid
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Initialize the board with styled buttons
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button button = new Button();
                button.setPrefSize(100, 100); // Set size of each cell
                button.setStyle(getDefaultButtonStyle()); // Apply default style
                board[row][col] = button;

                // Handle button clicks
                button.setOnAction(e -> handleButtonClick(button));

                // Add hover effect
                button.setOnMouseEntered(event -> button.setStyle(getHoverButtonStyle()));
                button.setOnMouseExited(event -> {
                    if (button.getText().isEmpty()) {
                        button.setStyle(getDefaultButtonStyle());
                    }
                });

                grid.add(button, col, row);
            }
        }

        primaryStage.setScene(new Scene(grid, 550, 550)); // Adjust size for styling
        primaryStage.setTitle("Enhanced Tic Tac Toe");
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        // Ignore clicks on already occupied cells
        if (!button.getText().isEmpty()) {
            return;
        }

        // Set the text for the current turn and apply style
        button.setText(isXTurn ? "X" : "O");
        button.setStyle(isXTurn ? getXButtonStyle() : getOButtonStyle());
        isXTurn = !isXTurn; // Switch turn

        // Check for winner or draw
        if (checkWinner()) {
            String winner = isXTurn ? "O" : "X"; // The last player is the winner
            showAlert(winner + " Wins!");
            resetBoard();
        } else if (isBoardFull()) {
            showAlert("It's a draw!");
            resetBoard();
        }
    }

    private boolean checkWinner() {
        // Check rows, columns, and diagonals for 5 in a row
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col <= BOARD_SIZE - 5; col++) {
                if (checkLine(row, col, 0, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row <= BOARD_SIZE - 5; row++) {
                if (checkLine(row, col, 1, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        // Top-left to bottom-right diagonals
        for (int row = 0; row <= BOARD_SIZE - 5; row++) {
            for (int col = 0; col <= BOARD_SIZE - 5; col++) {
                if (checkLine(row, col, 1, 1)) {
                    return true;
                }
            }
        }

        // Top-right to bottom-left diagonals
        for (int row = 0; row <= BOARD_SIZE - 5; row++) {
            for (int col = 4; col < BOARD_SIZE; col++) {
                if (checkLine(row, col, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLine(int startRow, int startCol, int rowIncrement, int colIncrement) {
        String first = board[startRow][startCol].getText();
        if (first.isEmpty()) {
            return false;
        }

        for (int i = 1; i < 5; i++) { // Check the next 4 cells in the line
            int row = startRow + i * rowIncrement;
            int col = startCol + i * colIncrement;
            if (!board[row][col].getText().equals(first)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setText(""); // Clear all cells
                board[row][col].setStyle(getDefaultButtonStyle());
            }
        }
        isXTurn = true; // Reset to X's turn
    }

    // Button styles
    private String getDefaultButtonStyle() {
        return "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-font-size: 24;";
    }

    private String getHoverButtonStyle() {
        return "-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2; -fx-font-size: 24;";
    }

    private String getXButtonStyle() {
        return "-fx-background-color: #ffcccb; -fx-border-color: black; -fx-border-width: 2; -fx-font-size: 24; -fx-font-weight: bold;";
    }

    private String getOButtonStyle() {
        return "-fx-background-color: #add8e6; -fx-border-color: black; -fx-border-width: 2; -fx-font-size: 24; -fx-font-weight: bold;";
    }
}