package de.hhu.propra16.avaders.catalogueLoader.exercises;

public class ExerciseConfig {
	private boolean babySteps;
	private String time;
	private boolean timeTracking;

	public ExerciseConfig(){
	}

	public boolean isBabySteps() {
		return babySteps;
	}

	public void setBabySteps(boolean babySteps) {
		this.babySteps = babySteps;
	}

	public String getBabyStepsTime() {
		return time;
	}

	public void setBabyStepsTime(String time) {
		this.time = time;
	}

	public boolean isTimeTracking() {
		return timeTracking;
	}

	public void setTimeTracking(boolean timeTracking) {
		this.timeTracking = timeTracking;
	}
}
