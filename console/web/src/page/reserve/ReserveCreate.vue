<template>
    <div class='reserve-create'>
        <h1 class='reserve-create__title'>创建预约记录</h1>
        <el-form
            ref='reserveForm'
            :disabled='!saveVisible'
            :model='form'
            :rules='rules'
            label-width='100px'>
            <el-form-item
                label='实验室'
                prop='roomId'>
                <el-select
                    v-model='form.roomId'
                    filterable
                    placeholder='请选择'>
                    <el-option
                        v-for='item in roomList'
                        :key='item.id'
                        :label='item.name'
                        :value='item.id' />
                </el-select>
            </el-form-item>
            <el-form-item
                label='内容'
                prop='content'>
                <el-input v-model='form.content'
                          :rows='4'
                          style='width: 500px'
                          type='textarea' />
            </el-form-item>
            <el-form-item
                label='预约人'
                prop='accountId'>
                <el-select
                    v-model='form.accountId'
                    filterable
                    placeholder='请选择'>
                    <el-option
                        v-for='item in accountList'
                        :key='item.id'
                        :label='item.passport'
                        :value='item.id' />
                </el-select>
            </el-form-item>
            <el-form-item
                label='开始时间'
                prop='reserveStartTime'>
                <el-date-picker
                    v-model='form.reserveStartTime'
                    default-time='12:00:00'
                    placeholder='选择日期'
                    type='datetime'
                    value-format='timestamp'>
                </el-date-picker>
            </el-form-item>
            <el-form-item
                label='结束时间'
                prop='reserveEndTime'>
                <el-date-picker
                    v-model='form.reserveEndTime'
                    default-time='12:00:00'
                    placeholder='选择日期'
                    type='datetime'
                    value-format='timestamp'>
                </el-date-picker>
            </el-form-item>
            <el-form-item label='拍照'>
                <video ref='video' autoplay :style="{ height: '150px' }"></video>
                <el-button @click='snapPhoto'>拍照</el-button>
            </el-form-item>
        </el-form>
        <el-form>
            <el-form-item>
                <el-button v-if="$hasPermission('RESERVE_CREATE') && saveVisible"
                           style='margin-left: 100px'
                           type='primary'
                           @click='submitForm'>保存
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import Reserve from '@/api/Reserve';
import ReserveRules from '@/rules/reserve/ReserveRules';
import Account from '@/api/Account';
import Room from '@/api/Room';

export default {
    name: 'ReserveDetail',
    props: {
        defaultValues: {
            type: Object,
            default() {
                return {
                    id: '',
                    content: '',
                    reserveStartTime: '',
                    reserveEndTime: '',
                    roomName: '',
                    accountPassport: '',
                    faceImage: ''
                };
            }
        }
    },
    mounted() {
        this.initCamera();
    },
    data() {
        return {
            form: Object.assign({}, this.defaultValues),
            rules: ReserveRules,
            accountList: [],
            roomList: [],
            saveVisible: true
        };
    },
    created() {
        this.queryAccountList();
        this.queryRoomList();
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
                navigator.mediaDevices.getUserMedia({ video: true }).then((stream) => {
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
            console.log(imageData);
            this.defaultValues.faceImage = imageData;
            this.$message.success('拍照成功');
        },
        async queryAccountList() {
            const { data } = await Account.query({
                pageSize: 1000,
                pageNum: 1
            });
            this.accountList = data.rows;
        },
        async queryRoomList() {
            const { data } = await Room.query({
                pageSize: 1000,
                pageNum: 1
            });
            this.roomList = data.rows;
        },
        async submitForm() {
            try {
                await this.$refs.reserveForm.validate();
            } catch (error) {
                return Promise.reject(new Error('表单校验失败'));
            }
            try {
                let params = Object.assign({}, this.form);
                this.saveVisible = false;
                await Reserve.create(params);
                this.$emit('success');
                this.$message.success(`创建成功！`);
            } catch (e) {
                this.$message.error(`创建失败！原因：` + e.message);
            }
        }
    }
};
</script>

<style lang='scss'>
.reserve-create {
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
