package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Button;

public class ButtonDisplay {
	private Button stepBack;
	private Button stepFurther;
	private Button start;
	private Button save;

	public ButtonDisplay(Button stepBack, Button stepFurther, Button start, Button save){
		this.stepBack    = stepBack;
		this.stepFurther = stepFurther;
		this.start       = start;
		this.save        = save;
		show(Step.WELCOME);
	}

	public void show(Step mode){
		switch (mode){
			case WELCOME:
				stepBack.setVisible(false);
				stepFurther.setVisible(false);
				start.setVisible(true);
				start.setDisable(true);
				save.setVisible(false);
				start.setText("START");
				break;
			case GREEN:
				stepBack.setVisible(true);
				stepBack.setDisable(false);
				stepFurther.setVisible(true);
				stepFurther.setDisable(false);
				start.setVisible(false);
				save.setVisible(false);
				stepBack.setText("BACK");
				stepFurther.setText("NEXT");
				break;
			case RED:
			case TEST_REFACTOR:
			case CODE_REFACTOR:
				stepBack.setVisible(false);
				stepFurther.setVisible(true);
				stepFurther.setDisable(false);
				start.setVisible(false);
				save.setVisible(false);
				stepFurther.setText("NEXT");
				break;
		}
		if(mode == Step.TEST_REFACTOR)
			stepFurther.setText("FINISH");
	}
}
