/**
 * @author Vincent Emmanuel Suwardy / 6182201067
 * @author Andrew Sebastian Aruan / 6182201004
 */

import java.util.Random;

public class GlobalVariable {
    // Seed untuk keperluan reproduksibilitas (agar hasilnya konsisten)
    public static final long SEED = new Random().nextLong(1000);
    // public static final long SEED = 151;    // SEED var untuk checker, debug dan testing

    // Penghasil angka acak dengan seed tetap
    public static final Random RANDOM = new Random(SEED);

    // Parameter untuk algoritma genetik
    public static final int POPULATION_SIZE = 100;       // Ukuran populasi                                                           
    public static final int MAX_GENERATIONS = 1000;      // Maksimal jumlah generasi
    public static final double MUTATION_RATE = 0.05;      // Tingkat mutasi                                                              
    public static final double CROSSOVER_RATE = 0.8;     // Tingkat crossover (pertukaran gen antar individu)                             
}




