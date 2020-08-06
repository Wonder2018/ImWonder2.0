/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 11:01:17
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-05 21:25:57
 */

// Init Live2D

if (!window.wonderTask) {
	window.wonderTask = [];
}

window.wonderTask.push({
	fun: loadBackground,
	params: ["body", ".cover-box > .cover-paint"],
});

$(window).scroll(function () {
	let offset = $(".title").offset().top + $(".title").height() - $(window).scrollTop();
	if (offset <= $(".nav").height()) {
		$(".nav").addClass("onview");
	} else {
		$(".nav").removeClass("onview");
	}
});
