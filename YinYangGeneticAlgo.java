import java.util.ArrayList;
// import java.util.BitSet;
import java.util.Random;

class YinYangGeneticAlgo {
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int gridSize;
    private Random rand;
    private ArrayList<YinYangChromosome> population;
    private YinYangFitnessFunction fitnessEvaluator;
    private YinYangChromosome stateAwal;

    public YinYangGeneticAlgo(int populationSize, double crossoverRate, double mutationRate, 
                     int gridSize, long seed, YinYangChromosome stateAwal){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.gridSize = gridSize;
        this.rand = new Random(seed);
        this.fitnessEvaluator = new YinYangFitnessFunction();
        this.population = new ArrayList<>();
        this.stateAwal = stateAwal;
    }

    // Function 1: Initialize Population
    public void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            YinYangChromosome chromosome = new YinYangChromosome(gridSize);
            // Randomly initialize non-fixed positions
            for (int j = 0; j < gridSize * gridSize; j++) {
                // kalau bukan fixed position 
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
        evaluatePopulation();
    }

    // Function 2: Evaluate Population
    private void evaluatePopulation() {
        for (YinYangChromosome chromosome : population) {
            double fitness = fitnessEvaluator.evaluate(chromosome);
            chromosome.setFitness(fitness);
        }
    }

    // Function 3: Roulette Wheel Selection
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

    // Function 4a: Single Point Uniform Crossover
    public YinYangChromosome uniformCrossover(YinYangChromosome parent1, YinYangChromosome parent2) {
        YinYangChromosome child = new YinYangChromosome(gridSize);
        
        for (int i = 0; i < gridSize * gridSize; i++) {
            if (parent1.getFixedPositions().get(i)) {
                // For fixed positions: copy the pre-placed value
                child.getBits().set(i, parent1.getBits().get(i));
                child.getFixedPositions().set(i, true);
            } else {
                // For non-fixed positions: randomly select from parents
                boolean parentValue = rand.nextBoolean() ? 
                    parent1.getBits().get(i) : parent2.getBits().get(i);
                child.getBits().set(i, parentValue);
            }
        }
        return child;
    }

    // Function 4b: Mutation
    public void mutate(YinYangChromosome chromosome) {
        for (int i = 0; i < gridSize * gridSize; i++) { 
            // Only mutate non-fixed positions
            boolean nilaiFixed = chromosome.getFixedPositions().get(i);
            if (!nilaiFixed && rand.nextDouble() < mutationRate) {
                chromosome.getBits().flip(i);
            }
        }
    }

    // Extra Function: Two Point Crossover (not used in main algorithm)
    public YinYangChromosome twoPointCrossover(YinYangChromosome parent1, YinYangChromosome parent2) {
        YinYangChromosome child = new YinYangChromosome(gridSize);
        int length = gridSize * gridSize;
        
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

    // Extra Function: Linear Rank Selection (not used in main algorithm)
    public YinYangChromosome linearRankSelection() {
        // Sort population by fitness
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
        return population.get(0);
    }

    // Function 5: Random Number Generator with Seed
    public double getRandomNumber() {
        return rand.nextDouble();
    }

    public int getRandomInt(int bound) {
        return rand.nextInt(bound);
    }

    // Main evolution process
    public void evolve(int generations) {
        for (int gen = 0; gen < generations; gen++) {
            ArrayList<YinYangChromosome> newPopulation = new ArrayList<>();
            
            while (newPopulation.size() < populationSize) {
                // Selection
                YinYangChromosome parent1 = rouletteWheelSelection();
                YinYangChromosome parent2 = rouletteWheelSelection();
                
                // Crossover
                YinYangChromosome child;
                if (rand.nextDouble() < crossoverRate) {
                    child = uniformCrossover(parent1, parent2);
                } else {
                    child = new YinYangChromosome(gridSize);
                    child.getBits().or(parent1.getBits());
                }
                
                // Mutation
                mutate(child);
                
                newPopulation.add(child);
            }
            
            population = newPopulation;
            evaluatePopulation();
        }
    }

    public YinYangChromosome findSolution(int maxGenerations, double targetFitness) {
        int generation = 0;
        YinYangChromosome bestSolution = null;
        double bestFitness = 0;
    
        while (generation < maxGenerations) {
            ArrayList<YinYangChromosome> newPopulation = new ArrayList<>();
            
            // Create new population
            while (newPopulation.size() < populationSize) {
                // Selection
                YinYangChromosome parent1 = rouletteWheelSelection();
                YinYangChromosome parent2 = rouletteWheelSelection();
                
                // Crossover
                YinYangChromosome child = null;
                if (rand.nextDouble() < crossoverRate) {
                    child = uniformCrossover(parent1, parent2);
                    child.setGeneration(generation+1);
                } else {
                    child = new YinYangChromosome(gridSize);
                    child.getBits().or(parent1.getBits()); // copy parent1
                    child.getFixedPositions().or(parent1.getFixedPositions());
                    child.setGeneration(parent1.getGeneration());
                }
                
                // Mutation
                mutate(child);
                
                // Evaluate new child
                double fitness = fitnessEvaluator.evaluate(child);
                child.setFitness(fitness);
                
                // Track best solution
                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    bestSolution = child;
                    
                    // If we found a perfect solution
                    if (bestFitness >= targetFitness) {
                        return bestSolution;
                    }
                }
                
                newPopulation.add(child);
            }
            
            // Replace old population
            population = newPopulation;
            generation++;
        }
        
        return bestSolution;
    }
}
