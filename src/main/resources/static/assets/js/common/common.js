function preLoading(percent) {
	if (window.isRobot) return;
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
	ppg.addAllTask(window.wonderTask);
	ppg.start();

	// if (navigator.userAgent.match(/(Trident\/7\.)|(Edge)/)) {
	// 	document.body.addEventListener &&
	// 		document.body.addEventListener("mousewheel", function () {
	// 			event.preventDefault();
	// 			var wd = event.wheelDelta;
	// 			var csp = window.pageYOffset;
	// 			window.scrollTo(0, csp - wd);
	// 		});
	// 	document.body.style.overflow = "hidden";
	// }
	// Init Title Animation

	//Init anchor link
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

L2Dwidget.init({
	model: {
		//jsonpath控制显示那个小萝莉模型，下面这个就是我觉得最可爱的小萝莉模型
		jsonPath: "/assets/live2d/33.v2/33.v2.model.json",
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