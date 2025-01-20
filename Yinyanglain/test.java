public class test{
    public static void main(String[] args) {
        int[][] puzzle = {
            {2,1,1,1},
            {2,2,1,1},
            {2,2,1,1},
            {2,2,1,1}
        };
        Chromosome papan = new Chromosome(4, new YinYangPuzzle(puzzle));
        System.out.println("fitness function : "+ papan.getFitness());
    }
}