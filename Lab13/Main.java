import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        long begin, end;
        int n, m, p;
        if(args.length < 3){
            System.out.println("Wrong number of arguments!");
            return;
        }
        try{
            if(args[0].matches("^[0-9]*$")) n = Integer.parseInt(args[0]); else throw new Exception("Invalid argumetn!");
            if(args[1].matches("^[0-9]*$")) m = Integer.parseInt(args[1]); else throw new Exception("Invalid argumetn!");
            if(args[2].matches("^[0-9]*$")) p = Integer.parseInt(args[2]); else throw new Exception("Invalid argumetn!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
	    Matrix a = new Matrix(n, m);
	    Matrix b = new Matrix(m, p);
	    try{
	        // Czas mierzony dla siebie w celach testowych
	        begin = System.nanoTime();
            Matrix c = a.multiply(b);
            end = System.nanoTime();
            System.out.println("Multiply: " + ((end-begin)/Math.pow(10,9)) + "s");
            begin = System.nanoTime();
            Matrix d = a.multiplyConcurrently(b);
            end = System.nanoTime();
            System.out.println("Multiply concurrently: " + ((end-begin)/Math.pow(10,9)) + "s");
            System.out.println("Is equal: " + c.equals(d));
        }catch(ArithmeticException e){
            System.out.println("Couldn't multiply the matrix: " + e.getMessage());
            e.printStackTrace();
        }

    }
}

class Matrix{
    private final int rows;
    private final int columns;
    private final double[][] matrix;

    public Matrix(int... data){
        this.rows = data[0];
        this.columns = data[1];
        this.matrix = new double[this.rows][this.columns];
        Random random = new Random();
        for(int i = 0; i < this.rows; ++i){
            for(int j = 0; j < this.columns; ++j){
                this.matrix[i][j] = random.nextDouble() * 10.0;
            }
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }

    public void setFieldValue(int i, int j, double value){
        this.matrix[i][j] = value;
    }

    public double getField(int i, int j){
        return this.matrix[i][j];
    }

    public double[] getColumn(int idx){
        double[] tempArr = new double[this.rows];
        int tempCounter = 0;
        for(int i = 0; i < this.rows; ++i){
            tempArr[tempCounter] = this.matrix[i][idx];
            tempCounter++;
        }
        return tempArr;
    }

    public Matrix multiply(Matrix obj){
        if(this.columns != obj.getRows()){
            throw new ArithmeticException("Wrong dimension: " + this.columns + " != " + obj.getRows());
        }
        Matrix result = new Matrix(this.rows, obj.getColumns());
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < obj.getColumns(); ++j){
                result.setFieldValue(i, j, calculateSum(this.matrix[i], obj.getColumn(j)));
            }
        }

        return result;
    }

    public Matrix multiplyConcurrently(Matrix obj){
        if(this.columns != obj.getRows()){
            throw new ArithmeticException("Wrong dimension: " + this.columns + " != " + obj.getRows());
        }
        Matrix result = new Matrix(this.rows, obj.getColumns());
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < obj.getColumns(); ++j){
                Thread thread = new SingleCount(i, j, result, this.matrix[i], obj.getColumn(j));
                thread.start();
                threads.add(thread);
            }
        }
        try {
            for (Thread t : threads) {
                t.join();
            }
        }catch (InterruptedException e){
            System.out.println("InterruptedException: " + e.getMessage());
        }
        return result;
    }

    private double calculateSum(double[] tempRow, double[] tempColumn){
        double sum = 0.;
        for(int i = 0; i < tempRow.length; ++i){
            sum += (tempRow[i] * tempColumn[i]);
        }
        return sum;
    }

    @Override
    public boolean equals(Object obj){
        if(obj.getClass() == this.getClass()){
            boolean isEqual = true;
            Matrix temp = (Matrix) obj;
            for(int i = 0; i < this.rows; ++i){
                for(int j = 0; j < this.columns; ++j){
                    if(temp.getField(i, j) != this.matrix[i][j]) isEqual = false;
                }
            }

            return isEqual;
        }
        return false;
    }

    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i < this.rows; ++i){
            for(int j = 0; j < this.columns; ++j){
                str += (this.matrix[i][j] + " ");
            }
            str += "\n";
        }
        return str;
    }
}

class SingleCount extends Thread{
    private final int iIdx;
    private final int jIdx;
    private final Matrix obj;
    private final double[] row;
    private final double[] column;

    public SingleCount(int i, int j, Matrix matrix, double[] row, double[] column){
        this.obj = matrix;
        this.iIdx = i;
        this.jIdx = j;
        this.row = row;
        this.column = column;
    }

    private void calculateFieldValue(){
        double sum = 0.;
        for(int i = 0; i < this.row.length; ++i){
            sum += (this.row[i] * this.column[i]);
        }
        this.obj.setFieldValue(this.iIdx, this.jIdx, sum);
    }

    @Override
    public void run() {
        calculateFieldValue();
    }
}

