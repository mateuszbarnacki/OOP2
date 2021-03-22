import java.util.Arrays;

class Main {
  public static void main(String[] args) {
      StudentUSOS s1 = new StudentWFiIS1("Marek", "Styczen", 211150, 5, "Java", 4.5, "Fizyka", 3.0, "Elektronika", 5.0);
        StudentUSOS s2 = new StudentWFiIS1("Darek", "Luty", 305459, 2, "Java", 5.0, "Matematyka", 4.5, "Elektronika", 4.0);
        info(s1);
        info(s2);

        System.out.println("--------------------------------------------------------------------");

        StudentUSOS s3 = new StudentWFiIS2("Marek", "Styczen", 211150, 5, "Java", 4.5, "Fizyka", 3.0, "Elektronika", 5.0);
        StudentUSOS s4 = new StudentWFiIS2("Darek", "Luty", 305459, 2, "Java", 5.0, "Matematyka", 4.5, "Elektronika", 4.0);
        info(s3);
        info(s4);

        System.out.println("--------------------------------------------------------------------");

        StudentWFiIS3 s5 = new StudentWFiIS3("Marek", "Styczen", 211150, 5, "Java", 4.5, "Fizyka", 3.0, "Elektronika", 5.0);
        StudentWFiIS3 s6 = new StudentWFiIS3("Darek", "Luty", 305459, 2, "Java", 5.0, "Matematyka", 4.5, "Elektronika",  4.0);
        info(s5);
        info(s6);
    }

    public static void info(StudentUSOS s) {
        System.out.println(s);
        System.out.println("\tSrednia: " + s.srednia());
        s.listaPrzedmiotow();
    }

    public static void info(StudentWFiIS3 s) {
        System.out.println(s);
        System.out.println("\tSrednia: " + s.srednia());
        s.listaPrzedmiotow();
    }
}
abstract class Man {
  private final String name;
  private final String surname;

  Man(String n, String s) {
    name = n;
    surname = s;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String toString() {
    return getName() + " " + getSurname();
  }
}

class Student extends Man {
        private int id;
        private double[] grades = new double[3];

        Student(String n, String s, int id, double... o) {
                super(n, s);
                this.id = id;
                for (int i = 0; i < o.length && i < grades.length; ++i)
                        grades[i] = o[i];
        }

        public String toString() {
                return super.toString() + ", id number: " + id + ", grades: " + Arrays.toString(grades);
        }

        public double average() {
                double s = 0.;
                for (var el : grades)
                        s += el;
                return s / grades.length;
        }

        public double getGrade(int i) {
                if (i < grades.length)
                        return grades[i];
                else
                        return 0;
        }
}

interface StudentUSOS {
    String toString();

    double srednia();

    void listaPrzedmiotow();
}


class StudentWFiIS1 extends Student implements StudentUSOS {
    private String[] przedmioty;
    private int rok;

    /* IMPLEMETACJA METOD: */
    public StudentWFiIS1(String name, String surname, int idx, int year, String firstClass, double firstGrade, String secondClass, double secondGrade, String thirdClass, double thirdGrade){
      super(name, surname, idx, firstGrade, secondGrade, thirdGrade);
      String[] newArr = {firstClass, secondClass, thirdClass};
      this.przedmioty = newArr;
      this.rok = year;
    }

    public double srednia(){
      return super.average();
    }

    public void listaPrzedmiotow(){
      for(int i = 0; i < this.przedmioty.length; ++i){
        System.out.println("\t" + (i+1) + ". " + this.przedmioty[i] + ": " + super.getGrade(i));
      }
    }

    public String toString(){
      return "[" + this.rok + "] " + super.toString();
    }
}


class StudentWFiIS2 implements StudentUSOS {
    private String[] przedmioty;
    private int rok;
    private Student stud;

    /* IMPLEMETACJA METOD: */

    public StudentWFiIS2(String name, String surname, int idx, int year, String firstClass, double firstGrade, String secondClass, double secondGrade, String thirdClass, double thirdGrade){
      this.stud = new Student(name, surname, idx, firstGrade, secondGrade, thirdGrade);
      this.rok = year;
      String[] newArr = {firstClass, secondClass, thirdClass};
      this.przedmioty = newArr;
    }

    public double srednia(){
      return stud.average();
    }

    public void listaPrzedmiotow(){
      for(int i = 0; i < this.przedmioty.length; ++i){
        System.out.println("\t" + (i+1) + ". " + this.przedmioty[i] + ": " + stud.getGrade(i));
      }
    }

    public String toString(){
        return "[" + this.rok + "] " +stud.toString();
    }
}


class StudentWFiIS3 extends Student {
    private StudentUSOS stud;

    /* IMPLEMETACJA METOD: */
    public StudentUSOS getStudentUSOS(int year, String firstClass, String secondClass, String thirdClass){
      return new StudentUSOS(){
        private String[] przedmioty;
        private int rok;

        {
          this.przedmioty = new String[] {firstClass, secondClass, thirdClass};
          this.rok = year;
        }

        public double srednia(){
          return average();
        }

        public void listaPrzedmiotow(){
          for(int i = 0; i < this.przedmioty.length; ++i){
            System.out.println("\t" + (i+1) + ". " + this.przedmioty[i] + ": " + getGrade(i));
          }
        }

        public String toString(){
          return "[" + rok + "] ";
        }
      };
    }


     public StudentWFiIS3(String name, String surname, int idx, int year, String firstClass, double firstGrade, String secondClass, double secondGrade, String thirdClass, double thirdGrade){
      super(name, surname, idx, firstGrade, secondGrade, thirdGrade);
      stud = getStudentUSOS(year, firstClass, secondClass, thirdClass);
    }

    public double srednia(){
      return stud.srednia();
    }

    public void listaPrzedmiotow(){
      stud.listaPrzedmiotow();
    }

    public String toString(){
      return stud.toString() + super.toString();
    }
}
