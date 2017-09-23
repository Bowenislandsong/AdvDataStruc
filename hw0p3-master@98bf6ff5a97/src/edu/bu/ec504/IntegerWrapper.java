package edu.bu.ec504;

/**
 * A wrapper for immutable classes that are effectively glorified integers.
 * Created by Prof. Ari Trachtenberg on 9/10/17.
 */
public abstract class IntegerWrapper {
    private IntegerWrapper() {} // no default constructor

    public IntegerWrapper(Integer theDatum) { datum = theDatum; }

    /**
     * @return The raw integer being stored
     */
    public Integer rawDatum() { return datum; }

    // equality is defined int erms of the private field
    @Override
    final public int hashCode() {
        return datum;
    }

    @Override
    final public boolean equals(Object obj)
    {
        return (obj instanceof IntegerWrapper && this.hashCode()==obj.hashCode());
    }

    public String toString() {
        return Integer.toString(datum);
    }

    private Integer datum;
}
