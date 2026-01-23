package com.nvminh162.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
	private String id;
	private String data;
	private long responseTimeMs;
	private boolean fromCache;
}
