import java.util.Scanner;
import java.util.Random;

class Main {
  public static void main(String[] args) {
    System.out.println("Podaj liczbe calkowita wieksza od 1:");
    Scanner scanner = new Scanner(System.in);
    int nx = 0;
    boolean isIncorrect = true;
    while(isIncorrect){
      try{
        isIncorrect = false;
        if(scanner.hasNextInt()){
          nx = scanner.nextInt();
          if(nx < 2){
            throw new OptionNotRecognizedException("Za mala wartosc nx: " + nx + "!");
          }
        }else{
          throw new OptionNotRecognizedException("Podaj liczbe calkowita!");
        }
      } catch(OptionNotRecognizedException e){
          System.out.println("Blad: " + e.getMessage());
          scanner.nextLine();
          isIncorrect = true;
      }
    }
    scanner.nextLine();
    Game game = new Game(nx);
    game.drawBoard();
    boolean isGame = true;
    String step = "";
    while(isGame){
      try{
        if(scanner.hasNext("[qwasd]")){
          step = scanner.next();
          try{  
            if(step.charAt(0) == 'a'){
              game.step(-1,0);
            }else if(step.charAt(0) == 'w'){
              game.step(0,1);
            }else if(step.charAt(0) == 's'){
              game.step(0,-1);
            }else if(step.charAt(0) == 'd'){
              game.step(1,0);
            }else if(step.charAt(0) == 'q'){
              isGame = false;  
            }
          } catch(WallException e){
            System.out.println(e.getMessage());
            game.drawBoard();
          }
          scanner.nextLine();
        }else{
          throw new OptionNotRecognizedException("Prosze wprowadzic znak z grupy: {\'a\', \'w\', \'s\', \'d\', \'q\''}.");
        }
      } catch(OptionNotRecognizedException e){
        System.out.println("OptionNotRecognizedException: " + e.getMessage());
        scanner.nextLine();
      }
    }

    scanner.close();
  }
}

class OptionNotRecognizedException extends Exception{
  public OptionNotRecognizedException(String info) { super(info); }
}

class WallException extends Exception{
  public WallException(String info) { super(info); }
}

class Game{
  private final char[][] board;
  private final int size;
  private int currXPos;
  private int currYPos;

  public Game(int size){
    this.size = size;
    board = new char[size][size];

    Random random = new Random();
    double temp;    
    for(int x = 0; x < this.size; ++x){
      for(int y = 0; y < this.size; ++y){
          temp = random.nextDouble();
          if(temp > 0.3){
            this.board[x][y] = ' ';
          }else{
            this.board[x][y] = 'X';
          }
      }
    }

    int minX = this.size;
    int minY = this.size;
    for(int x = 0; x < this.size; ++x){
      for(int y = 0; y < this.size; ++y){
        if(this.board[x][y] == ' '){
          if(x < minX){
            minX = x;
          }
          if(y < minY){
            minY = y;
          }
        }
      }
    }
    this.board[minX][minY] = 'o';
    this.currXPos = minX;
    this.currYPos = minY;
  }

  public void drawBoard(){
    System.out.print(" ");
    for(int i = 0; i < this.size; ++i){
      System.out.print("-");
    }
    System.out.println();
    for(int x = this.size-1; x >= 0; --x){
      System.out.print("|");
      for(int y = 0; y < this.size; ++y){
        System.out.print(this.board[x][y]);
      }
      System.out.print("|");
      System.out.println();
    }
    System.out.print(" ");
    for(int i = 0; i < this.size; ++i){
      System.out.print("-");
    }
    System.out.println();
  }

  public void step(int yCoord, int xCoord) throws WallException{
    if((this.currXPos + xCoord == this.size) || (this.currXPos + xCoord < 0) || (this.currYPos + yCoord == this.size) || (this.currYPos + yCoord < 0)){
      throw new WallException("WallException: wyjscie poza plansze!");
    }else if(this.board[this.currXPos+xCoord][this.currYPos+yCoord] == 'X'){
      throw new WallException("WallException: wejscie na sciane!");
    }else{
      this.board[this.currXPos][this.currYPos] = ' ';
      this.currXPos += xCoord;
      this.currYPos += yCoord;
      this.board[this.currXPos][this.currYPos] = 'o';
    }
    drawBoard();
  } 
}
