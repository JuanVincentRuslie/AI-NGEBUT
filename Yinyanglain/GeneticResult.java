/**
 * @author Vincent Emmanuel Suwardy / 6182201067
 * @author Andrew Sebastian Aruan / 6182201004
 */

/**
 * Container untuk hasil dari algoritma genetika
 */
public class GeneticResult {
    private final Chromosome solution;  // Solusi terbaik yang ditemukan oleh algoritma genetika
    private final int generations;      // Jumlah generasi yang dibutuhkan untuk menemukan solusi

    /**
     * Konstruktor untuk menginisialisasi hasil dari algoritma genetika
     * @param solution Kromosom solusi terbaik yang ditemukan
     * @param generations Jumlah generasi yang dibutuhkan
     */
    public GeneticResult(Chromosome solution, int generations) {
        this.solution = solution;
        this.generations = generations;
    }

    /**
     * Mengambil solusi terbaik yang ditemukan oleh algoritma genetika
     * @return Solusi terbaik dalam bentuk kromosom
     */
    public Chromosome getSolution() {
        return solution;    // Mengembalikan solusi terbaik
    }

    /**
     * Mengambil jumlah generasi yang dibutuhkan untuk menemukan solusi terbaik
     * @return Jumlah generasi
     */
    public int getGenerations() {
        return generations; // Mengembalikan jumlah generasi
    }
}