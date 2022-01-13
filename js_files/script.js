
//////////////切換按鈕////////////
$(".search-course").click(function () {
  $("#s-course").show();
  $("#s-user").hide();
});
$(".search-user").click(function () {
  $("#s-course").hide();
  $("#s-user").show();
});

		//////////////把資料塞進table裡////////////
var testData = {
	courseName: "SA",
	textbook: [
		{id: "IM0012",name: "CH1教材", semester: "108", department: "資管系"}, 
		{id: "IM0012",name: "CH1教材", semester: "108", department: "資管系"}, 
    {id: "IM0012",name: "CH1教材", semester: "108", department: "資管系"}, 
    {id: "IM0012",name: "CH1教材", semester: "108", department: "資管系"}, 
    {id: "IM0012",name: "CH1教材", semester: "108", department: "資管系"}
	]
};
var testJson = JSON.stringify(testData); //產生測試用的JSON資料

function json2table(jsonString, $table){
	var json = JSON.parse(jsonString); 
	
	var bodyRows = '';
	
	for(let hm of json.textbook){ //將資料填入表格
		bodyRows += '<tr>';
		bodyRows += '<td>' + hm.id + '</td>' ;
		bodyRows += '<td><a href="textbook.html"><span class="content-tbl-link">' + hm.name + '</span></a></td>' ;
    bodyRows += '<td>' + hm.semester + '</td>' ;
    bodyRows += '<td>' + hm.department + '</td>' ;
		bodyRows += '</tr>';
	}
	
	$table.find('tbody').append(bodyRows);
}
json2table(testJson, $(".content-tbl"));