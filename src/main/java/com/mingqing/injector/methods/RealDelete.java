package com.mingqing.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.mingqing.injector.enmus.CustomSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class RealDelete extends AbstractMethod {

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
      TableInfo tableInfo) {
    String sql;
    CustomSqlMethod sqlMethod = CustomSqlMethod.REAL_DELETE;
    sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
        sqlWhereEntityWrapper(true, tableInfo), sqlComment());
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
    return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
  }
}
