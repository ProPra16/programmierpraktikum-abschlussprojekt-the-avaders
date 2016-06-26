package de.hhu.propra16.avaders.catalogueLoader.exercises;

public class ExerciseConfig {
	boolean babySteps;
	String time;
	boolean timeTracking;

	public ExerciseConfig(){
	}

	public boolean isBabySteps() {
		return babySteps;
	}

	public void setBabySteps(boolean babySteps) {
		this.babySteps = babySteps;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isTimeTracking() {
		return timeTracking;
	}

	public void setTimeTracking(boolean timeTracking) {
		this.timeTracking = timeTracking;
	}
}
