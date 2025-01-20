package YinyangBaru;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class YinYangSolvernew {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();    // Timer untuk keperluan testing

        // Membaca puzzle dari file
        String inputFilePath = "puzzle_input.txt";
        
        // YinYangeChromosome puzzle = YinYangPuzzle.loadFromFile(inputFilePath);

        // Tambahan
        YinYangChromosome stateawal = FileTochromosomeConverter.loadFromFile(inputFilePath);

        // // Membuat solver genetika untuk puzzle tersebut
        // GeneticSolver solver = new GeneticSolver(puzzle);

        // // Menyelesaikan puzzle dan mendapatkan hasil
        // GeneticResult result = solver.solve();

        // Tambahan

        System.out.println("initialize GA");
        YinYangGeneticAlgo GA = new YinYangGeneticAlgo(stateawal);
        GA.initializePopulation();


        //panggil function untuk mencari solusi pada Genetik Algorithm
        YinYangChromosome solution = GA.findSolution();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;        // Durasi dalam milidetik
        System.out.println("Durasi :"+ duration + "ms");

        String outputFile = "puzzle_solution.txt";  // Nama file output untuk solusi
        // saveResultToFile(outputFile, solution);

        //tambahan
        FileTochromosomeConverter.saveToFile(solution, outputFile);
    }

    // /**
    //  * Menyimpan solusi dan laporan ke dalam file output.
    //  * @param filePath Path ke file output
    //  * @param result Hasil solusi dan laporan
    //  * @throws IOException Jika terjadi kesalahan saat menulis file
    //  */
    // private static void saveResultToFile(String filePath, YinYangChromosome result) throws IOException {
    //     // Menulis solusi dan laporan ke dalam file
    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    //         writer.write("Solusi:\n");
    //         writer.write(result.getSolution().toString());                              // Menampilkan grid dalam format yang diperlukan
    //         writer.write("\n\nLaporan:\n");
    //         writer.write("Seed: " + GlobalVariable.SEED + "\n");                        // Menyertakan seed yang digunakan dalam laporan
    //         writer.write("Generasi: " + result.getGenerations() + "\n");                // Menyertakan jumlah generasi
    //         writer.write("Fitness Akhir: " + result.getSolution().getFitness() + "\n"); // Menyertakan nilai fitness akhir
    //     }
    // }
}
