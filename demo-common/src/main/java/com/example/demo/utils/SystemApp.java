package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:44
 * @Version: 1.0
 */
public final class SystemApp {
    public static final String prefix = " expire date is ";
    private static final Logger loger = LoggerFactory.getLogger(SystemApp.class);

    public SystemApp() {
    }

    public static String generateCode() {
        return getCode();
    }

    public static String getCode() {
        return License.getGovermentServicePlatformID();
    }

    private static JSONObject validateLicense(String key, String version) {
        JSONObject result = new JSONObject();
        boolean flag = false;

        try {
            String code;
            String type;
            JSONObject map1;
            if (key.indexOf(",") != -1) {
                String[] test = key.split(",");
                code = test[0];
                type = test[1];
                map1 = KeyUtils.parseKey(code);
                JSONObject map2 = KeyUtils.parseKey(type);
                if (map1 != null && map2 != null) {
                    if (map1.get("TYPE").equals(map2.get("TYPE"))) {
                        result.put("message", "please check the license.dat,the checking method is repeat");
                    } else {
                        code = getCode();
                        JSONObject obj;
                        if (map1.get("TYPE") == "1") {
                            obj = checkMacAndValidate(map1, map2, code);
                            result.put("message", obj.get("message"));
                            flag = Boolean.parseBoolean((String) obj.get("flag"));
                        } else {
                            obj = checkMacAndValidate(map2, map1, code);
                            result.put("message", obj.get("message"));
                            flag = Boolean.parseBoolean((String) obj.get("flag"));
                        }
                    }
                } else {
                    result.put("message", "please check the license.dat");
                }
            } else {
                JSONObject map = KeyUtils.parseKey(key);
                if (map != null) {
                    code = getCode();
                    type = String.valueOf(map.get("TYPE"));
                    if (type.equals("1")) {
                        map1 = checkMac(map, code);
                        result.put("message", map1.get("message"));
                        flag = Boolean.parseBoolean((String) map1.get("flag"));
                    } else if (type.equals("0")) {
                        map1 = checkValidate(map, code);
                        result.put("message", map1.get("message"));
                        flag = Boolean.parseBoolean((String) map1.get("flag"));
                    }
                }
            }
        } catch (RuntimeException var11) {
            throw var11;
        } catch (Exception var12) {
            var12.printStackTrace();
            flag = false;
            result.put("message", "something is wrong...");
        }

        if (flag) {
            result.put("code", "1");
        } else {
            result.put("code", "0");
        }

        result.put("flag", flag);
        return result;
    }

    public static void initApplication(String profile) {
        checkLicence(profile);
    }

    private static String getTewenty() {
        StringBuffer line = new StringBuffer();

        for (int i = 0; i < 20; ++i) {
            line.append("-");
        }

        return line.toString();
    }

    private static String getSixtyTwo() {
        StringBuffer line = new StringBuffer();

        for (int i = 0; i < 62; ++i) {
            line.append("-");
        }

        return line.toString();
    }

    private static void checkLicence(String profile) {
        BufferedReader reader = null;
        Object inputStream = null;

        try {
            String key = null;
            String pathGetClass = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            int domainIndex;
            if (pathGetClass.indexOf("webapps") == -1) {
                if (pathGetClass.indexOf("/domains") != -1) {
                    domainIndex = pathGetClass.indexOf("/domains");
                    pathGetClass = pathGetClass.substring(0, domainIndex);
                } else if (pathGetClass.indexOf(".jar") != -1) {
                    pathGetClass = pathGetClass.substring(0, pathGetClass.indexOf(".jar"));
                    pathGetClass = pathGetClass.substring(0, pathGetClass.lastIndexOf("/"));
                    pathGetClass = pathGetClass.substring(5, pathGetClass.length());
                }
            } else {
                for (domainIndex = 0; domainIndex < 6; ++domainIndex) {
                    pathGetClass = pathGetClass.substring(0, pathGetClass.lastIndexOf("/"));
                }
            }

            String filepath = pathGetClass + "/" + profile + "-license.dat";

            try {
                if (loger.isDebugEnabled()) {
                    loger.debug("FileInputStream读取配置文件" + profile + "-license.dat===" + filepath);
                }

                inputStream = new FileInputStream(filepath);
            } catch (Exception var25) {
                if (loger.isDebugEnabled()) {
                    loger.debug("FileInputStream读取配置文件" + profile + "-license.dat失败，读取路径：" + filepath);
                }
            }

            if (inputStream == null) {
                if (loger.isDebugEnabled()) {
                    loger.debug("getContextClassLoader读取配置文件classpath:" + profile + "-license.dat");
                }

                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(profile + "-license.dat");
            }

            if (inputStream == null) {
                if (loger.isDebugEnabled()) {
                    loger.debug("ResourceRenderer读取配置文件classpath:" + profile + "-license.dat");
                }

                inputStream = ResourceRenderer.resourceLoader("classpath:" + profile + "-license.dat");
            }

            reader = new BufferedReader(new InputStreamReader((InputStream) inputStream, "UTF-8"));
            if (loger.isDebugEnabled()) {
                loger.debug("是否读取到配置文件license.dat" + reader.lines() + "config files");
            }

            for (String line = ""; (line = reader.readLine()) != null; key = line) {
            }

            JSONObject json = validateLicense(key, "3.0");
            String code = json.getString("code");
            if (!"1".equals(code)) {
                if (loger.isDebugEnabled()) {
                    loger.debug(License.getCompany() + getTewenty() + " Authorize Infomation " + getTewenty());
                    loger.debug(License.getCompany() + json.getString("message"));
                    loger.debug(License.getCompany() + getSixtyTwo());
                }

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException var24) {
                    var24.printStackTrace();
                }

                System.exit(0);
                return;
            }

            if (loger.isDebugEnabled()) {
                loger.debug(License.getCompany() + getTewenty() + " Authorize Infomation " + getTewenty());
                loger.debug(License.getCompany() + json.getString("message"));
                loger.debug(License.getCompany() + getSixtyTwo());
            }
        } catch (RuntimeException var26) {
            throw var26;
        } catch (Exception var27) {
            if (loger.isDebugEnabled()) {
                loger.debug(var27.getMessage());
            }

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException var23) {
                var23.printStackTrace();
            }

            System.exit(0);
            return;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }

                if (inputStream != null) {
                    ((InputStream) inputStream).close();
                    inputStream = null;
                }
            } catch (IOException var22) {
            }

        }

    }

    private static JSONObject checkMac(JSONObject mac_map, String code) {
        JSONObject result = new JSONObject();
        String cpu = String.valueOf(mac_map.get("KEY"));
        boolean cpu_v = cpu.indexOf(code) != -1;
        String flag = "false";
        if (loger.isDebugEnabled()) {
            loger.debug("cpu is " + cpu);
            loger.debug("code is " + code);
            loger.debug("cpu_v is " + cpu_v);
        }

        if (cpu_v) {
            flag = "true";
            result.put("message", "the mac address of " + License.getLicenseName() + ".dat is match,thanks for use!");
        } else {
            result.put("message", "the mac address of " + License.getLicenseName() + ".dat is not match,please try get another " + License.getLicense() + "...");
        }

        result.put("flag", flag);
        return result;
    }

    private static JSONObject checkValidate(JSONObject date_map, String code) {
        JSONObject result = new JSONObject();
        String flag = "false";
        int year = Integer.parseInt((String) date_map.get("YEAR"));
        int month = Integer.parseInt((String) date_map.get("MONTH"));
        int day = Integer.parseInt((String) date_map.get("DAY"));
        boolean date_v = KeyUtils.checkExpire(year, month, day);
        if (date_v) {
            flag = "true";
            result.put("message", License.getLicenseName() + " expire date is " + year + "-" + month + "-" + day + ", thanks for use!");
        } else {
            result.put("message", License.getLicenseName() + " expire date is " + year + "-" + month + "-" + day + ",Key: " + code + " is out of date...");
        }

        result.put("flag", flag);
        return result;
    }

    private static JSONObject checkMacAndValidate(JSONObject macmap, JSONObject datemap, String code) {
        String flag = "false";
        JSONObject result = new JSONObject();
        JSONObject macResult = checkMac(macmap, code);
        JSONObject validateResult = checkValidate(datemap, code);
        boolean macFlag = Boolean.parseBoolean(macResult.get("flag").toString());
        boolean validate = Boolean.parseBoolean(validateResult.get("flag").toString());
        if (macFlag && validate) {
            result.put("message", "the MAC ADDRESS and Expire date both match,thank you for use! ");
            flag = "true";
        } else if (macFlag) {
            result.put("message", validateResult.get("message"));
        } else {
            result.put("message", macResult.get("message"));
        }

        result.put("flag", flag);
        return result;
    }
}
