package de.hhu.propra16.avaders.extensions;

import de.hhu.propra16.avaders.logik.Step;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.hhu.propra16.avaders.logik.Step.*;

/**
 * Diese Klasse erhebt Statistiken über das Programmierverhalten des Benutzers.
 * Dafür kann sie die benötigte Zeit der einzelnen {@link Step}s speichern
 * und die Häufigkeit von {@link CompileError}s und {@link TestFailure}s.
 * Diese können auch in hübschen {@link Chart Diagrammen} dargestellt werden.
 */
public class Tracking {
	protected int secondsGREEN = 0;
	protected int secondsRED = 0;
	protected int secondsREFACTOR = 0;
	protected int secondsREFACTOR2 = 0;
	protected int secondsAcceptance = 0;
	private LocalTime currentStartTime;
	private Step currentState;
	protected Map<String, Integer> compileErrorMapGREEN = new HashMap<>(), compileErrorMapREFACTOR = new HashMap<>(), testErrorMap = new HashMap<>();

	/**
	 * Erzeugt eine Instanz von {@link Tracking}, ohne einen aktuellen {@link Step} zu setzen.
	 */
	public Tracking(){}

	/**
	 * Erzeugt eine Instanz von {@link Tracking} und setzt dabei den aktuellen {@link Step}.
	 * @param currentState Der aktuelle {@link Step}
	 */
	public Tracking(Step currentState){
		this.currentState = currentState;
	}

	/**
	 * Setzt den aktuellen {@link Step}.
	 * @param currentStep Der neue, aktuelle {@link Step}.
	 */
	public void setState(Step currentStep){
		currentState = currentStep;
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#GREEN GREEN} ist,
	 * wird nun die verstrichene Zeit für den {@link Step#GREEN GREEN}-{@link Step} gezählt.
	 */
	public void startGREEN(){
		if(!currentState.equals(GREEN)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#GREEN GREEN} ist,
	 * wird die verstrichene Zeit zur gesammt {@link Step#GREEN GREEN}-Zeit aufsummiert.
	 * Es wird nun nicht weiter gezählt.
	 */
	public void finishedGREEN(){
		if(currentState != GREEN) return;
		secondsGREEN += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#RED RED} ist,
	 * wird nun die verstrichene Zeit für den {@link Step#RED RED}-{@link Step} gezählt.
	 */
	public void startRED(){
		if(!currentState.equals(RED)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#RED RED} ist,
	 * wird die verstrichene Zeit zur gesammt {@link Step#RED RED}-Zeit aufsummiert.
	 * Es wird nun nicht weiter gezählt.
	 */
	public void finishedRED(){
		if(!currentState.equals(RED)) return;
		secondsRED += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#REFACTOR1 REFACTOR1} ist,
	 * wird nun die verstrichene Zeit für den {@link Step#REFACTOR1 REFACTOR1}-{@link Step} gezählt.
	 */
	public void startREFACTOR1(){
		if(!currentState.equals(REFACTOR1)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#REFACTOR1 REFACTOR1} ist,
	 * wird die verstrichene Zeit zur gesammt {@link Step#REFACTOR1 REFACTOR1}-Zeit aufsummiert.
	 * Es wird nun nicht weiter gezählt.
	 */
	public void finishedREFACTOR1(){
		if(!currentState.equals(REFACTOR1)) return;
		secondsREFACTOR += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#REFACTOR2 REFACTOR2} ist,
	 * wird nun die verstrichene Zeit für den {@link Step#REFACTOR2 REFACTOR2}-{@link Step} gezählt.
	 */
	public void startREFACTOR2(){
		if(!currentState.equals(REFACTOR2)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#REFACTOR2 REFACTOR2} ist,
	 * wird die verstrichene Zeit zur gesammt {@link Step#REFACTOR2 REFACTOR2}-Zeit aufsummiert.
	 * Es wird nun nicht weiter gezählt.
	 */
	public void finishedREFACTOR2() {
		if(!currentState.equals(REFACTOR2)) return;
		secondsREFACTOR2 += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#ACCEPTANCE_RED ACCEPTANCE_RED} ist,
	 * wird nun die verstrichene Zeit für den {@link Step#ACCEPTANCE_RED ACCEPTANCE_RED}-{@link Step} gezählt.
	 */
	public void startACCEPTANCE(){
		if(!currentState.equals(ACCEPTANCE_RED)) return;
		currentStartTime = LocalTime.now();
	}

	/**
	 * Wenn der aktuelle {@link Step} {@link Step#ACCEPTANCE_RED ACCEPTANCE_RED} ist,
	 * wird die verstrichene Zeit zur gesammt {@link Step#ACCEPTANCE_RED ACCEPTANCE_RED}-Zeit aufsummiert.
	 * Es wird nun nicht weiter gezählt.
	 */
	public void finishedACCEPTANCE() {
		if(!currentState.equals(ACCEPTANCE_RED)) return;
		secondsAcceptance += currentStartTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		currentState = null;
	}

	/**
	 * Beendet den aktuellen {@link Step} und summiert die verstichene Zeit auf.
	 * Wechselt daraufhin zum nächsten {@link Step}
	 * @param acceptanceEnabled Zeigt an, ob die Akzeptanztest genutzt werden.
	 */
	public void finishedStepAndMoveOn(boolean acceptanceEnabled){
		switch (currentState){
			case RED: finishedRED(); currentState = GREEN; break;
			case GREEN: finishedGREEN(); currentState = REFACTOR1; break;
			case REFACTOR1: finishedREFACTOR1(); currentState = REFACTOR2; break;
			case REFACTOR2: finishedREFACTOR2(); if(acceptanceEnabled)currentState = ACCEPTANCE_RED; else currentState = RED; break;
			case ACCEPTANCE_RED: finishedACCEPTANCE(); currentState = RED; break;
		}
	}

	/**
	 * Speichert die {@link CompileError}s, welche der Benutzer in dem {@link Step#GREEN GREEN}- oder {@link Step#REFACTOR1 REFACTOR1}-{@link Step} erzeugt.
	 * @param compileErrors Eine {@link Collection} welche die {@link CompileError}s enthält.
	 */
	public void addCompileExceptions(Collection<CompileError> compileErrors){
		if(currentState == RED) {
			compileErrors.forEach(x -> {
				String s = x.getMessage();
				if(s.contains("cannot be applied to given types")) s = "method <Method> in class <Class> cannot be applied to given types";
				if(s.contains("incompatible types:"))  s = "incompatible types:";
				if (compileErrorMapGREEN.containsKey(s))
					compileErrorMapGREEN.replace(s, compileErrorMapGREEN.get(s), compileErrorMapGREEN.get(s) + 1);
				else compileErrorMapGREEN.put(s, 1);
			});
		} else if(currentState == REFACTOR1){
			compileErrors.forEach(x -> {
				String s = x.getMessage();
				if(s.contains("cannot be applied to given types")) s = "method <Method> in class <Class> cannot be applied to given types";
				if(s.contains("incompatible types:"))  s = "incompatible types:";
				if (compileErrorMapREFACTOR.containsKey(s))
					compileErrorMapREFACTOR.replace(s, compileErrorMapREFACTOR.get(s), compileErrorMapREFACTOR.get(s) + 1);
				else compileErrorMapREFACTOR.put(s, 1);
			});
		}
	}

	void printCMap(){
		compileErrorMapGREEN.forEach((x, y) -> System.out.println(x+" "+y));
	}

	/**
	 * Speichert die {@link TestFailure}s, welche vom Benutzer erzeugt werden.
	 * @param testErrors Eine {@link Collection} welche die {@link TestFailure}s enthält.
	 */
	public void addTestExceptions(Collection<TestFailure> testErrors){
		testErrors.forEach(x -> {
			String s = x.getMessage();
			if(s.contains("cannot be applied to given types")) s = "method <Method> in class <Class> cannot be applied to given types";
			if(s.contains("incompatible types:"))  s = "incompatible types:";
			if(testErrorMap.containsKey(s)) compileErrorMapGREEN.replace(s, compileErrorMapGREEN.get(s), compileErrorMapGREEN.get(s)+1);
			else testErrorMap.put(s, 1);
		});
	}

	/**
	 * Nimmt zwei {@link List}en, welche Dateien repräsentieren, und erzeugt ein {@link Pair},
	 * welches den Zeitpunkt und die Unterschiede enthält.
	 * @param origin Die Ursprungsdatei
	 * @param current Die geänderte Datei
	 * @param className Der Klassenname der Datei
	 * @return das {@link Pair}
	 */
	public Pair<LocalDateTime, List<String>> diff(List<String> origin, List<String> current, String className){
		Patch patch = DiffUtils.diff(origin, current/*,new MyersDiff()*/);
		List<Delta> deltas = patch.getDeltas();
		List<String> diffList = DiffUtils.generateUnifiedDiff(className, className, origin, patch, 2);
		return new Pair<>(LocalDateTime.now(), diffList);
	}

	/**
	 * Gibt die Zeit, welche der Benutzer in dem {@link Step#GREEN GREEN}-{@link Step} verbracht hat.
	 * @return Der aktuelle Wert.
	 */
	public int getTimeForGREEN(){
		return secondsGREEN;
	}

	/**
	 * Gibt die Zeit, welche der Benutzer in dem {@link Step#RED RED}-{@link Step} verbracht hat.
	 * @return Der aktuelle Wert.
	 */
	public int getTimeForRED(){
		return secondsRED;
	}

	/**
	 * Gibt die Zeit, welche der Benutzer in dem {@link Step#REFACTOR1 REFACTOR1}-{@link Step} verbracht hat.
	 * @return Der aktuelle Wert.
	 */
	public int getTimeForREFACTOR1(){
		return secondsREFACTOR;
	}

	/**
	 * Gibt die Zeit, welche der Benutzer in dem {@link Step#REFACTOR2 REFACTOR2}-{@link Step} verbracht hat.
	 * @return Der aktuelle Wert.
	 */
	public int getTimeForREFACTOR2() {return secondsREFACTOR2; }

	/**
	 * Gibt die Zeit, welche der Benutzer in dem {@link Step#ACCEPTANCE_RED ACCEPTANCE_RED}-{@link Step} verbracht hat.
	 * @return Der aktuelle Wert.
	 */
	public int getTimeForACCEPTANCE(){
		return secondsREFACTOR;
	}

	/**
	 * Erzeugt ein {@link BarChart Balkendiagramm}, welches die {@link CompileError}s und ihre Häufigkeit im {@link Step#RED RED}-{@link Step} darstellt.
	 * @return Das Diagramm.
	 */
	public Chart showCompileErrorGREENChart(){
		CategoryAxis categoryAxis = new CategoryAxis();
		NumberAxis numberAxis = new NumberAxis();
		BarChart<Number, String> chart = new BarChart<>(numberAxis, categoryAxis);
		XYChart.Series<Number,String> series = new XYChart.Series<>();
		series.setName("GREEN");
		compileErrorMapGREEN.forEach((s, m) -> series.getData().add(new XYChart.Data<>(m, s)));
		chart.getData().add(series);
		return chart;
	}

	/**
	 * Erzeugt ein {@link BarChart Balkendiagramm}, welches die {@link CompileError}s und ihre Häufigkeit im {@link Step#REFACTOR1 REFACTOR1}-{@link Step} darstellt..
	 * @return Das Diagramm.
	 */
	public Chart showCompileErrorREFACTORChart(){
		CategoryAxis categoryAxis = new CategoryAxis();
		NumberAxis numberAxis = new NumberAxis();
		BarChart<Number, String> chart = new BarChart<>(numberAxis, categoryAxis);
		XYChart.Series<Number,String> series = new XYChart.Series<>();
		series.setName("REFACTOR");
		compileErrorMapREFACTOR.forEach((s, m) -> series.getData().add(new XYChart.Data<>(m, s)));
		chart.getData().add(series);
		return chart;
	}

	/**
	 * Erzeugt ein {@link BarChart Balkendiagramm}, welches die {@link CompileError}s und ihre Häufigkeit im {@link Step#RED RED}- und {@link Step#REFACTOR1 REFACTOR1}-{@link Step} darstellt.
	 * @return Das Diagramm.
	 */
	public Chart showCompileErrorChart(){
		CategoryAxis categoryAxis = new CategoryAxis();
		NumberAxis numberAxis = new NumberAxis();
		BarChart<Number, String> chart = new BarChart<>(numberAxis, categoryAxis);
		XYChart.Series<Number,String> seriesGREEN = new XYChart.Series<>();
		seriesGREEN.setName("GREEN");
		XYChart.Series<Number,String> seriesREFACTOR = new XYChart.Series<>();
		seriesREFACTOR.setName("REFACTOR");
		compileErrorMapGREEN.forEach((s, m) -> seriesGREEN.getData().add(new XYChart.Data<>(m, s)));
		compileErrorMapREFACTOR.forEach((s, m) -> seriesREFACTOR.getData().add(new XYChart.Data<>(m, s)));
		chart.getData().add(seriesGREEN);
		chart.getData().add(seriesREFACTOR);
		return chart;
	}

	/**
	 * Erzeugt ein {@link BarChart Balkendiagramm}, welches die {@link TestFailure}s und ihre Häufigkeit darstellt.
	 * @return Das Diagramm.
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
	 * Erzeugt ein {@link PieChart Kuchendiagramm}, welches die Zeit, die der Benutzer in den einzelnen {@link Step}s verbracht hat, anzeigt.
	 * @param acceptanceEnabled Zeigt an, ob die Akzeptanztest benutzt werden.
	 * @return Das Diagramm
	 */
	public Chart showTimeChart(boolean acceptanceEnabled){
		String s = "vier";
		ObservableList<PieChart.Data> chartData =
				FXCollections.observableArrayList(
						new PieChart.Data("RED", secondsRED),
						new PieChart.Data("GREEN", secondsGREEN),
						new PieChart.Data("REFACTOR1", secondsREFACTOR),
						new PieChart.Data("REFACTOR2", secondsREFACTOR2));
		if(acceptanceEnabled){ chartData.add(new PieChart.Data("ACCEPTANCE", secondsAcceptance)); s = "fünf";}
		PieChart chart = new PieChart(chartData);
		chart.setTitle("Verteilung der Arbeitszeit auf die "+s+" Phasen");
		return chart;
	}
}
