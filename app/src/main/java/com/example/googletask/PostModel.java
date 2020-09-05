package com.example.googletask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel {
    @SerializedName("Email Address")
    @Expose
    private String emailAddress;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Last Name")
    @Expose
    private String lastName;
    @SerializedName("Link to Project")
    @Expose
    private String linkToProject;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLinkToProject() {
        return linkToProject;
    }

    public void setLinkToProject(String linkToProject) {
        this.linkToProject = linkToProject;
    }
}
