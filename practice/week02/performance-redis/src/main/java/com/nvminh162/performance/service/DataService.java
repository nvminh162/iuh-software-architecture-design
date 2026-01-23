package com.nvminh162.performance.service;

import com.nvminh162.performance.model.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class DataService {

	/**
	 * Mô phỏng một thao tác chậm (như query database)
	 * Giả lập thời gian xử lý từ 150-200ms
	 */
	public DataResponse getDataWithoutCache(String id) {
		long startTime = System.currentTimeMillis();
		
		// Mô phỏng thời gian query database (150-200ms)
		simulateSlowOperation();
		
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		
		log.info("Lấy dữ liệu KHÔNG dùng cache cho ID: {}, Thời gian: {}ms", id, responseTime);
		
		return new DataResponse(id, "Data for " + id, responseTime, false);
	}

	/**
	 * Lấy dữ liệu với Redis cache
	 * Lần đầu sẽ chậm (150-200ms), các lần sau sẽ nhanh (< 10ms)
	 */
	@Cacheable(value = "dataCache", key = "#id")
	public DataResponse getDataWithCache(String id) {
		long startTime = System.currentTimeMillis();
		
		// Mô phỏng thời gian query database (150-200ms)
		simulateSlowOperation();
		
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		
		log.info("Lấy dữ liệu CÓ dùng cache cho ID: {}, Thời gian: {}ms (Lần đầu - chưa có cache)", id, responseTime);
		
		return new DataResponse(id, "Data for " + id, responseTime, false);
	}

	/**
	 * Mô phỏng thao tác chậm (như query database phức tạp)
	 */
	private void simulateSlowOperation() {
		try {
			// Giả lập thời gian xử lý từ 150-200ms
			Random random = new Random();
			int delay = 150 + random.nextInt(50); // 150-200ms
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
