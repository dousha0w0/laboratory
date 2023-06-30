<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 待办事项
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <FullCalendar :options="calendarOptions"/>
    </div>
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import Reserve from "@/api/Reserve";

export default {
  name: 'Meeting',
  components: {
    FullCalendar
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, resourceTimelinePlugin, bootstrapPlugin],
        initialView: 'dayGridMonth',
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        selectable: true,
        height: 600,
        resources: [],
        events: [],
        select: this.handleSelect,
        eventDrop: this.eventDrop,
        eventResize: this.eventResize
      }
    };
  },
  created() {
    this.queryMyReserveEvents();
  },
  methods: {
    async queryMyReserveEvents() {
      const {data} = await Reserve.myEvents({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.events = data.rows;
    },
  },
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
