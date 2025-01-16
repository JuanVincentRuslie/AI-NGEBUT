import java.util.BitSet;

public class YinYang3 {
    private int  panjang;
    private BitSet papan;

    public YinYang3(int panjang, BitSet papan){
        this.panjang = panjang;
        this.papan = papan;
    }

    public double fitnessFunction(){
        int fitness = 0;
        // putih disconected
        // fitness += disconected_area(papan, true, panjang);
        // hitam disconected
        // fitness += disconected_area(papan, false, panjang);

        // daerah puth
        // fitness += penaltyDaerah(papan, true, panjang);

        // daerah hitam
        fitness += penaltyDaerah(papan, false, panjang);
        
        // return (((panjang*panjang*1.0)-(fitness*1.0))*100/(panjang*panjang));
        return fitness;
    }

    // fitness function untuk area disconected
    public int disconected_area(BitSet papan, boolean symbol, int panjang){
        

        // Array untuk melacak sel yang sudah dikunjungi
        boolean[] visited = new boolean[panjang*panjang];

        //variabel untuk menyimpan jumlah area yang disconected
        int jumlahAreaDisconected = 0;
        
        // iterasi setiap grid
        for (int i = 0; i < panjang; i++) {
            for(int j = 0;j<panjang;j++){
                // Jika simbol ditemukan dan belum dikunjungi
                int index = (i*panjang)+j;
                if (symbol == papan.get(index)  && !visited[index]) {
                    jumlahAreaDisconected++;
                    floodFill(papan, visited, i, j, symbol, panjang);
                }
            }
        }

        return jumlahAreaDisconected - 1;
    }

    // validasi dengan cara flood fill nandain area yang dicheck dan masih terhubung untuk disconected
    private static void floodFill(BitSet papan, boolean[] visited, int x, int y, boolean symbol, int panjang) {
        
        // Cek batas grid dan kondisi simbol untuk error Handling
        if (x < 0 || y < 0 || x >= panjang || y >= panjang|| ((x*panjang)+y)>=panjang*panjang || visited[(x*panjang) +y] || symbol!=papan.get((x*panjang)+y)) {
            return;
        }

        // Tandai sel ini sebagai sudah dikunjungi
        visited[(x*panjang)+y] = true;

        // Rekursif untuk sel di atas, bawah, kiri, dan kanan
        floodFill(papan, visited, x + 1, y, symbol, panjang);
        floodFill(papan, visited, x - 1, y, symbol, panjang);
        floodFill(papan, visited, x, y + 1, symbol, panjang);
        floodFill(papan, visited, x, y - 1, symbol, panjang);
    }

    // fitness function untuk daerah yang membetuk kotak
    public static int penaltyDaerah(BitSet papan, boolean symbol, int panjang){
        // daerah yang sudah di visited
        boolean[] visited = new boolean[panjang*panjang];

        // jumlah titik yang membentuk kotak
        Integer jumlahTitik = 0;

        // iterasi per grid
        for(int i = 0;i<panjang-1;i++){
            for(int j = 0;j<panjang-1;j++){
                 
                if(visited[(i*panjang)+j]==false){
                     // tandain sudah di check;
                    visited[(i*panjang)+j]=true;
                    if(papan.get((i*panjang)+j)==symbol){
                        // check symbol sebelah kanan, diagonal kanan bawah, dan bawah
                        boolean checKanan = papan.get((i*panjang)+j+1)==symbol;
                        boolean checKananDiagonalBawah = papan.get(((i+1)*panjang)+j+1)==symbol;
                        boolean checkBawah = papan.get(((i+1)*panjang)+j)==symbol;
                        // chek apakah ada yang membetuk kotak dari symbol yang sama
                        if(checKanan && checKananDiagonalBawah && checkBawah){
                            // nambah 4 titik
                            jumlahTitik+=4;
                        

                            jumlahTitik=floodFillDaerah(papan, visited, i, j+1, symbol, panjang, jumlahTitik);
                            jumlahTitik=floodFillDaerah(papan, visited, i+1, j, symbol, panjang, jumlahTitik);
                        } 
                    }
               

                }
            }
        }
        return jumlahTitik;
    }

    private static Integer floodFillDaerah(BitSet papan, boolean[]visited,  int x, int y, boolean symbol, int panjang, Integer jumlahTitik){
        //error handling, jika titik x atau y sudah di ujung, sudah di visited, dan symbolnya sama
        if(x>=panjang-1 ||y >=panjang-1 || papan.get((x*panjang)+y)!=symbol  || visited[(x*panjang)+y]==true){
            return jumlahTitik ;
        }  

        visited[(x*panjang)+y] =true;

        // check symbol sebelah kanan, diagonal kanan bawah, dan bawah
        boolean checKanan =papan.get((x*panjang)+y+1)==symbol;
                    boolean checKananDiagonalBawah = papan.get(((x+1)*panjang)+y+1)==symbol;
                    boolean checkBawah = papan.get(((x+1)*panjang)+y)==symbol;

        // check apakah membentuk kotak
        if(checKanan && checKananDiagonalBawah && checkBawah){
            jumlahTitik+=2;

            jumlahTitik=floodFillDaerah(papan, visited, x, y+1, symbol, panjang, jumlahTitik);
            jumlahTitik=floodFillDaerah(papan, visited, x+1, y, symbol, panjang, jumlahTitik);
        }
        return jumlahTitik;
    }
}
