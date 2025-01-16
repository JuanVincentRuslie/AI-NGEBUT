import java.util.BitSet;

public class MainTest {
    public static void main(String[] args) {
        // char[][] papan ={
        //     {'w','w','v','b'},
        //     {'w','b','b','b'},
        //     {'w','b','v','w'},
        //     {'w','v','w','v'}
        // };
        
        // char[] papan2 =
        //     {'w','w','v','b',
        //     'w','b','b','b',
        //     'w','b','b','b',
        //     'w','v','w','v'};
        
        // YinYang permainan = new YinYang(papan);
        
        // YinYang2 permainanan2 = new YinYang2(4, papan2);
        
        // System.out.println("array char[] " +permainanan2.fitnessFunction());
        // System.out.println("array char[][] " +permainan.disconected_area(papan, 'w','v'));
        
        BitSet bitSet = new BitSet(16);

        bitSet.set(0, true);
        bitSet.set(1, true);
        bitSet.set(2, true);
        bitSet.set(3, false);
        bitSet.set(4, true);
        bitSet.set(5, false);
        bitSet.set(6, false);
        bitSet.set(7, false);
        bitSet.set(8, true);
        bitSet.set(9, false);
        bitSet.set(10, false);
        bitSet.set(11, false);
        bitSet.set(12, true);
        bitSet.set(13, false);
        bitSet.set(14, true);
        bitSet.set(15, true);

        /*
        putih, putih, putih, hitam,
        putih, hitam, hitam, hitam,
        putih, hitam, hitam, hitam,
        putih, hitam, putih, putih
         */ 
        YinYang3 papan = new YinYang3(4, bitSet);

        System.out.println(papan.fitnessFunction());
    }
    
    
}
