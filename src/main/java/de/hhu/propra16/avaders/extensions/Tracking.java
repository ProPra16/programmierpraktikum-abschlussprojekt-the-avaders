package de.hhu.propra16.avaders.extensions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static de.hhu.propra16.avaders.extensions.Tracking.Phase.*;

public class Tracking {
	private int secondsGREEN = 0, secondsRED = 0, secondsREFACTOR = 0;
	private LocalTime currentStartTime;
	private Phase currentState = Phase.NONE;
	enum Phase{GREEN, RED, REFACTOR, NONE}

	public Tracking(){}
	public Tracking(Phase currentState){
		this.currentState = currentState;
	}

	public void setState(Phase currentPhase){
		currentState = currentPhase;
	}

	public void startGREEN(){
		if(!currentState.equals(GREEN)) return;
		currentStartTime = LocalTime.now();
	}

	public void finishedGREEN(){
		System.out.println(!currentState.equals(GREEN));
		if(!currentState.equals(GREEN)) return;
		secondsGREEN += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = NONE;
		System.out.println("finished GREEN");
	}

	public void startRED(){
		if(!currentState.equals(RED)) return;
		currentStartTime = LocalTime.now();
	}

	public void finishedRED(){
		if(!currentState.equals(RED)) return;
		secondsRED += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = NONE;
	}

	public void startREFACTOR(){
		if(!currentState.equals(REFACTOR)) return;
		currentStartTime = LocalTime.now();
	}

	public void finishedREFACTOR(){
		if(!currentState.equals(REFACTOR)) return;
		secondsREFACTOR += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = NONE;
	}

	public void finishedPhaseAndMoveOn(){
		switch (currentState){
			case GREEN: finishedGREEN(); currentState = RED; break;
			case RED: finishedRED(); currentState = REFACTOR; break;
			case REFACTOR: finishedREFACTOR(); currentState = GREEN; break;
		}
	}

	public int getTimeForGREEN(){
		return secondsGREEN;
	}

	public int getTimeForRED(){
		return secondsRED;
	}

	public int getTimeForREFACTOR(){
		return secondsREFACTOR;
	}

	public Chart showChart(/*String chartType*/){
		ObservableList<PieChart.Data> chartData =
				FXCollections.observableArrayList(
						new PieChart.Data("GREEN)", secondsGREEN),
						new PieChart.Data("RED", secondsRED),
						new PieChart.Data("REFACTOR", secondsREFACTOR));
		PieChart chart = new PieChart(chartData);
		chart.setTitle("Verteilung der Arbeitzzeit auf die drei Phasen");
		return chart;
	}
}
