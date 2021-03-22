import java.lang.Math;

class Main {
  public static void main(String[] args) {
    test();
    calculate(1.0, 2.5, 3.0);
    calculate(1.0, -2.0, 1.0);
    calculate(1.0, 5.0, 6.0);
  }

  public static void calculate(double a, double b, double c){
    double delta = b * b - 4 * a * c;
    double result = (-1.0 * b) / (2 * a);
    if(delta != 0.0){
      Complex sqrtDelta = Complex.sqrt(delta);
      sqrtDelta = Complex.divide(sqrtDelta, new Complex(2 * a));
      if(delta < 0.0){
        Complex firstResult = new Complex(result, sqrtDelta.getIm());
        Complex secondResult = new Complex(result, (-1.0)* sqrtDelta.getIm());
        System.out.println("x1 = " + firstResult + ", x2 = " + secondResult);   
      }else{
        Complex firstResult = new Complex(result + sqrtDelta.getRe(), 0.0);
        Complex secondResult = new Complex(result - sqrtDelta.getRe() , 0.0);
        System.out.println("x1 = " + firstResult + ", x2 = " + secondResult);
      }
    } else {
      System.out.println("x = " + result);
    }
  }

  public static void test() {
        // Wykorzystanie konstruktorów:
        Complex c1 = new Complex(2.5, 13.1);
        Complex c2 = new Complex(-8.5, -0.9);
        System.out.println(c1); // 2.5 + 13.1i
        System.out.println(c2); // -8.5 - 0.9i
        System.out.println(new Complex(4.5)); // 4.5
        System.out.println(new Complex()); // 0.0
        System.out.println(new Complex(0, 5.1)); // 5.1i
        System.out.println();

        // Stałe typu Complex:
        System.out.println(Complex.I); // 1.0i
        System.out.println(Complex.ZERO); // 0.0
        System.out.println(Complex.ONE); // 1.0
        System.out.println();

        // Wykorzystanie metod zwracających wynik obliczeń:
        System.out.println("Re(c1) = " + c1.getRe()); // Re(c1) = 2.5
        System.out.println("Im(c1) = " + c1.getIm()); // Im(c1) = 13.1
        System.out.println("c1 + c2 = " + Complex.add(c1, c2)); // c1 + c2 = -6.0 + 12.2i
        System.out.println("c1 - c2 = " + Complex.subtract(c1, c2)); // c1 - c2 = 11.0 + 14.0i
        System.out.println("c1 * c2 = " + Complex.multiply(c1, c2)); // c1 * c2 = -9.46 - 113.6i
        System.out.println("c1 * 15.1 = " + Complex.multiply(c1, 15.1)); // c1 * 15.1 = 37.75 + 197.81i
        System.out.println("c1 / c2 = " + Complex.divide(c1, c2)); // c1 / c2 = -0.4522310429783739 - 1.4932931836846426i
        System.out.println("|c1| = " + c1.mod()); // |c1| = 13.336416310238668
        System.out.println("sqrt(243.36) = " + Complex.sqrt(243.36)); // sqrt(243.36) = 15.6
        System.out.println("sqrt(-243.36) = " + Complex.sqrt(-243.36)); // sqrt(-243.36) = 15.6i
        Complex c3 = new Complex(2.5, 13.1);
        System.out.println(c1.equals(c2)); // false
        System.out.println(c1.equals(c3)); // true
        // Poniższe wywołanie - dla chętnych :)
        System.out.println(c1.equals("test ze zlym obiektem")); // false
        System.out.println();

        // Metoda zamieniająca liczbę na jej sprzężenie:
        c1.conjugate();
        System.out.println("c1* = " + c1); // c1* = 2.5 - 13.1i

        // Metoda zamieniająca liczbę na przeciwną:
        c1.opposite();
        System.out.println("-c1 = " + c1); // -c1 = -2.5 + 13.1i
    }
}

class Complex{
  private double real;
  private double imagine;

  public static final String I = "1.0i";
  public static final String ZERO = "0.0";
  public static final String ONE = "1.0";

  public Complex(double re, double im){
      this.real = re;
      this.imagine = im;
  }

  public Complex(double re){
      this.real = re;
      this.imagine = 0.0;
  }

  public Complex(){
    this.real = 0.0;
    this.imagine = 0.0;
  }

  public double getRe(){
    return this.real;
  }

  public double getIm(){
    return this.imagine;
  }

  public static Complex add(Complex first, Complex second){
    Complex complex = new Complex(first.getRe() + second.getRe(), first.getIm() + second.getIm());
    return complex;
  }

  public static Complex subtract(Complex first, Complex second){
    Complex complex = new Complex(first.getRe() - second.getRe(), first.getIm() - second.getIm());
    return complex;
  }

  public static Complex multiply(Complex first, Complex second){
    Complex complex = new Complex(first.getRe()*second.getRe()-first.getIm()*second.getIm(), first.getIm()*second.getRe()+first.getRe()*second.getIm());
    return complex;
  }

  public static Complex multiply(Complex first, double number){
    Complex complex = new Complex(first.getRe() * number, first.getIm() * number);
    return complex;
  }

  public static Complex divide(Complex first, Complex second){
    double number = second.getRe() * second.getRe() + second.getIm() * second.getIm();
    Complex complex = new Complex((first.getRe()*second.getRe()+first.getIm()*second.getIm()) / number, (first.getIm()*second.getRe()-first.getRe()*second.getIm()) / number);
    return complex;
  }

  public double mod(){
    double value = this.real * this.real + this.imagine * this.imagine;
    return Math.sqrt(value);
  }

  public static Complex sqrt(double number){
    if(number >= 0.){
      double val = Math.sqrt(number);
      Complex complex = new Complex(val);
      return complex;
    }else{
      double val = Math.sqrt((-1) * number);
      Complex complex = new Complex(0.0, val);
      return complex;
    }
  }

  public void conjugate(){
    this.imagine *= (-1.0);
  }

  public void opposite(){
    this.real *= (-1.0);
    this.imagine *= (-1.0);
  }

  @Override
  public String toString(){
      if(this.imagine < 0.){
        return this.real + " - " + ((-1) * this.imagine) + "i";
      }
      if(this.imagine == 0.){
        return Double.toString(this.real);
      }
      if(this.real == 0. && this.imagine != 0.){
        return this.imagine + "i";
      }
      return this.real + " + " + this.imagine + "i";
  }

  @Override
  public boolean equals(Object object){
    if(this.getClass() == object.getClass()){
      Complex complex = (Complex) object;
      if(this.real == complex.getRe() && this.imagine == complex.getIm()){
        return true;
      }
      return false;
    }
    return false;
  }
}