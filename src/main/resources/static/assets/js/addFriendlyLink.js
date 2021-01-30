/*
 * @Author: Wonder2019
 * @Date: 2020-08-06 10:44:56
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-30 20:24:41
 */

/**
 * 通过预加载初始化标签云的回调
 *
 * @param {String} progressId 预加载器 ID
 */
function initTagList(progressId) {
	$(".tag-cloud-board").jQCloud(window.tagList, {
		removeOverflowing: true,
		shape: "rectangular",
	});
	window[progressId].count++;
}

if (!window.wonderTask) {
	window.wonderTask = [];
}

window.wonderTask.push({
	fun: loadBackground,
	params: [null, "body"],
});

window.wonderTask.push({
	fun: initTagList,
	params: [],
});
