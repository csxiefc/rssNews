 // 执行搜索
 function platSearch(page) {
  // 搜索总记录、搜索耗时、总计多少页
  var searchTotalNum = 0;
  var searchTime = 0;
  var searchTotalPage = 0;
  //
  var pageSize = 10; // 默认每页10行数据
  var vStart = 0;
  var vPage = 0
  if(page) {
   vStart = (page-1) * pageSize; // 计算solr开始行数
   vPage = page; // 当前是第几页
  }
  $("#keyTitle").text($("#key").val());
  var url = '/solr/search/keyQuery?start='+vStart+'&rows='+pageSize+'&key='+$("#key").val();
  $.ajax({
     headers: {
        Accept: "application/json; charset=utf-8"
     },
     type: "GET",
     url: url,
     beforeSend: function(request) {
        request.setRequestHeader("rss","5338244087b8cfa5906d76713f0528ee1f27cc24dfd9dcf549af51894a54c5cc1c839275258397030b3cf7263a51af4e");
     },
     success: function(res) {
        $("#right-body").html(""); // 清空列表项
        if(res.code == 500) {
            alert(res.msg);
            return false;
        }
        res.data.list.forEach( (item , index ) => {
            var html = "<div class='news-item'>";
            // html += "<div class='pic'><img title='' alt='' src='http://p3.music.126.net/tBTNafgjNnTL1KlZMt7lVA==/18885211718935735.jpg'></div>";
            html += "<div class='news-content'>";
            html += "发布时间："+item.pubdate;
            html += " 收录时间："+item.create_date;
            html += "<a target='_blank' id='"+item.id+"' href='"+item.link+"' class='title'>"+item.title+"</a>";
            html += "<span class='desc'>";
            if(item.description) {
                item.description = removeTags($.trim(item.description));
                if(item.description.length > 140) { // 摘要描述过长进行截取
                    html += item.description.substring(0,140)
                    html += "...";
                } else {
                    html += item.description;
                }
            }
            html += "</span>";
            html += "</div>";
            html += "</div>";
            $("#right-body").append(html);
        })
        // 计算页码
        var pageTotal = parseInt(res.data.totalNum / pageSize);
        var isHaveMore = res.data.totalNum % pageSize;
        if(isHaveMore != 0) {
         pageTotal++
        }
        var pageOptions = {'pageTotal':pageTotal,'curPage':vPage,paginationId:'pages'};
        dynamicPagingFunc(pageOptions); // 绘制分页码
        // 页面赋值展现
        searchTotalPage = pageTotal;
        searchTotalNum = res.data.totalNum;
        searchTime = res.data.seconds;
        $("#searchTotalNum").text(searchTotalNum);
        $("#searchTime").text(searchTime);
        $("#searchTotalPage").text(pageTotal);
       }
     }); // end ajax
  }

    function removeTags(str) { // 去掉HTML标签
      if ((str===null) || (str===''))
      return false;
      else
      str = str.toString();
      return str.replace( /(<([^>]+)>)/ig, '');
    }

 // 监听回车键
 document.onkeydown = function(e) {
  var ev = document.all ? window.event : e;
  if(ev.keyCode==13) {
	platSearch();
  }
 }