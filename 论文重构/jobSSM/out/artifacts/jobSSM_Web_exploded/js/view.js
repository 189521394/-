function salary() {
    $.ajax({
        url: "salary",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "薪资区间分布",
                    subtext: "单位 K"
                },
                tooltip: {},
                legend: {
                    data: ["薪资区间"]
                },
                xAxis: {
                    data: result[0]
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ["bar", "line"],
                            option: {
                                funnel: {
                                    x: "25%",
                                    width: "50%",
                                    funnelAlign: "left",
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                yAxis: {},
                series: [{
                    name: "薪资区间",
                    type: "bar",
                    data: result[1]
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}

function avgSalary() {
    $.ajax({
        url: "avgSalary",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "城市平均薪资",
                    subtext: "单位 K"
                },
                tooltip: {},
                legend: {
                    data: ["平均薪资"]
                },
                xAxis: {
                    data: result[0],
                    axisLabel: {
                        interval: 0,
                        formatter: function (value) {
                            return value.split('').join("\n")
                        }
                    }
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ["bar", "line"],
                            option: {
                                funnel: {
                                    x: "25%",
                                    width: "50%",
                                    funnelAlign: "left",
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                yAxis: {},
                series: [{
                    name: "平均薪资",
                    type: "bar",
                    data: result[1]
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}

function city() {
    $.ajax({
        url: "city",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "职位的区域分布",
                },
                tooltip: {},
                legend: {
                    orient: "vertical",
                    x: 100,
                    y: 80,
                    data: result
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ["pie"],
                            option: {
                                funnel: {
                                    x: "25%",
                                    width: "50%",
                                    funnelAlign: "left",
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "区域分布",
                    type: "pie",
                    radius: '55%',
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}

function degree() {
    $.ajax({
        url: "degree",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "职位的学历分布",
                },
                tooltip: {},
                legend: {
                    orient: "vertical",
                    x: 100,
                    y: 80,
                    data: result
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ["pie"],
                            option: {
                                funnel: {
                                    x: "25%",
                                    width: "50%",
                                    funnelAlign: "left",
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "学历分布",
                    type: "pie",
                    radius: '55%',
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}


function experience() {
    $.ajax({
        url: "experience",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "职位的经验要求",
                },
                tooltip: {},
                legend: {
                    orient: "vertical",
                    x: 100,
                    y: 80,
                    data: result
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ["pie"],
                            option: {
                                funnel: {
                                    x: "25%",
                                    width: "50%",
                                    funnelAlign: "left",
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "经验要求",
                    type: "pie",
                    radius: '55%',
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}


function skills() {
    $.ajax({
        url: "skills",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "技能需求数据统计结果",
                },
                tooltip: {},
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "技能需求数据统计结果",
                    type: "wordCloud",
                    sizeRange: [10, 100],
                    rotationRange: [-45, 90],
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255)].join(',') + ')'
                            }
                        },
                        emphasis:{
                            shadowBlur:10,
                            shadowColor:'#333'
                        }
                    },
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}



function jobName() {
    $.ajax({
        url: "jobName",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0]);

            var option = {
                title: {
                    text: "岗位名称数据统计结果",
                },
                tooltip: {},
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "岗位名称数据统计结果",
                    type: "wordCloud",
                    sizeRange: [10, 100],
                    rotationRange: [-45, 90],
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255)].join(',') + ')'
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}


function welfare() {
    $.ajax({
        url: "welfare",
        type: "GET",
        data: {},
        success: function (result) {
            echarts.dispose($("#main")[0]); var myChart = echarts.init($("#main")[0])
            var option = {
                title: {
                    text: "福利待遇数据统计结果",
                },
                tooltip: {},
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    },
                },
                series: [{
                    name: "福利待遇数据统计结果",
                    type: "wordCloud",
                    sizeRange: [10, 100],
                    rotationRange: [-45, 90],
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255),
                                    Math.round(Math.random() * 255)].join(',') + ')'
                            }
                        },
                        emphasis:{
                            shadowBlur:10,
                            shadowColor:'#333'
                        }
                    },
                    data: result
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}
