[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(projectName + "/src/views/" + dataSourceName + "/" + classNameLower  + ".vue")}[#t  /]

<template>
  <div class="app-container">
    <div class="filter-container">
    [#list table.columns as field]
    [#if field.asType=='String']
      <el-input v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" clearable style="width: 140px;" class="filter-item"  />
    [#elseif field.asType=='Long']
      <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable style="width: 100px;" class="filter-item"  />
    [#elseif field.asType=='Integer']
      <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable style="width: 100px;" class="filter-item"  />
    [#elseif field.asType=='Boolean']
      <el-select v-model="listQuery.${field.sqlName}" placeholder="是否${field.columnAlias}" clearable class="filter-item" style="width: 140px">
        <el-option v-for="item in boolOptions" :key="item.k" :label="item.v" :value="item.k" />
      </el-select>
    [#elseif field.asType=='Enum']
      <el-select v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" clearable class="filter-item" style="width: 140px">
        <el-option v-for="item in ${field.sqlName}Options" :key="item.k" :label="item.v" :value="item.k" />
      </el-select>
    [#elseif field.asType=='Date']
    [#elseif field.asType=='BigDecimal']
      <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable style="width: 100px;" class="filter-item"/>
    [#elseif field.asType=='Object']
    [#else]
      <el-input v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" style="width: 100px;" clearable class="filter-item"/>
    [/#if]
    [/#list]
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        基本搜索
      </el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="showSearch">
        复杂搜索
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.id }}</span>
        </template>
      </el-table-column>

    [#list table.columns as field]
    [#if field.asType=='String']
      <el-table-column label="${field.columnAlias}" width="120px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Long']
      <el-table-column label="${field.columnAlias}" width="100px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Integer']
      <el-table-column label="${field.columnAlias}" width="100px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Boolean']
      <el-table-column label="${field.columnAlias}" width="100px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} | booleanFilter }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Enum']
      <el-table-column label="${field.columnAlias}" width="60px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} | ${field.sqlName}Filter }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Date']
      <el-table-column label="${field.columnAlias}" width="140px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='BigDecimal']
      <el-table-column label="${field.columnAlias}" width="100px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [#elseif field.asType=='Object']
    [#else]
      <el-table-column label="${field.columnAlias}" width="50px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.sqlName} }}</span>
        </template>
      </el-table-column>
    [/#if]
    [/#list]
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleModifyStatus(row,'delete')">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.size" @pagination="getList" />

    <el-dialog title="content内容"  :visible.sync="dialogContentVisible" width="50%">
      <el-table :data="contentData">
        <el-table-column property="k" label="键" ></el-table-column>
        <el-table-column property="v" label="值" ></el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog :visible.sync="dialogSearchVisible" width="99%">
      <el-row>
        <el-col span=9>
          <el-card :body-style="{ padding: '1px' }">
            <div slot="header" class="clearfix">
              <span>基本查询</span>
            </div>
    [#list table.columns as field]
    [#if field_index % 3 == 0]
      [#if field_index > 0]
            </el-row>
      [/#if]
            <el-row>
    [/#if]
    [#if field.asType=='String']
              <el-col span=8 align="center">
                <el-input v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" clearable />
              </el-col>
    [#elseif field.asType=='Long']
              <el-col span=8 align="center">
                <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable />
              </el-col>
    [#elseif field.asType=='Integer']
              <el-col span=8 align="center">
                <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable  />
              </el-col>
    [#elseif field.asType=='Boolean']
              <el-col span=8 align="center">
                <el-select v-model="listQuery.${field.sqlName}" placeholder="是否${field.columnAlias}" clearable>
                  <el-option v-for="item in boolOptions" :key="item.k" :label="item.v" :value="item.k" />
                </el-select>
              </el-col>
    [#elseif field.asType=='Enum']
              <el-col span=8 align="center">
                <el-select v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" clearable >
                  <el-option v-for="item in ${field.sqlName}Options" :key="item.k" :label="item.v" :value="item.k" />
                </el-select>
              </el-col>
    [#elseif field.asType=='Date']
    [#elseif field.asType=='BigDecimal']
              <el-col span=8 align="center">
                <el-input v-model.number="listQuery.${field.sqlName}" type="number" placeholder="${field.columnAlias}" clearable />
              </el-col>
    [#elseif field.asType=='Object']
    [#else]
              <el-col span=8 align="center">
                <el-input v-model="listQuery.${field.sqlName}" placeholder="${field.columnAlias}" clearable/>
              </el-col>
    [/#if]
    [#if (field_index % 3 != 0 ) && (!field_has_next)]
            </el-row>
    [/#if]
    [/#list]
          </el-card>
        </el-col>

        <el-col span=5>
          <el-card :body-style="{ padding: '1px' }">
            <div slot="header" class="clearfix">
              <span>拼接and查询</span>
              <el-button @click="addAndQueryItem" style="float: right; padding: 3px 0" type="text">增加</el-button>
            </div>
          <el-row v-for="(item, index) in andQueryItems" :key="index">
            <el-col span=10 align="center">
              <el-input v-model="item.k" placeholder="请输入key" ></el-input>
            </el-col>
            <el-col span=10 align="center">
              <el-input v-model="item.v" placeholder="请输入value"  ></el-input>
            </el-col>
            <el-col span=4 align="center">
              <el-button size="mini" type="danger" @click="deleteAndQueryItem(item, index)">
                删除
              </el-button>
            </el-col>
          </el-row>
          </el-card>
        </el-col>

        <el-col span=5>
          <el-card :body-style="{ padding: '1px' }">
            <div slot="header" class="clearfix">
              <span>拼接or查询</span>
              <el-button @click="addOrQueryItem" style="float: right; padding: 3px 0" type="text">增加</el-button>
            </div>
          <el-row v-for="(item, index) in orQueryItems" :key="index">
            <el-col span=10 align="center">
              <el-input v-model="item.k" placeholder="请输入key"  ></el-input>
            </el-col>
            <el-col span=10 align="center">
              <el-input v-model="item.v" placeholder="请输入value"  ></el-input>
            </el-col>
            <el-col span=4 align="center">
              <el-button size="mini" type="danger" @click="deleteOrQueryItem(item, index)">
                删除
              </el-button>
            </el-col>
          </el-row>
          </el-card>
        </el-col>

        <el-col span=5>
          <el-card :body-style="{ padding: '1px' }">
            <div slot="header" class="clearfix">
              <span>排序规则</span>
              <el-button @click="addSortItem" style="float: right; padding: 3px 0" type="text">增加</el-button>
            </div>
            <el-row v-for="(item, index) in sortItems" :key="index">
            <el-col span=12 align="center">
              <el-select v-model="item.field" placeholder="排序字段">
                <el-option v-for="item in fieldOptions" :key="item.k" :label="item.v" :value="item.k" />
              </el-select>
            </el-col>
            <el-col span=7 align="center">
              <el-select v-model="item.sortAsc" placeholder="排序">
                <el-option v-for="item in sortOptions" :key="item.k" :label="item.v" :value="item.k" />
              </el-select>
            </el-col>
            <el-col span=5 align="center">
              <el-button size="mini" type="danger" @click="deleteSortItem(item, index)">
                删除
              </el-button>
            </el-col>
          </el-row>
          </el-card>
        </el-col>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleFilter">
          查询
        </el-button>
        <el-button type="primary" @click="resetQuery">
          重置
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, fetchOne} from '@/api/${dataSourceName}/${classNameLower}'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

[#list table.columns as column]
	[#if column.asType=='Enum']
const ${column.sqlName}Options = [
	[#list column.enumList as e]
  { k: ${e.enumKey}, v: '${e.enumDesc}' }[#if e_has_next],[/#if]

	[/#list]
]
const ${column.sqlName}KV = ${column.sqlName}Options.reduce((acc, cur) => {
  acc[cur.k] = cur.v
  return acc
}, {})
	[#elseif column.asType=='Object']
	[#else]
	[/#if]
[/#list]
const boolOptions = [
  { k: true, v: '是' },

  { k: false, v: '否' }

]
const boolKV = boolOptions.reduce((acc, cur) => {
  acc[cur.k] = cur.v
  return acc
}, {})

const fieldOptions = [
  [#list table.columns as column]
  { k: '${column.sqlName}', v: '${column.columnAlias}', t: '${column.asType}' },
  [/#list]
  { k: 'id', v: 'ID', t: 'Integer' }
]

const sortOptions = [
  { k: true, v: '升序' },

  { k: false, v: '降序' }

]
const sortKV = sortOptions.reduce((acc, cur) => {
  acc[cur.k] = cur.v
  return acc
}, {})

export default {
  name: '${dataSourceName}-${classNameLower}',
  components: { Pagination },
  directives: { waves },
  filters: {
    booleanFilter(k) {
      return boolKV[k]
    },
[#list table.columns as column]
	[#if column.asType=='Enum']
    ${column.sqlName}Filter(k) {
      return ${column.sqlName}KV[k]
    },
	[#elseif column.asType=='Object']
	[#else]
	[/#if]
[/#list]
    parseTime
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: null,
        size: 20,
    [#list table.columns as field]
        ${field.sqlName}: null,
    [/#list]
        andContent: null,
        orContent: null,
        sorts: null,
        id: null
      },
[#list table.columns as column]
	[#if column.asType=='Enum']
      ${column.sqlName}Options: ${column.sqlName}Options,
	[#elseif column.asType=='Object']
	[#else]
	[/#if]
[/#list]
      boolOptions : boolOptions,
      sortOptions : sortOptions,
      fieldOptions : fieldOptions,
      andQueryItems :[],
      orQueryItems :[],
      sortItems :[],
      contentData : [],
      dialogContentVisible:false,
      dialogSearchVisible: false,
      downloadLoading: false
    }
  },
  computed: {
    // 计算属性的 getter
    andContent: function () {
      return this.andQueryItems.reduce((acc, cur) => {
        if(cur.k !== '' && cur.v !== ''){
          acc[cur.k] = cur.v
        }
        return acc
      }, {})
    }
    ,orContent: function () {
      return this.orQueryItems.reduce((acc, cur) => {
        if(cur.k !== '' && cur.v !== ''){
          acc[cur.k] = cur.v
        }
        return acc
      }, {})
    }
    ,sorts: function () {
      return this.sortItems.filter(item => item.field !== '' && item.sortAsc !== '')
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.body.items
        this.total = response.body.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    resetQuery() {
      this.listQuery = {
    [#list table.columns as field]
        ${field.sqlName}: null,
    [/#list]
        id: null,
        andContent: null,
        orContent: null,
        sorts: null,
        page: 1
      }
      this.andQueryItems=[]
      this.orQueryItems=[]
      this.sortItems=[]
    },
    addAndQueryItem() {
      this.andQueryItems.push({ 'k': '', 'v': '' })
    },
    deleteAndQueryItem(item,index) {
      this.andQueryItems.splice(index, 1)
    },
    addOrQueryItem() {
      this.orQueryItems.push({ 'k': '', 'v': '' })
    },
    deleteOrQueryItem(item,index) {
      this.orQueryItems.splice(index, 1)
    },
    addSortItem() {
      this.sortItems.push({ 'field': '', 'sortAsc': '' })
    },
    deleteSortItem(item,index) {
      this.sortItems.splice(index, 1)
    },
    handleFilter() {
      this.listQuery.page = 1
      this.listQuery.andContent = this.andContent
      this.listQuery.orContent = this.orContent
      this.listQuery.sorts = this.sorts
      this.getList()
    },
    showSearch() {
      this.dialogSearchVisible = true
    },
    showDetail(rc) {
      let ct = this.contentData = []
      if(rc!==undefined && rc!==null){
      rc.forEach(function(content) {
        let inx = content.indexOf(':')
        let ck = content.substring(0,inx)
        let cv = content.substring(inx+1)
        ct.push({ 'k': ck, 'v': cv })
      });
      }
      this.dialogContentVisible = true
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

