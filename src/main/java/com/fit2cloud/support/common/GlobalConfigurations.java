package com.fit2cloud.support.common;

import com.fit2cloud.commons.utils.PropertiesConfigurer;
import org.apache.commons.lang3.StringUtils;

public class GlobalConfigurations {


	public static boolean isReleaseMode(){
		return StringUtils.equals(PropertiesConfigurer.getProperty("run.mode", "local"), "release");
	}

}
