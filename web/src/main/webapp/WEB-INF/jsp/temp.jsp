<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>仪表盘</title>
    <link rel="stylesheet" href="/vendor/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="/vendor/font-awesome/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="/css/stylesOA.css">
</head>
<body class="sidebar-fixed header-fixed">
<div class="page-wrapper">
    <nav class="navbar page-header" style="padding-top: 0px">
        <a href="#" class="btn btn-link sidebar-mobile-toggle d-md-none mr-auto">
            <i class="fa fa-bars"></i>
        </a>

        <a class="navbar-brand" href="#">
            <img src="/imgs/logo.png" alt="logo">
        </a>

        <a href="#" class="btn btn-link sidebar-toggle d-md-down-none">
            <i class="fa fa-bars"></i>
        </a>

        <ul class="navbar-nav ml-auto">
            <li class="nav-item d-md-down-none">
                <a href="#">
                    <i class="fa fa-bell"></i>
                    <span class="badge badge-pill badge-danger">5</span>
                </a>
            </li>

            <li class="nav-item d-md-down-none">
                <a href="#">
                    <i class="fa fa-envelope-open"></i>
                    <span class="badge badge-pill badge-danger">5</span>
                </a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    <img src="/imgs/avatar-1.png" class="avatar avatar-sm" alt="logo">
                    <span class="small ml-1 d-md-down-none">liu</span>
                </a>

                <div class="dropdown-menu dropdown-menu-right">

                    <a href="#" class="dropdown-item">
                        <i class="fa fa-user"></i> 个人信息
                    </a>

                    <a href="#" class="dropdown-item">
                        <i class="fa fa-envelope"></i> 信息
                    </a>

                    <a href="#" class="dropdown-item">
                        <i class="fa fa-lock"></i> 退出
                    </a>
                </div>
            </li>
        </ul>
    </nav>

    <div class="main-container">
        <div class="sidebar">
            <nav class="sidebar-nav">
                <ul class="nav">
                    <li class="nav-title">导航</li>

                    <li class="nav-item">
                        <a href="/index.html" class="nav-link active">
                            <i class="icon icon-speedometer"></i> 仪表盘
                        </a>
                    </li>
                    <!--菜单开始-->
                    <!--业务统计模块-->
                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-graph"></i> 业务统计 <i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items">
                            <li class="nav-item">
                                <a href="/ch_month.html" class="nav-link">
                                    <i class="icon icon-graph"></i>&nbsp;&nbsp;产品销量
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="/ch_person.html" class="nav-link">
                                    <i class="icon icon-graph"></i>&nbsp;&nbsp;销售员业绩
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="/ch_area.html" class="nav-link">
                                    <i class="icon icon-graph"></i>&nbsp;&nbsp;地区销量
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!--活动管理模块-->
                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-target"></i> 活动管理模块 <i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items">
                            <ul class="nav-dropdown-items">
                                <li class="nav-item">
                                    <a href="/ac_list.html" class="nav-link">
                                        <i class="icon icon-target"></i>&nbsp;&nbsp;促销活动
                                    </a>
                                </li>

                                <li class="nav-item">
                                    <a href="/gi_list.html" class="nav-link">
                                        <i class="icon icon-target"></i>&nbsp;&nbsp;礼品领取记录
                                    </a>
                                </li>

                            </ul>
                        </ul>
                    </li>
                    <!--农产品管理模块-->
                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-energy"></i> 农产品管理 <i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items">
                            <li class="nav-item">
                                <a href="/g_list.html" class="nav-link">
                                    <i class="icon icon-energy"></i> &nbsp;&nbsp;农产品列表
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="/g_infoAdd.html" class="nav-link">
                                    <i class="icon icon-energy"></i> &nbsp;&nbsp;增加新农产品
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!--人事管理模块-->
                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-puzzle"></i> 人事管理 <i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items">
                            <li class="nav-item">
                                <a href="/r_info.html" class="nav-link">
                                    <i class="icon icon-puzzle"></i> &nbsp;&nbsp;信息管理
                                </a>
                            </li>

                            <li class="nav-item">
                                <a href="/r_attence.html" class="nav-link">
                                    <i class="icon icon-puzzle"></i> &nbsp;&nbsp;考勤记录
                                </a>
                            </li>

                            <li class="nav-item">
                                <a href="/r_leave.html" class="nav-link">
                                    <i class="icon icon-puzzle"></i>&nbsp;&nbsp; 出差审核
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-title">More</li>
                    <li class="nav-item">
                        <a href="/z_suggest.html" class="nav-link">
                            <i class="icon icon-grid"></i>投诉与建议
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="/z_consult.html" class="nav-link">
                            <i class="icon icon-grid"></i> 技术咨询
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="/z_notice.html" class="nav-link">
                            <i class="icon icon-grid"></i> 技术发布
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

        <div class="content">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-md-12">
                        <!--模板结束11111-->
                        <div class="wrapper wrapper-content animated fadeInRight">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="ibox float-e-margins">
                                        <div class="ibox-title">
                                            <h5>退货列表</h5>
                                        </div>
                                        <div class="ibox-content">
                                            <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper form-inline"
                                                 role="grid">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="normal-input"
                                                               class="form-control-label">状态</label>
                                                        <select id="go_type" class="form-control" >
                                                            <option grade="1" value="1">全部</option>
                                                            <option grade="1" value="2">待审核</option>
                                                            <option grade="1" value="3">审核通过</option>
                                                            <option grade="1" value="4">审核拒绝</option>
                                                            <option grade="1" value="5">用户取消</option>
                                                        </select>
                                                    </div>
                                                </div>


                                                <table class="table table-striped table-bordered table-hover dataTables-example dataTable no-footer"
                                                       id="DataTables_Table_0"
                                                       aria-describedby="DataTables_Table_0_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-sort="ascending" aria-label=""
                                                        >序号
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">订单号
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">姓名
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">农产品
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">数量
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">退款金额
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">原因
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">状态
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">操作时间
                                                        </th>
                                                        <th class="sorting" tabindex="0"
                                                            aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
                                                            aria-label="">操作
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>

                                                    <tr class="gradeA even">
                                                        <td class="sorting_1">1</td>
                                                        <td class=" ">12345678944</td>
                                                        <td class=" ">张三</td>
                                                        <td class=" ">除草剂</td>
                                                        <td class=" ">1</td>
                                                        <td class=" ">8999</td>
                                                        <td class=" ">买多了</td>
                                                        <td class=" ">审核通过</td>
                                                        <td class=" ">2018-12-01</td>
                                                        <td class=" "><a href="" class="btn btn-info">查看</a></td>
                                                    </tr>
                                                    <tr class="gradeA even">
                                                        <td class="sorting_1">1</td>
                                                        <td class=" ">12345678944</td>
                                                        <td class=" ">张三san</td>
                                                        <td class=" ">除草剂</td>
                                                        <td class=" ">1</td>
                                                        <td class=" ">8999</td>
                                                        <td class=" ">买多了</td>
                                                        <td class=" ">用户取消</td>
                                                        <td class=" ">2018-12-01</td>
                                                        <td class=" "><a href="" class="btn btn-info">查看</a></td>
                                                    </tr>
                                                    <tr class="gradeA even">
                                                        <td class="sorting_1">1</td>
                                                        <td class=" ">1222222222</td>
                                                        <td class=" ">张三</td>
                                                        <td class=" ">除草剂</td>
                                                        <td class=" ">1</td>
                                                        <td class=" ">8999</td>
                                                        <td class=" ">买多了</td>
                                                        <td class=" ">待处理</td>
                                                        <td class=" ">2018-12-01</td>
                                                        <td class=" ">
                                                            <a href="" class="btn btn-primary">处理</a>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="dataTables_info" id="DataTables_Table_0_info"
                                                             role="alert" aria-live="polite" aria-relevant="all">显示 1 到
                                                            10 项，共 11 项
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="dataTables_paginate paging_simple_numbers"
                                                             id="DataTables_Table_0_paginate">
                                                            <ul class="pagination">
                                                                <li class="paginate_button previous disabled"
                                                                    aria-controls="DataTables_Table_0" tabindex="0"
                                                                    id="DataTables_Table_0_previous"><a href="#"
                                                                                                        style="width: 70px;height: 30px">上一页</a>
                                                                </li>
                                                                <li class="paginate_button active"
                                                                    aria-controls="DataTables_Table_0" tabindex="0"><a
                                                                        href="#">1</a></li>
                                                                <li class="paginate_button "
                                                                    aria-controls="DataTables_Table_0" tabindex="0"><a
                                                                        href="#">2</a></li>
                                                                <li class="paginate_button next"
                                                                    aria-controls="DataTables_Table_0" tabindex="0"
                                                                    id="DataTables_Table_0_next"><a href="#"
                                                                                                    style="width: 70px;height: 30px">下一页</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!--模板开始22222-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/popper.js/popper.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/vendor/chart.js/chart.min.js"></script>
<script src="/js/carbon.js"></script>
<div class="layui-layer layui-anim layui-layer-dialog layui-layer-border " id="layui-layer1" type="dialog" times="1"
     showtime="0" contype="string" style="z-index: 19891015; top: 254px; left: 563px;">
    <div class="layui-layer-title" style="cursor: move;" move="ok">信息</div>
    <div class="layui-layer-content">确认下架展示？</div>
    <span class="layui-layer-setwin"><a class="layui-layer-ico layui-layer-close layui-layer-close1"
                                        href="javascript:;"></a></span>
    <div class="layui-layer-btn"><a class="layui-layer-btn0">确认</a><a class="layui-layer-btn1">取消</a></div>
</div>
</body>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var barChart = $('#bar-chart');
        if (barChart.length > 0) {
            new Chart(barChart, {
                type: 'bar',
                data: {
                    labels: ["一月", "二月", "三月", "四月", "五月", "六月"],
                    datasets: [{
                        label: '# of Votes',
                        data: [12, 22, 3, 5, 4, 3],
                        backgroundColor: [
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
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
                                beginAtZero: false
                            }
                        }]
                    }
                }
            });
        }
    });
</script>
</html>
