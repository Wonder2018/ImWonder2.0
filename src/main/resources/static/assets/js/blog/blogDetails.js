/*
 * @Author: Wonder2019 
 * @Date: 2020-08-06 10:44:56 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-06 10:52:05
 */


function renderMd(progressId) {
    $.post(window.blogMarkdownUrl,function(data){
        $(".blog-content").html(marked(data))
        window[progressId].count++;
    },"text")
}
if (!window.wonderTask) {
	window.wonderTask = [];
}

window.wonderTask.push({
	fun: loadBackground,
	params: [null, "body"],
});

window.wonderTask.push({
	fun: renderMd,
	params: [],
});