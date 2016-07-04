package de.hhu.propra16.avaders.extensions;

import de.hhu.propra16.avaders.logik.Step;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static de.hhu.propra16.avaders.logik.Step.*;

public class Tracking {
	private int secondsGREEN = 0, secondsRED = 0, secondsREFACTOR = 0, secondsREFACTOR2 = 0;
	private LocalTime currentStartTime;
	private Step currentState;
	private Map<String, Integer> compileErrorMap = new HashMap<>(), testErrorMap = new HashMap<>();

	/**
	 * Creates an instance of Tracking with no current Step
	 */
	public Tracking(){}

	/**
	 * Creates an instance of Tracking with the given Step as current Step
	 * @param currentState the current Step
	 */
	public Tracking(Step currentState){
		this.currentState = currentState;
	}

	/**
	 * sets the current Step
	 * @param currentStep the new current Step
	 */
	public void setState(Step currentStep){
		currentState = currentStep;
	}

	/**
	 * starts counting the Time for the GREEN-Step if it's the current Step
	 */
	public void startGREEN(){
		if(!currentState.equals(GREEN)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * stops counting the time and adds the time to the complete time of GREEN-Step
	 */
	public void finishedGREEN(){
		System.out.println(!currentState.equals(Step.GREEN));
		if(!currentState.equals(GREEN)) return;
		secondsGREEN += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
		System.out.println("finished GREEN");
	}

	/**
	 * starts counting the Time for the RED-Step if it's the current Step
	 */
	public void startRED(){
		if(!currentState.equals(RED)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * stops counting the time and adds the time to the complete time of RED-Step
	 */
	public void finishedRED(){
		if(!currentState.equals(RED)) return;
		secondsRED += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * starts counting the Time for the REFACTOR1-Step if it's the current Step
	 */
	public void startREFACTOR1(){
		if(!currentState.equals(REFACTOR1)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * stops counting the time and adds the time to the complete time of REFACTOR1-Step
	 */
	public void finishedREFACTOR1(){
		if(!currentState.equals(REFACTOR1)) return;
		secondsREFACTOR += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * starts counting the Time for the REFACTOR2-Step if it's the current Step
	 */
	public void startREFACTOR2(){
		if(!currentState.equals(REFACTOR2)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * stops counting the time and adds the time to the complete time of REFACTOR1-Step
	 */
	public void finishedREFACTOR2() {
		if(!currentState.equals(REFACTOR2)) return;
		secondsREFACTOR2 += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * stops the current Step, adds the needet time and set's the current Step to the next Step
	 * @param refactor2Enabled idicates whether REFACTOR2 is enabled or not
	 */
	public void finishedStepAndMoveOn(boolean refactor2Enabled){
		switch (currentState){
			case RED: finishedGREEN(); currentState = GREEN; break;
			case GREEN: finishedRED(); currentState = REFACTOR1; break;
			case REFACTOR1: finishedREFACTOR1(); if(!refactor2Enabled)currentState = RED;else currentState = REFACTOR2; break;
			case REFACTOR2: finishedREFACTOR2(); currentState = RED; break;
		}
	}

	/**
	 * Trackes the Compile-errors of the user
	 * @param compileErrors a Collection which holds the {@link CompileError}s
	 */
	public void addCompileExceptions(Collection<CompileError> compileErrors){
		compileErrors.forEach(x -> {
			if(compileErrorMap.containsKey(x.getMessage())) compileErrorMap.replace(x.getMessage(), compileErrorMap.get(x.getMessage()), compileErrorMap.get(x.getMessage())+1);
			else compileErrorMap.put(x.getMessage(), 1);
		});
	}

	/**
	 * Trackes the Test-errors of the user
	 * @param testErrors a Collection which holds the {@link TestFailure}s
	 */
	public void addTestExceptions(Collection<TestFailure> testErrors){
		testErrors.forEach(x -> {
			if(testErrorMap.containsKey(x.getMessage())) compileErrorMap.replace(x.getMessage(), compileErrorMap.get(x.getMessage()), compileErrorMap.get(x.getMessage())+1);
			else testErrorMap.put(x.getMessage(), 1);
		});
	}

	public void diff(List<String> origin, List<String> current){
		Patch patch = DiffUtils.diff(origin, current/*,new MyersDiff()*/);
		List<Delta> deltas = patch.getDeltas();
		//deltas.forEach(x -> System.out.println(x.getRevised()+"\n"));
		DiffUtils.generateUnifiedDiff("Brot", "Brot", origin, patch, 2).forEach(System.out::println);
	}

	/**
	 * returns the time used for GREEN
	 * @return the current value
	 */
	public int getTimeForGREEN(){
		return secondsGREEN;
	}

	/**
	 * returns the time used for RED
	 * @return the current value
	 */
	public int getTimeForRED(){
		return secondsRED;
	}

	/**
	 * returns the time used for REFACTOR
	 * @return the current value
	 */
	public int getTimeForREFACTOR1(){
		return secondsREFACTOR;
	}

	/**
	 * returns maybe the time used for REFACTOR2
	 * @return randomValueOfDoom rofl
	 */
	public int getTimeForREFACTOR2() {return secondsREFACTOR2; }

	/**
	 * builds an BarCart showing the {@link CompileError}s and their number of occurence
	 * @return the chart
	 */
	public Chart showCompileErrorChart(){
		CategoryAxis categoryAxis = new CategoryAxis();
		NumberAxis numberAxis = new NumberAxis();
		BarChart<Number, String> chart = new BarChart<>(numberAxis, categoryAxis);
		XYChart.Series<Number,String> series = new XYChart.Series<>();
		compileErrorMap.forEach((s,m) -> series.getData().add(new XYChart.Data<>(m, s)));
		chart.getData().add(series);
		return chart;
	}

	/**
	 * builds an BarCart showing the {@link TestFailure}s and their number of occurence
	 * @return the chart
	 */
	public Chart showTestErrorChart(){
		CategoryAxis categoryAxis = new CategoryAxis();
		NumberAxis numberAxis = new NumberAxis();
		BarChart<Number, String> chart = new BarChart<>(numberAxis, categoryAxis);
		XYChart.Series<Number, String> series = new XYChart.Series<>();
		testErrorMap.forEach((s,m) -> series.getData().add(new XYChart.Data<>(m, s)));
		chart.getData().add(series);
		return chart;
	}

	/**
	 * builds a PieChart showing the used time for the different Steps
	 * @param refactor2Enabled idicates whether REFACTOR2 is enabled or not
	 * @return the chart
	 */
	public Chart showTimeChart(boolean refactor2Enabled){
		ObservableList<PieChart.Data> chartData =
				FXCollections.observableArrayList(
						new PieChart.Data("GREEN)", secondsGREEN),
						new PieChart.Data("RED", secondsRED),
						new PieChart.Data("REFACTOR1", secondsREFACTOR));
		if(refactor2Enabled) chartData.add(new PieChart.Data("REFACTOR2", secondsREFACTOR2));
		PieChart chart = new PieChart(chartData);
		chart.setTitle("Verteilung der Arbeitzzeit auf die drei Phasen");
		return chart;
	}
}
