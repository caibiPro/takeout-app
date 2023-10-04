package com.mingqing.common.utils;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("reggie.unfiltered")
public class UnfilteredPaths {

  private List<String> paths;
}
