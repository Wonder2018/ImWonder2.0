/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 16:45:38
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-28 11:38:42
 */

/**
 * 背景轮播器
 *
 * @class SetBackgroundImage
 */
class SetBackgroundImage {
	/**
	 * Creates an instance of SetBackgroundImage.
	 * @param {Array<Image>} imgs 轮播图片列表
	 * @param {Element} back 后部图片存放标签
	 * @param {Element} front 前部图片存放标签
	 * @param {number} [time=5 * 1000] 轮播间隔
	 * @param {boolean} [autoChange=true] 自动轮播
	 * @memberof SetBackgroundImage
	 */
	constructor(imgs, back, front, time = 5 * 1000, autoChange = true) {
		this.back = back;
		this.front = front;
		this.time = time;
		this.count = imgs.length;
		this.index = 0;
		this.autoChange = autoChange;
		this.imgs = [];
		this.status = "stop";
		for (let img of imgs) {
			let tmp = {
				url: img.path,
				blur: img.bz,
			};
			this.imgs.push(tmp);
		}
		this.timerID = null;
		this.nextBgi(true);
	}
	/**
	 * 立刻切换到下一张图片
	 *
	 * @param {boolean} [resetTimer=false] 刷新计时器
	 * @memberof SetBackgroundImage
	 */
	nextBgi(resetTimer = false) {
		if (this.back) {
			this.back.style.backgroundImage = `url(${this.imgs[this.index].url})`;
		}
		if (this.front) {
			this.front.style.backgroundImage = `url(${this.imgs[this.index].blur})`;
		}
		this.index = ++this.index % this.imgs.length;
		if (resetTimer) {
			clearInterval(this.timerID);
			this.timerID = setInterval(this.nextBgi.bind(this), this.time);
			this.status = "run";
		}
	}
	/**
	 * 停止轮播
	 *
	 * @memberof SetBackgroundImage
	 */
	stop() {
		clearInterval(this.timerID);
		this.status = "stop";
	}
}
