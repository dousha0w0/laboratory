export default {
    name: [
        {
            required: true,
            message: '请输入实验室',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '实验室长度不能超过255'
        }
    ],
    roomImageIds: [
        {
            required: true,
            message: '请输入实景照片',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 64,
            trigger: 'blur',
            message: '实景照片长度不能超过64'
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
