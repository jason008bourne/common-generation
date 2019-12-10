[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectPath + "/routes/ctl/" + classNameLower  + "_ctl.py")}[#t  /]
#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import request
from flask import current_app as app
from routes import open_api as api
from base.biz import BizUtil
from base.biz import Result
from biz import ${classNameLower}_biz


@api.route("${ctlUrlPrefix}/save",  methods=["POST"])
def ${classNameLower}_save():
    try:
        args = request.get_json()
        return Result.success(${classNameLower}_biz.save(**args))
    except Exception as e:
        return BizUtil.handle_error("save error", e)


@api.route("${ctlUrlPrefix}/edit",  methods=["POST"])
def ${classNameLower}_edit():
    try:
        args = request.get_json()
        return Result.success(${classNameLower}_biz.edit(**args))
    except Exception as e:
        return BizUtil.handle_error("edit error", e)


@api.route("${ctlUrlPrefix}/get",  methods=["POST"])
def ${classNameLower}_get():
    try:
        args = request.get_json()
        return Result.success(${classNameLower}_biz.get(**args))
    except Exception as e:
        return BizUtil.handle_error("get error", e)


@api.route("${ctlUrlPrefix}/list",  methods=["POST"])
def ${classNameLower}_list():
    try:
        args = request.get_json()
        return Result.success(${classNameLower}_biz.query(**args))
    except Exception as e:
        return BizUtil.handle_error("list error", e)


@api.route("${ctlUrlPrefix}/remove",  methods=["POST"])
def ${classNameLower}_remove():
    try:
        args = request.get_json()
        return Result.success(${classNameLower}_biz.remove(**args))
    except Exception as e:
        return BizUtil.handle_error("remove error", e)

