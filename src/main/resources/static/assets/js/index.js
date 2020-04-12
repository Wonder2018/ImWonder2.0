let blurRound = 20;
let img = new Image();
let backImg;
let frountImg;
function setBackground(url, raw, blur) {
    backImg = raw;
    frountImg = blur;
    img.src = url;
}

img.onload = function () {
    let blurDate = fullGaussianBlur(img, blurRound);
    backImg.style.backgroundImage = `url(${img.src})`;
    frountImg.style.backgroundImage = `url(${blurDate})`;
};

$(document).ready(function () {
    setBackground("assets/img/bg/img3.jpg", document.body, document.querySelector(".cover-box > .cover-paint"));
});

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
        jsonPath: "https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json",
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
