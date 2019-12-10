[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectPath + "/biz/" + classNameLower  + "_biz.py")}[#t  /]
#!/usr/bin/env python
# -*- coding: utf-8 -*-

from base import bean_util
from base.model import Page
from base.biz import BaseService
from base.biz import BizUtil
from model.${dataSourceName}.${classNameLower}_model import ${className}DO


def query(**arg_dict):
    res = ${className}Service.query(**arg_dict)
    if res:
        return res.__dict__


def get(**arg_dict):
    res = ${className}Service.get(**arg_dict)
    if res:
        return bean_util.sqlalchemy_obj_to_dict(res)


def save(**arg_dict):
    """做参数过滤，基础校验等工作"""
    obj = bean_util.to_obj(${className}DO, **arg_dict)
    res = ${className}Service.save(obj)
    if res:
        return bean_util.sqlalchemy_obj_to_dict(res)


def edit(**arg_dict):
    """做参数过滤，基础校验等工作"""
    obj = bean_util.to_obj(${className}DO, **arg_dict)
    BizUtil.check_assert(obj.id, "id")
    res = ${className}Service.edit(obj)
    if res:
        return bean_util.sqlalchemy_obj_to_dict(res)


def remove(**arg_dict):
    """做参数过滤，基础校验等工作"""
    obj = bean_util.to_obj(${className}DO, **arg_dict)
    res = ${className}Service.delete_by_id(obj)
    ${className}Service.commit()
    if res:
        return bean_util.sqlalchemy_obj_to_dict(res)


class ${className}Service(BaseService):

    @classmethod
    def save(cls, po):
        """balabala 写你的逻辑 """
        cls.insert(po)
        cls.commit()
        return po

    @classmethod
    def edit(cls, po):
        obj = cls.update_by_id(po)
        cls.commit()
        return obj

    @classmethod
    def get(cls, **arg_dict):

        q = ${className}DO.gen_query(**arg_dict)

        """拼接其他条件"""
        return q.first()

    @classmethod
    def query(cls, **arg_dict):

        q = ${className}DO.gen_query(**arg_dict)
        """拼接其他条件"""
        page_model = Page.gen_page(**arg_dict)
        page_result = q.paginate(page_model.page, page_model.size)
        return page_model.copy_page(page_result)

    @classmethod
    def remove(cls, **arg_dict):
        q = ${className}DO.gen_query(**arg_dict)
        row_count = q.delete(synchronize_session=False)
        cls.commit()
        """拼接其他条件"""
        return row_count
