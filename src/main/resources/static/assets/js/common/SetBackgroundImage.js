/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 16:45:38
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-05 21:01:49
 */
class SetBackgroundImage {
	/**
	 * Creates an instance of SetBackgroundImage.
	 * @param {Array<Image>} imgs
	 * @param {Element} back
	 * @param {Element} front
	 * @param {number} [time=5 * 1000]
	 * @param {boolean} [autoChange=true]
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
	nextBgi(resetTimer = false) {
		if (this.back) {
			this.back.style.backgroundImage = `url(${this.imgs[this.index].url})`;
		}
		if (this.front) {
			this.front.style.backgroundImage = `url(${this.imgs[this.index].blur})`;
		}
		this.index = this.index++ % this.imgs.length;
		if (resetTimer) {
			clearInterval(this.timerID);
			this.timerID = setInterval(this.nextBgi.bind(this), this.time);
			this.status = "run";
		}
	}
	stop() {
		clearInterval(this.timerID);
		this.status = "stop";
	}
}
