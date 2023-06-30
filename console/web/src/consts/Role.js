// 若有子权限，则需将父权限加入过滤列表中
export const FILTER_AUTH = [
    'ACCOUNT',
    'ROLE',
    'SYS_LOG',
    'MEETING',
    'REPAIR',
    'RESERVE',
    'ROOM',
]

export const AUTH_MENU = [
    {
        id: 0,
        label: '全选',
        children: [
            {
                id: 'DASHBOARD',
                label: '主页'
            },
            {
                id: 'MEETING',
                label: '实验管理',
                children: [
                    {
                        id: 'MY_MEETING',
                        label: '实验管理-我的实验'
                    },
                    {
                        id: 'MEETING_QUERY',
                        label: '实验管理-查询'
                    },
                    {
                        id: 'MEETING_CREATE',
                        label: '实验管理-创建'
                    },
                    {
                        id: 'MEETING_UPDATE',
                        label: '实验管理-编辑'
                    },
                    {
                        id: 'MEETING_DELETE',
                        label: '实验管理-删除'
                    },
                    {
                        id: 'MEETING_EXPORT',
                        label: '实验管理-导出'
                    }
                ]
            },
            {
                id: 'REPAIR',
                label: '通知管理',
                children: [
                    {
                        id: 'REPAIR_QUERY',
                        label: '通知管理-查询'
                    },
                    {
                        id: 'REPAIR_CREATE',
                        label: '通知管理-创建'
                    },
                    {
                        id: 'REPAIR_UPDATE',
                        label: '通知管理-编辑'
                    },
                    {
                        id: 'REPAIR_DELETE',
                        label: '通知管理-删除'
                    },
                    {
                        id: 'REPAIR_EXPORT',
                        label: '通知管理-导出'
                    }
                ]
            },
            {
                id: 'RESERVE',
                label: '预约记录管理',
                children: [
                    {
                        id: 'RESERVE_QUERY',
                        label: '预约记录管理-查询'
                    },
                    {
                        id: 'RESERVE_CREATE',
                        label: '预约记录管理-创建'
                    },
                    {
                        id: 'RESERVE_UPDATE',
                        label: '预约记录管理-编辑'
                    },
                    {
                        id: 'RESERVE_DELETE',
                        label: '预约记录管理-删除'
                    },
                    {
                        id: 'RESERVE_EXPORT',
                        label: '预约记录管理-导出'
                    }, {
                        id: 'RESERVE_STATUS',
                        label: '预约记录管理-状态修改'
                    }
                ]
            },
            {
                id: 'ROOM',
                label: '实验室管理',
                children: [
                    {
                        id: 'ROOM_QUERY',
                        label: '实验室管理-查询'
                    },
                    {
                        id: 'ROOM_CREATE',
                        label: '实验室管理-创建'
                    },
                    {
                        id: 'ROOM_UPDATE',
                        label: '实验室管理-编辑'
                    },
                    {
                        id: 'ROOM_DELETE',
                        label: '实验室管理-删除'
                    },
                    {
                        id: 'ROOM_EXPORT',
                        label: '实验室管理-导出'
                    }
                ]
            },
            {
                id: 'ROLE',
                label: '角色管理',
                children: [
                    {
                        id: 'ROLE_QUERY',
                        label: '角色管理-查询'
                    },
                    {
                        id: 'ROLE_CREATE',
                        label: '角色管理-创建'
                    },
                    {
                        id: 'ROLE_UPDATE',
                        label: '角色管理-编辑'
                    },
                    {
                        id: 'ROLE_DELETE',
                        label: '角色管理-删除'
                    }
                ]
            },
            {
                id: 'ACCOUNT',
                label: '用户管理',
                children: [
                    {
                        id: 'ACCOUNT_QUERY',
                        label: '用户管理-查询'
                    },
                    {
                        id: 'ACCOUNT_CREATE',
                        label: '用户管理-创建'
                    },
                    {
                        id: 'ACCOUNT_UPDATE',
                        label: '用户管理-编辑'
                    },
                    {
                        id: 'ACCOUNT_DELETE',
                        label: '用户管理-删除'
                    },
                    {
                        id: 'ACCOUNT_PERSONAL',
                        label: '用户管理-个人信息修改'
                    },
                    {
                        id: 'ACCOUNT_CHANGE_PASSWORD',
                        label: '用户管理-修改密码'
                    }
                ]
            },
            {
                id: 'DEPARTMENT',
                label: '班级管理',
                children: [
                    {
                        id: 'DEPARTMENT_QUERY',
                        label: '班级管理-查询'
                    },
                    {
                        id: 'DEPARTMENT_CREATE',
                        label: '班级管理-创建'
                    },
                    {
                        id: 'DEPARTMENT_UPDATE',
                        label: '班级管理-编辑'
                    },
                    {
                        id: 'DEPARTMENT_DELETE',
                        label: '班级管理-删除'
                    },
                    {
                        id: 'DEPARTMENT_EXPORT',
                        label: '班级管理-导出'
                    }
                ]
            },
            {
                id: 'SYS_LOG',
                label: '系统日志管理',
                children: [
                    {
                        id: 'SYS_LOG_QUERY',
                        label: '系统日志管理-查询'
                    }
                ]
            }
        ]
    }
];
