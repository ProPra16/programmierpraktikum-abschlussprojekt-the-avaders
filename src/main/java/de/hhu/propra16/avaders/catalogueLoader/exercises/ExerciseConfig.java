package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * ExerciseConfig keeps track of configurations for the exercise
 * it is attached to
 */
public class ExerciseConfig {
	private boolean babySteps;
	private int time;
	private boolean timeTracking;
	private boolean atdd;

	public ExerciseConfig(){
	}

	/**
	 * @return True if babysteps is enabled
     */
	public boolean isBabySteps() {
		return babySteps;
	}

	/**
	 * @param babySteps The value babysteps will be set to
     */
	public void setBabySteps(boolean babySteps) {
		this.babySteps = babySteps;
	}

	/**
	 * @return The time (as string), specified by babysteps, until all changes are reverted
     */
	public int getBabyStepsTime() {
		return time;
	}

	/**
	 * @param time The time babystepsTime will beset to
     */
	public void setBabyStepsTime(int time) {
		this.time = time;
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
