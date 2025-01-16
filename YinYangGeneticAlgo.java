import java.util.ArrayList;
// import java.util.BitSet;
import java.util.Random;

class YinYangGeneticAlgo {
    private int populationSize; 
    private double crossoverRate;
    private double mutationRate;
    private int gridSize;
    private Random rand; //object random
    private ArrayList<YinYangChromosome> population; //arraylist untuk menyimpan populasi
    private YinYangFitnessFunction fitnessEvaluator;
    private YinYangChromosome stateAwal; 

    //konstruktor
    public YinYangGeneticAlgo(int populationSize, double crossoverRate, double mutationRate, int gridSize, long seed, YinYangChromosome stateAwal){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.gridSize = gridSize;
        this.rand = new Random(seed);
        this.fitnessEvaluator = new YinYangFitnessFunction();
        this.population = new ArrayList<>();
        this.stateAwal = stateAwal;
    }

    // Tahap 1 : Inisiasi populasi
    public void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            YinYangChromosome chromosome = new YinYangChromosome(gridSize);
            // set random value kepada posisi bitset yang bukan bitpermanen/fixedposition
            for (int j = 0; j < gridSize * gridSize; j++) {
                // cek kalau bukan fixed position 
                boolean fixed = this.stateAwal.getFixedPositions().get(j);
                if (!fixed) {
                    chromosome.getBits().set(j, rand.nextBoolean());
                }else{
                    boolean nilaiFixed = this.stateAwal.getBits().get(j);
                    chromosome.getBits().set(j,nilaiFixed);
                    chromosome.getFixedPositions().set(j, true);
                }
            }
            chromosome.setGeneration(0);
            population.add(chromosome);
        }
        //panggil metode evaluasi
        evaluatePopulation();
    }

    // Tahap 2 : evaluasi populasi
    private void evaluatePopulation() {
        for (YinYangChromosome chromosome : population) {
            //evaluasi masing masing fitness kromosom pada populasi
            double fitness = fitnessEvaluator.evaluate(chromosome); 
            //lalu set fitness value pada masing2 kromosom
            chromosome.setFitness(fitness); 
        }
    }

    //Tahap 3 : seleksi pada populasi 
    public YinYangChromosome linearRankSelection() {
        //sort populasi beradasarkan fitness
        population.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));
        
        int n = population.size();
        double totalRank = n * (n + 1) / 2.0;
        double random = rand.nextDouble() * totalRank;
        double sum = 0;
        
        for (int i = 0; i < n; i++) {
            sum += (n - i);
            if (sum > random) {
                return population.get(i);
            }
        }
        //ambil chromosome yang paling bagus
        return population.get(0);
    }

    //Tahap 3 : seleksi pada populasi 
    public YinYangChromosome rouletteWheelSelection() {
        double totalFitness = population.stream()
                .mapToDouble(YinYangChromosome::getFitness)
                .sum();
        
        double random = rand.nextDouble() * totalFitness;
        double sum = 0;
        
        for (YinYangChromosome chromosome : population) {
            sum += chromosome.getFitness();
            if (sum > random) {
                return chromosome;
            }
        }
        return population.get(population.size() - 1);
    }


   //Tahap 4 : crossover (single point uniform crossover)
    public YinYangChromosome uniformCrossover(YinYangChromosome parent1, YinYangChromosome parent2) {
        YinYangChromosome child = new YinYangChromosome(gridSize);
        
        for (int i = 0; i < gridSize * gridSize; i++) {
            if (parent1.getFixedPositions().get(i)) {
                //copy fixedbits dari parent ke child
                child.getBits().set(i, parent1.getBits().get(i));
                child.getFixedPositions().set(i, true);
            } else {
                //copy value bits non fixed kepada child secara random
                boolean parentValue = rand.nextBoolean() ? 
                    parent1.getBits().get(i) : parent2.getBits().get(i);
                child.getBits().set(i, parentValue);
            }
        }
        return child;
    }

    //Tahap 4 : crossover (two point crossover)
    public YinYangChromosome twoPointCrossover(YinYangChromosome parent1, YinYangChromosome parent2) {
        YinYangChromosome child = new YinYangChromosome(gridSize);
        int length = gridSize * gridSize;
        
        //set 2point random
        int point1 = rand.nextInt(length);
        int point2 = rand.nextInt(length);
        
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }
        
        for (int i = 0; i < length; i++) {
            if (parent1.getFixedPositions().get(i)) {
                child.getBits().set(i, parent1.getBits().get(i));
                child.getFixedPositions().set(i, true);
            } else {
                if (i < point1 || i > point2) {
                    child.getBits().set(i, parent1.getBits().get(i));
                } else {
                    child.getBits().set(i, parent2.getBits().get(i));
                }
            }
        }
        return child;
    }

    //Tahap 5 Mutasi
    public void mutate(YinYangChromosome chromosome) {
        for (int i = 0; i < gridSize * gridSize; i++) { 
            // mutasi secara random bit yang tidak fixed
            boolean nilaiFixed = chromosome.getFixedPositions().get(i);
            if (!nilaiFixed && rand.nextDouble() < mutationRate) {
                chromosome.getBits().flip(i);
            }
        }
    }


    // Function 6 : Random Number Generator with Seed
    public double getRandomNumber() {
        return rand.nextDouble();
    }
    public int getRandomInt(int bound) {
        return rand.nextInt(bound);
    }

    
    //Main function untuk panggil semua function
    public YinYangChromosome findSolution(int maxGenerations, double targetFitness) {
        int generation = 1;
        YinYangChromosome bestSolution = null;
        double bestFitness = 0;
        
        // while loop untuk generasi
        while (generation < maxGenerations) {


            //buat populasi baru
            ArrayList<YinYangChromosome> newPopulation = new ArrayList<>();
            
            
            while (newPopulation.size() < populationSize) {
                // Selection
                YinYangChromosome parent1 = linearRankSelection();                
                YinYangChromosome parent2 = linearRankSelection();
                
                // Crossover
                YinYangChromosome child = null;
                if (rand.nextDouble() < crossoverRate) {
                    child = uniformCrossover(parent1, parent2);
                    child.setGeneration(generation);
                } else {
                    child = new YinYangChromosome(gridSize);
                    child.getBits().or(parent1.getBits()); // copy parent1
                    child.getFixedPositions().or(parent1.getFixedPositions());
                    child.setGeneration(parent1.getGeneration());
                }
                
                // Mutation
                mutate(child);
                
                // Evaluate child chromosom
                double fitness = fitnessEvaluator.evaluate(child);
                child.setFitness(fitness);
                
                // track solusi terbaik
                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    bestSolution = child;
                    
                    // if jika menemukan solusi terbaik
                    if (bestFitness >= targetFitness) {
                        bestSolution.setGeneration(generation);
                        return bestSolution;
                    }
                }
                
                newPopulation.add(child);
            }
            
            // ubah populasi lama dengan populasi baru
            population = newPopulation;
            generation++;
        }
        bestSolution.setGeneration(generation);
        return bestSolution;
    }
}
