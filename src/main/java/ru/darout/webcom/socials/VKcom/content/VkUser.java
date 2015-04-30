package ru.darout.webcom.socials.VKcom.content;

import org.apache.commons.lang3.StringUtils;

public class VkUser {

    private int vkUserId;
    private String firstName;
    private String lastName;
    private String photo;
    private String bdate;

    public int getVkUserId() {
        return vkUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBdate() {
        return bdate;
    }

    public boolean withBirthday() {
        return StringUtils.isNotBlank(bdate);
    }

    @Override
    public String toString() {
        return "VkUser [" + firstName + " " + lastName + ", id=" + vkUserId + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof VkUser) {
            VkUser another = (VkUser) obj;
            return vkUserId == another.vkUserId;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return vkUserId;
    }

}

