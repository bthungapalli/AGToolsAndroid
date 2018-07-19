package com.agtools.agtools.Model;

/**
 * Created by HOME on 1/20/2018.
 */
public class CommudityResponse {
    public String commodityId;
    public String commodityName;
    public String commodityType;
    public int userId;
    public String name;
    public int usertypeId;
    public int subusertypeId;
    public int subscriptionId;
    public String usertype;
    public String subusertype;
    public String subscriptionDetails;
    public Boolean subscriptionStatus;
    public String enddate;
    public int months;
    public int packageDetailId;
    public int packageId;
    public String packageName;
    public String userPkgId;
    public String id;
    public String paypal_id;

    public CommudityResponse(String commodityId, String commodityName, String commodityType, int userId, String name, int usertypeId, int subusertypeId, int subscriptionId, String usertype, String subusertype, String subscriptionDetails, Boolean subscriptionStatus, String enddate, int months, int packageDetailId, int packageId, String packageName, String userPkgId, String id, String paypal_id) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityType = commodityType;
        this.userId = userId;
        this.name = name;
        this.usertypeId = usertypeId;
        this.subusertypeId = subusertypeId;
        this.subscriptionId = subscriptionId;
        this.usertype = usertype;
        this.subusertype = subusertype;
        this.subscriptionDetails = subscriptionDetails;
        this.subscriptionStatus = subscriptionStatus;
        this.enddate = enddate;
        this.months = months;
        this.packageDetailId = packageDetailId;
        this.packageId = packageId;
        this.packageName = packageName;
        this.userPkgId = userPkgId;
        this.id = id;
        this.paypal_id = paypal_id;
    }

    public CommudityResponse(String commodityId, String commodityName, String commodityType) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityType = commodityType;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(int usertypeId) {
        this.usertypeId = usertypeId;
    }

    public int getSubusertypeId() {
        return subusertypeId;
    }

    public void setSubusertypeId(int subusertypeId) {
        this.subusertypeId = subusertypeId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getSubusertype() {
        return subusertype;
    }

    public void setSubusertype(String subusertype) {
        this.subusertype = subusertype;
    }

    public String getSubscriptionDetails() {
        return subscriptionDetails;
    }

    public void setSubscriptionDetails(String subscriptionDetails) {
        this.subscriptionDetails = subscriptionDetails;
    }

    public Boolean getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(Boolean subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getPackageDetailId() {
        return packageDetailId;
    }

    public void setPackageDetailId(int packageDetailId) {
        this.packageDetailId = packageDetailId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUserPkgId() {
        return userPkgId;
    }

    public void setUserPkgId(String userPkgId) {
        this.userPkgId = userPkgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaypal_id() {
        return paypal_id;
    }

    public void setPaypal_id(String paypal_id) {
        this.paypal_id = paypal_id;
    }
}
