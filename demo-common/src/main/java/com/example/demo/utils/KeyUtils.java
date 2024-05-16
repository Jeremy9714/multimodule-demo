package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:45
 * @Version: 1.0
 */
public class KeyUtils {
    public KeyUtils() {
    }

    private static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    private static String getMacosMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("/bin/sh -c ifconfig -a");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("ether");
                if (index >= 0) {
                    mac = line.substring(index + "ether".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    private static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    private static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ifconfig ");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("hwaddr");
                if (index != -1) {
                    mac = line.substring(index + 4).trim();
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    private static String getIpAddress() {
        String IP = "";
        InetAddress ia = null;

        try {
            ia = InetAddress.getLocalHost();
            IP = ia.getHostAddress().trim();
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
        }

        return IP;
    }

    private String getIpAddressOnLinux() {
        Enumeration allNetInterfaces = null;
        InetAddress ip = null;
        String IPAdd = "";

        try {
            for(allNetInterfaces = NetworkInterface.getNetworkInterfaces(); allNetInterfaces.hasMoreElements(); ip = null) {
                NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
                ip = (InetAddress)netInterface.getInetAddresses().nextElement();
                if (ip.isSiteLocalAddress() || ip.isLoopbackAddress()) {
                    break;
                }
            }

            IPAdd = ip.getHostAddress();
        } catch (SocketException var5) {
            var5.printStackTrace();
        }

        return IPAdd;
    }

    private String getMacosxUUID() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("system_profiler SPHardwareDataType");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var5 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("hardware uuid");
                if (index >= 0) {
                    mac = line.substring(index + "hardware uuid".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    private static String getMACAddress(InetAddress ia) throws Exception {
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < mac.length; ++i) {
            if (i != 0) {
                sb.append("-");
            }

            String s = Integer.toHexString(mac[i] & 255);
            sb.append(s.length() == 1 ? 0 + s : s);
        }

        return sb.toString().toUpperCase();
    }

    private static String getMACAddress() {
        String address = "";
        BufferedReader br = null;

        try {
            String command = "cmd.exe /c ipconfig /all";
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while(true) {
                String line;
                if ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") <= -1) {
                        continue;
                    }

                    int index = line.indexOf(":");
                    index += 2;
                    address = line.substring(index);
                }

                br.close();
                String var17 = address.trim();
                return var17;
            }
        } catch (IOException var15) {
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

        }

        return address;
    }

    private static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            boolean var4 = true;

            while((line = bufferedReader.readLine()) != null) {
                int index = line.toLowerCase().indexOf("physical address");
                if (index >= 0) {
                    index = line.indexOf(":");
                    if (index >= 0) {
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    private static String hexByte(byte b) {
        String s = "000000" + Integer.toHexString(b);
        return s.substring(s.length() - 2);
    }

    private static String getMac() {
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            Object var1 = null;

            StringBuilder sb;
            do {
                byte[] mac;
                do {
                    NetworkInterface nwi;
                    do {
                        do {
                            do {
                                do {
                                    if (!el.hasMoreElements()) {
                                        return null;
                                    }

                                    nwi = (NetworkInterface)el.nextElement();
                                } while(nwi.isLoopback());
                            } while(nwi.isVirtual());
                        } while(nwi.isPointToPoint());
                    } while(!nwi.isUp());

                    mac = nwi.getHardwareAddress();
                } while(mac == null);

                sb = new StringBuilder();

                for(int i = 0; i < mac.length; ++i) {
                    sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
                }
            } while(sb.length() <= 0);

            String macUrl = sb.toString();
            macUrl = macUrl.toLowerCase();
            macUrl = macUrl.trim();
            return macUrl;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static String getMacAddress() {
        String os = getOSName();
        if (os.startsWith("windows")) {
            try {
                InetAddress ia = InetAddress.getLocalHost();
                return getMACAddress(ia);
            } catch (Exception var2) {
                var2.printStackTrace();
                return getIpAddress();
            }
        } else if (os.startsWith("linux")) {
            return getLinuxMACAddress();
        } else {
            return os.startsWith("mac") ? getMacosMACAddress() : getMac();
        }
    }

    public static String getIp() {
        return getIpAddress();
    }

    public static String getJniMacAddress() {
        return getMac();
    }

    public static boolean checkExpire(int year, int month, int day) {
        Calendar ctime = Calendar.getInstance();
        ctime.set(year, month - 1, day);
        Date date = ctime.getTime();
        Date cdate = new Date();
        return cdate.before(date);
    }

    public static JSONObject parseKey(String key) {
        return License.parseKey(key);
    }
}
