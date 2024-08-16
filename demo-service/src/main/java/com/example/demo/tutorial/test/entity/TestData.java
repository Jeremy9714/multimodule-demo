package com.example.demo.tutorial.test.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/7/24 15:35
 * @Version: 1.0
 */
@Data
public class TestData<T> implements Serializable {
    private static final long serialVersionUID = -2352L;

    private String id;

    private String name;

    private int age;

    private T data;

    private TestData(String id, String name, int age, T data) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.data = data;
    }

    static class Builder<T> {
        private String id;

        private String name;

        private int age;

        private T data;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public String toString() {
            return "Builder[id=" + id + ", name=" + name + ", age=" + age + ", data=" + data + ']';
        }

        public TestData build() {
            return new TestData(id, name, age, data);
        }
    }

    public static void main(String[] args) {
        Builder<Integer> builder = new Builder<>();
        TestData data = builder.id("123").name("name").age(18).data(123).build();
        System.out.println(data);
    }

}
