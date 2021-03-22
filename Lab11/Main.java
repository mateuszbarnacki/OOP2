import java.lang.RuntimeException;
import java.util.*;

class Main {
    public static void main(String[] args) {
        int n = 0;
        int m = 0;
        try{
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            if(n <= m) throw new RuntimeException("Bledne wartosci poczatkowe!");
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return;
        }

        PrepareArrays prep = new PrepareArrays(n, m);
        prep.prepare();

        Test test = new Test(prep.getFirst(), prep.getSecond(), prep.getThird());
        test.test();
    }
}

class PrepareArrays{
    private final String[] t1;
    private final String[] t2;
    private final String[] t3;
    private final Random random;

    public PrepareArrays(int n, int m){
        this.t1 = new String[n];
        this.t2 = new String[m];
        this.t3 = new String[m];
        this.random = new Random();
    }

    public void prepare(){
        System.out.println("Losowanie " + this.t1.length + " lancuchow.");
        prepareFirst();
        prepareSecond();
        prepareThird();
    }

    public String[] getFirst(){
        return this.t1;
    }

    public String[] getSecond(){
        return this.t2;
    }

    public String[] getThird(){
        return this.t3;
    }

    private void prepareFirst(){
        int size = 0, val = 0;
        char temp = ' ';
        boolean isNotChar = true;
        for(int i = 0; i < this.t1.length; ++i){
            String tempStr = "";
            size = this.random.nextInt(15);
            size += 5;
            for(int j = 0; j < size; ++j){
                isNotChar = true;
                while(isNotChar){
                    val = this.random.nextInt(128);
                    if((val >= 65) && (val <= 90)){
                        temp = (char)val;
                        isNotChar = false;
                    }
                    if((val >= 97) && (val <= 122)){
                        temp = (char)val;
                        isNotChar = false;
                    }
                }
                tempStr += temp;
            }
            this.t1[i] = tempStr;
        }
    }

    private void prepareSecond(){
        int val = 0;
        for(int i = 0; i < this.t2.length; ++i){
            val = this.random.nextInt(this.t1.length);
            this.t2[i] = this.t1[val];
        }
    }

    private void prepareThird(){
        int val = 0;
        boolean flag;
        for(int i = 0; i < this.t2.length; ++i){
            flag = true;
            while(flag){
                val = this.random.nextInt(this.t1.length);
                for(int j = 0; j < this.t2.length; ++j){
                    if(this.t1[val].compareTo(this.t2[j]) == 0){
                        flag = true;
                    } else{
                        flag = false;
                        break;
                    }
                }
            }
            this.t3[i] = this.t1[val];
        }
    }
}

class Test{
    private final String[] firstObj;
    private final String[] secondObj;
    private final String[] thirdObj;
    private final List<String> arrList;
    private final List<String> linkList;
    private final Map<String, String> hashMap;
    private final TreeMap<String, String> treeMap;

    public Test(String[] first, String[] second, String[] third){
        this.firstObj = first;
        this.secondObj = second;
        this.thirdObj = third;
        this.arrList = new ArrayList<String>();
        this.linkList = new LinkedList<String>();
        this.hashMap = new HashMap<String, String>();
        this.treeMap = new TreeMap<String, String>();
    }

    public void test(){
        System.out.println("Testowanie dla " + this.secondObj.length + " lancuchow.");
        fillCollections();
        System.out.println("Poczatek, rozmiary: " + this.hashMap.size() + ", " + this.treeMap.size() + ", " + this.arrList.size() + ", " + this.linkList.size() + "\n");
        searchCollectionsUsingSecObj();
        searchCollectionsUsingThirdObj();
        traverseLists();
        removeElements();
        System.out.println("Koniec, rozmiary: " + this.hashMap.size() + ", " + this.treeMap.size() + ", " + this.arrList.size() + ", " + this.linkList.size());
    }

    private void fillCollections(){
        long start, end, firstTime, secTime, thirdTime, fourthTime;
        start = System.nanoTime();
        for(String elem : this.firstObj){
            this.hashMap.put(elem, null);
        }
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        for(String elem : this.firstObj){
            this.treeMap.put(elem, null);
        }
        end = System.nanoTime();
        secTime = end - start;

        start = System.nanoTime();
        for(String elem : this.firstObj){
            this.arrList.add(elem);
        }
        end = System.nanoTime();
        thirdTime = end - start;

        start = System.nanoTime();
        for(String elem : this.firstObj){
            this.linkList.add(elem);
        }
        end = System.nanoTime();
        fourthTime = end - start;

        System.out.println("Generate: ArrayList(" + (double)(thirdTime)/1000000 + " ms), LinkedList(" + (double)(fourthTime)/1000000 + " ms), TreeMap(" + (double)(secTime)/1000000 + " ms), HashMap(" + (double)(firstTime)/1000000 + " ms)\n");
    }

    private void searchCollectionsUsingSecObj(){
        long start, end, firstTime, secTime, thirdTime, fourthTime;
        start = System.nanoTime();
        for(String elem : this.secondObj){
            this.hashMap.get(elem);
        }
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        for(String elem : this.secondObj){
            this.treeMap.get(elem);
        }
        end = System.nanoTime();
        secTime = end - start;

        start = System.nanoTime();
        for(String elem : this.secondObj){
            this.arrList.indexOf(elem);
        }
        end = System.nanoTime();
        thirdTime = end - start;

        start = System.nanoTime();
        for(String elem : this.secondObj){
            this.linkList.indexOf(elem);
        }
        end = System.nanoTime();
        fourthTime = end - start;

        System.out.println("Search: ArrayList(" + (double)(thirdTime)/1000000 + " ms), LinkedList(" + (double)(fourthTime)/1000000 + " ms), TreeMap(" + (double)(secTime)/1000000 + " ms), HashMap(" + (double)(firstTime)/1000000 + " ms)\n");
    }

    private void searchCollectionsUsingThirdObj(){
        long start, end, firstTime, secTime, thirdTime, fourthTime;
        start = System.nanoTime();
        for(String elem : this.thirdObj){
            this.hashMap.get(elem);
        }
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        for(String elem : this.thirdObj){
            this.treeMap.get(elem);
        }
        end = System.nanoTime();
        secTime = end - start;

        start = System.nanoTime();
        for(String elem : this.thirdObj){
            this.arrList.indexOf(elem);
        }
        end = System.nanoTime();
        thirdTime = end - start;

        start = System.nanoTime();
        for(String elem : this.thirdObj){
            this.linkList.indexOf(elem);
        }
        end = System.nanoTime();
        fourthTime = end - start;

        System.out.println("SearchNOT: ArrayList(" + (double)(thirdTime)/1000000 + " ms), LinkedList(" + (double)(fourthTime)/1000000 + " ms), TreeMap(" + (double)(secTime)/1000000 + " ms), HashMap(" + (double)(firstTime)/1000000 + " ms)");
    }

    private void traverseLists(){
        long start, end, firstTime, secTime, thirdTime;
        start = System.nanoTime();
        for(int i = 0; i < this.arrList.size(); ++i){
            this.arrList.get(i);
        }
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        for(String elem : this.arrList){
        }
        end = System.nanoTime();
        secTime = end - start;

        Iterator<String> iter = this.arrList.iterator();
        start = System.nanoTime();
        while(iter.hasNext()){
            iter.next();
        }
        end = System.nanoTime();
        thirdTime = end - start;

        System.out.println("\tjava.util.ArrayList: for(" + (double)(firstTime)/1000000 + " ms), for-each(" + (double)(secTime)/1000000 + " ms), iterator(" + (double)(thirdTime)/1000000 + " ms)");

        start = System.nanoTime();
        for(int i = 0; i < this.linkList.size(); ++i){
            this.linkList.get(i);
        }
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        for(String elem : this.linkList){
        }
        end = System.nanoTime();
        secTime = end - start;

        iter = this.linkList.iterator();
        start = System.nanoTime();
        while(iter.hasNext()){
            iter.next();
        }
        end = System.nanoTime();
        thirdTime = end - start;

        System.out.println("\tjava.util.LinkedList: for(" + (double)(firstTime)/1000000 + " ms), for-each(" + (double)(secTime)/1000000 + " ms), iterator(" + (double)(thirdTime)/1000000 + " ms)\n");
    }

    private void removeElements(){
        long start, end, firstTime, secTime, thirdTime, fourthTime;
        start = System.nanoTime();
        this.hashMap.clear();
        end = System.nanoTime();
        firstTime = end - start;

        start = System.nanoTime();
        this.treeMap.clear();
        end = System.nanoTime();
        secTime = end - start;

        start = System.nanoTime();
        this.arrList.clear();
        end = System.nanoTime();
        thirdTime = end - start;

        start = System.nanoTime();
        this.linkList.clear();
        end = System.nanoTime();
        fourthTime = end - start;

        System.out.println("Remove: ArrayList(" + (double)(thirdTime)/1000000 + " ms), LinkedList(" + (double)(fourthTime)/1000000 + " ms), TreeMap(" + (double)(secTime)/1000000 + " ms), HashMap(" + (double)(firstTime)/1000000 + " ms)\n");
    }
}