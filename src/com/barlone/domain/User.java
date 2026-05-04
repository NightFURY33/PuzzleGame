package com.barlone.domain;

import java.util.Objects;

public class User {
    // 修复命名规范与拼写错误，提升 Analysability
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter 和 Setter 保持规范
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * 重写 equals 方法，确保登录校验逻辑的 Functional Correctness
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 引用相同直接返回 true (Performance Efficiency)
        if (o == null || getClass() != o.getClass()) return false; // 类型检查 (Robustness)
        User user = (User) o;
        // 使用 Objects.equals 防止 null 导致的 NullPointerException (Reliability)
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    /**
     * 必须重写 hashCode，确保数据一致性
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}