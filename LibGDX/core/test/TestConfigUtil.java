import static com.vormadal.turborocket.configurations.ConfigUtil.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch.Config;
import com.vormadal.turborocket.configurations.ConfigUtil;
public class TestConfigUtil {

	private static final int prop1 = 10;
	private static final float prop2 = 12.0f;
	private static final String prop3 = "test";
	private static final Integer prop4 = 32;
	
	@BeforeClass
	public static void init(){
		setPath("test.properties");
		saveProp("key1", prop1);
		saveProp("key2", prop2);
		saveProp("key3", prop3);
		saveProp("key4", prop4);
	}
	
	@Test
	public void test1(){
		int value = readInt("key1");
		Assert.assertEquals(prop1, value);
	}
	
	@Test
	public void test2(){
		saveProp("key5", 10);
		float value = readFloat("key2");
		Assert.assertTrue(value == prop2);
		removeProp("key5");
	}
	
	@Test
	public void test3(){
		saveProp("key1", 1);
		int value = readInt("key1");
		Assert.assertTrue(value == 1);
		saveProp("key1", prop1);
	}
	
	@AfterClass
	public static void cleanup(){
		removeProp("key1");
		removeProp("key2");
		removeProp("key3");
		removeProp("key4");
		resetPath();
		
	}
}
