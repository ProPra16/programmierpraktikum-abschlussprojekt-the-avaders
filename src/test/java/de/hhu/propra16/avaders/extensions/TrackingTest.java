package de.hhu.propra16.avaders.extensions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrackingTest {

	Tracking tracking;
	@Before
	public void initialize(){
		tracking = new Tracking();
	}
	@Test
	public void countGREEN() throws Exception{
		tracking.setState(Tracking.Phase.GREEN);
		tracking.startGREEN();
		Thread.sleep(5000);
		tracking.finishedGREEN();
		Assert.assertEquals(5, tracking.getTimeForGREEN());
	}

	@Test
	public void countRED() throws Exception{
		tracking.setState(Tracking.Phase.RED);
		tracking.startRED();
		Thread.sleep(6000);
		tracking.finishedGREEN();
		tracking.finishedRED();
		Assert.assertEquals(0, tracking.getTimeForGREEN());
		Assert.assertEquals(6,tracking.getTimeForRED());
	}

	@Test
	public void countREFACTOR() throws Exception{
		tracking.setState(Tracking.Phase.REFACTOR);
		tracking.startREFACTOR();
		Thread.sleep(4000);
		tracking.finishedREFACTOR();
		Assert.assertEquals(4, tracking.getTimeForREFACTOR());
	}

	@Test
	public void testCycle() throws Exception{
		tracking.setState(Tracking.Phase.GREEN);
		tracking.startGREEN();
		Thread.sleep(1000);
		tracking.finishedPhaseAndMoveOn();
		tracking.startRED();
		Thread.sleep(2000);
		tracking.finishedPhaseAndMoveOn();
		tracking.startREFACTOR();
		Thread.sleep(1000);
		tracking.finishedREFACTOR();
		Assert.assertEquals(1, tracking.getTimeForGREEN());
		Assert.assertEquals(2, tracking.getTimeForRED());
		Assert.assertEquals(1, tracking.getTimeForREFACTOR());
	}
}
