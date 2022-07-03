package com.example.madadagaronline.Models;

public class Job_Model {
    public String job_title;
    public String job_rate;
    public String job_location;
    public String job_description;
    public String job_id;
    public String client_id;
    public String mazdor_id;
    public String job_status;
    public String time;
    public String catogry;

    public Job_Model() {
    }

    public Job_Model(String job_title, String job_rate, String job_location, String job_description, String job_id, String client_id, String mazdor_id, String job_status, String time, String catogry) {
        this.job_title = job_title;
        this.job_rate = job_rate;
        this.job_location = job_location;
        this.job_description = job_description;
        this.job_id = job_id;
        this.client_id = client_id;
        this.mazdor_id = mazdor_id;
        this.job_status = job_status;
        this.time = time;
        this.catogry = catogry;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_rate() {
        return job_rate;
    }

    public void setJob_rate(String job_rate) {
        this.job_rate = job_rate;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getMazdor_id() {
        return mazdor_id;
    }

    public void setMazdor_id(String mazdor_id) {
        this.mazdor_id = mazdor_id;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCatogry() {
        return catogry;
    }

    public void setCatogry(String catogry) {
        this.catogry = catogry;
    }
}
