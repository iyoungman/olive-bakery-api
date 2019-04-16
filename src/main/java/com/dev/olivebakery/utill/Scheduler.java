package com.dev.olivebakery.utill;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YoungMan on 2019-04-15.
 */

@Component
public class Scheduler {

//	@Scheduled(cron = "10 * * * * MON-FRI")
	@Scheduled(cron = "0 0 21 * * MON-FRI")
	public void cronJobSch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Java cron job expression:: " + strDate);
	}
}
