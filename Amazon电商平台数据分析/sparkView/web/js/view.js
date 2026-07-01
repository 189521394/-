function setActive(el) {
    $(".nav-item").removeClass("active");
    $(el).addClass("active");
}

function top10() {
    $.ajax({
        url: "top10",
        type: "GET",
        data: {},
        success: function (result) {
            var myChart = echarts.init($("#main")[0]);
            var option = {
                title: {
                    text: "十大火爆商品用户关注数据"
                },
                tooltip: {},
                legend: {
                    data: ["查看", "加入购物车", "购买"]
                },
                xAxis: {
                    data: result[0],
                    axisLabel: {
                        rotate: 15
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
                    name: "查看",
                    type: "bar",
                    data: result[1]
                },
                {
                    name: "加入购物车",
                    type: "bar",
                    data: result[2]
                },
                {
                    name: "购买",
                    type: "bar",
                    data: result[3]
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}
function top3() {
    $.ajax({
        url: "top3",
        type: "GET",
        data: {},
        success: function (result) {
            var myChart = echarts.init($("#main")[0]);
            var option = {
                title: {
                    text: "五十个州火爆商品数据"
                },
                tooltip: {},
                legend: {
                    data: ["商品4767", "商品4856", "商品5115"]
                },
                xAxis: {
                    data: result[0],
                    axisLabel: {
                        rotate: 45
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
                    name: "商品4767",
                    type: "bar",
                    data: result[1]
                },
                {
                    name: "商品4856",
                    type: "bar",
                    data: result[2]
                },
                {
                    name: "商品5115",
                    type: "bar",
                    data: result[3]
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}
function jump() {
    $.ajax({
        url: "jump",
        type: "GET",
        data: {},
        success: function (result) {
            var myChart = echarts.init($("#main")[0]);
            var option = {
                title: {
                    text: "网页单跳转化率"
                },
                tooltip: {},
                legend: {
                    data: ["单跳转化率"]
                },
                xAxis: {
                    data: result[0],
                    axisLabel: {
                        rotate: 45
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
                    name: "单跳转化率",
                    type: "bar",
                    data: result[1]
                }]
            }
            myChart.clear()
            myChart.setOption(option)
        }
    })
}