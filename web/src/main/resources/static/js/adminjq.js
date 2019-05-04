$(document).ready(function () {
//使用Ajax获取json数据 折线统计图传入goodsid 查询商品销量
    $("#single-select2").change(function () {
        var goodsid = $("#single-select2").val()
        $.ajax({
            url: 'getGoodSell',
            type: "get",
            data: {goodsid: goodsid},
            dataType: 'json',
        }).done(function (results) {
            // 将获取到的json数据分别存放到两个数组中
            var labels = [], data = [];
            for (var i = 0; i < results.length; i++) { //第一层循环取到各个list
                labels.push(results[i].month);
                data.push(results[i].count);
            }
            // 设置图表的数据
            var tempData = {
                labels: labels,
                datasets: [{
                    label: '销售量',
                    labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
                    data: data,
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
            };
            $('#bar-chart').remove(); // this is my <canvas> element
            $('#bar-chart-p').append('<canvas id="bar-chart"></canvas>');
            var ctx = document.getElementById("bar-chart").getContext("2d");
            var myLineChart = new Chart(ctx, {
                type: 'bar',
                data: tempData,
                options: {
                    maintainAspectRatio: true,
                }
            });
        });
    });

    /**
     * 下拉触发 Ajax
     * 获取json数据 折线统计图传入goodsid 查询商品销量
     */
    $("#single-seller").change(function () {
        var sellerid = $("#single-seller").val()
        // console.log("==="+$("#bar-chart").html());
        $.ajax({
            url: 'getSellerSell',
            type: "get",
            data: {sellerid: sellerid},
            dataType: 'json',
        }).done(function (results) {
            // 将获取到的json数据分别存放到两个数组中
            var labels = [], data = [];
            for (var i = 0; i < results.length; i++) { //第一层循环取到各个list
                labels.push(results[i].month);
                data.push(results[i].count);
            }
            // 设置图表的数据
            var tempData = {
                labels: labels,
                datasets: [{
                    label: '销售量',
                    labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
                    data: data,
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
            };

            $('#bar-chart').remove(); // this is my <canvas> element
            $('#bar-chart-p').append('<canvas id="bar-chart"></canvas>');

            var ctx = document.getElementById("bar-chart").getContext("2d");

            var myLineChart = new Chart(ctx, {
                type: 'bar',
                data: tempData,
                options: {
                    maintainAspectRatio: true,
                }
            });
        });
    });

    var jsonData = $.ajax({//择商品
        url: 'selectAllGoods',
        type: "get",
        dataType: 'json',
    }).done(function (results) {
        $('#single-select2').html('');
        var options = '';
        options += '<option value="">--请选择--</option>';
        for (var i = 0; i < results.length; i++) {
            options += '<option value="' + results[i].id + '">' + results[i].name + '</option>';
        }
        $('#single-select2').append(options);
    }).fail(function (error) {
        $('#single-select2').html('');
        var options = '';
        options += '<option value="">--请选择--</option>';
        $('#single-select2').append(options);
    });

    $.ajax({//下拉选择择销售员
        url: 'getSellerAll',
        type: "get",
        dataType: 'json',
    }).done(function (results) {
        $('#single-seller').html('');
        var options = '';
        options += '<option value="">--请选择--</option>';
        for (var i = 0; i < results.length; i++) {
            options += '<option value="' + results[i].id + '">' + results[i].name + '</option>';
        }
        $('#single-seller').append(options);
    }).fail(function (error) {
        $('#single-seller').html('');
        var options = '';
        options += '<option value="">--请选择--</option>';
        $('#single-seller').append(options);
    });


    $.ajax({//得到销售员总销售额前10
        url: 'getSellerTop10',
        type: "get",
        dataType: 'json',
    }).done(function (results) {
        var td = '';
        var temp;
        for (var i = 0; i < results.length; i++) {
            temp = i + 1;
            td += '<tr><td>' + temp + '</td><td>' + results[i].name + '</td><td>' + results[i].count + '</td></tr>';
        }
        $('#sellertop10').append(td);
    }).fail(function (error) {
    });

    $.ajax({//得到商品销售量前10
        url: 'getGoodSellTop10',
        type: "get",
        dataType: 'json',
    }).done(function (results) {
        var td = '';
        var temp;
        for (var i = 0; i < results.length; i++) {
            temp = i + 1;
            td += '<tr><td>' + temp + '</td><td>' + results[i].name + '</td><td>' + results[i].count + '</td></tr>';
        }
        $('#goodstop10').append(td);
    }).fail(function (error) {
    });


    $("#suggest-select").change(function () {
        var option = $("#suggest-select").val()
        console.log("option:" + option)
        $.ajax({
            url: 'questionSelect',
            type: "get",
            data: {option: option},
            dataType: 'json',
        }).done(function (results) {
            $("#suggestid").html('');

            for (var i = 0; i < results.length; i++) { //第一层循环取到各个list
                var url = "questionInfo?id=" + results[i].id;
                if (option == 0) {
                    $("#suggestid").append('<div><label>' + results[i].createTime + '</label><div class="alert alert-warning"><a href="' + url + '">' + results[i].title + '</a>&nbsp;&nbsp;&nbsp;<a style="color:red">待处理</a></div><br></div>')
                } else if (option == 1) {
                    $("#suggestid").append('<div><label>' + results[i].createTime + '</label><div class="alert alert-warning"><a href="' + url + '">' + results[i].title + '</a>&nbsp;&nbsp;&nbsp;<a style="color:black">未处理</a></div><br></div>')
                } else {
                    $("#suggestid").append('<div><label>' + results[i].createTime + '</label><div class="alert alert-success"><a href="' + url + '">' + results[i].title + '</a>&nbsp;&nbsp;&nbsp;<a style="color:black">已处理</a></div><br></div>')
                }
            }
        });

    });


});


