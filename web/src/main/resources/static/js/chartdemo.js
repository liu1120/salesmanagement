$(document).ready(function () {
//使用Ajax获取json数据
    var jsonData = $.ajax({
        url: 'getTotalAmountFormat',
        type: "get",
        dataType: 'json',
    }).done(function (results) {
        // 将获取到的json数据分别存放到两个数组中
        var labels = [], data=[];
        for(var i=0;i<results.length;i++){ //第一层循环取到各个list
            labels.push(results[i].month);
            data.push(results[i].amt);
        }

        // 设置图表的数据
        var tempData = {
            labels : labels,
            datasets: [{
                label: '月销售额',
                data: data,
                backgroundColor: 'rgba(66, 165, 245, 0.5)',
                borderColor: '#2196F3',
                borderWidth: 1
            }]
        };

        // 获取所选canvas元素的内容
        var ctx = document.getElementById("line-chart");
        //设置图表高度
        var myLineChart = new Chart(ctx, {
            type: 'line',
            data: tempData,
            options: {
                maintainAspectRatio: true,
            }
        });
    });



    /**
     * Bar Chart
     */
    var barChart = $('#bar-chart');

    if (barChart.length > 0) {
        new Chart(barChart, {
            type: 'bar',
            data: {
                labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: [
                        'rgba(244, 88, 70, 0.5)',
                        'rgba(33, 150, 243, 0.5)',
                        'rgba(0, 188, 212, 0.5)',
                        'rgba(42, 185, 127, 0.5)',
                        'rgba(156, 39, 176, 0.5)',
                        'rgba(253, 178, 68, 0.5)'
                    ],
                    borderColor: [
                        '#F45846',
                        '#2196F3',
                        '#00BCD4',
                        '#2ab97f',
                        '#9C27B0',
                        '#fdb244'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }

    /**
     * Radar Chart
     */
    var radarChart = $('#radar-chart');

    if (radarChart.length > 0) {
        new Chart(radarChart, {
            type: 'radar',
            data: {
                labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
                datasets: [{
                    label: 'Users',
                    data: [100, 45, 87, 50, 77, 20],
                    backgroundColor: 'rgba(244, 88, 70, 0.5)',
                    borderColor: '#F45846',
                    borderWidth: 1
                }, {
                    label: 'Votes',
                    data: [23, 55, 75, 54, 95, 100],
                    backgroundColor: 'rgba(33, 150, 243, 0.5)',
                    borderColor: '#2196F3',
                    borderWidth: 1
                }]
            }
        });
    }

    /**
     * Pie Chart
     */
    var pieChart = $('#pie-chart');

    if (pieChart.length > 0) {
        new Chart(pieChart, {
            type: 'pie',
            data: {
                labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
                datasets: [{
                    label: 'Users',
                    data: [100, 45, 87, 50, 77, 20],
                    backgroundColor: [
                        'rgba(244, 88, 70, 0.5)',
                        'rgba(33, 150, 243, 0.5)',
                        'rgba(0, 188, 212, 0.5)',
                        'rgba(42, 185, 127, 0.5)',
                        'rgba(156, 39, 176, 0.5)',
                        'rgba(253, 178, 68, 0.5)'
                    ],
                    borderColor: [
                        'rgba(244, 88, 70, 0.5)',
                        'rgba(33, 150, 243, 0.5)',
                        'rgba(0, 188, 212, 0.5)',
                        'rgba(42, 185, 127, 0.5)',
                        'rgba(156, 39, 176, 0.5)',
                        'rgba(253, 178, 68, 0.5)'
                    ],
                    borderWidth: 1
                }]
            }
        });
    }

    /**
     * Widget Line Chart
     */
    var wLineChart = $('.widget-line-chart');

    wLineChart.each(function (index, canvas) {
        new Chart(canvas, {
            type: 'line',
            data: {
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                datasets: [{
                    label: 'Users',
                    data: [12, 19, 3, 5, 2, 3, 20, 33, 23, 12, 33, 10],
                    borderColor: '#fff',
                    borderWidth: 1,
                    fill: false,
                }]
            },
            options: {
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            display: false,
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false,
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            display: false,
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false,
                        }
                    }]
                }
            }
        });
    });
});
