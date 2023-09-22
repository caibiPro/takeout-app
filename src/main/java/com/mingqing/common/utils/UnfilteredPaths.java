package com.mingqing.common.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("unfiltered")
public class UnfilteredPaths {
	private List<String> paths;
}
