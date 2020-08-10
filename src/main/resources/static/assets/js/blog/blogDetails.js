/*
 * @Author: Wonder2019
 * @Date: 2020-08-06 10:44:56
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-10 10:12:50
 */

let markedOpt = {
	baseUrl: null,
	breaks: false,
	gfm: true,
	headerIds: true,
	headerPrefix: "",
	highlight: function (code, lang, callback) {
		return hljs.highlightAuto(code).value;
	},
	langPrefix: "language-",
	mangle: true,
	pedantic: false,
	sanitize: false,
	sanitizer: null,
	silent: false,
	smartLists: false,
	smartypants: false,
	tokenizer: null,
	walkTokens: null,
	xhtml: false,
};

function renderMd(progressId) {
	$.post(
		blogDetails.markdownId,
		function (data) {
			for (let item of blogDetails.resourceList) {
				data = data.replace(`http://wonderblog.img${item.order}`, item.imageId);
			}
			$(".blog-content").html(marked(data, markedOpt));
			window[progressId].count++;
		},
		"text"
	);
}

function initTagList(progressId) {
	$(".tag-cloud-board").jQCloud(window.tagList, {
		removeOverflowing: true,
		shape: "rectangular",
	});
	window[progressId].count++;
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

window.wonderTask.push({
	fun: initTagList,
	params: [],
});
