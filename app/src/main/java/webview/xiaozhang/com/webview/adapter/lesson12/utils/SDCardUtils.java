package webview.xiaozhang.com.webview.adapter.lesson12.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class SDCardUtils {


    /**
     * 判断sdcard是否挂载
     * @return
     */
    public static boolean isSDCardMounted() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取sdcard绝对物理路径
     * @return
     */
    public static String getSDCardPath() {

        if (isSDCardMounted()) {

            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {

            return null;
        }

    }


    /**
     * 获取sdcard的全部的空间大小。返回MB字节
     * @return
     */
    public static long getSDCardSize() {

        if (isSDCardMounted()) {

            StatFs fs = new StatFs(getSDCardPath());

            long size = fs.getBlockSize();

            long count = fs.getBlockCount();

            return size * count / 1024 / 1024;

        }

        return 0;

    }


    /**
     * 获取sdcard的剩余的可用空间的大小。返回MB字节
     * @return
     */
    public static long getSDCardFreeSize() {

        if (isSDCardMounted()) {

            StatFs fs = new StatFs(getSDCardPath());

            long size = fs.getBlockSize();

            long count = fs.getAvailableBlocks();

            return size * count / 1024 / 1024;

        }

        return 0;

    }


    /**
     * 删除指定路径的文件
     */
    public static void deleteAllFiles(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            int len = 0;
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    deleteAllFiles(files[i].getPath());

                }
            }
        } else {
            file.deleteOnExit();
        }

    }


    /**
     * 删除文件夹下的所有内容
     *
     * @param cacheFile
     */
    public void deleteFiles(File cacheFile) {
        File flist[] = cacheFile.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    deleteFiles(flist[i]);
                } else {
                    flist[i].delete();
                }
            }
        }
    }


    /**
     *
     * 将文件（byte[]）保存进sdcard指定的路径下
     * @param data 保存文件的字节数组
     * @param dir  文件保存到的文件夹  ，无需添加sd卡目录 内部已封装
     * @param filename 保存的文件名
     * @return
     */
    public static boolean saveFileToSDCard(byte[] data, String dir, String filename) {

        BufferedOutputStream bos = null;

        if (isSDCardMounted()) {


            File file = new File(getSDCardPath() , dir);

            if (!file.exists()) {
                boolean flags = file.mkdirs();
            }

            try {

                bos = new BufferedOutputStream(new FileOutputStream(new File(file, filename)));

                bos.write(data, 0, data.length);

                bos.flush();

                return true;

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                try {
                    if (bos != null) {
                        bos.close();
                    }

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }

        }

        return false;

    }


    /**
     * 已知文件的路径，从sdcard中获取到该文件，返回byte[]
     * @param dir  文件保存到的文件夹  ，无需添加sd卡目录 内部已封装
     * @param filename 文件名
     * @return
     */
    public static byte[] loadFileFromSDCard(String dir , String filename) {

        BufferedInputStream bis = null;

        ByteArrayOutputStream baos = null;

        if (isSDCardMounted()) {

            File file = new File(new File(getSDCardPath(),dir),filename);

            if (file.exists()) {

                try {

                    baos = new ByteArrayOutputStream();

                    bis = new BufferedInputStream(new FileInputStream(file));

                    byte[] buffer = new byte[1024 * 8];

                    int c = 0;

                    while ((c = bis.read(buffer)) != -1) {

                        baos.write(buffer, 0, c);

                        baos.flush();

                    }

                    return baos.toByteArray();

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    try {

                        if (bis != null) {

                            bis.close();

                            baos.close();

                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            }

        }

        return null;

    }


}
