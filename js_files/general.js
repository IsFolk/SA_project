var courseInfo = {
		name: "系統分析與設計",
		semester: "110-1",
		code: "IM3069*",
		division: "資管系",
		professor: "喵喵喵",
		credits: 3,
		description: "課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹"
};
$(".course-name").text(courseInfo.name);
$(".course-semester").text(courseInfo.semester);
$(".course-code").text(courseInfo.code);
$(".course-division").text(courseInfo.division);
$(".course-professor").text(courseInfo.professor);
$(".course-credits").text(courseInfo.credits);
$(".course-description").text(courseInfo.description);

////////////判斷是教授還是學生/////////
var userIsProfessor = 1;
if(userIsProfessor){
	$(".stu-only").remove();
}else{
	$(".pro-only").remove();
}


