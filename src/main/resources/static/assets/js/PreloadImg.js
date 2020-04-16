/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 13:22:15
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-04-16 17:53:42
 */

/**
 * preload imgs
 *
 * @class PreloadImg
 */
class PreloadImg {
	/**
	 * Creates an instance of PreloadImg
	 * @param {Array<String>} urls
	 * @param {boolean} [isAutoLoad=true]
	 * @param {CallableFunction} callback
	 * @memberof PreloadImg
	 */
	constructor(urls, isAutoLoad = true, callback) {
		this.imgs = [];
		this.length = urls.length;
		this.percent = 0;
		this.urls = urls;
		this.state = "ready";
		this.callback = callback;
		if (isAutoLoad) {
			this.loadImage();
		}
	}
	/**
	 * Start
	 *
	 * @returns
	 * @memberof PreloadImg
	 */
	loadImage() {
		if (this.state != "ready") {
			console.warn(`Illegal State >> ${this.state}`);
			return;
		}
		this.start = new Date().getTime();
		this.state = "loading";
		for (let i = 0; i < this.length; i++) {
			this.imgs.push(new Image());
			this.imgs[i].src = this.urls[i];
		}
		this.checkProgress();
	}
	/**
	 * check state every 300ms
	 *
	 * @memberof PreloadImg
	 */
	checkProgress() {
		this.intervalID = setInterval(() => {
			let count = 0;
			for (const img of this.imgs) {
				if (img.complete) count++;
			}
			if (count == this.length) {
				clearInterval(this.intervalID);
				this.state = "complete";
				if (this.callback) {
					this.callback(this.imgs, new Date().getTime() - this.start);
				}
			} else {
				this.percent = Math.floor((count * 100) / this.length) / 100;
			}
		}, 300);
	}
}
