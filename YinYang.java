public class YinYang{
    // attribute
    int Heuristiik;
    char[][] papan;

    // constructure
    public YinYang(char[][] papan){
        this.papan = papan;
        this.Heuristiik = 0;
    }

    // fitness function untuk area disconected
    public int disconected_area(char[][] papan, char symbolTetap, char symbolSementara){
        //panjang papan permain
        int rows = papan.length;
        int cols = papan[0].length;

        // Array untuk melacak sel yang sudah dikunjungi
        boolean[][] visited = new boolean[rows][cols];

        //variabel untuk menyimpan jumlah area yang disconected
        int jumlahAreaDisconected = 0;
        
        // iterasi setiap grid
        for (int i = 0; i < rows; i++) {
            for(int j = 0;j<cols;j++){
                // Jika simbol ditemukan dan belum dikunjungi
                if ((symbolTetap == papan[i][j] ||symbolSementara == papan[i][j])  && !visited[i][j]) {
                    jumlahAreaDisconected++;
                    floodFill(papan, visited, i, j, symbolTetap, symbolSementara);
                }
            }
        }

        return jumlahAreaDisconected;
    }

    // validasi dengan cara flood fill nandain area yang dicheck dan masih terhubung untuk disconected
    private static void floodFill(char[][] papan, boolean[][] visited, int x, int y, char symbolTetap, char symbolSementara) {
        // panjang papan
        int rows = papan.length;
        int cols = papan[0].length;

        // Cek batas grid dan kondisi simbol untuk error Handling
        if (x < 0 || y < 0 || x >= rows || y >= cols || visited[x][y] || (symbolTetap!=(papan[x][y])&& symbolSementara!=(papan[x][y]))) {
            return;
        }

        // Tandai sel ini sebagai sudah dikunjungi
        visited[x][y] = true;

        // Rekursif untuk sel di atas, bawah, kiri, dan kanan
        floodFill(papan, visited, x + 1, y, symbolTetap,symbolSementara);
        floodFill(papan, visited, x - 1, y, symbolTetap,symbolSementara);
        floodFill(papan, visited, x, y + 1, symbolTetap,symbolSementara);
        floodFill(papan, visited, x, y - 1, symbolTetap,symbolSementara);
    }

    //untuk ada yang membetuk graph
}