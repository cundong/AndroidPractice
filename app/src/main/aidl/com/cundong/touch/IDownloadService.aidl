// IUpdateService.aidl
package com.cundong.touch;

// Declare any non-default types here with import statements

interface IDownloadService {

    /**
     * 下载
     * @param url
     */
     void download(String url);

     /**
     * 删除下载任务
     * @param url
     */
     void delete(String url);

     /**
     * 停止下载任务
     * @param url
     */
     void stop(String url);


     /**
     * 获取下载队列大小
     * @return
     */
     int getQueueSize();
}