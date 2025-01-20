/**
 * @author Vincent Emmanuel Suwardy / 6182201067
 * @author Andrew Sebastian Aruan / 6182201004
 */

import java.io.*;
import java.util.*;

/**
 * Solver utama untuk puzzle Yin-Yang menggunakan algoritma genetika
 */
public class YinYangSolver {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();    // Timer untuk keperluan testing

        // Membaca puzzle dari file
        String inputFilePath = "puzzle_input.txt";
        YinYangPuzzle puzzle = YinYangPuzzle.loadFromFile(inputFilePath);

        // Membuat solver genetika untuk puzzle tersebut
        GeneticSolver solver = new GeneticSolver(puzzle);

        // Menyelesaikan puzzle dan mendapatkan hasil
        GeneticResult result = solver.solve();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;        // Durasi dalam milidetik
        System.out.println("Durasi :"+ duration + "ms");

        String outputFile = "puzzle_solution.txt";  // Nama file output untuk solusi
        saveResultToFile(outputFile, result);
    }

    /**
     * Menyimpan solusi dan laporan ke dalam file output.
     * @param filePath Path ke file output
     * @param result Hasil solusi dan laporan
     * @throws IOException Jika terjadi kesalahan saat menulis file
     */
    private static void saveResultToFile(String filePath, GeneticResult result) throws IOException {
        // Menulis solusi dan laporan ke dalam file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Solusi:\n");
            writer.write(result.getSolution().toString());                              // Menampilkan grid dalam format yang diperlukan
            writer.write("\n\nLaporan:\n");
            writer.write("Seed: " + GlobalVariable.SEED + "\n");                        // Menyertakan seed yang digunakan dalam laporan
            writer.write("Generasi: " + result.getGenerations() + "\n");                // Menyertakan jumlah generasi
            writer.write("Fitness Akhir: " + result.getSolution().getFitness() + "\n"); // Menyertakan nilai fitness akhir
        }
    }
}