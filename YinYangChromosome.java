import java.util.*;


class YinYangChromosome {
    private BitSet bits;
    private BitSet fixedPositions;
    private int gridSize;
    private int chromosomeLength;
    private double fitness;

    public YinYangChromosome(int size) {
        this.gridSize = size;
        this.chromosomeLength = size * size;
        this.bits = new BitSet(chromosomeLength);
        this.fixedPositions = new BitSet(chromosomeLength);
    }

    public void setPrePlacedCell(int x, int y, boolean isWhite) {
        int index = y * gridSize + x;
        bits.set(index, isWhite);
        fixedPositions.set(index, true);
        
    }

    public boolean getCellColor(int x, int y) {
        return bits.get(y * gridSize + x);
    }

    public void setCellColor(int x, int y, boolean value) {
        bits.set(y * gridSize + x, value);
    }

    public boolean isFixed(int x, int y) {
        return fixedPositions.get(y * gridSize + x);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public BitSet getBits() {
        return bits;
    }

    public BitSet getFixedPositions() {
        return fixedPositions;
    }

    
    
}