package com.mingqing.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.mingqing.injector.enmus.CustomSqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class DeleteBatchIdsAutoFill extends AbstractMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql;
		CustomSqlMethod sqlMethod = CustomSqlMethod.LOGIC_DELETE_BATCH_BY_IDS_WITH_AUTO_FILL;

		if (tableInfo.isWithLogicDelete()) {
			sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo), tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA), tableInfo.getLogicDeleteSql(true, true));
			SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
			return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
		} else {
			sqlMethod = CustomSqlMethod.DELETE_BATCH_BY_IDS_WITH_AUTO_FILL;
			sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
			SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
			return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
		}
	}
}
