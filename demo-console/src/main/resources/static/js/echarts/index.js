$(function () {
    initCharts();
});

function initCharts() {
    initBarChart();
};

function initBarChart() {
    var barChart = echarts.init(document.getElementById("bar-chart"));
    var xDataArr = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
    var yDataArr = [100, 200, 100, 200, 100, 300, 300, 400, 500, 100, 100, 500];
    var dataMap = [xDataArr, yDataArr];
    var option = {
        color: ['rgba(109, 150, 245, 1)'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {},
        dataset: {
            source: dataMap
        },
        grid: {
            left: '3%',
            right: '6%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: [0, 0.01],
            axisLine: {
                show: false
            },
            splitLine: {
                show: true,
                lineStyle: {
                    width: 1,
                    color: ['rgba(227, 227, 228, 1)'],
                    type: 'dotted'
                }
            }
            // data: XdataArr
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '价值',
                type: 'bar',
                barMaxWidth: 18,
                label: {
                    show: true,
                    position: 'right',
                    // distance: 5 ,
                    // rotate ... ,
                    // offset ... ,
                    color: 'rgba(109, 150, 245, 1)',
                    fontSize: 14
                }
            }
        ]
    };
    barChart.setOption(option);
}
