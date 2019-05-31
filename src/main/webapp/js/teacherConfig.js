// 学院和专业的对应关系
let optionsMap = {
    "两江人工智能学院": ["软件工程", "大数据"],
    "计算机科学与工程学院": ["计算机科学与技术", "网络工程"],
};

let teacherUrl = "/teacher";

let teacherColumns = [{
    field: 'state',
    checkbox: true,
}, {
    title: "index",
    field: "index",
    align: 'center',
    sortable: true
}, {
    title: 'Id',
    field: 'id',
    align: 'center',
    sortable: true,

}, {
    title: 'Tno',
    field: 'tno',
    align: 'center',
    sortable: true,
}, {
    title: '姓名',
    field: 'name',
    align: 'center',

}, {
    title: '性别',
    field: 'gender',
    align: 'center',

}, {
    title: '年龄',
    field: 'age',
    align: 'center',
    sortable: true,

}, {
    title: '学院',
    field: 'academy',
    align: 'center',

}, {
    title: '专业',
    field: 'dept',
    align: 'center',

}, {
    title: '薪水 ',
    field: 'salary',
    align: 'center',
    sortable: true,
}];

