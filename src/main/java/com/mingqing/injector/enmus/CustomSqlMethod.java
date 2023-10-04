package com.mingqing.injector.enmus;

import lombok.Getter;

@Getter
public enum CustomSqlMethod {
  LOGIC_DELETE_BATCH_BY_IDS_WITH_AUTO_FILL("deleteBatchIdsAutoFill",
      "根据ID集合，批量逻辑删除数据，并自动填充",
      "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),
  DELETE_BATCH_BY_IDS_WITH_AUTO_FILL("deleteBatchIdsAutoFill", "根据ID集合，批量删除数据，并自动填充",
      "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),
  REAL_DELETE("realDelete", "根据wrapper条件直接删除记录",
      "<script>\nDELETE FROM %s %s %s\n</script>");

  private final String method;

  private final String desc;

  private final String sql;

  CustomSqlMethod(String method, String desc, String sql) {
    this.method = method;
    this.desc = desc;
    this.sql = sql;
  }

}
