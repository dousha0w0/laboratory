<template>
  <div class='meeting-create'>
    <h1 class='meeting-create__title'>创建实验</h1>
    <el-form
        ref='meetingForm'
        :disabled='!saveVisible'
        :model='form'
        :rules='rules'
        label-width='100px'>
      <el-form-item
          label='实验名称'
          prop='name'>
        <el-input v-model='form.name'/>
      </el-form-item>
      <el-form-item
          label='内容'
          prop='content'>
        <el-input v-model='form.content'
                  :rows='4'
                  style='width: 500px'
                  type='textarea'/>
      </el-form-item>
      <el-form-item
          label='人员名单'
          prop='accountIds'>
        <el-select v-model='form.accountIds' multiple placeholder='请选择'>
          <el-option
              v-for='item in accountList'
              :key='item.id'
              :label='item.passport'
              :value='item.id'>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
          label='实验室预定'>
        <FullCalendar :options='calendarOptions'/>
      </el-form-item>
      <el-form-item label="拍照">
        <video ref="video" autoplay :style="{ height: '150px' }"></video>
        <el-button @click="snapPhoto">拍照</el-button>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <el-button v-if="$hasPermission('MEETING_CREATE') && saveVisible"
                   style='margin-left: 100px'
                   type='primary'
                   @click='submitForm'>保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Meeting from '@/api/Meeting';
import Account from '@/api/Account';
import MeetingRules from '@/rules/meeting/MeetingRules';
import Room from '@/api/Room';
import '@fullcalendar/core/vdom'; // solves problem with Vite
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import Reserve from '@/api/Reserve';


export default {
  name: 'MeetingDetail',
  components: {
    FullCalendar
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          id: '',
          name: '',
          content: '',
          accountIds: '',
          roomName: '',
          roomId: '',
          reserveStartTime: '',
          reserveEndTime: '',
          faceImage: '',
        };
      }
    }
  },
  data() {
    return {
      form: Object.assign({}, this.defaultValues),
      rules: MeetingRules,
      roomList: [],
      accountList: [],
      saveVisible: true,
      hasSelected: false,
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, resourceTimelinePlugin, bootstrapPlugin],
        initialView: 'resourceTimeline',
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
  mounted() {
    this.initCamera()
  },
  created() {
    this.queryRoomResource();
    this.queryReserveEvents();
    this.queryAccountList();
  },
  watch: {
    $route(to, from) {
      if (to.path.includes('/$tool.firstLowerCase($foreignModel)-create')) {
        this.form = this.defaultValues;
        this.defaultValues.imageList = [];
        this.saveVisible = true;
      }
    }
  },
  methods: {
    initCamera() {
      if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({video: true}).then((stream) => {
          this.$refs.video.srcObject = stream;
          this.$refs.video.play();
        });
      }
    },
    snapPhoto() {
      const canvas = document.createElement('canvas');
      canvas.width = this.$refs.video.videoWidth;
      canvas.height = this.$refs.video.videoHeight;
      const context = canvas.getContext('2d');
      context.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height);
      const imageData = canvas.toDataURL('image/jpg').split(',')[1];
      console.log(imageData)
      this.form.faceImage = imageData;
      this.$message.success('拍照成功');
    },
    eventResize(info) {
      if (info.startDelta.milliseconds + this.form.reserveStartTime < new Date().getTime()) {
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      this.form.reserveStartTime = this.form.reserveStartTime + info.startDelta.milliseconds;
      this.form.reserveEndTime = this.form.reserveEndTime + info.endDelta.milliseconds;
    },
    eventDrop(info) {
      if (this.form.reserveStartTime + info.delta.milliseconds < new Date().getTime()) {
        info.revert;
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      this.form.reserveStartTime = this.form.reserveStartTime + info.delta.milliseconds;
      this.form.reserveEndTime = this.form.reserveEndTime + info.delta.milliseconds;
    },
    handleSelect(info) {
      if (new Date(info.start).getTime() < new Date().getTime()) {
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      if (this.hasSelected) {
        this.$message.error(`一次只能预约一个时间段`);
        return;
      }
      this.hasSelected = true;
      this.form.roomId = info.resource.id;
      this.form.reserveStartTime = new Date(info.start).getTime();
      this.form.reserveEndTime = new Date(info.end).getTime();
      this.calendarOptions.events.push({
        'color': '#0099C',
        'displayEventTime': true,
        'editable': true,
        'start': info.start,
        'end': info.end,
        'title': this.form.name,
        'resourceIds': [info.resource.id]
      });
    },
    async queryAccountList() {
      const {data} = await Account.query({
        pageSize: 1000,
        pageNum: 1
      });
      this.accountList = data.rows;
    },
    async queryRoomResource() {
      const {data} = await Room.resource({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.resources = data.rows;
    },
    async queryReserveEvents() {
      const {data} = await Reserve.events({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.events = data.rows;
    },
    async submitForm() {
      try {
        await this.$refs.meetingForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }

      try {
        if (!this.form.roomId) {
          this.$message.error(`请选择实验室`);
          return;
        }
        let params = Object.assign({}, this.form);
        this.saveVisible = false;
        await Meeting.create(params);
        await this.queryReserveEvents();
        this.$emit('success');
        this.$message.success(`创建成功！`);
      } catch (e) {
        this.$message.error(`创建失败！原因：` + e.message);
      }
    }
  }
}
;
</script>

<style lang='scss'>
.meeting-create {
  &__title {
    font-size: 18px;
    font-weight: normal;
    color: #606266;
  }

  &__form {
    width: 1000px;
  }

  .el-form {
    .el-input {
      width: 250px;
    }
  }

  .item__content {
    line-height: 20px;
    display: inline-block;
    color: #606266;
  }
}
</style>
