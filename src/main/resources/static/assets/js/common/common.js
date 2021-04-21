let base64 = {
    /**
     * 字符串转 base64
     *
     * @param {String} str 要加密的字符串
     * @returns {String} base64 值
     */
    encode(str) {
        return btoa(encodeURI(str)).replace(/=+$/, "");
    },
    /**
     * base64 转字符串
     *
     * @param {String} str 要解密的 base64
     * @returns {String} 字符串值
     */
    decode(str) {
        return decodeURI(atob(str));
    }
};

/**
 * 预加载进度回调
 *
 * @param {String} percent 进度百分比
 * @returns 检测到搜索引擎爬虫则不处理预加载画面。
 */
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
    // preLoading("70%");
    let ppg = new PreProgress(
        true,
        function (params) {
            $(".verso-cover").height(document.body.scrollHeight - $(".verso").offset().top);
            preLoading("100%");
        },
        function (percent) {
            preLoading(percent);
        }
    );
    ppg.addAllTask(window.wonderTask || {});
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
                scrollTop: $($.attr(this, "href")).offset().top
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
        jsonPath: "/assets/live2d/33.v2/33.v2.model.json",
        scale: 1
    },
    display: {
        position: "right",
        width: 150,
        height: 300,
        hOffset: 0,
        vOffset: -20
    },
    mobile: {
        show: true,
        scale: 0.5
    },
    react: {
        opacityDefault: 0.7,
        opacityOnHover: 0.2
    }
});
