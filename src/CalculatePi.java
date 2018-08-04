import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Li on 2018/6/25.
 */
public class CalculatePi implements Callable<List<Double>> {

    private int divideNum;
    private double mid;
    private int numThread;
    private int j;
    private int begin;
    private double x, y, sum = 0.0;
    private List<Double> twoTarget = new ArrayList<>();

    public CalculatePi(int divideNum, int numThread, int begin) {
        this.divideNum = divideNum;
        this.numThread = numThread;
        this.begin = begin;
        this.j = this.divideNum / this.numThread;
        this.mid = 1.0 / (2 * this.divideNum);
    }

    public CalculatePi() {

    }

    @Override
    public List<Double> call() throws Exception {
        long start = System.currentTimeMillis();
        for (int k = 0; k < j; k++) {
            x = begin + numThread * k;
            x = (x + mid) * (2 * mid);
            y = 4.0 / (1.0 + x * x);
            sum += y * (2 * mid);
        }
        long end = System.currentTimeMillis();
        twoTarget.add(sum);
        twoTarget.add((end - start) / 1.0);
        return twoTarget;
    }


    public List<Double> doMe(int divideNum, int numThread) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<List<Double>>> results = new ArrayList<>();
        double pi = 0.0;
        double time = 0.0;
        List<Double> towNum = new ArrayList<>();
        for (int i = 0; i < numThread; i++) {
            results.add(exec.submit(new CalculatePi(divideNum, numThread, i)));
        }


        for (Future<List<Double>> result : results) {
            pi += result.get().get(0);
            time += result.get().get(1);
        }
        towNum.add(pi);
        towNum.add(time);
        return towNum;
    }

    public static void main(String[] args) throws Exception {


        CalculatePi cp = new CalculatePi();
        PrintWriter pw = new PrintWriter(new File("N定P与T关系_ws.txt"));

        for (int i = 1; i <= 20; i++) {
            List<Double> twoNum = cp.doMe(100000, i);
            pw.write(i+" ");
            pw.write(twoNum.get(0)+" ");
            pw.write(twoNum.get(1)+"\n");
        }
        pw.write("\n");
        for (int i = 1; i <= 20; i++) {
            List<Double> twoNum = cp.doMe(1000000, i);
            pw.write(i+" ");
            pw.write(twoNum.get(0)+" ");
            pw.write(twoNum.get(1)+"\n");
        }
        pw.flush();
        pw.close();


        PrintWriter pw2 = new PrintWriter(new File("P定N与T关系_ws.txt"));
        for (int i=1000;i<=1000*1000;i+=5000){//迭代200次
            List<Double> doMe = cp.doMe(i, 8);
            pw2.write(i+" ");
            pw2.write(doMe.get(0)+" ");
            pw2.write(doMe.get(1)+" ");
            DecimalFormat df = new DecimalFormat("0.00000000");
            pw2.write(df.format(Math.abs(Math.PI-doMe.get(0)))+"\n");
        }
        pw2.flush();
        pw2.close();
    }


}

