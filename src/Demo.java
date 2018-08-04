import java.text.DecimalFormat;

/**
 * @author Li on 2018/6/26.
 */
public class Demo {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00000000");
        System.out.println(df.format(Math.abs(Math.PI-3.1425914874232928)));

//        int k=0;
//        for (int i=1000;i<=1000*1000;i+=5000){
//            ++k;
//        }
//        System.out.println(k);
    }

}
