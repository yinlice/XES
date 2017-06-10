package Wxzt.servlet.Common.system;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-6-23.
 */
public class showSystem {
    private static final int CPUTIME = 500;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    //获取内存使用率
    public static Object getMemery() {
        systemMemeryBean m = new systemMemeryBean();
        try {
            Sigar sigar = new Sigar();
            Mem mem = sigar.getMem();
            int totalMemery = (int) (mem.getTotal() / (1024 * 1024));
            int userMemery = (int) (mem.getUsed() / (1024 * 1024));
            m.setTotalMemery(totalMemery);
            m.setUseMemery(userMemery);
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return m;
    }

    //获取文件系统使用率
    public static List<Object> getDisk() {
        // 操作系统
        List<Object> list = new ArrayList<Object>();
        for (char c = 'A'; c <= 'Z'; c++) {
            String dirName = c + ":/";
            File win = new File(dirName);
            if (win.exists()) {
                systemFileBean f = new systemFileBean();
                f.setFileName(String.valueOf(c));
                long totalSpace = (long) win.getTotalSpace()/(1024*1024);
                long freeSpace = (long) win.getFreeSpace()/(1024*1024);
                f.setTotalSpace((int) totalSpace);
                f.setFreeSpace((int) freeSpace);
                f.setUserSpace((int) (totalSpace - freeSpace));
                list.add(f);
            }
        }
        return list;
    }

    //获得cpu使用率
    public static String getCpuRatioForWindows() {
        try {
            String procCmd =
                    System.getenv("windir")
                            + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date d = new Date();
            System.out.println("CPU使用率1:"+formatter.format(d));

            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Date d0 = new Date();
            System.out.println("CPU使用率1:"+formatter.format(d0));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            Date d1 = new Date();
            System.out.println("CPU使用率1:"+formatter.format(d1));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return "CPU使用率:" + Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
            } else {
                return "CPU使用率:" + 0 + "%";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "CPU使用率:" + 0 + "%";
        }
    }

    //读取cpu相关信息
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }
                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                proc.getInputStream().close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     * @param src 要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx 截止坐标（包括该坐标）
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++)
        {
            tgt += (char)b[i];
        }
        return tgt;
    }
    public static void main(String[] args){
        System.out.println(getMemery().toString());
        System.out.println(getDisk());
        System.out.println(getCpuRatioForWindows());
    }
}
