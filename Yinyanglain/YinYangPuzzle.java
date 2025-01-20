/**
 * @author Vincent Emmanuel Suwardy / 6182201067
 * @author Andrew Sebastian Aruan / 6182201004
 */

import java.io.*;
import java.util.*;
 
/**
 * Representasi dari Puzzle Yin-Yang
 */
public class YinYangPuzzle {
    private final int[][] grid; // Matriks yang mewakili grid puzzle
    private final int size;     // Ukuran grid (panjang sisi)

    /**
     * Konstruktor untuk membuat objek puzzle dengan grid yang diberikan
     * @param grid Matriks dua dimensi yang mewakili grid puzzle
     */
    public YinYangPuzzle(int[][] grid) {
        this.grid = grid;
        this.size = grid.length; // Ukuran grid berdasarkan jumlah baris
    }

    /**
     * Mengambil ukuran dari grid (jumlah baris/kolom)
     * @return Ukuran grid (jumlah baris/kolom)
     */
    public int getSize() {
        return size;
    }

    /**
     * Mengambil grid puzzle
     * @return Matriks dua dimensi yang mewakili grid puzzle
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Memuat puzzle dari file
     * @param filePath Path ke file yang berisi grid puzzle
     * @return Objek YinYangPuzzle yang mewakili puzzle yang dimuat
     * @throws IOException Jika terjadi kesalahan saat membaca file
     */
    public static YinYangPuzzle loadFromFile(String filePath) throws IOException {
        // Membaca file dan memuat grid puzzle
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<int[]> gridList = new ArrayList<>();
            String line;

            // Membaca setiap baris dalam file
            while ((line = reader.readLine()) != null) {

                // Memecah setiap baris berdasarkan spasi dan mengkonversi ke dalam nilai integer
                gridList.add(Arrays.stream(line.split(" "))
                        .map(s -> {
                            switch (s) {
                                case ".":
                                    return 0;  // Sel kosong
                                case "W":
                                    return 1;  // Bidak putih
                                case "B":
                                    return 2;  // Bidak hitam
                                default:
                                    return -1; // Masukan tidak valid
                            }
                        })
                        .mapToInt(Integer::intValue)
                        .toArray());
            }

            // Mengonversi gridList menjadi array dua dimensi dan mengembalikannya sebagai objek YinYangPuzzle
            int[][] grid = gridList.toArray(new int[0][]);
            return new YinYangPuzzle(grid);
        }
    }

    /**
     * Mengubah grid puzzle menjadi string untuk ditampilkan
     * @return String yang mewakili grid puzzle dalam format yang dapat dibaca
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Iterasi untuk setiap baris grid
        for (int[] row : grid) {
            for (int cell : row) {
                
                // Menambahkan representasi sel sesuai dengan nilainya
                switch (cell) {
                    case 0:
                        sb.append(". "); // Sel kosong
                        break;
                    case 1:
                        sb.append("W "); // Bidak putih
                        break;
                    case 2:
                        sb.append("B "); // Bidak hitam
                        break;
                }
            }
            sb.append("\n"); // Menambahkan baris baru setelah setiap baris grid
        }

        return sb.toString(); // Mengembalikan string yang mewakili grid
    }
}