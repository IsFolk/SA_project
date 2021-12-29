$(".search-course").click(function () {
  $("#s-course").show();
  $("#s-user").hide();
});
$(".search-user").click(function () {
  $("#s-course").hide();
  $("#s-user").show();
});
/////////////////////////////////////
var courseInfo = {
  name: "系統分析與設計",
  number: "IM3069*",
  professor: "喵喵喵"
};
$(".course-name").text(courseInfo.name);
$(".course-number").text(courseInfo.number);
$(".professor").text("教授:" + courseInfo.professor);

//////////////把資料塞進table裡////////////

var testData = {
  courseName: "SA",
  homework: [
    { title: "作業一", endDate: "2021.12.3", status: "95" },
    { title: "作業二", endDate: "2021.12.4", status: "待批改" },
    { title: "作業三", endDate: "2021.12.5", status: "尚未繳交" },
    { title: "喵喵", endDate: "にゃんにゃん", status: "wwwwww" },
    { title: "喵喵", endDate: "にゃんにゃん", status: "wwwwww" }
  ]
};
var testJson = JSON.stringify(testData); //產生測試用的JSON資料

function json2table(jsonString, $table) {
  var json = JSON.parse(jsonString);

  var bodyRows = "";

  for (let hm of json.homework) {
    //將資料填入表格
    bodyRows += "<tr>";
    bodyRows +=
      '<td><a href="#"><span class="link">' + hm.title + "</span></a></td>";
    bodyRows += "<td>" + hm.endDate + "</td>";
    bodyRows += "<td>" + hm.status + "</td>";
    bodyRows += "</tr>";
  }

  $table.find("tbody").append(bodyRows);
}

json2table(testJson, $("#content-tbl"));
//切換篩選條件//
