package de.hhu.propra16.avaders.catalogueLoader.exercises;

import de.hhu.propra16.avaders.extensions.Babysteps;
import de.hhu.propra16.avaders.extensions.Tracking;

/**
 * ExerciseConfig keeps track of configurations for the {@link Exercise}
 * it is attached to
 */
public class ExerciseConfig {
	private boolean babySteps;
	private int time;
	private boolean timeTracking;
	private boolean atdd;

	private static final int standardTime = 120;

	/**
	 * Returns whether or not {@link Babysteps} is enabled in this exercise
	 * @return True if {@link Babysteps} is enabled
     */
	public boolean isBabySteps(){	return babySteps;	}

	/**
	 * Sets whether or not the attached {@link Exercise} is marked as having {@link Babysteps} enabled
	 * @param babySteps The value babySteps will be set to
     */
	public void setBabySteps(boolean babySteps){	this.babySteps = babySteps;	}

	/**
	 * Returns the time that {@link Babysteps} should execute for in the attached {@link Exercise}
	 * @return The time until all changes are to be reverted
     */
	public int getBabyStepsTime(){	return time;	}

	/**
	 * Sets the time {@link Babysteps} will be executed for.
	 * If the parameter holds the value 0, the standardTime {@value standardTime}
	 * is used in it's stead.
	 * @param time The time babystepsTime will be set to.
	 *             If it is 0, then the standardTime ({@value standardTime} seconds)
	 *             is set instead.
     */
	public void setBabyStepsTime(int time){
		if(time != 0)	this.time = time;
		else this.time = standardTime;
	}

	/**
	 * Returns whether or not {@link Tracking} is supposed to be enabled in the attached
	 * {@link Exercise}
	 * @return True if time {@link Tracking} is enabled
     */
	public boolean isTimeTracking(){	return timeTracking;	}

	/**
	 * Sets the value of timeTracking
	 * @param timeTracking The value timeTracking will be set to
     */
	public void setTimeTracking(boolean timeTracking){	this.timeTracking = timeTracking;	}

	/**
	 * Returns if the attached {@link Exercise} should have atdd enabled <p>
	 * <strong>NOTE: THIS FEATURE IS NOT SUPPORTED IN THIS REALEASE
	 * AND THUS ALWAYS RETURNS FALSE</strong>
	 * @return The value of atdd
	 *
     */
	public boolean isAtdd(){	return false;	}

	/**
	 * Sets atdd to the given value.
	 * @param atdd The value atdd will be set to
     */
	public void setAtdd(boolean atdd){	this.atdd = atdd;	}
}
