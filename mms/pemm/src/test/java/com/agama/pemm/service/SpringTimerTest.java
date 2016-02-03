package com.agama.pemm.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringTimerTest {
 
 /**
  * 启动时执行一次，之后每隔3秒执行一次
  */
// @Scheduled(fixedRate = 1000 * 3)
 public void print() {
  System.out.println("timer running...");
 }
  
 /**
  * 定时启动。每天凌晨 16:19 执行一次
  */
// @Scheduled(cron = "0 19 16 * * *")
 public void show() {
  System.out.println("定时器启动...");
 }
 public static void main(String[] args) {
	 ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-core.xml");
	 System.out.println("----执行完成");
	
}
}

