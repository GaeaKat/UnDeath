package com.nekokittygames.modjam.UnDeath;

import net.minecraftforge.common.config.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class Configs {

	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgId {
		public boolean block() default false;
	}
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgBool {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgString {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgDouble {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgInteger {}

	

	@CfgDouble
	public static double ZombificationChance=1.0;
	
	@CfgDouble
	public static double SkellificationChance=1.0;
	
	@CfgDouble
	public static double slimeEngulfChance=1.0;

    @CfgString
	public static String undeadkillsYouAchivementID="undead:undeadkillsyou";
	
	@CfgString
	public static String YoukillsYouAchivementID="undead:youkillsyou";
	
	public static void  load(Configuration config) {
		try {
			config.load();
			Field[] fields = Configs.class.getFields();
			for(Field field : fields) {
					if(field.isAnnotationPresent(CfgBool.class)){
						boolean bool = field.getBoolean(null);
						bool = config.get(Configuration.CATEGORY_GENERAL,
								field.getName(), bool).getBoolean(bool);
						field.setBoolean(null, bool);
					}
					else if(field.isAnnotationPresent(CfgString.class))
					{
						String string=(String)field.get(null);
						string=config.get(Configuration.CATEGORY_GENERAL, field.getName(), string).getString();
						field.set(null, string);
					}
					else if(field.isAnnotationPresent(CfgDouble.class))
					{
						double doub=field.getDouble(null);
						doub=config.get(Configuration.CATEGORY_GENERAL, field.getName(), doub).getDouble(doub);
						field.set(null, doub);
					}
					else if(field.isAnnotationPresent(CfgInteger.class))
					{
						int integer=field.getInt(null);
						integer=config.get(Configuration.CATEGORY_GENERAL, field.getName(), integer).getInt();
						field.set(null, integer);
					}

			}
		} catch(Exception e) {
			//UnDeath.logging.log(Level.SEVERE, "Got an error in loading config!", e);
		} finally {
			config.save();
		}
	}
}
