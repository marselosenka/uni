package Model.Position;

import Model.Finding.Finding;

/**
 * A class that represents a position of the board
 * that has a Finding.
 */
public class FindingPosition extends Position{
    private boolean activeFinding;
    private Finding finding;

    /**
     * Constructor: Initializes a FindingPosition, calls super class
     * to initialize the required attributes in order to be a Position.
     * @param index index on the board.
     * @param image_path path for the position image.
     * @param finding finding on this position.
     */
    public FindingPosition(int index,String image_path,Finding finding) {
        super(index,image_path);
        this.activeFinding = true;
        this.finding = finding;
    }

    /**
     * Accessor: Used to know if the position has a finding or not.
     * Pre-Condition: The game has started.
     * Post-Condition: Should inform if the finding is there or not.
     * @return true if it is, false if there finding has been claimed.
     */
    public boolean isActiveFinding() {
        return activeFinding;
    }

    /**
     * Transformer(mutative): Changes the status of Finding, if there's a finding or not.
     * Pre-Condition: None.
     * Post-Condition: The new value should update the status of the finding.
     * @param activeFinding True if there is one, False if there's not.
     */
    public void setActiveFinding(boolean activeFinding) {
        this.activeFinding = activeFinding;
    }

    /**
     * Accessor: Receive the finding of this position.
     * Pre-Condition: It has been initialized.
     * Post-Condition: The player should see the finding
     * @return an object of type Finding.
     */
    public Finding getFinding() {
        return finding;
    }

    /**
     * Transformer(mutative): Sets a finding for this position.
     * Pre-Condition: None.
     * Post-Condition: Finding has been set.
     * @param finding an Object of class Finding.
     */
    public void setFinding(Finding finding) {
        this.finding = finding;
    }
}