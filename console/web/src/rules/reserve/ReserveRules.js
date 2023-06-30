export default {
    roomId: [
        {
            required: true,
            message: '请输入实验室',
            trigger: 'blur'
        }
    ],


    accountId: [
        {
            required: true,
            message: '请输入预约人',
            trigger: 'blur'
        }
    ],

    reserveStartTime: [
        {
            required: true,
            message: '请输入开始时间',
            trigger: 'blur'
        }
    ],

    reserveEndTime: [
        {
            required: true,
            message: '请输入结束时间',
            trigger: 'blur'
        }
    ],

    createTime: [
        {
            required: true,
            message: '请输入创建时间',
            trigger: 'blur'
        }
    ],

    faceImage: [
        {
            required: true,
            message: '请拍摄人脸图像',
            trigger: 'blur'
        }
    ]


};
