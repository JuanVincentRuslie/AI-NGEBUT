package YinyangBaru;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileTochromosomeConverter {
    
        /**
 * Loads a YinYang puzzle from a file and converts it to a chromosome
 * @param filename The path to the input file
 * @return YinYangChromosome representing the puzzle state
 * @throws IOException If there's an error reading the file
 * @throws IllegalArgumentException If the file format is invalid
 */


    public static YinYangChromosome loadFromFile(String filename) throws IOException {
        List<String> lines = readFile(filename);
        validateInput(lines);
        
        int gridSize = lines.size();
        YinYangChromosome chromosome = new YinYangChromosome(gridSize);
        
        // Convert the puzzle to chromosome
        for (int i = 0; i < gridSize; i++) {
            String[] cells = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < gridSize; j++) {
                String cell = cells[j].toUpperCase();
                if (cell.equals("W")) {
                    chromosome.setPermanentBits(j, i, true);  // white = true
                } else if (cell.equals("B")) {
                    chromosome.setPermanentBits(j, i, false); // black = false
                } else if (!cell.equals(".")) {
                    throw new IllegalArgumentException(
                        "Invalid character at position (" + i + "," + j + "): " + cell
                    );
                }
            }
        }
        
        return chromosome;
    }
    
    /**
     * Reads all lines from the file
     */
    private static List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {  // Skip empty lines
                    lines.add(line);
                }
            }
        }
        return lines;
    }
    
    /**
     * Validates that the input format is correct
     */
    private static void validateInput(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        int gridSize = lines.size();
        
        // Check each line
        for (int i = 0; i < gridSize; i++) {
            String[] cells = lines.get(i).trim().split("\\s+");
            if (cells.length != gridSize) {
                throw new IllegalArgumentException(
                    "Line " + (i + 1) + " has " + cells.length + " cells, expected " + gridSize
                );
            }
        }
    }
    
    /**
     * Saves a chromosome back to a file (useful for debugging or solution output)
     */
    public static void saveToFile(YinYangChromosome chromosome, String filename) throws IOException {
        int gridSize = chromosome.getGridSize();
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.write("Solusi:\n");
            for (int i = 0; i < gridSize; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < gridSize; j++) {
                    if (chromosome.isFixed(j, i)) {
                        line.append(chromosome.getCellColor(j, i) ? "W" : "B");
                    } else {
                        line.append(".");
                    }
                    if (j < gridSize - 1) {
                        line.append(" ");
                    }
                }
                
                writer.println(line);          
                
            }
            writer.write("\n\nLaporan:\n");
            writer.write("Seed: " + YinYangParameter.RANDOM_SEED + "\n");                        // Menyertakan seed yang digunakan dalam laporan
            writer.write("Generasi: " + chromosome.getGeneration() + "\n");                // Menyertakan jumlah generasi
            writer.write("Fitness Akhir: " + chromosome.getFitness() + "\n");
        }
    }
}
