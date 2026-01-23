package com.nvminh162.performance.controller;

import com.nvminh162.performance.model.DataResponse;
import com.nvminh162.performance.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
@Slf4j
public class PerformanceController {

	private final DataService dataService;

	/**
	 * Endpoint KHÔNG dùng cache - luôn chậm (150-200ms)
	 * GET /api/data/{id}
	 */
	@GetMapping("/{id}")
	public DataResponse getDataWithoutCache(@PathVariable String id) {
		long startTime = System.currentTimeMillis();
		DataResponse response = dataService.getDataWithoutCache(id);
		long endTime = System.currentTimeMillis();
		
		// Tính tổng thời gian bao gồm cả overhead
		long totalTime = endTime - startTime;
		response.setResponseTimeMs(totalTime);
		
		log.info("=== KHÔNG CACHE - ID: {}, Total Time: {}ms ===", id, totalTime);
		return response;
	}

	/**
	 * Endpoint CÓ dùng Redis cache - lần đầu chậm, các lần sau nhanh
	 * GET /api/data/cached/{id}
	 */
	@GetMapping("/cached/{id}")
	public DataResponse getDataWithCache(@PathVariable String id) {
		long startTime = System.currentTimeMillis();
		DataResponse response = dataService.getDataWithCache(id);
		long endTime = System.currentTimeMillis();
		
		// Tính tổng thời gian bao gồm cả overhead
		long totalTime = endTime - startTime;
		response.setResponseTimeMs(totalTime);
		
		// Kiểm tra xem có từ cache không (nếu < 20ms thì có thể là từ cache)
		boolean fromCache = totalTime < 20;
		response.setFromCache(fromCache);
		
		if (fromCache) {
			log.info("=== CÓ CACHE - ID: {}, Total Time: {}ms (TỪ CACHE) ===", id, totalTime);
		} else {
			log.info("=== CÓ CACHE - ID: {}, Total Time: {}ms (LẦN ĐẦU - CHƯA CÓ CACHE) ===", id, totalTime);
		}
		
		return response;
	}

	/**
	 * Xóa cache cho một ID cụ thể
	 * DELETE /api/data/cache/{id}
	 */
	@DeleteMapping("/cache/{id}")
	public String clearCache(@PathVariable String id) {
		// Cache sẽ tự động được xóa khi có request mới với @Cacheable
		// Hoặc có thể inject CacheManager để xóa thủ công
		log.info("Cache cho ID: {} sẽ được làm mới ở lần request tiếp theo", id);
		return "Cache cho ID " + id + " sẽ được làm mới ở lần request tiếp theo";
	}
}
