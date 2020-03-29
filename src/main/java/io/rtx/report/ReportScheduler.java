package io.rtx.report;

import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportScheduler {

	private static final long SECONDS_30 = 30 * 1000;

	@Scheduled(fixedDelay = 5000) // 5.000 millisecond = 5s
	public void sayHello1() {
		System.out.println("Hello  5s : " + LocalTime.now());
	}

	@Scheduled(fixedDelay = SECONDS_30)
	public void sayHello2() {
		System.out.println("Hello 30s : " + LocalTime.now());
	}
}
