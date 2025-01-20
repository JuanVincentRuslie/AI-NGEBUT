/**
 * @author Vincent Emmanuel Suwardy / 6182201067
 * @author Andrew Sebastian Aruan / 6182201004
 */ 
import java.util.*;

/**
 * Solver algoritma genetika untuk puzzle Yin-Yang
 */
public class GeneticSolver {
    private final YinYangPuzzle puzzle; // Menyimpan puzzle Yin-Yang yang akan diselesaikan

    /**
     * Konstruktor untuk menginisialisasi solver dengan puzzle Yin-Yang
     * @param puzzle Puzzle Yin-Yang yang akan diselesaikan
     */
    public GeneticSolver(YinYangPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * Metode untuk menyelesaikan puzzle menggunakan algoritma genetika
     * @return Hasil dari algoritma genetika, berupa solusi dan jumlah generasi
     */
    public GeneticResult solve() {
        List<Chromosome> population = initializePopulation(); // Inisialisasi populasi awal
        int generation = 0;

        // Proses evolusi sampai jumlah generasi mencapai maksimum
        while (generation < GlobalVariable.MAX_GENERATIONS) {
            population = evolve(population); // Evolusi populasi
            Chromosome best = getBestChromosome(population); // Ambil kromosom terbaik dari populasi

            // Jika fitness terbaik adalah 100, solusi telah ditemukan
            if (best.getFitness() == 100) {
                return new GeneticResult(best, generation); // Mengembalikan hasil dengan solusi yang ditemukan
            }

            generation++; // Meningkatkan jumlah generasi
        }

        // Jika solusi tidak ditemukan dalam batas generasi, kembalikan solusi terbaik yang ditemukan
        return new GeneticResult(getBestChromosome(population), generation);
    }

    /**
     * Inisialisasi populasi pertama untuk algoritma genetika
     * @return Daftar kromosom yang mewakili populasi awal
     */
    private List<Chromosome> initializePopulation() {
        List<Chromosome> population = new ArrayList<>();

        // Membuat kromosom baru untuk populasi
        while (population.size() < GlobalVariable.POPULATION_SIZE) {
            // Membuat kromosom dengan ukuran puzzle dan puzzle itu sendiri
            population.add(new Chromosome(puzzle.getSize(), puzzle));
        }
        return population;
    }

    /**
     * Proses evolusi untuk mengubah populasi menjadi generasi berikutnya
     * @param population Populasi saat ini
     * @return Populasi baru setelah evolusi
     */
    private List<Chromosome> evolve(List<Chromosome> population) {
        List<Chromosome> newPopulation = new ArrayList<>();

        // Proses crossover dan mutasi untuk setiap individu
        while (newPopulation.size() < population.size()) {
            Chromosome parent1 = selectParent(population);
            Chromosome parent2 = selectParent(population);

            // Jika hanya ada satu individu, crossover dengan dirinya sendiri
            if (population.size() == 1) {
                parent2 = parent1;
            }

            Chromosome offspring = parent1.crossover(parent2); // Lakukan crossover
            offspring.mutate(); // Lakukan mutasi
            newPopulation.add(offspring); // Tambahkan keturunan ke populasi baru
        }
        return newPopulation; // Kembalikan populasi baru
    }

    /**
     * Memilih orang tua dari populasi
     * @param population Populasi yang ada
     * @return Orang tua yang dipilih
     */
    private Chromosome selectParent(List<Chromosome> population) {
        // return tournamentSelection(population);
        return rouletteWheelSelection(population);
        // return rankSelection(population);
    }

    /**
     * Memilih orang tua dari populasi menggunakan metode seleksi turnamen.
     * @param population Daftar individu dalam populasi.
     * @return Individu terbaik dari turnamen.
     */
    private Chromosome tournamentSelection(List<Chromosome> population) {
        Chromosome best = null; // Menyimpan individu terbaik dari turnamen
        int tournamentSize = 5; // Ukuran turnamen (jumlah kandidat yang dipilih)

        // Melakukan seleksi untuk ukuran turnamen yang ditentukan
        for (int i = 0; i < tournamentSize; i++) {
            // Memilih kandidat acak dari populasi
            Chromosome candidate = population.get(GlobalVariable.RANDOM.nextInt(population.size()));
            // Memilih kandidat dengan fitness terbaik
            if (best == null || candidate.getFitness() < best.getFitness()) {
                best = candidate; // Update individu terbaik
            }
        }
        return best; // Mengembalikan individu terbaik dari turnamen
    }

    /**
     * Memilih orang tua dari populasi menggunakan metode seleksi roda roulette.
     * @param population Daftar individu dalam populasi.
     * @return Individu yang dipilih berdasarkan probabilitas fitness.
     */
    private Chromosome rouletteWheelSelection(List<Chromosome> population) {
        double totalFitness = 0; // Menyimpan total fitness dari populasi
        // Menghitung total fitness
        for (Chromosome chromosome : population) {
            totalFitness += chromosome.getFitness();
        }

        // Menghasilkan nilai acak antara 0 dan total fitness
        double randomValue = GlobalVariable.RANDOM.nextDouble() * totalFitness;
        double cumulativeFitness = 0; // Menyimpan fitness kumulatif

        // Memilih individu berdasarkan probabilitas
        for (Chromosome chromosome : population) {
            cumulativeFitness += chromosome.getFitness(); // Menambahkan fitness individu ke kumulatif
            // Jika kumulatif fitness melebihi nilai acak, pilih individu ini
            if (cumulativeFitness >= randomValue) {
                return chromosome; // Mengembalikan individu yang dipilih
            }
        }
        return population.get(0); // Kembalikan individu pertama jika tidak ada yang terpilih
    }

    /**
     * Memilih orang tua dari populasi menggunakan metode seleksi peringkat.
     * @param population Daftar individu dalam populasi.
     * @return Individu yang dipilih berdasarkan peringkat fitness.
     */
    private Chromosome rankSelection(List<Chromosome> population) {
        // Mengurutkan populasi berdasarkan fitness (fitness lebih rendah lebih baik)
        // population.sort(Comparator.comparingInt(Chromosome::getFitness));
        population.sort(Comparator.comparingDouble(Chromosome::getFitness)); //percobaan fitness kedua

        // Menghitung total peringkat
        double totalRank = 0;
        for (int i = 0; i < population.size(); i++) {
            totalRank += (i + 1);                   // Peringkat dimulai dari 1
        }

        // Menghasilkan nilai acak antara 0 dan total peringkat
        double randomValue = GlobalVariable.RANDOM.nextDouble() * totalRank;
        double cumulativeRank = 0;                  // Menyimpan peringkat kumulatif

        // Memilih individu berdasarkan peringkat
        for (int i = 0; i < population.size(); i++) {
            cumulativeRank += (i + 1);              // Menambahkan peringkat individu ke kumulatif
            if (cumulativeRank >= randomValue) {    // Jika kumulatif peringkat melebihi nilai acak, pilih individu ini
                return population.get(i);           // Mengembalikan individu yang dipilih
            }
        }
        return population.get(0); // Kembalikan individu pertama jika tidak ada yang terpilih
    }

    /**
     * Mengambil kromosom terbaik dalam populasi berdasarkan nilai fitness
     * @param population Populasi yang ada
     * @return Kromosom dengan fitness terbaik (terendah)
     */
    private Chromosome getBestChromosome(List<Chromosome> population) {
        // return population.stream().min(Comparator.comparingInt(Chromosome::getFitness)).orElseThrow();
        return population.stream().min(Comparator.comparingDouble(Chromosome::getFitness)).orElseThrow(); //percobaan fitness kedua
    }
}
 