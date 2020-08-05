/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 11:01:17
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-05 21:25:57
 */

// Init Live2D

$(window).scroll(function () {
	let offset = $(".title").offset().top + $(".title").height() - $(window).scrollTop();
	if (offset <= $(".nav").height()) {
		$(".nav").addClass("onview");
	} else {
		$(".nav").removeClass("onview");
	}
});

L2Dwidget.init({
	model: {
		//jsonpath控制显示那个小萝莉模型，下面这个就是我觉得最可爱的小萝莉模型
		jsonPath: "assets/live2d/33.v2/33.v2.model.json",
		scale: 1,
	},
	display: {
		position: "right", //看板娘的表现位置
		width: 150, //小萝莉的宽度
		height: 300, //小萝莉的高度
		hOffset: 0,
		vOffset: -20,
	},
	mobile: {
		show: true,
		scale: 0.5,
	},
	react: {
		opacityDefault: 0.7,
		opacityOnHover: 0.2,
	},
});
