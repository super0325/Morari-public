function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function (n, prec) {
      var k = Math.pow(10, prec);
      return '' + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}
// 拿取全部登入時間
let registerdate;
let dateCounts = {}; // 用來存放每個日期的計數
fetch("/morari/admin/camper/api/userregisterdate")
  .then(response => response.json())
  .then(data => {
    data.forEach(element => {
      registerdate = moment(Date.parse(element)).format('M/DD');
      if (registerdate in dateCounts) {
        dateCounts[registerdate] += 1; // 該日期已存在，計數加1
      } else {
        dateCounts[registerdate] = 1; // 該日期不存在，初始化計數為1
      }
    });


    //   設定前幾天的資訊
let howdays= 10;
    let today = new Date();
    let days = [];
    for (var i = howdays; i >= 0; i--) {
      var d = new Date(today);
      d.setDate(d.getDate() - i);
      var month = d.getMonth() + 1;
      var day = d.getDate();
      var label = month + '/' + day;
      days.push(label);
    }
    // 使用 days 為 X 軸
    
    let Xlabels = [];
    let values = [];
    for (var i = 0; i < days.length; i++) {
      var date = days[i];
      var count = dateCounts[date] || 0;
      Xlabels.push(date);
      values.push(count);
    }
   

    // Area Chart Example
    var ctx = document.getElementById("registerdate");
    var myLineChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: Xlabels,
        datasets: [
          {
            // 數據標籤(人數，收益...)
            label: "Register",
            // 曲線度，0-1 
            lineTension: 0.1,
            // 曲線下方顏色
            backgroundColor: "rgba(78, 115, 223, 0.05)",
            // 曲線的顏色
            borderColor: "rgba(78, 115, 223, 1)",
            // 數據點
            pointRadius: 3,
            pointBackgroundColor: "rgba(78, 115, 223, 1)",
            pointBorderColor: "rgba(78, 115, 223, 1)",
            // 數據點的hover
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
            pointHoverBorderColor: "rgba(78, 115, 223, 1)",
            // 數據點的點擊
            pointHitRadius: 10,
            // 數據點的邊框粗細
            pointBorderWidth: 2,
            data: values,
          },
        ]
    
      },
      options: {
        maintainAspectRatio: false,
        layout: {
          padding: {
            left: 10,
            right: 25,
            top: 25,
            bottom: 0
          }
        },
        scales: {
          // X軸配置
          xAxes: [{
            time: {
              unit: 'date', // 時間單位
            },
            gridLines: {
              display: true, // 是否顯示網格線
              drawBorder: true, // 是否顯示軸線
            },
            ticks: {
              maxRotation: 0,//旋轉
              maxTicksLimit: 10, // X軸最大標籤數
            },
          }],
          // Y軸配置
          yAxes: [{
            ticks: {
              maxRotation: 0,//旋轉
              maxTicksLimit: 5, // Y軸最大標籤數
              padding: 10, // 標籤距離軸線的距離
              // 回調函數，用於自定義標籤顯示格式，這裡在標籤前面加上$符號
              callback: function (value, index, values) {
                return   number_format(value)+'人';
              },
            },
            gridLines: {
              color: "rgb(234, 236, 244)", // 網格線的顏色
              zeroLineColor: "rgb(234, 236, 244)", // X軸在0刻度的顏色
              drawBorder: false, // 是否顯示軸線
              borderDash: [2], // 網格線的虛線樣式
              zeroLineBorderDash: [2], // X軸在0刻度的虛線樣式
            },
          }],
        },
        legend: {
          display: true, // 是否顯示圖例
        },
        tooltips: {
          backgroundColor: "rgb(255,255,255)", // 提示框背景顏色
          bodyFontColor: "#858796", // 提示框正文字體顏色
          titleMarginBottom: 10, // 標題距離正文的距離
          titleFontColor: '#6e707e', // 提示框標題顏色
          titleFontSize: 14, // 提示框標題字體大小
          borderColor: '#dddfeb', // 提示框邊框顏色
          borderWidth: 1, // 提示框邊框寬度
          xPadding: 15, // 提示框水平方向的內邊距
          yPadding: 15, // 提示框垂直方向的內邊距
          displayColors: false, // 是否顯示顏色框
          intersect: false, // 鼠標在多個數據集中時是否顯示提示框
          mode: 'index', // 觸發提示框的模式
          caretPadding: 10, // 提示框三角形距離軸線的距離
          callbacks: {
            label: function (tooltipItem, chart) {
              // 回調函數，用於自定義提示框本文內容
              var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
              return datasetLabel + ': ' + number_format(tooltipItem.yLabel)+' 人';
            }
          }
        }
      }
    });





  })

