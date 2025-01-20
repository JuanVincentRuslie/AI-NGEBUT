package YinyangBaru;
import java.util.*;


class YinYangChromosome {
    private BitSet bits; //bitstring untuk menyimpan encoding yinyang
    private BitSet fixedPositions; //bitstring untuk menandai bits yang permanen
    private int gridSize; //lebar papan
    private int chromosomeLength; //panjang kromosom
    private double fitness; //nilai fitness
    private int generation; //untuk menyimpan generasi keberapa pada hasil
    
  

    public YinYangChromosome(int size) {
        this.gridSize = size;
        this.chromosomeLength = size * size;
        this.bits = new BitSet(chromosomeLength);
        this.fixedPositions = new BitSet(chromosomeLength);
    }


    //set bitpermanent 
    public void setPermanentBits(int x, int y, boolean color) {
        int index = y * gridSize + x; //konversi dari 2D array ke index 1D
        bits.set(index, color); //isi value pada encoding bitstring
        fixedPositions.set(index, true); //tandai bit yang permanen
        // test
        boolean a  = fixedPositions.get(index);
    }

    public boolean getCellColor(int x, int y) {
        return bits.get(x * gridSize + y);
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
    
    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}