let base64 = {
    /**
     * 字符串转 base64
     *
     * @param {String} str 要加密的字符串
     * @returns {String} base64 值
     */
    encode: function (str) {
        return btoa(encodeURI(str)).replace(/=+$/, "");
    },
    /**
     * base64 转字符串
     *
     * @param {String} str 要解密的 base64
     * @returns {String} 字符串值
     */
    decode: function (str) {
        return decodeURI(atob(str));
    },
};

function throttle(func, delay, ...args) {
    var timer = null;
    return function () {
        var context = this;
        if (!timer) {
            timer = setTimeout(function () {
                func.apply(context, args);
                timer = null;
            }, delay);
        }
    };
}

$(function () {
    let page = window.location.pathname;
    let activeLink = $(`.nav-link[href="${page}"]`);
    activeLink.addClass("active");
    for (let parent = activeLink.parent(); !parent.is("body"); parent = parent.parent()) {
        if (parent.is(".collapse")) {
            parent.collapse("show");
            parent.prev().addClass("current-collapse");
        }
    }
});

$(function () {
    $(".sidebar-switcher").on("click", () => {
        if ($(".sidebar").hasClass("sidebar-hide")) {
            $(".sidebar").removeClass("sidebar-hide");
        } else {
            $(".sidebar").addClass("sidebar-hide");
        }
    });
});
