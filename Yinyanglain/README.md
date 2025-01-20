# Tugas Besar Mata Kuliah Artificial Intelligence

### Topik: Local Search menggunakan Algoritma Genetik

**Dibuat oleh:** 
- Vincent Emmanuel Suwardy / 6182201067
- Andrew Sebastian Aruan / 6182201004

Proyek ini merupakan implementasi dari algoritma genetik untuk menyelesaikan puzzle Yin-Yang, sebuah jenis puzzle logika yang menantang. Dalam puzzle ini, tujuan utamanya adalah untuk mengisi grid dengan dua jenis simbol (hitam dan putih) dengan mematuhi beberapa aturan tertentu, seperti keseimbangan jumlah simbol di setiap baris dan kolom serta larangan untuk memiliki dua simbol yang sama bersebelahan.

## Fitur Utama

- **Algoritma Genetik**: Menggunakan teknik evolusi untuk mencari solusi optimal dengan melakukan seleksi, crossover, dan mutasi pada populasi kandidat solusi.
- **Representasi Puzzle**: Memuat puzzle dari file teks dan menyimpannya setelah menemukan solusi.
- **Evaluasi Fitness**: Menghitung nilai fitness untuk setiap kandidat solusi berdasarkan seberapa baik mereka memenuhi aturan puzzle.
- **Laporan Hasil**: Menyimpan solusi yang ditemukan dan laporan hasil algoritma, termasuk jumlah generasi yang dibutuhkan dan nilai fitness akhir.

## Struktur Proyek

- **YinYangSolver**: Kelas utama yang mengatur eksekusi program.
- **YinYangPuzzle**: Kelas yang merepresentasikan puzzle dan memuat grid dari file.
- **Chromosome**: Kelas yang merepresentasikan kandidat solusi dalam algoritma genetik.
- **GeneticResult**: Kelas yang menyimpan hasil dari algoritma genetik.
- **GeneticSolver**: Kelas yang mengimplementasikan logika algoritma genetik untuk menyelesaikan puzzle.
- **GlobalVariable**: Kelas yang menyimpan seluruh static variable yang dibutuhkan termasuk SEED

## Cara Menjalankan

1. Pastikan Anda memiliki Java Development Kit (JDK) terinstal di sistem Anda.
2. Siapkan file input (`puzzle_input.txt`) dengan format yang sesuai.
3. Kompilasi dan jalankan program menggunakan perintah berikut:

   ```bash
   javac YinYangSolver.java YinYangPuzzle.java Chromosome.java GeneticResult.java GeneticSolver.java GlobalVariable.java
   java YinYangSolver
   ```

4. Hasil solusi akan disimpan dalam file output (`puzzle_solution.txt`).