package com.example.demo.scope;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:34
 * @Version: 1.0
 */
public class DataScope {
    private String scopeName = "QHDM";
    private List<String> deptIds;
    private List<String> regionIds;

    public List<String> getRegionIds() {
        return this.regionIds;
    }

    public void setRegionIds(List<String> regionIds) {
        this.regionIds = regionIds;
    }

    public DataScope() {
    }

    public DataScope(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    public DataScope(String scopeName, List<String> deptIds) {
        this.scopeName = scopeName;
        this.deptIds = deptIds;
    }

    public List<String> getDeptIds() {
        return this.deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    public String getScopeName() {
        return this.scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
}
