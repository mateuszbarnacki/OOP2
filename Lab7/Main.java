import java.util.Random;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    if(args.length < 3){
            System.out.println("NIE MOZNA UTWORZYC PLANSZY!");
            return;
        }
        Maze maze = new Maze(Double.parseDouble(args[2]), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        Option[] options = Option.values();
        for(Option option : options){
            System.out.println(option);
        }
        Scanner scanner = new Scanner(System.in);
        char z;
        boolean isGame = true;
        maze.drawBoard();
        boolean isCorrect = false;
        Option temp = null;
        while(isGame){
            z = scanner.next().charAt(0);
            for(Option option : options){
                if(option.getSign() == z){
                    isCorrect = true;
                    temp = option;
                }
            }
            if(isCorrect){
                if(temp.getSign() == 'q'){
                    System.out.println("KONIEC GRY!");
                    isGame = false;
                }else if(temp.getSign() == 'r'){
                    maze = new Maze(Double.parseDouble(args[2]), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                    maze.drawBoard();
                }else{
                    maze.step(temp.getDirection(), (board, x, y, direction) -> {
                        if ((x + direction.getHorizontal() >= board.length) || (y + direction.getVertical() >= board[0].length)) {
                            return false;
                        }else if(board[x+direction.getHorizontal()][y+direction.getVertical()] == 'X'){
                            return false;
                        }
                        return true;
                    });
                    if(maze.isEnd()){
                        isGame = false;
                    }
                    isCorrect = false;
                }
            }else{
                System.out.println("BLEDNY ZNAK!");
            }
        }
        scanner.close();
  }
}

enum Direction{
  LEFT(-1, 0),
  RIGHT(1, 0),
  UP(0, 1),
  DOWN(0, -1);

  private int horizontalCoord;
  private int verticalCoord;

  Direction(int first, int second){
    this.horizontalCoord = first;
    this.verticalCoord = second;
  }

  public int getHorizontal(){
    return this.horizontalCoord;
  }

  public int getVertical(){
    return this.verticalCoord;
  }

  @Override
  public String toString(){
    return "[" + this.horizontalCoord + ", " + this.verticalCoord + "]";
  }
}
  enum Option{
    RESET('r', "Reset planszy", null),
    LEFT('a', "Przesun w lewo", Direction.LEFT),
    RIGHT('d', "Przesun w prawo", Direction.RIGHT),
    UP('w', "Przesun w gore", Direction.UP),
    DOWN('s', "Przesun w dol", Direction.DOWN),
    EXIT('q', "Zakonczenie gry", null);

    private char sign;
    private String description;
    private Direction direction;

    Option(char sign, String description, Direction direction){
      this.sign = sign;
      this.description = description;
      this.direction = direction;
    }

    public char getSign(){
      return this.sign;
    }

    public String getDescription(){
      return this.description;
    }

    public Direction getDirection(){
      return this.direction;
    }

    @Override
    public String toString(){
      return "\'" + this.sign + "\' ==> opcja " + super.toString() + ", opis: " + this.description + (this.direction != null ? ", wektor przesuniecia: " + this.direction : " "); 
    }
  }

  interface CheckStep {
    boolean test(char[][] board, int i0, int j0, Direction dir);
  }

  class Maze{
    private char[][] board;
    private int xSize;
    private int ySize;
    private double probability;
    private int currXPosition;
    private int currYPosition;

    public Maze(double probability, int... args){
      this.xSize = args[0];
      this.ySize = args[1];
      this.probability = probability;
      board = new char[this.xSize][this.ySize];
      for(int i = 0; i < this.xSize; ++i){
        for(int j = 0; j < this.ySize; ++j){
          if((i == 0) || (i==this.xSize-1) || (j == 0) || (j == this.ySize-1)){
            this.board[i][j] = 'X';
          }
          if((i == this.xSize/2) && (j == (this.ySize-1))){
            this.board[i][j] = ' ';
          }
        }
      }
      Random random = new Random();
      double temp;
      int xMin, yMin;
      xMin = this.xSize+1;
      yMin = this.ySize+1; 
      for(int i = 1; i < this.xSize-1; ++i){
        for(int j = 1; j < this.ySize-1; ++j){
            temp = random.nextDouble();
            if(temp <= this.probability){
              this.board[i][j] = 'X';
            }else{
              this.board[i][j] = ' ';
            }
            if(j < yMin) yMin = j;
            if(i < xMin) xMin = i;
        }
      }
      this.board[xMin][yMin] = 'o';
      this.currXPosition = xMin;
      this.currYPosition = yMin;
    }

  public void step(Direction dir, CheckStep check){
    if(check.test(this.board, this.currXPosition, this.currYPosition, dir)){
      this.board[this.currXPosition][this.currYPosition] = ' ';
      this.currXPosition += dir.getHorizontal();
      this.currYPosition += dir.getVertical();
      this.board[this.currXPosition][this.currYPosition] = 'o';
      drawBoard();
    }else{
      System.out.println("NIE UDALO SIE WYKONAC TAKIEGO RUCHU.");
    }
    if(isEnd()){
      System.out.println("GRATULACJE, WYGRALES!");
    }
  }

  public void drawBoard(){
    for(int j = this.ySize-1; j >= 0; --j){
      for(int i = 0; i < this.xSize; ++i){
        System.out.print(this.board[i][j]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public boolean isEnd(){
    boolean endGame = false;
    if((this.currXPosition == this.xSize/2) && (this.currYPosition == (this.ySize-1))){
      endGame = true;
    }
    return endGame;
  }

  }