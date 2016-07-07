package de.hhu.propra16.avaders.extensions;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import vk.core.api.*;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.hhu.propra16.avaders.logik.Step.GREEN;
import static de.hhu.propra16.avaders.logik.Step.RED;
import static de.hhu.propra16.avaders.logik.Step.REFACTOR1;

public class BabystepsTest extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane gridPane = new StackPane();
		primaryStage.setScene(new Scene(gridPane,200,200));
		//HTMLEditor htmlEditor = new HTMLEditor();
		//htmlEditor.addEventHandler(KeyEvent.KEY_RELEASED,evt -> {htmlEditor.setHtmlText(highlight(htmlEditor.getHtmlText()));
		//	System.out.println(htmlEditor.getHtmlText());
		//	((WebView)htmlEditor.lookup("WebView")).getEngine().loadContent(htmlEditor.getHtmlText());
		//	((WebView)htmlEditor.lookup("WebView")).getEngine().executeScript("document.getElementById('my-id').focus()");
		//	System.out.println(htmlEditor.isFocused());
		//});
		//HBox hBox = new HBox();
		//hBox.getChildren().add(htmlEditor);
		//gridPane.add(hBox,0,1);
		//Node[] nodes = htmlEditor.lookupAll(".tool-bar").toArray(new Node[0]);
		//for(Node node : nodes)
		//{
		//	node.setVisible(false);
		//	node.setManaged(false);
		//}
		//Button style = new Button("style");
		//style.setOnAction(evt -> htmlEditor.setHtmlText(highlight(htmlEditor.getHtmlText())));
		//gridPane.add(style,0,0);
		TrackingTestDummy dummy = new TrackingTestDummy();
		dummy.generateRandomData();
		Chart chart = dummy.showCompileErrorChart();
		chart.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(chart, Priority.ALWAYS);
		gridPane.getChildren().add(chart);
		gridPane.getChildren().add(dummy.showTimeChart(false));
		//gridPane.setGridLinesVisible(true);
		System.out.println(dummy.getTimeForRED());
		System.out.println(dummy.getTimeForGREEN());
		System.out.println(dummy.getTimeForREFACTOR1());
		System.out.println(dummy.getTimeForREFACTOR2());
		primaryStage.show();
		//startTimer(gridPane);
		//startHTMLTimer(gridPane, htmlEditor);
	}

	private String highlight(String htmlText) {
		htmlText = htmlText.replace("<head></head>", "<head><style type=\"text/css\">\n" +
				"      .publicS {\n" +
				"        color: #0600BC;\n" +
				"      }\n" +
				"      .string {\n" +
				"        color: #0BA725;\n" +
				"      }\n" +
				"    </style></head>");
		//htmlText = htmlText.replace("<body contenteditable=\"true\">", "<body contenteditable='true' id='my-id'>");
		htmlText = htmlText.replace(" public ","<span class=\"publicS\"> public </span>");
		htmlText = htmlText.replace(">public ","><span class=\"publicS\">public </span>");
		htmlText = htmlText.replace(" public<","<span class=\"publicS\"> public</span><");
		htmlText = htmlText.replace(">public<","><span class=\"publicS\">public</span><");
		htmlText = htmlText.replace("<span class=\"publicS\"><span class=\"publicS\">", "<span class=\"publicS\">");
		//htmlText = htmlText.replaceAll("[^=]\"[^>]", "<span class=\"string\">\"");
		//htmlText = htmlText.replaceAll("\"[>]", "</span>");
		Matcher m = Pattern.compile("[^=][\"](?<text>[^>].*?)[\"][^>]").matcher(htmlText);
		while (m.find()){
			//System.out.println("match -> "+m.group("text") +" <-");
			htmlText = htmlText.replace("\""+m.group("text")+"\"","<span class=\"string\">\""+m.group("text")+"\"</span>");
		}
		//htmlText.replace("<span class=\"string\"><span class=\"string\">", "\"<span class=\"string\">");
		//htmlText = htmlText.replace("</span></span>", "</span>");
		//System.out.println(htmlText);
		return htmlText;
	}

	//@Test
	public void startTimer(GridPane gp) throws Exception {
		TextArea textArea = new TextArea("test");
		Button button = new Button("start");
		Babysteps babysteps = new Babysteps(textArea);
		babysteps.setOnTimeout(() -> System.out.println("aaa"));
		//button.setOnAction(evt -> babysteps.startTimer(10));
		gp.add(textArea,0,0);
		gp.add(button,0,1);
		textArea.setText("ich bin anders");
		//assertEquals("test",textArea.getText());
		//assertEquals(false, textArea.isEditable());
		//System.out.println(textArea.getText()+" re: "+babysteps.getRemainingSeconds());
		//if(!textArea.getText().equals("test")) throw new Exception("TextArea wurde nicht zurückgesetzt");
		//if(!textArea.isEditable()) throw new Exception("TextArea kann noch bearbeitet werden");
	}

	private void startHTMLTimer(GridPane gridPane, HTMLEditor htmlEditor) {
		Button bbutton = new Button("start Babysteps");
		bbutton.setOnAction(evt -> {Babysteps babysteps = new Babysteps(htmlEditor); babysteps.startTimerHTML(10);});
		gridPane.add(bbutton,1,0);

	}

	public Chart testCompileError(){
		Tracking tracking = new Tracking(RED);
		CompilationUnit compilationUnit = new CompilationUnit("penis", "public class penis{ public static äää(){return} pribate nonstatic öö(){machzurück();}}", false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compilationUnit);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		Collection<CompileError> compilerErrorsForCompilationUnit = compilerResult.getCompilerErrorsForCompilationUnit(compilationUnit);
		tracking.addCompileExceptions(compilerErrorsForCompilationUnit);
		tracking.setState(REFACTOR1);
		tracking.addCompileExceptions(compilerErrorsForCompilationUnit);
		return tracking.showCompileErrorChart();
	}

	public Chart testCycle() throws Exception{
		Tracking tracking = new Tracking();
		tracking.setState(GREEN);
		tracking.startGREEN();
		Thread.sleep(1000);
		tracking.finishedStepAndMoveOn(false);
		tracking.startRED();
		Thread.sleep(2000);
		tracking.finishedStepAndMoveOn(false);
		tracking.startREFACTOR1();
		Thread.sleep(1000);
		tracking.finishedREFACTOR1();
		return tracking.showTimeChart(false);
	}

}
