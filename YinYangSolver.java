import java.util.*;

public class YinYangSolver {
    public static void main(String[] args) {
        int gridsize;
        char[][] papan;
        int populationsize;
        double crossoverRate;
        double mutationRate;
        int randomseed;

        Scanner scanner = new Scanner(System.in);

        System.out.println("please enter puzzle size : ");

        gridsize = scanner.nextInt();

        papan = new char[gridsize][gridsize];

        System.out.println("please enter puzzle placement ('.' for null, 'w' for white, 'b' for black ) : ");

        int i,j;
        for(i = 0; i < gridsize; i++){
            for(j = 0; j < gridsize; j++){
                papan[i][j] = scanner.next().charAt(0);
            }
        }

        

        System.out.println("enter population size : ");
        populationsize = scanner.nextInt();
        System.out.println("enter crossover rate : ");
        crossoverRate = scanner.nextDouble();
        System.out.println("enter mutation rate : ");
        mutationRate = scanner.nextDouble();
        System.out.println("enter seed : ");
        randomseed = scanner.nextInt();

        
        System.out.println("test data : ");

        System.out.println("puzzle : ");

        for(i = 0; i < gridsize; i++){
            for(j = 0; j < gridsize; j++){
                System.out.print(papan[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println(gridsize);
        System.out.println(populationsize);
        System.out.println(crossoverRate);
        System.out.println(mutationRate);
        System.out.println(randomseed);

        YinYangChromosome stateAwal = new YinYangChromosome(gridsize);

        for(i = 0; i < gridsize; i++){
            for(j = 0; j < gridsize; j++){
                if(papan[i][j] == 'w'){
                    stateAwal.setPrePlacedCell(j,i,true);
                }else if(papan[i][j] == 'b'){
                    stateAwal.setPrePlacedCell(j,i,false);
                }
            }
        }
        

        System.out.println("initialize GA");
        YinYangGeneticAlgo GA = new YinYangGeneticAlgo(populationsize, crossoverRate, mutationRate, gridsize, randomseed, stateAwal);
        GA.initializePopulation();

        double targetFitness = 100;
        int maxgeneration = 1000;

        YinYangChromosome solution = GA.findSolution(maxgeneration, targetFitness);
        printSolution(solution);
        
    }

    private static void printSolution(YinYangChromosome solution) {
        if (solution == null) {
            System.out.println("No solution found");
            return;
        }
        
        System.out.println("Solution found with fitness: " + solution.getFitness());
        System.out.println("Grid:");
        System.out.println("Generation: "+solution.getGeneration());
        for (int x = 0; x < solution.getGridSize(); x++) {
            for (int y = 0; y < solution.getGridSize(); y++) {
                if (solution.getCellColor(x, y)) {
                    System.out.print("W "); 
                } else {
                    System.out.print("B "); 
                }
            }
            System.out.println();
        }
    }
}


