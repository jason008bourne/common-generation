[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(true)}[#t  /]
${gg.setOutputFile(projectPath + "/model/" + dataSourceName + "/" + classNameLower  + "_model.py")}[#t  /]
#!/usr/bin/env python
# -*- coding: utf-8 -*-

from enum import Enum, unique
from instance import db
from base.model import BaseDO


class ${className}DO(BaseDO):

  [#if dataSourceName??]
    __bind_key__ = "${dataSourceName}"
  [/#if]
    __tablename__ = "${table.sqlName}"

[#list table.columns as field]
    # ${(field.remarks)!}
  [#if field.asType=='String']
    ${field.sqlName} = db.Column(db.String(64))
  [/#if]
  [#if field.asType=='Long']
    ${field.sqlName} = db.Column(db.BigInteger)
  [/#if]
  [#if field.asType=='Integer']
    ${field.sqlName} = db.Column(db.Integer)
  [/#if]
  [#if field.asType=='Boolean']
    ${field.sqlName} = db.Column(db.Boolean, default=False)
  [/#if]
  [#if field.asType=='Enum']
    ${field.sqlName} = db.Column(db.SmallInteger)
  [/#if]
  [#if field.asType=='Date']
    ${field.sqlName} = db.Column(db.DateTime)
  [/#if]
  [#if field.asType=='BigDecimal']
    ${field.sqlName} = db.Column(db.DECIMAL)
   [/#if]

[/#list]

[#list table.columns as column]
	[#if column.asType=='Enum']
@unique
class ${column.enumClassName}(Enum):
	[#list column.enumList as e]
    ${e.enumAlias} = ${e.enumKey}
	[/#list]
	[#elseif column.asType=='Object']
	[#else]
	[/#if]
[/#list]


