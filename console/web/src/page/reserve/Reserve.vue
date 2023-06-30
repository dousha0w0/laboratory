<template>
  <div>
    <div class='crumbs'>
      <el-breadcrumb separator='/'>
        <el-breadcrumb-item>
          <i class='el-icon-lx-cascades'/> 预约记录管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class='container'>
      <div class='handle-box'>
        <el-form inline>
          <el-form-item label='状态'>
            <el-select
                v-model="filter.status"
                filterable
                placeholder="请选择">
              <el-option
                  v-for="item in statusList"
                  :key="item.code"
                  :label="item.desc"
                  :value="item.code"/>
            </el-select>
          </el-form-item>
          <el-form-item label='实验室'>
            <el-input v-model='filter.roomName' class='handle-input mr10' style='width:180px'/>
          </el-form-item>
          <el-form-item label='预约人'>
            <el-input v-model='filter.accountPassport' class='handle-input mr10' style='width:180px'/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('RESERVE_QUERY')" icon='el-icon-search'
                       type='primary'
                       @click='queryList()'>查询
            </el-button>
            <el-button v-if="$hasPermission('RESERVE_EXPORT')" icon='el-icon-search'
                       type='primary'
                       @click='exportExcel()'>导出
            </el-button>
            <!--            <el-button v-if="$hasPermission('RESERVE_CREATE')" icon="el-icon-search"-->
            <!--                       type="primary"-->
            <!--                       @click="create()">创建-->
            <!--            </el-button>-->
          </el-form-item>
        </el-form>
      </div>
      <paged-table
          :current-page='filter.pageNum'
          :data='tableData'
          :page-size='filter.pageSize'
          :total='total'
          @current-change='queryList'
          @size-change='pageSizeChange'>
        <el-table-column label="状态">
          <template slot-scope="{ row }">
            <el-tag type="info" v-if="row.status ===1">未审核</el-tag>
            <el-tag type="warning" v-else-if="row.status ===2">审核中</el-tag>
            <el-tag type="success" v-else-if="row.status ===3">审核通过</el-tag>
            <el-tag type="danger" v-else-if="row.status ===4">审核失败</el-tag>
          </template>
        </el-table-column>
        <el-table-column label='内容' prop='content' show-overflow-tooltip/>
        <el-table-column label='开始时间'>
          <template slot-scope='{ row }'>{{ row.reserveStartTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label='结束时间'>
          <template slot-scope='{ row }'>{{ row.reserveEndTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label='创建时间'>
          <template slot-scope='{ row }'>{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label='修改时间'>
          <template slot-scope='{ row }'>{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label='实验室' prop='roomName'/>
        <el-table-column label='预约人' prop='accountPassport'/>
        <el-table-column align='center' label='操作' width='180'>
          <template slot-scope='scope'>
            <el-button
                v-if="$hasPermission('RESERVE_QUERY')"
                size='small'
                type='text'
                @click='detail(scope.row.id)'
            >详情
            </el-button>
            <el-button
                v-if="$hasPermission('RESERVE_DELETE')"
                size='small'
                type='text'
                @click='remove(scope.row.id)'
            >删除
            </el-button>
          </template>
        </el-table-column>
      </paged-table>
    </div>
  </div>
</template>

<script>
import Reserve from '@/api/Reserve';
import PagedTable from '@/components/Table';

export default {
  name: 'Reserve',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        status: '',
        content: '',
        roomName: '',
        accountPassport: '',
        pageNum: 1,
        pageSize: 20,
      },
      total: 0,
      accountList: [],
      roomList: [],
      tableData: [],
      statusList: [
        {'code': 1, 'desc': '所有'},
        {'code': 2, 'desc': '未审核'},
        {'code': 3, 'desc': '审核中'},
        {'code': 4, 'desc': '审核成功'},
        {'code': 5, 'desc': '审核失败'}
      ]
    };
  },
  created() {
    this.filter.status = this.statusList[0].code;
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Reserve.query(params);
      console.log(this.filter.status);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Reserve.exportExcel(params);
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    detail(id) {
      this.$router.push(`/reserve-detail?id=${id}`);
    },
    create() {
      this.$router.push(`/reserve-create`);
    },

    async remove(id) {
      await this.$confirm('是否确认未通过？', '删除预约记录', {
        type: 'warn'
      });
      try {
        await Reserve.remove(id);
        this.$message.success('删除成功！');
        await this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.mr10 {
  margin-right: 10px;
}
</style>
