package com.cundong.practice.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cundong.practice.app.AppEnv;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cundong on 2015/7/15.
 * 文件操作相关工具
 */
public class FileUtil {

    private static final boolean DEBUG = AppEnv.DEBUG;

    private static final String TAG = DEBUG ? "FileUtil" : FileUtil.class.getSimpleName();

    /**
     * 确保文件能够被创建且为新文件
     *
     * @param file
     * @return
     */
    public static boolean ensureNewFile(File file) {
        File parent = file.getParentFile();
        if (!parent.isDirectory()) {
            parent.mkdirs();
        }
        boolean isNew = true;
        if (file.isFile()) {
            isNew = file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, "", e);
            }
            return false;
        }
        return true;
    }

    public static boolean ensureNewFile(String fileName) {
        File file = new File(fileName);
        return ensureNewFile(file);
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String getMd5ByString(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = in.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            value = bytes2Hex(digester.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length * 2];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }

        return new String(res);
    }

    public static boolean checkMd5(File file, String md5) {
        if (TextUtils.isEmpty(md5)) {
            throw new RuntimeException("md5 cannot be empty");
        }
        String fileMd5 = getMd5ByFile(file);

        if (DEBUG) {
            Log.d(TAG, String.format("file.md5=%s, parsed md5=%s", fileMd5, md5));
        }
        if (md5.equals(fileMd5)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkFileAvailable(File file) {
        return null != file && file.isFile() && 0 < file.length();
    }

    public static boolean checkFileAvailable(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.isFile() && 0 < file.length();
    }

    public static void delete(String path) {

        if (TextUtils.isEmpty(path)) {
            return;
        }

        File f = new File(path);

        if (f == null || !f.exists()) {
            return;
        }

        if (f.isDirectory()) {//如果是目录，先递归删除
            File[] listFiles = null;

            //不知为何 f.listFiles() 会抛出NullPointerException
            try {
                listFiles = f.listFiles();
            } catch (NullPointerException e) {

            }

            if (listFiles != null && listFiles.length > 0) {
                String[] list = f.list();
                if (null != list && list.length > 0) {
                    for (int i = 0; i < list.length; i++) {
                        delete(path + "//" + list[i]);//先删除目录下的文件
                    }
                }
            }
        }
        f.delete();
    }

    public static void deleteIn(String path) {

        if (TextUtils.isEmpty(path)) {
            return;
        }

        File f = new File(path);

        if (f == null || !f.isDirectory()) {
            return;
        }

        File[] listFiles = null;

        //不知为何 f.listFiles() 会抛出NullPointerException
        try {
            listFiles = f.listFiles();
        } catch (NullPointerException e) {

        }

        if (listFiles != null && listFiles.length > 0) {
            String[] list = f.list();
            if (null != list && list.length > 0) {
                for (int i = 0; i < list.length; i++) {
                    delete(path + "//" + list[i]);//先删除目录下的文件
                }
            }
        }
    }

    public static void delete(File f) {

        if (f == null || !f.exists()) {
            return;
        }

        String path = f.getAbsolutePath();
        if (f.isDirectory()) {      //如果是目录，先递归删除
            File[] listFiles = null;

            //不知为何 f.listFiles() 会抛出NullPointerException
            try {
                listFiles = f.listFiles();
            } catch (NullPointerException e) {

            }

            if (listFiles != null && listFiles.length > 0) {
                String[] list = f.list();
                if (null != list && list.length > 0) {
                    for (int i = 0; i < list.length; i++) {
                        delete(path + "//" + list[i]);//先删除目录下的文件
                    }
                }
            }
        }

        f.delete();
    }

    public static boolean copyFromAssets(Context context, String assetsFile, File outputFile) {
        if (outputFile.isFile()) {
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, "", e);
            }
            return false;
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getAssets().open(assetsFile);
            os = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int len;
            while (-1 != (len = is.read(buffer))) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            if (DEBUG) {
                Log.e(TAG, "", e);
            }
            return false;
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    public static boolean copyFromAssets(Context context, String assetsFile, String outputFile) {
        File tmp = new File(outputFile);
        return copyFromAssets(context, assetsFile, tmp);
    }

    public static boolean verifyParentDir(File file) {
        File parent = file.getParentFile();
        if (!parent.isDirectory())
            return parent.mkdirs();
        return true;
    }

    public static void CopyStream(InputStream source, OutputStream target) throws IOException {
        // Copy
        final int BUF_SIZE = 4096;
        byte[] buffer = new byte[BUF_SIZE];
        int length = 0;
        while ((length = source.read(buffer)) > 0) {
            target.write(buffer, 0, length);
        }
        target.flush();
    }

    /**
     * 根据输入的文件路径，得到文件名字，包含扩展名
     * <p/>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 根据输入的文件路径，得到文件名字，不包含扩展名
     * <p/>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * 根据输入的文件路径，得到文件所在的文件夹名称
     * <p/>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 根据输入的文件路径，得到扩展名
     * <p/>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * write file
     * <p/>
     * <p/>
     * writeFile("/sdcard/test/test/test.txt", "something", true)
     * <p/>
     *
     * @param filePath
     * @param content
     * @param append
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        File file = new File(filePath);
        try {
            File parent = file.getParentFile();
            if (!parent.isDirectory()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    if (DEBUG) {
                        Log.e(TAG, "", e);
                    }
                }
            }

            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 从本地读取json
     *
     * @param filePath 文件路径
     * @return json
     */
    public static JSONObject readLocalJson(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        String resultString = "";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject object = null;
        try {
            object = new JSONObject(resultString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static boolean copy(String src, String dst) throws IOException {
        File srcFile = new File(src);
        if (!srcFile.isFile()) {
            return false;
        }
        File dstFile = new File(dst);
        if (dstFile.isFile()) {
            dstFile.delete();
        }
        dstFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(dstFile);
        FileInputStream fis = new FileInputStream(srcFile);
        byte[] buffer = new byte[1024];
        int len;
        while (0 < (len = fis.read(buffer))) {
            fos.write(buffer, 0, len);
        }
        return true;
    }

    public static byte[] toByte(File file) throws IOException {
        byte[] gifBuffer = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(gifBuffer);
        return gifBuffer;
    }
}