package org.usfirst.frc.team3926.UserClasses;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Map;

/**
 * Holds and handles the filtering of data for the SmartFilter
 */
public class SmartFilterData extends Subsystem {

    /** Amount of error (Double) from the contour at and index (Integer) TODO test memory usage with this */
    private Map<Integer, Double> percentError;
    /** Index associated with this contour */
    private int                  index;
    /** Width of the contour associated with this data */
    private double               width;
    /** Height of the contour associated with this data */
    private double               height;
    /** X-axis value of the center of the associated contour */
    private double               centerX;
    /** Y-axis value of the center of the associated contour */
    private double               centerY;
    /** If this contour meets the advanced criteria set by this program */
    private boolean              passedSelfEvaluation;

    /**
     * Creates a subsystem. This will set the name of the subsystem to the name of the class.
     */
    SmartFilterData(int index, double width, double height, double centerX, double centerY, double area) {

        this.index = index;
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Compares this contour to another to figure out if another contour is the companion to this one
     */
    void twoContourComapare() {

    }

    /**
     * Looks at this contour's information to determine if it fits the criteria we want
     * TODO finish
     */
    void selfCompare() {

        passedSelfEvaluation = true;

    }

    /**
     * Set the default command for a subsystem here.
     */
    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }

}

