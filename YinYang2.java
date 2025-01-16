

public class YinYang2 {
    private int panjang;
    private char[] papan;

    public YinYang2(int panjang, char[] papan){
        this.panjang = panjang;
        this.papan = papan;
    }

    public double fitnessFunction(){
        int fitness = 0;
        // putih disconected
        fitness += disconected_area(papan, 'w', 'v', panjang);
        // hitam disconected
        fitness += disconected_area(papan, 'b', 'p', panjang);

        // daerah hitam
        fitness += penaltyDaerah(papan, 'b', 'p', panjang);
        // daerah puth
        fitness += penaltyDaerah(papan, 'w', 'v', panjang);

        // return (((panjang*panjang)-fitness)*100/(panjang*panjang));
        return fitness;
    }

    // fitness function untuk area disconected
    public int disconected_area(char[] papan, char symbolTetap, char symbolSementara, int panjang){
        

        // Array untuk melacak sel yang sudah dikunjungi
        boolean[] visited = new boolean[panjang*panjang];

        //variabel untuk menyimpan jumlah area yang disconected
        int jumlahAreaDisconected = 0;
        
        // iterasi setiap grid
        for (int i = 0; i < panjang; i++) {
            for(int j = 0;j<panjang;j++){
                // Jika simbol ditemukan dan belum dikunjungi
                int index = (i*panjang)+j;
                if ((symbolTetap == papan[index] ||symbolSementara == papan[index])  && !visited[index]) {
                    jumlahAreaDisconected++;
                    floodFill(papan, visited, i, j, symbolTetap, symbolSementara, panjang);
                }
            }
        }

        return jumlahAreaDisconected - 1;
    }

     // validasi dengan cara flood fill nandain area yang dicheck dan masih terhubung untuk disconected
     private static void floodFill(char[] papan, boolean[] visited, int x, int y, char symbolTetap, char symbolSementara, int panjang) {
        
        // Cek batas grid dan kondisi simbol untuk error Handling
        if (x < 0 || y < 0 || x >= panjang || y >= panjang|| ((x*panjang)+y)>=panjang*panjang || visited[(x*panjang) +y] || (symbolTetap!=(papan[(x*panjang)+y])&& symbolSementara!=(papan[(x*panjang)+y]))) {
            return;
        }

        // Tandai sel ini sebagai sudah dikunjungi
        visited[(x*panjang)+y] = true;

        // Rekursif untuk sel di atas, bawah, kiri, dan kanan
        floodFill(papan, visited, x + 1, y, symbolTetap,symbolSementara, panjang);
        floodFill(papan, visited, x - 1, y, symbolTetap,symbolSementara, panjang);
        floodFill(papan, visited, x, y + 1, symbolTetap,symbolSementara, panjang);
        floodFill(papan, visited, x, y - 1, symbolTetap,symbolSementara, panjang);
    }

    // fitness function untuk daerah yang membetuk kotak
    public static int penaltyDaerah(char[] papan, char symbolTetap, char symbolSementara, int panjang){
        // daerah yang sudah di visited
        boolean[] visited = new boolean[panjang*panjang];

        // jumlah titik yang membentuk kotak
        int jumlahTitik = 0;

        // iterasi per grid
        for(int i = 0;i<panjang-1;i++){
            for(int j = 0;j<panjang-1;j++){
                 // tandain sudah di check;
                 visited[(i*panjang)+j]=true;
                if(papan[(i*panjang)+j]==symbolTetap || papan[(i*panjang)+j]==symbolSementara){
                    // check symbol sebelah kanan, diagonal kanan bawah, dan bawah
                    boolean checKanan =papan[(i*panjang)+j+1]==symbolTetap||papan[(i*panjang)+j+1]==symbolSementara;
                    boolean checKananDiagonalBawah = papan[((i+1)*panjang)+j+1]==symbolTetap||papan[((i+1)*panjang)+j+1]==symbolSementara;
                    boolean checkBawah = papan[((i+1)*panjang)+j]==symbolTetap||papan[((i+1)*panjang)+j]==symbolSementara;
                    // chek apakah ada yang membetuk kotak dari symbol yang sama
                    if(checKanan && checKananDiagonalBawah && checkBawah){
                        // nambah 4 titik
                        jumlahTitik+=4;
                       

                        floodFillDaerah(papan, visited, i, j+1, symbolTetap, symbolSementara, panjang, jumlahTitik);
                        floodFillDaerah(papan, visited, i+1, j, symbolTetap, symbolSementara, panjang, jumlahTitik);
                    } 

                }
            }
        }
        return jumlahTitik;
    }

    private static void floodFillDaerah(char[] papan, boolean[]visited,  int x, int y, char symbolTetap, char symbolSementara, int panjang, int jumlahTitik){
        //error handling, jika titik x atau y sudah di ujung, sudah di visited, dan symbolnya sama
        if(x>=panjang-1 ||y >=panjang-1 || (papan[(x*panjang)+y]!=symbolTetap && papan[(x*panjang)+y]!=symbolSementara) || visited[(x*panjang)+y]==true){
            return;
        }  

        visited[(x*panjang)+y] =true;

        // check symbol sebelah kanan, diagonal kanan bawah, dan bawah
        boolean checKanan =papan[(x*panjang)+y+1]==symbolTetap||papan[(x*panjang)+y+1]==symbolSementara;
        boolean checKananDiagonalBawah = papan[((x+1)*panjang)+y+1]==symbolTetap||papan[((x+1)*panjang)+y+1]==symbolSementara;
        boolean checkBawah = papan[((x+1)*panjang)+y]==symbolTetap||papan[((x+1)*panjang)+y]==symbolSementara;

        // check apakah membentuk kotak
        if(checKanan && checKananDiagonalBawah && checkBawah){
            jumlahTitik+=2;

            floodFillDaerah(papan, visited, x, y+1, symbolTetap, symbolSementara, panjang, jumlahTitik);
            floodFillDaerah(papan, visited, x+1, y, symbolTetap, symbolSementara, panjang, jumlahTitik);
        }
    }
}
