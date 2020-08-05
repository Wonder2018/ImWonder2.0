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
	ppg.addAllTask(window.wonderTask)
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
