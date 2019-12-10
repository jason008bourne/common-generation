[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectName + "/src/api/" + dataSourceName + "/" + classNameLower  + ".js")}[#t  /]

import request from '@/utils/request'

export function fetchList(data) {
  return request({
    url: '${ctlUrlPrefix}/list',
    method: 'post',
    data
  })
}

export function fetchOne(data) {
  return request({
    url: '${ctlUrlPrefix}/get',
    method: 'post',
    data
  })
}

export function saveEntity(data) {
  return request({
    url: '${ctlUrlPrefix}/save',
    method: 'post',
    data
  })
}

export function editEntity(data) {
  return request({
    url: '${ctlUrlPrefix}/edit',
    method: 'post',
    data
  })
}

export function removeEntity(data) {
  return request({
    url: '${ctlUrlPrefix}/remove',
    method: 'post',
    data
  })
}
