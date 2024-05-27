package com.capgemini.wsb.fitnesstracker.user.internal;

public class UserBasicInfoDto {
    private Long id;
    private String fullName;

    public UserBasicInfoDto(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
