/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 11:01:17
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-04-21 11:57:27
 */
// Reset
let blurRound = 20;
let changeTime = 5 * 1000;

// Function
function loadBackground(back, frount, progressId) {
	let urls = ["assets/img/bg/img2.jpg"];
	// $.post(
	// 	"bgis",
	// function (urls) {
	new PreloadImg(urls, true, function (imgs) {
		new SetBackgroundImage(imgs, $(back)[0], $(frount)[0], blurRound, changeTime, false);
		window[progressId].count++;
	});
	// 	},
	// 	"json"
	// );
}

function load() {}

function preLoading(percent) {
	$(".loading-prog>.loading-line").animate({ width: percent }, 300, function () {
		$(this).children().html(percent);
		if (percent == "100%") {
			$(this).children().html("加载完成!&nbsp;&nbsp;&nbsp;&nbsp;");
			setTimeout(function () {
				$(".loading-cover").fadeOut();
				$(document.body).removeClass("loading");
				setTimeout(function () {
					let a = $(".loading-cover")[0];
					document.body.removeChild(a);
				}, 1000);
			}, 100);
		}
	});
}

// On ready
$(document).ready(function () {
	preLoading("70%");
	let ppg = new PreProgress(true, function () {
		preLoading("100%");
	});
	ppg.addTask({ fun: loadBackground, params: ["body", ".cover-box > .cover-paint", ppg.getId()] });
	ppg.start();
	// back = document.body;
	// frount = $(".cover-box > .cover-paint")[0];
	// Init Background
	// setBackground("assets/img/bg/img2.jpg", document.body, document.querySelector());

	// Init Title Animation
	$(window).scroll(function () {
		let offset = $(".title").offset().top + $(".title").height() - $(window).scrollTop();
		if (offset <= $(".nav").height()) {
			$(".nav").addClass("onview");
		} else {
			$(".nav").removeClass("onview");
		}
	});

	// Init anchor link
	$("a").click(function () {
		if (!$.attr(this, "href").startsWith("#")) {
			return true;
		}
		$("html, body").animate(
			{
				scrollTop: $($.attr(this, "href")).offset().top,
			},
			500
		);
		return false;
	});

	// Init tag cloud
	// $("#my_favorite_latin_words").jQCloud(word_list);
});

// Init Live2D
L2Dwidget.init({
	model: {
		//jsonpath控制显示那个小萝莉模型，下面这个就是我觉得最可爱的小萝莉模型
		jsonPath: "assets/live2d/mikoto/mikoto.model.json",
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
