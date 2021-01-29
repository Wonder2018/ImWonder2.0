/*
 * @Author: Wonder2019
 * @Date: 2020-07-30 16:31:44
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-21 13:04:17
 */

// Reset
let changeTime = 10 * 1000;

/**
 * 预加载背景图片轮播的回调
 *
 * @param {String} progressId 预加载器 ID
 * @param {String} back 后部图片存放标签的选择器
 * @param {String} front 前部图片存放标签的选择器
 */
function loadBackground(progressId, back, front) {
	let urls = [];
	let backObj = $(back)[0];
	let frontObj = $(front)[0];
	if (backObj || frontObj) {
		// 统计要加载的图片
		window.bgList.forEach((item) => {
			if (backObj) { // 后部清晰图片
				urls.push(item.path);
			}
			if (frontObj) {// 前部模糊图片
				urls.push(item.bz);
			}
		});
	}
	// 预加载图片
	new PreloadImg(urls, true, function (imgs) {
		new SetBackgroundImage(window.bgList, backObj, frontObj, changeTime, false);
		window[progressId].count++;
	});
}
