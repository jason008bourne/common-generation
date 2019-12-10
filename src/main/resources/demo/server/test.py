[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectPath + "/test/" + classNameLower  + "_test.py")}[#t  /]
#!/usr/bin/env python
# -*- coding: utf-8 -*-

import unittest
from .test_base import BaseTestCase
import requests
import json
from biz import ${classNameLower}_biz

api_url = "http://127.0.0.1:5000/open"
headers = {"token": "", "Content-Type": "application/json;charset=UTF-8"}


class Test${className}Biz(BaseTestCase):

    def test_get(self):
        param = {
            "id": 1
        }
        print(${classNameLower}_biz.get(**param))

    def test_save(self):
        param = {

        }
        print(${classNameLower}_biz.save(**param))

    def test_edit(self):
        param = {
    [#list table.columns as field]
        [#if field.asType=='String']
            "${field.sqlName}": "字符串",
        [#elseif field.asType=='Long']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Integer']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Boolean']
            "${field.sqlName}": True,
        [#elseif field.asType=='Enum']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Date']
            "${field.sqlName}": "1970-01-01 12:12:12",
        [#elseif field.asType=='BigDecimal']
            "${field.sqlName}": "1.22",
        [#elseif field.asType=='Object']
        [#else]
            "${field.sqlName}": "字符串",
        [/#if]
    [/#list]
            "id": 1
        }
        print(${classNameLower}_biz.edit(**param))

    def test_list(self):
        param = {
        }
        print(${classNameLower}_biz.query(**param))

    def test_remove(self):
        param = {
            "id": 1
        }
        print(${classNameLower}_biz.remove(**param))


class Test${className}Ctl(unittest.TestCase):

    def test_get(self):
        param = {
            "id": 1
        }
        response = requests.post(api_url + "${ctlUrlPrefix}/get", data=json.dumps(param), headers=headers)
        print(response.text)

    def test_save(self):
        param = {
        }
        response = requests.post(api_url + "${ctlUrlPrefix}/save", data=json.dumps(param), headers=headers)
        print(response.text)

    def test_edit(self):
        param = {
    [#list table.columns as field]
        [#if field.asType=='String']
            "${field.sqlName}": "字符串",
        [#elseif field.asType=='Long']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Integer']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Boolean']
            "${field.sqlName}": True,
        [#elseif field.asType=='Enum']
            "${field.sqlName}": 1,
        [#elseif field.asType=='Date']
            "${field.sqlName}": "1970-01-01 12:12:12",
        [#elseif field.asType=='BigDecimal']
            "${field.sqlName}": "1.22",
        [#elseif field.asType=='Object']
        [#else]
            "${field.sqlName}": "字符串",
        [/#if]
    [/#list]
            "id": 1
        }
        response = requests.post(api_url + "${ctlUrlPrefix}/edit", data=json.dumps(param), headers=headers)
        print(response.text)

    def test_list(self):
        param = {
        }
        response = requests.post(api_url + "${ctlUrlPrefix}/list", data=json.dumps(param), headers=headers)
        print(response.text)

    def test_remove(self):
        param = {
            "id": 1
        }
        response = requests.post(api_url + "${ctlUrlPrefix}/remove", data=json.dumps(param), headers=headers)
        print(response.text)


if __name__ == "__main__":
    unittest.main()
