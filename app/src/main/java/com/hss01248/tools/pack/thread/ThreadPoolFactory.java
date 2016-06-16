package com.hss01248.tools.pack.thread;



/**
 * @author  Administrator
 * @time 	2015-7-16 上午9:42:11
 * @des	TODO
 *
 * @version $Rev: 14 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 09:46:07 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public class ThreadPoolFactory {
	static ThreadPoolProxy	mNormalPool;
	static ThreadPoolProxy	mDownLoadPool;

	/**得到一个普通的线程池*/
	public static ThreadPoolProxy getNormalPool() {
		if (mNormalPool == null) {
			synchronized (ThreadPoolProxy.class) {
				if (mNormalPool == null) {
					mNormalPool = new ThreadPoolProxy(5, 5, 3000);
				}
			}
		}
		return mNormalPool;
	}
	/**得到一个下载的线程池*/
	public static ThreadPoolProxy getDownLoadPool() {
		if (mDownLoadPool == null) {
			synchronized (ThreadPoolProxy.class) {
				if (mDownLoadPool == null) {
					mDownLoadPool = new ThreadPoolProxy(3, 3, 3000);
				}
			}
		}
		return mDownLoadPool;
	}
}
