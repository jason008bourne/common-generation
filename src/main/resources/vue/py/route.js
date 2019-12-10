[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectName + "/src/router/" + classNameLower  + ".js")}[#t  /]

  {
    path: '/${dataSourceName}',
    component: Layout,
    redirect: '/${dataSourceName}/${classNameLower}',
    name: '${dataSourceName}',
    meta: { title: '${dataSourceName}', icon: 'example' },
    children: [
      {
        path: 'qq1',
        name: 'qq1',
        component: () => import('@/views/qq1/index'),
        meta: { title: 'qq1', icon: 'table' }
      },
      {
        path: '${classNameLower}',
        name: '${classNameLower}',
        component: () => import('@/views/${dataSourceName}/${classNameLower}'),
        meta: { title: '${(table.remarks)!}', icon: 'table' }
      }
    ]
  },