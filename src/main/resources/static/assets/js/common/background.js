/*
 * @Author: Wonder2019
 * @Date: 2020-07-30 16:31:44
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-08 14:54:06
 */

// Reset
let changeTime = 5 * 1000;

// Function
function loadBackground(progressId, back, front) {
	let urls = [];
	let backObj = $(back)[0];
	let frontObj = $(front)[0];
	window.bgList.forEach((item) => {
		if (backObj) {
			urls.push(item.path);
		}
		if (frontObj) {
			urls.push(item.bz);
		}
	});
	new PreloadImg(urls, true, function (imgs) {
		new SetBackgroundImage(window.bgList,backObj, frontObj, changeTime, false);
		window[progressId].count++;
	});
}