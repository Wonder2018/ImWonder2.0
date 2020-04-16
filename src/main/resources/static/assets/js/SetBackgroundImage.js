/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 16:45:38 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-04-16 18:02:07
 */
class SetBackgroundImage {
	/**
     * Creates an instance of SetBackgroundImage.
     * @param {Array<Image>} imgs
     * @param {Element} back
     * @param {Element} front
     * @param {number} blurRound
     * @param {number} [time=5 * 1000]
     * @param {boolean} [autoChange=true]
     * @memberof SetBackgroundImage
     */
    constructor(imgs, back, front, blurRound, time = 5 * 1000, autoChange = true) {
		this.back = back;
		this.front = front;
		this.blurRound = blurRound;
		this.time = time;
		this.count = imgs.length;
		this.index = 0;
		this.autoChange = autoChange;
        this.imgs = [];
        this.status = "stop"
		for (const img of imgs) {
			let tmp = {
				origin: img,
				url: img.src,
			};
			tmp.blurDate = fullGaussianBlur(img, this.blurRound);
			this.imgs.push(tmp);
		}
		this.timerID = null;
		this.nextBgi(true);
	}
	nextBgi(resetTimer = false) {
		this.back.style.backgroundImage = `url(${this.imgs[this.index].url})`;
		this.front.style.backgroundImage = `url(${this.imgs[this.index].blurDate})`;
        this.index = this.index++%this.imgs.length;
		if (resetTimer) {
			clearInterval(this.timerID);
            this.timerID = setInterval(this.nextBgi.bind(this), this.time);
            this.status = "run"
		}
	}
	stop() {
        clearInterval(this.timerID);
        this.status = "stop"
	}
}
