package de.hhu.propra16.avaders.catalogueLoader.exercises;

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

	public ExerciseConfig(){}

	/**
	 * @return True if babysteps is enabled
     */
	public boolean isBabySteps() {
		return babySteps;
	}

	/**
	 * @param babySteps The value babySteps will be set to
     */
	public void setBabySteps(boolean babySteps) {
		this.babySteps = babySteps;
	}

	/**
	 * @return The time until all changes are to be reverted
     */
	public int getBabyStepsTime() {
		return time;
	}

	/**
	 * @param time The time babystepsTime will be set to.
	 *             If it is 0, then the standardTime ({@value standardTime} seconds)
	 *             is set instead.
     */
	public void setBabyStepsTime(int time){
		if(time != 0)	this.time = time;
		else this.time = standardTime;
	}

	/**
	 * @return True if time tracking is enabled
     */
	public boolean isTimeTracking() {
		return timeTracking;
	}

	/**
	 * @param timeTracking The value timeTracking will be set to
     */
	public void setTimeTracking(boolean timeTracking) {
		this.timeTracking = timeTracking;
	}

	/**
	 * @return The value of atdd
     */
	public boolean isAtdd() {
		return atdd;
	}

	/**
	 * @param atdd The value atdd will be set to
     */
	public void setAtdd(boolean atdd) {
		this.atdd = atdd;
	}
}
