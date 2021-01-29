/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 11:01:17
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-28 11:45:04
 */

/**
 * 预加载初始化标签云的回调
 *
 * @param {String} progressId 预加载器 ID
 */
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

let r = /Mozilla\/\d*\.\d* .* AppleWebKit\/\d*\.\d* .* Chrome\/\d*\.\d*\.\d*\.\d*\ Mobile (Safari\/\d*\.\d*)$/;
let ua = window.navigator.userAgent;

if (r.test(ua) || ua.indexOf("Android") < 0) {
	window.wonderTask.push({
		fun: loadBackground,
		params: ["body", ".cover-box > .cover-paint"],
	});
} else { // 安卓非 Chrome 浏览器不支持背景 fixed，因此不显示第一屏，取消高斯模糊效果。
	window.wonderTask.push({
		fun: function (progressId) {
			$(".titlepage").addClass("hide");
			$("nav.nav").addClass("onview");
			window[progressId].count++;
		},
		params:[]
	});

	window.wonderTask.push({
		fun: loadBackground,
		params: [null, "body"],
	});
}

window.wonderTask.push({
	fun: initTagList,
	params: [],
});

$(window).scroll(function () {
	let offset = $(".title").offset().top + $(".title").height() - $(window).scrollTop();
	if (offset <= $(".nav").height()) {
		$(".nav").addClass("onview");
	} else {
		$(".nav").removeClass("onview");
	}
});
