export default {
    name: [
        {
            required: true,
            message: '请输入班级',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '班级长度不能超过255'
        }
    ],

    createTime: [
        {
            required: true,
            message: '请输入创建时间',
            trigger: 'blur'
        }
    ],


}
