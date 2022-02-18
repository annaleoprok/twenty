import java.util.Arrays;

public class MyClass {
    static int size = 10000000;
    static int half = size/2;

    public static void singleThread(){
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for(int i = 0; i < size; i++){
            arr[i]=(float) (arr[i]*Math.sin(0.2f+ i/5)*Math.cos(0.2f+ i/5)*Math.cos(0.4f+ i/2));
        }
        System.out.println("Время работы");
        System.out.println(System.currentTimeMillis() -a);
    }
    public static void doubleThread(){
        float[] arr= new float[size];
        float[] arrOne =  new float[half];
        float[] arrTwo =  new float[half];
        Arrays.fill(arr, 1);
        long a= System.currentTimeMillis();
        System.arraycopy(arr,  0, arrOne, 0, half);
        System.arraycopy(arr, half, arrTwo, 0, half);

        MyThread mOne= new MyThread(arrOne, 0);
        MyThread mTwo= new MyThread(arrTwo, half);

        mOne.start();
        mTwo.start();

        try {
            mOne.join();
            mTwo.join();
        }
        catch (InterruptedException ie){
            System.out.println("Потоки прерваны");
        }
        System.out.println("Время работы 2");
        System.out.println(System.currentTimeMillis() -a);
    }
    public static void main(String[] args){
        singleThread();
    }
}
 class MyThread extends Thread{
    float[] arr;
    int shift;

    MyThread(float[] array, int shift){
        this.arr=array;
        this.shift=shift;
    }
    @Override
     public void run(){
        for (int i = 0; i < arr.length; i++){
            arr[i]= (float) (arr[i]*Math.sin(0.2f+(i+shift/5))*Math.cos(0.2f+(i+shift/5))*Math.cos(0.4f+i/2));
        }
    }
 }